/**
 * 
 */
package com.redarpa.bookmatch.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.redarpa.bookmatch.dao.IUserDAO;
import com.redarpa.bookmatch.dto.JwtResponse;
import com.redarpa.bookmatch.dto.LoginRequest;
import com.redarpa.bookmatch.dto.MessageResponse;
import com.redarpa.bookmatch.dto.SignupRequest;
import com.redarpa.bookmatch.dto.User;
import com.redarpa.bookmatch.service.UserDetailsImpl;
import com.redarpa.bookmatch.service.UserServiceImp;
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
	
	@Autowired
	UserServiceImp userServiceImpl;

	// Post mapping to sign in and get the token
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

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
	public ResponseEntity<?> registerUser(@RequestParam(value = "image", required = false) MultipartFile imageFile, @Valid @RequestPart("signup") SignupRequest signUpRequest) {

	    // Checks email is available
	    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
	        return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
	    }

	    try {
	        // Create new user's account
	        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
	                encoder.encode(signUpRequest.getPassword()));

	        // Sets roles and saves user
	        user.setRoleId("ROLE_USER");
	        userRepository.save(user);

	        if (imageFile != null && !imageFile.isEmpty()) {
	            userServiceImpl.saveUserWithImage(user, imageFile.getBytes());
	        } else {
	            saveImg(user.getId_user());
	        }

	        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	    } catch (IOException ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Error saving user image: " + ex.getMessage()));
	    }
	}
	
	public void saveImg(Long id) {

		try {
			String defaultImagePath = "default_user.jpg";

			BufferedImage defaultImage = ImageIO.read(getClass().getClassLoader().getResource(defaultImagePath));

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ImageIO.write(defaultImage, "jpg", outputStream);
			byte[] defaultImageBytes = outputStream.toByteArray();

			User userById = userServiceImpl.userById(id);
			userById.setProfile_image(defaultImageBytes);

			userServiceImpl.saveImage(userById);
		} catch (Exception ex) {
			System.out.println("Error saving user image:" + ex.getMessage());
		}
	}
}
