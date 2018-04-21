package com.ucu.edu.user.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PostFilter;

import com.ucu.edu.user.model.PublicUserProjection;
import com.ucu.edu.user.model.User;

@RepositoryRestResource(collectionResourceRel = "user", path = "users", excerptProjection = PublicUserProjection.class)
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
	
	Page<User> findDistinctUserByEmailContaining(@Param("email") String email, Pageable pageable);

	Page<User> findDistinctUserByEmailContainingOrLastnameContainingOrFirstnameContaining(@Param("email") String email, @Param("lastname") String lastname, @Param("firstname") String firstname, Pageable pageable);
	
	@Override
	User findOne(Long arg0);

	@Override
	@PostFilter("hasRole('ROLE_ADMIN') or (filterObject!=null and filterObject.id == @securityUtil.getLogInUserId())")
	List<User> findAll();

	@Query("select u from User u where (1 = ?#{hasRole('ROLE_ADMIN') ? 1 : 0}) or (u.id = ?#{ @securityUtil.getLogInUserId()})")
	Page<User> findAll(Pageable pageable);

}
