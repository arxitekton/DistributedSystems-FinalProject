package com.ucu.edu.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ucu.edu.security.dto.ExampleUserDetails;
import com.ucu.edu.user.model.User;
import com.ucu.edu.user.repository.UserRepository;

public class RepositoryUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryUserDetailsService.class);

	private UserRepository repository;

	@Autowired
	public RepositoryUserDetailsService(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByEmail(username);

		if (user == null) {
			LOGGER.debug("No user found with username: {}", username);
			throw new UsernameNotFoundException("No user found with username: " + username);
		}

		ExampleUserDetails principal = ExampleUserDetails.getBuilder()
				.firstName(user.getFirstname())
				.id(user.getId())
				.lastName(user.getLastname())
				.password(user.getPassword())
				.role(user.getRole())
				.socialSignInProvider(user.getSignInProvider())
				.username(user.getEmail())
				.phone(user.getPhone())
				//.noOfOrders(user.getNoOfOrders())
				.build();

		return principal;
	}

}
