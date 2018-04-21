package com.ucu.edu.user.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
//import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.ucu.edu.configuration.BulkSMSProperties;
import com.ucu.edu.email.EmailService;
import com.ucu.edu.security.util.SecurityUtil;
import com.ucu.edu.sms.SMSManager;
import com.ucu.edu.user.dto.ForgotForm;
import com.ucu.edu.user.dto.RegistrationForm;
import com.ucu.edu.user.model.PIN;
import com.ucu.edu.user.model.User;
import com.ucu.edu.user.repository.UserRepository;
import com.ucu.edu.user.service.DuplicateEmailException;
import com.ucu.edu.user.service.UserService;
import com.ucu.edu.security.repository.PINRepository;


@RepositoryRestController
@RequestMapping("/")
public class UserRestController {
	
	@Resource
	private Environment env;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PINRepository pinRepo;
	
	@Autowired
	private BulkSMSProperties bulkSMSProperties;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);

	@Autowired
	@Qualifier("userService")
	private UserService service;
	
		
	@RequestMapping(value = "/user/create", method = RequestMethod.POST)
	@ResponseBody
	PersistentEntityResource createUserAccount(@Valid @ModelAttribute("user") RegistrationForm userAccountData,
			BindingResult result, WebRequest request, HttpServletRequest req, HttpServletResponse response, PersistentEntityResourceAssembler resourceAssembler)
			throws DuplicateEmailException, IOException {

		User registered = createUserAccount(userAccountData, result);
		LOGGER.debug("createUserAccount for userId: {}", registered.getId());
		
		return resourceAssembler.toFullResource(registered);

	}
	
	@RequestMapping(value = "/user/forgotpass", method = RequestMethod.POST)
	@ResponseBody
	ResponseEntity<String> forgotPass(ForgotForm forgotForm) {
		
		Boolean isItAllowedToSendSMS = Boolean.parseBoolean(env.getRequiredProperty("app.forgotpass.sendsms"));
		Boolean isItAllowedToSendEmail = Boolean.parseBoolean(env.getRequiredProperty("app.forgotpass.sendemail"));
		
		String email = forgotForm.getEmail();
		LOGGER.debug("Attemp to find user by email: {}", email);
		
		User user = userRepo.findByEmail(email);
		

        if (email != "") {
        	
    		try {
    			
    			if (email == null || user == null) {
    				throw new ResourceNotFoundException();
    			}
  			
       			LOGGER.debug("Find user with id: {}", user.getId());

       			LOGGER.debug("attempt to delete old pin from user: {}", user.getId());
       			user.setPin(null);
       			
    			String pin = SecurityUtil.generateRandom(6);
    			LOGGER.debug("Generate new PIN: {}", pin);
    			
    			PIN.Builder pinBuilder = PIN.getBuilder().pin(pin).user(user);
    			PIN newPIN = pinBuilder.build();
    			
    			pinRepo.saveAndFlush(newPIN);
    			
       			LOGGER.debug("Create new pinWrapper with id: {}", newPIN.getId());
       			
       			
       			
       			String phone = null;
       			
       			if (user!= null) {
       				phone = user.getPhone();
       				user.setPin(newPIN);
           			userRepo.saveAndFlush(user);
       			}
       			
       			LOGGER.debug("phone number: {}", phone);
       			
   				String msg = "Your Password reset code: " + pin;
      			
       			// Send Email       			      			
       			if (isItAllowedToSendEmail && email != null) {
       				LOGGER.debug("Attemp to send email to : {}", email);
       				emailService.sendSimpleMessage(email, "Distr sys: reset code", msg);
       			}   				

   				// Send SMS       			
       			if (isItAllowedToSendSMS && phone != null) {

       				SMSManager smsManager = new SMSManager(bulkSMSProperties);
       				LOGGER.debug("Attemp to send SMS to phone: {}", phone);
       				smsManager.sendSMS(phone, msg);
       			} else {
       				LOGGER.debug("Canceled sending SMS to phone: {}", phone);
       			}
       			
    			
    			return ResponseEntity.status(HttpStatus.ACCEPTED).body("SMS was successfully sent");
    			
    		}
    		catch (ResourceNotFoundException e) {
    			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    		}
    		catch (IllegalArgumentException e) {
    			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    		}
    		
        }	
        
   		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);


	}
	
	private User createUserAccount(RegistrationForm userAccountData, BindingResult result) throws DuplicateEmailException {
		User registered = null;

		try {
			LOGGER.debug("Attempt to register new user: {}",userAccountData.getEmail() );
			
			registered = service.registerNewUserAccount(userAccountData);
		} catch (DuplicateEmailException ex) {
			LOGGER.debug("The email address: {} is already in use.",userAccountData.getEmail());
			throw new DuplicateEmailException(ex.getMessage());
		}

		return registered;
	}

	
}
