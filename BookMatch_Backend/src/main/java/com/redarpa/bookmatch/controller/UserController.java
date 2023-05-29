package com.redarpa.bookmatch.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redarpa.bookmatch.dto.User;
import com.redarpa.bookmatch.service.UserDetailsImpl;
import com.redarpa.bookmatch.service.UserServiceImp;

/**
 * @author RedArpa - BookMatch
 *
 */

@CrossOrigin(origins = "*", maxAge = 3600) // Allows requests from all origins with 1 hour cache
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserServiceImp userServiceImpl;
	
	@Autowired
	PasswordEncoder encoder;

	@GetMapping("/users")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public List<User> listUsers() {
		return userServiceImpl.listAllUsers();
	}
	
	@GetMapping("/user/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public User userById(@PathVariable(name = "id") Long id) {

		User userById = new User();

		userById = userServiceImpl.userById(id);

		if (userById.getProfile_image() == null || userById.getProfile_image().equals(null)
				|| userById.getProfile_image().equals("")) {
			saveImg(userById.getId_user());
		}

		return userById;
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
			System.out.println("Ocurrió un error al asignar la imagen por defecto: " + ex.getMessage());
		}
	}

	@PutMapping(value = "/user/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public User updateUser(@PathVariable(name = "id") Long id, @RequestParam(value = "image", required = false) MultipartFile imageFile, @RequestPart("user") String user) throws IOException {
	    User selectedUser = userServiceImpl.userById(id);

	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
	        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
	        Long userId = userDetails.getId();

	        // Comparar el ID del usuario actual con el ID del usuario del libro
	        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
	                && !selectedUser.getId_user().equals(userId)) {
	            throw new AccessDeniedException("No tienes permiso para modificar este perfil");
	        }
	    }

	    ObjectMapper objectMapper = new ObjectMapper();
	    User updatedUser = objectMapper.readValue(user, User.class);

	    selectedUser.setUsername(updatedUser.getUsername());

	    if (imageFile != null && !imageFile.isEmpty()) {
	        selectedUser = userServiceImpl.updateUserWithImage(selectedUser, imageFile.getBytes());
	    } else {
	        selectedUser = userServiceImpl.updateUser(selectedUser);
	        saveImg(selectedUser.getId_user());
	    }

	    if (selectedUser.getProfile_image() == null || selectedUser.getProfile_image().equals(null)
	            || selectedUser.getProfile_image().equals("")) {
	        saveImg(selectedUser.getId_user());
	    }

	    return selectedUser;
	}
	
	@PutMapping(value = "/user/{id}/password")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public User updatePassword(@PathVariable(name = "id") Long id, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
	    User selectedUser = userServiceImpl.userById(id);

	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
	        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
	        Long userId = userDetails.getId();

	        // Comparar el ID del usuario actual con el ID del usuario seleccionado
	        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
	                && !selectedUser.getId_user().equals(userId)) {
	            throw new AccessDeniedException("No tienes permiso para modificar este perfil");
	        }
	    }

	    // Verificar si la contraseña anterior coincide
	    if (!encoder.matches(oldPassword, selectedUser.getPass())) {
	        throw new IllegalArgumentException("La contraseña anterior no coincide");
	    }

	    // Encriptar y actualizar la nueva contraseña
	    String newPasswordHash = encoder.encode(newPassword);
	    selectedUser.setPass(newPasswordHash);

	    User updatedUser = userServiceImpl.updateUser(selectedUser);

	    return updatedUser;
	}


	@DeleteMapping("/user/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteUser(@PathVariable(name = "id") Long id) {
		userServiceImpl.deleteUser(id);
	}
}
