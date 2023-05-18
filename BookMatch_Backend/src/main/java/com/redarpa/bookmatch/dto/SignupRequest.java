/**
 * 
 */
package com.redarpa.bookmatch.dto;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author Marc
 *
 */
public class SignupRequest {

	// Attributes
		@NotBlank
		@Size(min = 3, max = 20)
		private String username;

		@NotBlank
		@Size(max = 50)
		@Email
		private String email;

		private Set<String> role;

		@NotBlank
		@Size(min = 6, max = 40)
		private String password;

		// Getters
		public String getUsername() {
			return username;
		}

		public String getEmail() {
			return email;
		}

		public String getPassword() {
			return password;
		}

		public Set<String> getRole() {
			return this.role;
		}

		// Setters
		public void setUsername(String username) {
			this.username = username;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public void setRole(Set<String> role) {
			this.role = role;
		}
}
