package com.ucu.edu.security.service;

import java.util.List;

import com.ucu.edu.security.model.PersistentLogin;

public interface PersistentLoginService {

	public PersistentLogin create(PersistentLogin persistentLogin);

	public PersistentLogin save(PersistentLogin persistentLogin);

	public List<PersistentLogin> findAll();

	public PersistentLogin findBySeries(String series);

	public PersistentLogin findByUsername(String username);

	public PersistentLogin delete(Long series);

}
