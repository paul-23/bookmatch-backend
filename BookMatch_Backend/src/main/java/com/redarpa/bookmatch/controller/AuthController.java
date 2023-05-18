/**
 * 
 */
package com.redarpa.bookmatch.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redarpa.bookmatch.dao.IRoleDAO;
import com.redarpa.bookmatch.dao.IUserDAO;
import com.redarpa.bookmatch.dto.ERole;
import com.redarpa.bookmatch.dto.JwtResponse;
import com.redarpa.bookmatch.dto.LoginRequest;
import com.redarpa.bookmatch.dto.MessageResponse;
import com.redarpa.bookmatch.dto.Role;
import com.redarpa.bookmatch.dto.SignupRequest;
import com.redarpa.bookmatch.dto.User;
import com.redarpa.bookmatch.service.UserDetailsImpl;
import com.redarpa.bookmatch.utils.JwtUtils;

import jakarta.validation.Valid;

/**
 * @author Marc
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600) // Allows requests from all origins with 1 hour cache
@RestController
@RequestMapping("/auth")
public class AuthController {

	// Autowire dependencies
		@Autowired
		AuthenticationManager authenticationManager;

		@Autowired
		IUserDAO userRepository;

		@Autowired
		IRoleDAO roleDAO;

		@Autowired
		PasswordEncoder encoder;

		@Autowired
		JwtUtils jwtUtils;

		// Post mapping to sign in and get the token
		@PostMapping("/signin")
		public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());

			return ResponseEntity.ok(
					new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
		}

		// Signup to reguster a new user
		@PostMapping("/signup")
		public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
			// Checks username is availabe
			if (userRepository.existsByUsername(signUpRequest.getUsername())) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
			}

			// Checks email is availabe
			if (userRepository.existsByEmail(signUpRequest.getEmail())) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
			}

			// Create new user's account
			User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
					encoder.encode(signUpRequest.getPassword()));

			Set<String> strRoles = signUpRequest.getRole();
			Set<Role> roles = new HashSet<>();

			// Checks roles are valid and adds them to list of roles
			if (strRoles == null) {
				Role userRole = roleDAO.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(userRole);
			} else {
				strRoles.forEach(role -> {
					switch (role) {
					case "admin":
						Role adminRole = roleDAO.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);

						break;
					default:
						Role userRole = roleDAO.findByName(ERole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
					}
				});
			}

			// Sets roles and saves user
			user.setRoles(roles);
			userRepository.save(user);

			return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
		}
}
