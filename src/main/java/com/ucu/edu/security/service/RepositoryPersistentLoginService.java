package com.ucu.edu.security.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucu.edu.security.model.PersistentLogin;
import com.ucu.edu.security.repository.PersistentLoginRepository;

@Service
public class RepositoryPersistentLoginService implements PersistentLoginService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryPersistentLoginService.class);

	private PersistentLoginRepository repository;

	@Autowired
	public RepositoryPersistentLoginService(PersistentLoginRepository repository) {
		this.repository = repository;
	}

	@Override
	public PersistentLogin create(PersistentLogin persistentLogin) {
		LOGGER.info("Attempt to create token for user: {}", persistentLogin.getUsername());
		return repository.save(persistentLogin);
	}

	@Override
	public PersistentLogin save(PersistentLogin persistentLogin) {
		LOGGER.info("Attempt to save token for user: {}", persistentLogin.getUsername());
		return repository.save(persistentLogin);
	}

	@Override
	public PersistentLogin findBySeries(String series) {
		LOGGER.info("Attempt to find token By Series: {}", series);
		return repository.findBySeries(series);
	}

	@Override
	public PersistentLogin findByUsername(String username) {
		LOGGER.info("Attempt to find token By Username: {}", username);
		return repository.findByUsername(username);
	}

	@Override
	public PersistentLogin delete(Long series) {
		LOGGER.info("Attempt to delete token By series: {}", series);
		return null;
	}

	@Override
	public List<PersistentLogin> findAll() {
		LOGGER.info("Attempt to find all tokens.");
		return repository.findAll();
	}

}
