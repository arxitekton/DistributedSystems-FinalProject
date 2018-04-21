package com.ucu.edu.user.dto;


import org.apache.commons.lang3.builder.ToStringBuilder;


public class ForgotForm {

	private String email;
	
	private String pin;

	private String newpassword;

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("email", email)
				.append("pin", pin)
				.append("newpassword", newpassword)
				.toString();
	}

}
