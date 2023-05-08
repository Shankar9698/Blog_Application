package com.blog.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.blog.securities.CustomUserDetailService;

@Configuration
@EnableWebSecurity
//WebSecurityConfigurerAdapter class has now depreciated, this is latest configuration
public class SecurityConfig {
	@Autowired
	private CustomUserDetailService customUserDetailService;
//http basic authuntication(js format in web browser)
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests().anyRequest().authenticated().and().httpBasic();
		return http.build();

	}

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(customUserDetailService);//object->userDetailService
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(Collections.singletonList(authenticationProvider));
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();//to bcrypy the password
	}

}
