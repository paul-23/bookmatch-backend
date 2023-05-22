/**
 * 
 */
package com.redarpa.bookmatch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redarpa.bookmatch.dao.IUserDAO;
import com.redarpa.bookmatch.dto.JwtResponse;
import com.redarpa.bookmatch.dto.LoginRequest;
import com.redarpa.bookmatch.dto.MessageResponse;
import com.redarpa.bookmatch.dto.SignupRequest;
import com.redarpa.bookmatch.dto.User;
import com.redarpa.bookmatch.service.UserDetailsImpl;
import com.redarpa.bookmatch.utils.JwtUtils;

import jakarta.validation.Valid;

/**
 * @author RedArpa - BookMatch
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
		String roles = userDetails.getAuthorities().stream().findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("");

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	// Signup to reguster a new user
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		// Checks username is available
	    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
	        return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
	    }

	    // Checks email is available
	    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
	        return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
	    }

	    // Create new user's account
	    User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
	            encoder.encode(signUpRequest.getPassword()));

	    String strRoleId = signUpRequest.getRole(); // Obtener el ID del rol como una cadena de texto

	    String userRole; // Declarar una variable para almacenar el rol

	    // Verificar y asignar el rol basado en el ID del rol
	    switch (strRoleId) {
	        case "admin":
	            userRole = "ROLE_ADMIN"; // Rol de administrador
	            break;
	        default:
	            userRole = "ROLE_USER"; // Rol de usuario
	            break;
	    }

	    // Sets roles and saves user
	    user.setRoleId(userRole);
	    userRepository.save(user);

	    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
