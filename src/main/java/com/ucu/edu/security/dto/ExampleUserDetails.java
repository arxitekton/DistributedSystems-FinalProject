package com.ucu.edu.security.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUser;

import com.ucu.edu.user.model.Role;
import com.ucu.edu.user.model.SocialMediaService;

public class ExampleUserDetails extends SocialUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String firstName;

	private String lastName;

	private Role role;
	
	private SocialMediaService socialSignInProvider;

	private String phone;

	public ExampleUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public static Builder getBuilder() {
		return new Builder();
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Role getRole() {
		return role;
	}

	public SocialMediaService getSocialSignInProvider() {
		return socialSignInProvider;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setSocialSignInProvider(SocialMediaService socialSignInProvider) {
		this.socialSignInProvider = socialSignInProvider;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	public static class Builder {

		private Long id;

		private String username;

		private String firstName;

		private String lastName;

		private String password;

		private Role role;

		private SocialMediaService socialSignInProvider;

		private Set<GrantedAuthority> authorities;

		private String phone;

		public Builder() {
			this.authorities = new HashSet<>();
		}

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder username(String username) {
			this.username = username;
			return this;
		}

		public Builder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public Builder password(String password) {
			if (password == null) {
				password = "SocialUser";
			}

			this.password = password;
			return this;
		}

		public Builder role(Role role) {
			this.role = role;

			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
			this.authorities.add(authority);

			return this;
		}

		public Builder socialSignInProvider(SocialMediaService socialSignInProvider) {
			this.socialSignInProvider = socialSignInProvider;
			return this;
		}

		public Builder phone(String phone) {
			this.phone = phone;
			return this;
		}

		public ExampleUserDetails build() {
			ExampleUserDetails user = new ExampleUserDetails(username, password, authorities);

			user.id = id;
			user.firstName = firstName;
			user.lastName = lastName;
			user.role = role;
			user.socialSignInProvider = socialSignInProvider;
			user.phone = phone;
			return user;
		}
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("username", getUsername())
				.append("firstName", firstName)
				.append("lastName", lastName)
				.append("role", role)
				.append("socialSignInProvider", socialSignInProvider)
				.append("phone", phone)
				.toString();
	}

}
