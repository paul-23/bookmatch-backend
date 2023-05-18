/**
 * 
 */
package com.redarpa.bookmatch.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author Marc
 *
 */
@Entity
@Table(name = "roles")
public class Role {

	// Attributes
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;

		// Define enum of type STRING
		@Enumerated(EnumType.STRING)
		@Column(length = 20)
		private ERole name;

		// Constructor
		public Role() {

		}

		// Getters
		public Role(ERole name) {
			this.name = name;
		}

		public Integer getId() {
			return id;
		}

		public ERole getName() {
			return name;
		}

		// Setters
		public void setId(Integer id) {
			this.id = id;
		}

		public void setName(ERole name) {
			this.name = name;
		}
}
