package com.ucu.edu.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ucu.edu.security.dto.ExampleUserDetails;

@Component
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		ExampleUserDetails userdetails = (ExampleUserDetails) authentication.getPrincipal();

		LOGGER.info("AuthenticationSuccess. userId: {}", userdetails.getId());
		
		String url = "/api/private?projection=privateUser";
		request.getRequestDispatcher(url).forward(request, response);
	}
}
