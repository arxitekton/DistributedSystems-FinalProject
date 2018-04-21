package com.ucu.edu.user.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ucu.edu.security.util.SecurityUtil;
import com.ucu.edu.user.dto.RegistrationForm;
import com.ucu.edu.user.model.User;
import com.ucu.edu.user.repository.UserRepository;

@Service("userService")
public class RepositoryUserService implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryUserService.class);

	private PasswordEncoder passwordEncoder;

	private UserRepository repository;

	@Autowired
	public RepositoryUserService(PasswordEncoder passwordEncoder, UserRepository repository) {
		this.passwordEncoder = passwordEncoder;
		this.repository = repository;
	}

	@Transactional
	@Override
	public User registerNewUserAccount(RegistrationForm userAccountData) throws DuplicateEmailException {
		LOGGER.debug("Registering new user account with information: {}", userAccountData);

		if (emailExist(userAccountData.getEmail())) {
			LOGGER.debug("Email: {} exists. Throwing exception.", userAccountData.getEmail());
			throw new DuplicateEmailException("The email address: "  + userAccountData.getEmail() + " is already in use.");
		}

		LOGGER.debug("Email: {} does not exist. Continuing registration.", userAccountData.getEmail());

		String encodedPassword = encodePassword(userAccountData);

		User.Builder user = User.getBuilder()
				.email(userAccountData.getEmail())
				.phone(userAccountData.getPhone())
				.firstname(userAccountData.getFirstName())
				.lastname(userAccountData.getLastName())
				.password(encodedPassword)
				.usePhoneCall(userAccountData.isUsePhoneCall())
				.useSMS(userAccountData.isUseSMS());

		if (userAccountData.isSocialSignIn()) {
			user.signInProvider(userAccountData.getSignInProvider());
		}

		User registered = user.build();

		LOGGER.debug("Persisting new user with information: {}", registered);

		return repository.save(registered);
	}


	@Transactional
	@Override
	public User changePassword(String input){
		
		User user = repository.findOne(SecurityUtil.getLogInUserId());
		
		LOGGER.debug("Attempt to change password from user with id: ", user.getId());

		String encodedPassword = encodePass(input);
		user.setPassword(encodedPassword);
		
		return user;
	}

	
	private boolean emailExist(String email) {
		LOGGER.debug("Checking if email {} is already found from the database.", email);

		User user = repository.findByEmail(email);

		if (user != null) {
			LOGGER.debug("User account: {} found with email: {}. Returning true.", user, email);
			return true;
		}

		LOGGER.debug("No user account found with email: {}. Returning false.", email);

		return false;
	}

	private String encodePassword(RegistrationForm dto) {
		String encodedPassword = null;
		
		if (dto != null && dto.getPassword() != null) {
			encodedPassword = passwordEncoder.encode(dto.getPassword());			
		}		

		return encodedPassword;
	}

	private String encodePass(String input) {
		String encodedPassword = null;

		encodedPassword = passwordEncoder.encode(input);

		return encodedPassword;
	}

	@Override
	public User create(User user) {
		return repository.save(user);
	}

	@Override
	public User save(User user) {
		return repository.save(user);
	}

	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public User findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public User findByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	public User update(User user) {
		User updatedUser = repository.findOne(user.getId());

		updatedUser.setEmail(user.getEmail());
		updatedUser.setFirstname(user.getFirstname());
		updatedUser.setLastname(user.getLastname());

		return updatedUser;
	}

	@Override
	public User delete(Long id) {
		User deletedUser = repository.findOne(id);
		repository.delete(deletedUser);

		return deletedUser; // ?
	}

	@Override
	public Page<User> listAllByPage(Pageable pageable) {
		System.out.println(repository.findAll(pageable).toString());
		return repository.findAll(pageable);
	}

	public boolean checkAccess() {
		return false;
	}

}
