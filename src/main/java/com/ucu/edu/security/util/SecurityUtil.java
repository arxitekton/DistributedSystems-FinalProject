package com.ucu.edu.security.util;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ucu.edu.security.dto.ExampleUserDetails;
import com.ucu.edu.user.model.Role;
import com.ucu.edu.user.model.User;


@Component("securityUtil")
public class SecurityUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtil.class);

	public static void logInUser(User user) {
		LOGGER.info("Logging in user: {}", user);

		ExampleUserDetails userDetails = ExampleUserDetails.getBuilder()
				.id(user.getId())
				.username(user.getEmail())
				.firstName(user.getFirstname())	
				.lastName(user.getLastname())
				.password(user.getPassword())
				.role(user.getRole())
				.socialSignInProvider(user.getSignInProvider())		
				.build();
		LOGGER.debug("Logging in principal: {}", userDetails);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		LOGGER.info("User: {} has been logged in.", userDetails);
	}

	public static Long getLogInUserId() {

		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser") {
			return null;
		}
		ExampleUserDetails userdetails = (ExampleUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return userdetails.getId();
	}

	public static boolean hasRoleAdmin() {

		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser") {
			return false;
		}
		ExampleUserDetails userdetails = (ExampleUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
		LOGGER.debug("userdetails.getRole: " + userdetails.getRole());
		return userdetails.getRole().equals(Role.ROLE_ADMIN);
	}
	
	public static String generateRandom(int length) {
	    Random random = new Random();
	    char[] digits = new char[length];
	    digits[0] = (char) (random.nextInt(9) + '1');
	    for (int i = 1; i < length; i++) {
	        digits[i] = (char) (random.nextInt(10) + '0');
	    }
	    return new String(digits);
	}
}
