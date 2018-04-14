package com.ucu.edu.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ucu.edu.security.model.PersistentLogin;

public interface PersistentLoginRepository extends JpaRepository<PersistentLogin, Long> {

	PersistentLogin findBySeries(String series);

	PersistentLogin findByUsername(String username);

}
