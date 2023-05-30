package com.redarpa.bookmatch.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redarpa.bookmatch.dto.Editorial;
import com.redarpa.bookmatch.service.EditorialServiceImp;

/**
 * @author RedArpa - BookMatch
 *
 */

@CrossOrigin(origins = "*", maxAge = 3600) // Allows requests from all origins with 1 hour cache
@RestController
@RequestMapping("/api")
public class EditorialController {

	@Autowired
	EditorialServiceImp editorialServiceImp;

	/**
	 * Get all editorials
	 * @return
	 */
	@GetMapping("/editorials")
	public List<Editorial> listEditorial() {
		return editorialServiceImp.listAllEditorial();
	}

	/**
	 * Add new editorial
	 * @param editorial
	 * @return the editorial added
	 */
	@PostMapping("/editorials")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public Editorial saveEditorial(@RequestBody Editorial editorial) {
		return editorialServiceImp.saveEditorial(editorial);
	}
	
	/**
	 * Get editorial by name
	 * @param name
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/editorial/name/{name}")
	public Editorial editorialByTitle(@PathVariable(name = "name") String name) throws IOException {
		return editorialServiceImp.editorialByName(name);
	}

	/**
	 * Get editorial by id
	 * @param id
	 * @return
	 */
	@GetMapping("/editorials/{id}")
	public Editorial editorialById(@PathVariable(name = "id") Long id) {

		Editorial editorialById = new Editorial();

		editorialById = editorialServiceImp.editorialById(id);

		System.out.println("Editorial by Id: " + editorialById);

		return editorialById;
	}

	/**
	 * Edit editorial info
	 * @param id
	 * @param editorial
	 * @return
	 */
	@PutMapping("/editorials/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Editorial updateEditorial(@PathVariable(name = "id") Long id, @RequestBody Editorial editorial) {

		Editorial selectedEditorial = new Editorial();
		Editorial updatedEditorial = new Editorial();

		selectedEditorial = editorialServiceImp.editorialById(id);

		selectedEditorial.setId_editorial(editorial.getId_editorial());
		selectedEditorial.setName_editorial(editorial.getName_editorial());

		updatedEditorial = editorialServiceImp.updateEditorial(selectedEditorial);

		System.out.println("Updated editorial is: " + updatedEditorial);

		return updatedEditorial;
	}

	/**
	 * Delete editorial by id
	 * @param id
	 */
	@DeleteMapping("/editorial/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteEditorial(@PathVariable(name = "id") Long id) {
		editorialServiceImp.deleteEditorial(id);
	}

}
