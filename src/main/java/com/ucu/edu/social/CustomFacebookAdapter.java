package com.ucu.edu.social;

import org.springframework.social.ApiException;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookAdapter;

public class CustomFacebookAdapter extends FacebookAdapter {

	static final String[] FIELDS = { "id", "email", "first_name", "last_name" };

	public boolean test(Facebook facebook) {
		try {
			facebook.fetchObject("me", org.springframework.social.facebook.api.User.class, FIELDS);
			return true;
		} catch (ApiException e) {
			return false;
		}
	}

	public void setConnectionValues(Facebook facebook, ConnectionValues values) {
		org.springframework.social.facebook.api.User profile = facebook.fetchObject("me", org.springframework.social.facebook.api.User.class, FIELDS);
		values.setProviderUserId(profile.getId().toString());
		values.setDisplayName(profile.getFirstName());
		values.setProfileUrl(profile.getLink());
		values.setImageUrl(facebook.getBaseGraphApiUrl() + profile.getId() + "/picture");

		System.out.println("Email: " + profile.getEmail());
		System.out.println("Link: " + profile.getLink());
		System.out.println("Id: " + profile.getId().toString());
	}

	public UserProfile fetchUserProfile(Facebook facebook) {
		org.springframework.social.facebook.api.User profile = facebook.fetchObject("me", org.springframework.social.facebook.api.User.class, FIELDS);
		System.out.println("Email: " + profile.getEmail());

		return new UserProfileBuilder().setId(profile.getId().toString()).setName(profile.getFirstName())
				.setFirstName(profile.getFirstName()).setLastName(profile.getLastName()).setEmail(profile.getEmail())
				.build();
	}

	public void updateStatus(Facebook facebook, String message) {
		facebook.feedOperations().updateStatus(message);
	}

}