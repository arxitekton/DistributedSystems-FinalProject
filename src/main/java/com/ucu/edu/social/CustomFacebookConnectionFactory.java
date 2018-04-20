package com.ucu.edu.social;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookServiceProvider;

public class CustomFacebookConnectionFactory extends OAuth2ConnectionFactory<Facebook> {

	public CustomFacebookConnectionFactory(String appId, String appSecret) {
		this(appId, appSecret, null);
	}

	public CustomFacebookConnectionFactory(String appId, String appSecret, String appNamespace) {
		super("facebook", new FacebookServiceProvider(appId, appSecret, appNamespace), new CustomFacebookAdapter());
	}

}