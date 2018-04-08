package com.ucu.edu.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthSuccessHandler.class);

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

		LOGGER.info("Access Denied!");
		
        response.setContentType("text/html;charset=UTF-8");
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Sorry, You don't have privileges to view this page!");

	}

}
