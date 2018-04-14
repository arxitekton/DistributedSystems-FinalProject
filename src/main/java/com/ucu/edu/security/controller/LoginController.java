package com.ucu.edu.security.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;

import com.ucu.edu.security.util.SecurityUtil;
import com.ucu.edu.social.CustomFacebookConnectionFactory;
import com.ucu.edu.social.SocialConnectionSignUp;
import com.ucu.edu.user.model.User;
import com.ucu.edu.user.repository.UserRepository;


@Controller
public class LoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtil.class);
	
	@Autowired
	private CustomFacebookConnectionFactory facebookConnectionFactory;
	
	@Autowired
	private GoogleConnectionFactory googleConnectionFactory;

	@Autowired
	private UsersConnectionRepository usersConnectionRepository;
	
	@Autowired
	private ProviderSignInUtils providerSignInUtils;
	
	@Autowired
	private SocialConnectionSignUp socialConnectionSignUp;

	@Autowired
	private UserRepository userRepo;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginPage() {
		return "login";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup() {
		LOGGER.debug("LoginController: redirect:/user/register");
		return "redirect:/user/register";
	}
	
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String signin() {
		LOGGER.debug("LoginController: forward:/api/private?projection=privateUser");
		return "forward:/api/private?projection=privateUser";
	}
	
	@RequestMapping(value = "/oauth_token/facebook", method = RequestMethod.GET)
	public String oauth_tokenFacebook(@RequestParam String token, NativeWebRequest request) {
				
		LOGGER.debug("input token: {}", token);
		
		AccessGrant accessGrant = new AccessGrant(token);
		LOGGER.debug("accessGrant getScope: {}", accessGrant.getScope());
		
		Connection<?> connection = facebookConnectionFactory.createConnection(accessGrant);
		
		LOGGER.debug("connection.fetchUserProfile().getEmail(): {}", connection.fetchUserProfile().getEmail());
		
		handleSignIn(connection, request);
	    
//		LOGGER.debug("LoginController: forward:/api/private?projection=privateUser");
		return "forward:/signin";
	}
	
	@RequestMapping(value = "/oauth_token/google", method = RequestMethod.GET)
	public String oauth_tokenGoogle(@RequestParam String token, NativeWebRequest request) {
				
		LOGGER.debug("input token: {}", token);
		
		AccessGrant accessGrant = new AccessGrant(token);
		LOGGER.debug("accessGrant getScope: {}", accessGrant.getScope());
		
		Connection<?> connection = googleConnectionFactory.createConnection(accessGrant);
		
		LOGGER.debug("connection.fetchUserProfile().getEmail(): {}", connection.fetchUserProfile().getEmail());
		
		handleSignIn(connection, request);

		return "forward:/signin";
	}
	
	private void handleSignIn(Connection<?> connection, NativeWebRequest request) {
		List<String> userIds = usersConnectionRepository.findUserIdsWithConnection(connection);
		if (userIds.size() == 1) {
			usersConnectionRepository.createConnectionRepository(userIds.get(0)).updateConnection(connection);		
			UserProfile userProfile = connection.fetchUserProfile();
			String email = userProfile.getEmail();
			User registered = userRepo.findByEmail(email);
			if (registered == null) {
				socialConnectionSignUp.execute(connection);
				registered = userRepo.findByEmail(email);
			}
//			signInAdapter.signIn(userIds.get(0), connection, request);
			SecurityUtil.logInUser(registered);
			
		} else {
			// TODO: This should never happen, but need to figure out what to do if it does happen. 
			LOGGER.error("Expected exactly 1 matching user. Got " + userIds.size() + " metching users.");
		}
	}

}
