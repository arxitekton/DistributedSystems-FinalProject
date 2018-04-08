package com.ucu.edu.configuration;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.ucu.edu.social.CustomFacebookConnectionFactory;
import com.ucu.edu.social.SocialConnectionSignUp;
import com.ucu.edu.user.service.UserService;

@Configuration
@EnableSocial
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = { "com.ucu.edu" })
public class SocialContext implements SocialConfigurer {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	@Qualifier("userService")
	private UserService service;
	
    @Inject
    private SocialConnectionSignUp signup;
    
    @Bean
    public CustomFacebookConnectionFactory facebookConnectionFactory() {
    	CustomFacebookConnectionFactory facebookConnectionFactory = new CustomFacebookConnectionFactory("AppID", "AppSecret");
        facebookConnectionFactory.setScope("email");
        return facebookConnectionFactory;
    }
    
    @Bean
    public GoogleConnectionFactory googleConnectionFactory() {
    	GoogleConnectionFactory googleConnectionFactory = new GoogleConnectionFactory("AppID", "AppSecret");
    	googleConnectionFactory.setScope("email");
        return googleConnectionFactory;
    }

	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
		
		CustomFacebookConnectionFactory customFacebookConnectionFactory = new CustomFacebookConnectionFactory(
				env.getProperty("facebook.app.id"),
				env.getProperty("facebook.app.secret"));
		customFacebookConnectionFactory.setScope("public_profile,email");
		cfConfig.addConnectionFactory(customFacebookConnectionFactory);
		
		GoogleConnectionFactory gcf = new GoogleConnectionFactory(
				env.getProperty("google.app.id"),
				env.getProperty("google.app.secret"));
		gcf.setScope("email");
		cfConfig.addConnectionFactory(gcf);
	}

	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
		repository.setConnectionSignUp(signup);
		return repository;
	}

	@Bean
	public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator,
			ConnectionRepository connectionRepository) {
		return new ConnectController(connectionFactoryLocator, connectionRepository);
	}
	
	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator,
			UsersConnectionRepository connectionRepository) {
		return new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
	}

}