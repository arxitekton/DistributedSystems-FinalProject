package com.ucu.edu.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CustomAuthenticationEntryPoint  implements AuthenticationEntryPoint{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

	@Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

		LOGGER.info("SC_UNAUTHORIZED!");
		
        response.setContentType("text/html;charset=UTF-8");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Full authentication is required to access this resource.");

	}
}
