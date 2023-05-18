/**
 * 
 */
package com.redarpa.bookmatch.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.redarpa.bookmatch.service.UserDetailsServiceImpl;

/**
 * @author Marc
 *
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

	// Autowire UserDetailsServiceImpl
		@Autowired
		UserDetailsServiceImpl userDetailsService;

		// Autowire AuthEntryPointJwt
		@Autowired
		private AuthEntryPointJwt unauthorizedHandler;

		// Token filter bean
		@Bean
		public AuthTokenFilter authenticationJwtTokenFilter() {
			return new AuthTokenFilter();
		}

		// Authentication provider bean
		@Bean
		public DaoAuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

			authProvider.setUserDetailsService(userDetailsService);
			authProvider.setPasswordEncoder(passwordEncoder());

			return authProvider;
		}

		// Authentication manager bean
		@Bean
		public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
			return authConfig.getAuthenticationManager();
		}

		// Password Encoder
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

		// Security Filter Chain
		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			http.csrf().disable()
		    .sessionManagement()
		        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		        .and()
		    .authorizeHttpRequests()
		        .requestMatchers("/auth/**").permitAll()
		        .requestMatchers("/api/**").permitAll().anyRequest().authenticated()
		    .and()
		    .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
		    .and()
		    .authenticationProvider(authenticationProvider())
		    .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

			return http.build();
		}
}
