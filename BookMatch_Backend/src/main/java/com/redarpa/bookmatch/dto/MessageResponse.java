/**
 * 
 */
package com.redarpa.bookmatch.dto;

/**
 * @author RedArpa - BookMatch
 *
 */

public class MessageResponse {

	// Attributes
		private String message;

		// Constructor
		public MessageResponse(String message) {
			this.message = message;
		}

		// Getter
		public String getMessage() {
			return message;
		}

		// Setter
		public void setMessage(String message) {
			this.message = message;
		}
}
