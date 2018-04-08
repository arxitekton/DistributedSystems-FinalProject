package com.ucu.edu.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import com.ucu.edu.security.service.RepositoryUserDetailsService;
import com.ucu.edu.security.service.SimpleSocialUserDetailsService;
import com.ucu.edu.user.repository.UserRepository;

@Configuration
@ComponentScan("com.ucu.edu")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
public class SecurityContext extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	PersistentTokenRepository tokenRepository;

	@Autowired
	private AuthSuccessHandler authSuccessHandler;

	@Autowired
	private AuthFailureHandler authFailureHandler;

	@Autowired
	private CustomAccessDeniedHandler accessDeniedHandler;
	
	;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); // for development only
		http
				.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/login/authenticate")
				.usernameParameter("username")
				.passwordParameter("password").successHandler(authSuccessHandler)
				.failureHandler(authFailureHandler)

			.and()
				.rememberMe()
				.rememberMeServices(rememberMeServices())
				.key("posc")
				
			.and()
				.logout()
				.deleteCookies("JSESSIONID")
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
				
			.and()
				.exceptionHandling().accessDeniedHandler(accessDeniedHandler)
				.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
				
			.and()
				.authorizeRequests()		
				
				.antMatchers(
						"/admin/**",
						"/newuser",
						"/user/create/**"
				).hasRole("ADMIN")

				.antMatchers(
						"/**",
						"/models/**",
						"/api/**",
						"/auth/**",
						"/login/**",
						"/test_login/**",
						"/insearchtest/**",
						"/signup/**",
						"/user/register/**",
						"/test/**",
						"/item/**",
						"/order/**",
						"/upload/**",
						"/img/**",
						"/file/**",
						"/personalise/**",
						"/user/forgotpass/**",
						"/user/updatepass/**"
				).permitAll()
				
				.antMatchers(
						"/user/changepassword/**",
						"/api/me/**",
						"/delete/**"
				).hasAnyRole("USER", "ADMIN")
				.and().apply(new SpringSocialConfigurer())
				.and().apply(getSpringSocialConfigurer());

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SocialUserDetailsService socialUserDetailsService() {
		return new SimpleSocialUserDetailsService(userDetailsService());
	}
	
	private SpringSocialConfigurer getSpringSocialConfigurer() {
        SpringSocialConfigurer config = new SpringSocialConfigurer();
        config.alwaysUsePostLoginUrl(true);
        config.postLoginUrl("/api/me");

        return config;
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		RepositoryUserDetailsService repositoryUserDetailsService = new RepositoryUserDetailsService(userRepository);
		return repositoryUserDetailsService;
	}

	@Bean
	public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
		return new SecurityEvaluationContextExtension();
	}

	@Bean
	public AbstractRememberMeServices rememberMeServices() {
		PersistentTokenBasedRememberMeServices rememberMeServices = new PersistentTokenBasedRememberMeServices("posc",
				userDetailsService(), tokenRepository);
		rememberMeServices.setAlwaysRemember(true);
		rememberMeServices.setCookieName("remember-me");
		rememberMeServices.setTokenValiditySeconds(2147483647);
		return rememberMeServices;
	}

}
