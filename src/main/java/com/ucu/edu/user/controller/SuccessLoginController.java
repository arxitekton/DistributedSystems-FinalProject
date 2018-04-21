package com.ucu.edu.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ucu.edu.security.dto.ExampleUserDetails;
import com.ucu.edu.security.model.PersistentLogin;
import com.ucu.edu.security.service.PersistentLoginService;
import com.ucu.edu.user.model.User;
import com.ucu.edu.user.repository.UserRepository;
import com.ucu.edu.user.service.UserService;
import com.ucu.edu.util.AppUtils;


@RepositoryRestController
@RequestMapping("/api/private")
public class SuccessLoginController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SuccessLoginController.class);

	@Autowired
	@Qualifier("userService")
	private UserService service;

	@Autowired
	private PersistentLoginService persistentLoginService;

	@Autowired
	private EntityLinks entityLinks;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	PersistentTokenRepository tokenRepository;

	private final AbstractRememberMeServices rememberMeServices;

	@Autowired
	public SuccessLoginController(AbstractRememberMeServices rememberMeServices) {
		this.rememberMeServices = rememberMeServices;
	}
	
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	ResponseEntity<Object> getAboutMe(HttpServletRequest req, HttpServletResponse response, PersistentEntityResourceAssembler resourceAssembler) {

		HttpHeaders responseHeaders = new HttpHeaders();
	
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		
		if (principal == "anonymousUser") {
			LOGGER.debug("Full authentication is required to access this resource");
			return new ResponseEntity<Object>(null,responseHeaders, HttpStatus.UNAUTHORIZED);		
		}
		
		ExampleUserDetails userDetails = (ExampleUserDetails) principal;
		
		Authentication result1 = SecurityContextHolder.getContext().getAuthentication();
		
		/* Examines the incoming request and checks for the presence of the configured
		 * "remember me" parameter. If it's present, or if <tt>alwaysRemember</tt> is set to
		 * true, calls <tt>onLoginSucces</tt>.
		 * </p>
		 */
		rememberMeServices.loginSuccess(req, response, result1);

		PersistentLogin persistentLogin = persistentLoginService.findByUsername(userDetails.getUsername());
		PersistentRememberMeToken token = tokenRepository.getTokenForSeries(persistentLogin.getSeries());

		String[] tokens = new String[] { token.getSeries(), token.getTokenValue() };

		responseHeaders.set("remember-me", AppUtils.encodeCookie(tokens));

		User user = userRepo.findOne(userDetails.getId());
				
		Resource<Object> resource = resourceAssembler.toFullResource(user);
		return new ResponseEntity<Object>(resource, responseHeaders, HttpStatus.CREATED);
	}
	

	@RequestMapping(value = "/test", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/hal+json")
	public ResponseEntity<Resource<User>> getUser(HttpServletRequest req, HttpServletResponse response, Authentication authentication) {

		ExampleUserDetails userDetails = (ExampleUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Authentication result1 = SecurityContextHolder.getContext().getAuthentication();

		rememberMeServices.loginSuccess(req, response, result1);

		PersistentLogin persistentLogin = persistentLoginService.findByUsername(userDetails.getUsername());
		PersistentRememberMeToken token = tokenRepository.getTokenForSeries(persistentLogin.getSeries());

		String[] tokens = new String[] { token.getSeries(), token.getTokenValue() };

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("remember-me", AppUtils.encodeCookie(tokens));

		User user = userRepo.findOne(userDetails.getId());
		Resource<User> resource = new Resource<User>(user);

		resource.add(entityLinks.linkFor(User.class).slash(userDetails.getId()).withSelfRel());
		resource.add(entityLinks.linkToSingleResource(User.class, userDetails.getId()));


		return new ResponseEntity<Resource<User>>(resource, responseHeaders, HttpStatus.CREATED);

	}
}
