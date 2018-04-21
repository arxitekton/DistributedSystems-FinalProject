package com.ucu.edu.user.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.ucu.edu.security.util.SecurityUtil;
import com.ucu.edu.user.dto.ForgotForm;
import com.ucu.edu.user.dto.PasswordForm;
import com.ucu.edu.user.dto.RegistrationForm;
import com.ucu.edu.user.model.PIN;
import com.ucu.edu.user.model.SocialMediaService;
import com.ucu.edu.user.model.User;
import com.ucu.edu.user.repository.UserRepository;
import com.ucu.edu.user.service.DuplicateEmailException;
import com.ucu.edu.user.service.UserService;
import com.ucu.edu.security.repository.PINRepository;

@Controller
public class UserController {

	@Resource
	private Environment env;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	protected static final String ERROR_CODE_EMAIL_EXIST = "NotExist.user.email";
	protected static final String MODEL_NAME_REGISTRATION_DTO = "user";
	protected static final String VIEW_NAME_REGISTRATION_PAGE = "user/registrationForm";

	@Autowired
	@Qualifier("userService")
	private UserService service;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	PersistentTokenRepository tokenRepository;
	

	@Autowired
	private PINRepository pinRepo;

	@PersistenceContext
	EntityManager entityManager;

	private final ProviderSignInUtils providerSignInUtils;

	@Autowired
	public UserController(ProviderSignInUtils providerSignInUtils) {
		this.providerSignInUtils = providerSignInUtils;
	}

	@RequestMapping(value = "/user/register", method = RequestMethod.GET)
	public String showRegistrationForm(WebRequest request, Model model) {
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
		
		RegistrationForm registration = createRegistrationDTO(connection);
		model.addAttribute("user", registration);

		return "user/registrationForm";
	}

	private RegistrationForm createRegistrationDTO() {
		RegistrationForm dto = new RegistrationForm();

		return dto;
	}
	
	private RegistrationForm createRegistrationDTO(Connection<?> connection) {
		RegistrationForm dto = new RegistrationForm();

		if (connection != null) {
			UserProfile socialMediaProfile = connection.fetchUserProfile();
			dto.setEmail(socialMediaProfile.getEmail());
			dto.setFirstName(socialMediaProfile.getFirstName());
			dto.setLastName(socialMediaProfile.getLastName());

			ConnectionKey providerKey = connection.getKey();
			dto.setSignInProvider(SocialMediaService.valueOf(providerKey.getProviderId().toUpperCase()));
		}

		return dto;
	}
	
	@RequestMapping(value = "/user/create", method = RequestMethod.GET)
	public String showCreationForm(WebRequest request, Model model) {

		RegistrationForm registration = createRegistrationDTO();
		model.addAttribute("user", registration);

		return "user/creationForm";
	}

	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public String registerUserAccount(@Valid @ModelAttribute("user") RegistrationForm userAccountData,
			BindingResult result, WebRequest request, HttpServletRequest req, HttpServletResponse response)
			throws DuplicateEmailException, IOException {

		User registered = createUserAccount(userAccountData, result);

		SecurityUtil.logInUser(registered);
		providerSignInUtils.doPostSignUp(registered.getEmail(), request);
		
		return "forward:/api/private?projection=privateUser";

	}

	@RequestMapping(value = "/user/updatepass", method = RequestMethod.POST)
	public String updatePassword(ForgotForm forgotForm,
			BindingResult result, WebRequest request, HttpServletRequest req, HttpServletResponse response){

		String email = null;
		String pin = null;
		String newpassword = null;
		
		if (forgotForm != null) {
			email = forgotForm.getEmail();
			pin = forgotForm.getPin();
			newpassword = forgotForm.getNewpassword();
		}
		
		LOGGER.debug("Attemp to find user by email: {}", email);
		User user = userRepo.findByEmail(email);
		
		PIN PINFromDB = new PIN();
		
		if (user != null) {
			LOGGER.debug("User with email: {} exists. Let's try to get PIN", email);
			PINFromDB = user.getPin();			
		}
		
		String pinFromDB = null;
		
		if (PINFromDB != null) {
			LOGGER.debug("PIN exists. with id: {}. Let's try to get pin", PINFromDB.getId());
			pinFromDB = PINFromDB.getPin();			
		}
				
		if ( (pin != null && pinFromDB != null && pin.equals(pinFromDB)) || (pinFromDB != null) ) {
			
			LOGGER.debug("pin equals pinFromDB. Let's try to log in user");
			SecurityUtil.logInUser(user);
			
			LOGGER.debug("Let's try to change password");
			service.changePassword(newpassword);
			
			LOGGER.debug("Let's try to delete PINFromDB");
			user.setPin(null);
			pinRepo.delete(PINFromDB);
			
			return "forward:/api/private?projection=privateUser";
			
		} else {
			
			LOGGER.debug("pin not equals pinFromDB. AccessDenied!");
			throw new AccessDeniedException("check your PIN and try again");
		}
		
	}

	
	@RequestMapping(value = "/user/changepassword", method = RequestMethod.POST)
	public String changePassword(PasswordForm passwordForm) {
		
		String password = passwordForm.getPassword();

		service.changePassword(password);

		return "forward:/api/private?projection=privateUser";

	}
	
	@RequestMapping(value = "/api/me", method = { RequestMethod.GET, RequestMethod.POST })
	public String redirectToPrivateMe(WebRequest request, HttpServletRequest req, HttpServletResponse response)
			throws DuplicateEmailException, IOException {
		LOGGER.debug("forward from /api/me to /api/me?projection=privateUser");
		return "forward:/api/private?projection=privateUser";

	}
	
	@RequestMapping(value = "/auth/test",method = RequestMethod.GET)
	public @ResponseBody String getitem(@RequestParam("code") String code,@RequestParam("state") String state){
		LOGGER.debug("auth code: {}; state: {}", code, state);
	    return "auth code: " + code;
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
