package com.redarpa.bookmatch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

@RestController
@RequestMapping("/api")
public class EditorialController {

	@Autowired
	EditorialServiceImp editorialServiceImp;

	@GetMapping("/editorials")
	public List<Editorial> listEditorial() {
		return editorialServiceImp.listAllEditorial();
	}

	@PostMapping("/editorials")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public Editorial saveEditorial(@RequestBody Editorial editorial) {
		return editorialServiceImp.saveEditorial(editorial);
	}

	@GetMapping("/editorials/{id}")
	public Editorial editorialById(@PathVariable(name = "id") Long id) {

		Editorial editorialById = new Editorial();

		editorialById = editorialServiceImp.editorialById(id);

		System.out.println("Editorial by Id: " + editorialById);

		return editorialById;
	}

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

	@DeleteMapping("/editorial/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteEditorial(@PathVariable(name = "id") Long id) {
		editorialServiceImp.deleteEditorial(id);
	}

}
