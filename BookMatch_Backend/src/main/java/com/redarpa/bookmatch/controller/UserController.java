package com.redarpa.bookmatch.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.redarpa.bookmatch.dto.User;
import com.redarpa.bookmatch.service.UserServiceImp;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserServiceImp userServiceImpl;

	@GetMapping("/users")
	public List<User> listUsers() {
		return userServiceImpl.listAllUsers();
	}

	@PostMapping("/user")
	public User saveUser(@RequestParam("image") MultipartFile imageFile, @RequestParam("user") User user) throws IOException {
		User user1;
		if (imageFile != null && !imageFile.isEmpty()) {
			user1 = userServiceImpl.saveUserWithImage(user, imageFile.getBytes());
		} else {
			user1 = userServiceImpl.saveUser(user);
			saveImg(user1.getId_user());
		}
		return user1;
	}
	
	@GetMapping("/user/{id}")
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

	@PutMapping("/user/image/{id}")
	public User saveProfileImage(@PathVariable(name = "id") Long id, @RequestParam("image") MultipartFile imageFile)
			throws IOException {

		BufferedImage bufferedImage = ImageIO.read(imageFile.getInputStream());

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "jpg", outputStream);
		byte[] imageBytes = outputStream.toByteArray();

		User userById = userServiceImpl.userById(id);
		userById.setProfile_image(imageBytes);

		userServiceImpl.saveImage(userById);

		return userById;
	}

	@PutMapping("/user/{id}")
	public User updateUser(@PathVariable(name = "id") Long id, @RequestParam("image") MultipartFile imageFile, @RequestParam("user") User user) throws IOException {
	    User selectedUser = userServiceImpl.userById(id);
	    selectedUser.setUsername(user.getUsername());
	    selectedUser.setEmail(user.getEmail());
	    selectedUser.setPass(user.getPass());

	    User updatedUser;
	    if (imageFile != null && !imageFile.isEmpty()) {
	        updatedUser = userServiceImpl.updateUserWithImage(selectedUser, imageFile.getBytes());
	    } else {
	        updatedUser = userServiceImpl.updateUser(selectedUser);
	        saveImg(updatedUser.getId_user());
	    }

	    if (updatedUser.getProfile_image() == null || updatedUser.getProfile_image().equals(null)
	            || updatedUser.getProfile_image().equals("")) {
	        saveImg(updatedUser.getId_user());
	    }

	    return updatedUser;
	}

	@DeleteMapping("/user/{id}")
	public void deleteUser(@PathVariable(name = "id") Long id) {
		userServiceImpl.deleteUser(id);
	}
}
