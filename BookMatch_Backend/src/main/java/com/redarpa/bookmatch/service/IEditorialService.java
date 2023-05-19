package com.redarpa.bookmatch.service;

import java.util.List;

import com.redarpa.bookmatch.dto.Editorial;

public interface IEditorialService {
	
	public List<Editorial> listAllEditorial(); // List All editorial

	public Editorial saveEditorial(Editorial editorial); // Save editorial

	public Editorial editorialById(Long id); // Read editorial data

	public Editorial updateEditorial(Editorial editorial); // Update editorial

	public void deleteEditorial(Long id); // Delete an editorial
	
}
