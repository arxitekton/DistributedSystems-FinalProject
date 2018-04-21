package com.ucu.edu.user.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import com.ucu.edu.user.model.User;

@Projection(name = "publicUser", types = User.class)
public interface PublicUserProjection {
	
	Long getId();
	
	@Value("#{@securityUtil.hasRoleAdmin()? target.email : null}")
	String getEmail();
	
	String getFirstname();

}
