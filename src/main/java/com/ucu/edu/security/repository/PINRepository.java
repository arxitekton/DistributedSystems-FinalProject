package com.ucu.edu.security.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import com.ucu.edu.user.model.PIN;


@RepositoryRestResource(collectionResourceRel = "pin", path = "pins")
public interface PINRepository extends JpaRepository<PIN, Long> {
	
	@Override
	//@PostAuthorize("hasRole('ROLE_ADMIN') or (returnObject!=null and returnObject.user!=null and returnObject.user.id == @securityUtil.getLogInUserId())")
	PIN findOne(Long arg0);

	@Override
	@PostFilter("hasRole('ROLE_ADMIN') or (filterObject!=null and filterObject.user!=null and filterObject.user.id == @securityUtil.getLogInUserId())")
	List<PIN> findAll();

	@Override
	@Query("select s from PIN s where (1 = ?#{hasRole('ROLE_ADMIN') ? 1 : 0}) or (s.user.id = ?#{ @securityUtil.getLogInUserId()})")
	Page<PIN> findAll(Pageable pageable);	
	
	@Override
//	@PreAuthorize("hasRole('ROLE_ADMIN') or (#arg0 != null and #arg0.user != null and #arg0.user.id == @securityUtil.getLogInUserId()) ")
	void delete(@Param("arg0") PIN arg0);
	
	@Override
	//@PreAuthorize("hasRole('ROLE_ADMIN') or (#arg0 != null and #arg0.user != null and #arg0.user.id == @securityUtil.getLogInUserId()) ")
	<S extends PIN> S save(@Param("arg0") S arg0);

	@Override
//	@PreAuthorize("hasRole('ROLE_ADMIN') or (#arg0 != null and #arg0.user != null and #arg0.user.id == @securityUtil.getLogInUserId()) ")
	<S extends PIN> S saveAndFlush(@Param("arg0") S arg0);

}

