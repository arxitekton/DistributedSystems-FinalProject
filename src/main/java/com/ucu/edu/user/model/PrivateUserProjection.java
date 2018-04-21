package com.ucu.edu.user.model;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import com.ucu.edu.user.model.User;


@Projection(name = "privateUser", types = User.class)
public interface PrivateUserProjection {

	@Value("#{@securityUtil.hasRoleAdmin() or @securityUtil.getLogInUserId()==target.getId()? target.creationTime : null}")
	ZonedDateTime getCreationTime();
	
	@Value("#{@securityUtil.hasRoleAdmin() or @securityUtil.getLogInUserId()==target.getId()? target.id : null}")
	Long getId();

	@Value("#{@securityUtil.hasRoleAdmin() or @securityUtil.getLogInUserId()==target.getId()? target.userId : null}")
	Long getUserId();

	@Value("#{@securityUtil.hasRoleAdmin() or @securityUtil.getLogInUserId()==target.getId()? target.email : null}")
	String getEmail();

	@Value("#{@securityUtil.hasRoleAdmin() or @securityUtil.getLogInUserId()==target.getId()? target.firstname : null}")
	String getFirstname();

	@Value("#{@securityUtil.hasRoleAdmin() or @securityUtil.getLogInUserId()==target.getId()? target.lastname : null}")
	String getLastname();

	@Value("#{@securityUtil.hasRoleAdmin()? target.password : null}")
	String getPassword();

	@Value("#{@securityUtil.hasRoleAdmin()? target.role : null}")
	Role getRole();

	@Value("#{@securityUtil.hasRoleAdmin() or @securityUtil.getLogInUserId()==target.getId()? target.phone : null}")
	String getPhone();

	@Value("#{@securityUtil.hasRoleAdmin() or @securityUtil.getLogInUserId()==target.getId()? target.state : null}")
	String getState();

	@Value("#{@securityUtil.hasRoleAdmin() or @securityUtil.getLogInUserId()==target.getId()? target.link : null}")
	String getLink();
	
	@Value("#{@securityUtil.hasRoleAdmin() or @securityUtil.getLogInUserId()==target.getId()? target.usePhoneCall : null}")
	Boolean isUsePhoneCall();
	
	@Value("#{@securityUtil.hasRoleAdmin() or @securityUtil.getLogInUserId()==target.getId()? target.useSMS : null}")
	Boolean isUseSMS();
}
