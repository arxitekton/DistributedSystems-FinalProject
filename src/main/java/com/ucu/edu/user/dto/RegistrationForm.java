package com.ucu.edu.user.dto;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.ucu.edu.user.model.SocialMediaService;


public class RegistrationForm {

	@Email
	@NotEmpty
	@Size(max = 100)
	private String email;

	@Size(max = 20)
	private String phone;

	@NotEmpty
	@Size(max = 100)
	private String firstName;

	@Size(max = 100)
	private String lastName;

	private String password;

	private String passwordVerification;
	
	private SocialMediaService signInProvider;
	
	private Boolean usePhoneCall = true;

	private Boolean useSMS = true;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}	
	

	public Boolean isUsePhoneCall() {
		return usePhoneCall;
	}

	public void setUsePhoneCall(Boolean usePhoneCall) {
		this.usePhoneCall = usePhoneCall;
	}

	public Boolean isUseSMS() {
		return useSMS;
	}

	public void setUseSMS(Boolean useSMS) {
		this.useSMS = useSMS;
	}

	public Boolean isNormalRegistration() {
		return signInProvider == null;
	}

	public Boolean isSocialSignIn() {
		return signInProvider != null;
	}

	public SocialMediaService getSignInProvider() {
		return signInProvider;
	}

	public void setSignInProvider(SocialMediaService signInProvider) {
		this.signInProvider = signInProvider;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("email", email)
				.append("phone", phone)
				.append("firstName", firstName)
				.append("lastName", lastName)
				.append("signInProvider", signInProvider)
				.append("usePhoneCall", usePhoneCall)
				.append("useSMS", useSMS)
				.toString();
	}

}
