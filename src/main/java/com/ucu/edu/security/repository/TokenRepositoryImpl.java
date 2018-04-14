package com.ucu.edu.security.repository;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;

import com.ucu.edu.security.model.PersistentLogin;
import com.ucu.edu.security.repository.PersistentLoginRepository;

@Repository("tokenRepositoryDao")
public class TokenRepositoryImpl implements PersistentTokenRepository {

	static final Logger LOGGER = LoggerFactory.getLogger(TokenRepositoryImpl.class);

	private PersistentLoginRepository repository;

	@Autowired
	public TokenRepositoryImpl(PersistentLoginRepository repository) {
		this.repository = repository;
	}

	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		
		String username = token.getUsername();
		LOGGER.info("Attempt to create Token for user : {}", username);
		
		PersistentLogin oldPersistentLogin = repository.findByUsername(username);
		if (oldPersistentLogin != null) {
			LOGGER.debug("PersistentLogin already exists with series : {}. Let's try to update", oldPersistentLogin.getSeries());
			updateTokenLastUsed(oldPersistentLogin.getSeries(), token.getDate());
		} else {
			
			LOGGER.debug("PersistentLogin not exists. Let's try to create new one");

			PersistentLogin persistentLogin = new PersistentLogin();
			persistentLogin.setUsername(token.getUsername());
			LOGGER.debug("Username has been set: {}", persistentLogin.getUsername());

			persistentLogin.setSeries(token.getSeries());
			LOGGER.debug("Series has been set: {}", persistentLogin.getSeries());
			
			persistentLogin.setToken(token.getTokenValue());
			LOGGER.debug("TokenValue has been set: {}", persistentLogin.getToken());
			
			persistentLogin.setLast_used(token.getDate());
			LOGGER.debug("Last_used date has been set: {}", persistentLogin.getLast_used());
			
			repository.save(persistentLogin);			
		}

	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		LOGGER.info("Fetch Token if any for seriesId : {}", seriesId);
		try {

			PersistentLogin persistentLogin = repository.findBySeries(seriesId);

			return new PersistentRememberMeToken(persistentLogin.getUsername(), persistentLogin.getSeries(),
					persistentLogin.getToken(), persistentLogin.getLast_used());
		} catch (Exception e) {
			LOGGER.info("Token not found...");
			return null;
		}
	}

	@Override
	public void removeUserTokens(String username) {
		LOGGER.info("Removing Token if any for user : {}", username);

		PersistentLogin persistentLogin = repository.findByUsername(username);
		if (persistentLogin != null) {
			LOGGER.info("rememberMe was selected");
			repository.delete(persistentLogin);
		}

	}

	@Override
	public void updateToken(String seriesId, String tokenValue, Date lastUsed) {
		LOGGER.info("Updating Token for seriesId : {}", seriesId);
		PersistentLogin persistentLogin = repository.findBySeries(seriesId);
		/*
		 * // do not remove this: it`s true way:
		 * persistentLogin.setToken(tokenValue);
		 */
		persistentLogin.setLast_used(lastUsed);
		repository.save(persistentLogin);
	}
	
	public void updateTokenLastUsed(String seriesId, Date lastUsed) {
		LOGGER.info("Updating Token LastUsed : {}", lastUsed);
		PersistentLogin persistentLogin = repository.findBySeries(seriesId);
		persistentLogin.setLast_used(lastUsed);
		repository.save(persistentLogin);
	}

}
