package com.ucu.edu.user.dto;


import org.apache.commons.lang3.builder.ToStringBuilder;


public class PasswordForm {

	private String password;

	private String passwordVerification;


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordVerification() {
		return passwordVerification;
	}

	public void setPasswordVerification(String passwordVerification) {
		this.passwordVerification = passwordVerification;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("password", password)
				.append("passwordVerification", passwordVerification)
				.toString();
	}

}
