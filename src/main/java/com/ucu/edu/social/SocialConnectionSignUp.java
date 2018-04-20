package com.ucu.edu.social;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Component;

import com.ucu.edu.user.dto.RegistrationForm;
import com.ucu.edu.user.model.SocialMediaService;
import com.ucu.edu.user.model.User;
import com.ucu.edu.user.service.DuplicateEmailException;
import com.ucu.edu.user.service.UserService;

@Component
public class SocialConnectionSignUp implements ConnectionSignUp {

	private static final Logger LOGGER = LoggerFactory.getLogger(SocialConnectionSignUp.class);

	@Inject
//	@Qualifier("userService")
	private UserService service;


    public String execute(Connection<?> connection) {
		
    	LOGGER.debug("Attempt to create RegistrationForm userAccountData");
		RegistrationForm userAccountData = createRegistrationDTO(connection);
		
		User registered = null;

		try {
			LOGGER.debug("Attempt to register New User Account with email: {}", userAccountData.getEmail());
			
			if (service == null) {
				LOGGER.debug("null userService");
			}
			
			registered = service.registerNewUserAccount(userAccountData);
			
		} catch (DuplicateEmailException e) {
			
			LOGGER.debug("DuplicateEmailException");
			e.printStackTrace();
			return null;
		}

        return registered.getEmail();
    }
    
	
	private RegistrationForm createRegistrationDTO(Connection<?> connection) {
		RegistrationForm dto = new RegistrationForm();

		if (connection != null) {
			
			LOGGER.debug("connection not null");
			
			UserProfile socialMediaProfile = connection.fetchUserProfile();
			LOGGER.debug("Create socialMediaProfile with email: {}", socialMediaProfile.getEmail());
			dto.setEmail(socialMediaProfile.getEmail());
			dto.setFirstName(socialMediaProfile.getFirstName());
			dto.setLastName(socialMediaProfile.getLastName());

			ConnectionKey providerKey = connection.getKey();
			dto.setSignInProvider(SocialMediaService.valueOf(providerKey.getProviderId().toUpperCase()));
		}

		return dto;
	}

}
