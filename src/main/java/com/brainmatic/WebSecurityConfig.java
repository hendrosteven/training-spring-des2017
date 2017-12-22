package com.brainmatic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.brainmatic.services.UserService;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth)
		throws Exception{
		auth.userDetailsService(userService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers(HttpMethod.GET, "/api/category").permitAll()
		.antMatchers(HttpMethod.GET, "/api/product").permitAll()
		.antMatchers(HttpMethod.POST, "/api/user").permitAll()
		.antMatchers(HttpMethod.POST, "/api/user/login").permitAll()
		.anyRequest().fullyAuthenticated()
		.and().httpBasic().and().csrf().disable();
	}
	
	
}

