package com.ucu.edu.user.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ucu.edu.user.dto.RegistrationForm;
import com.ucu.edu.user.model.User;
import com.ucu.edu.user.service.DuplicateEmailException;

public interface UserService {
	public User registerNewUserAccount(RegistrationForm userAccountData) throws DuplicateEmailException;

	public User create(User user);

	public User save(User user);

	public List<User> findAll();

	public Page<User> listAllByPage(Pageable pageable);

	public User findById(Long id);

	public User findByEmail(String email);

	public User update(User user);

	public User delete(Long id);

	User changePassword(String input);
}
