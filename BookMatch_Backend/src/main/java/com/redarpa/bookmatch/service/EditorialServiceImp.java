package com.redarpa.bookmatch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redarpa.bookmatch.dao.IEditorialDAO;
import com.redarpa.bookmatch.dto.Editorial;

@Service
public class EditorialServiceImp implements IEditorialService {

	@Autowired
	IEditorialDAO iEditorialDAO;

	@Override
	public List<Editorial> listAllEditorial() {
		return iEditorialDAO.findAll();
	}

	@Override
	public Editorial saveEditorial(Editorial editorial) {
		return iEditorialDAO.save(editorial);
	}

	@Override
	public Editorial editorialById(Long id) {
		return iEditorialDAO.findById(id).get();
	}

	@Override
	public Editorial updateEditorial(Editorial editorial) {
		return iEditorialDAO.save(editorial);
	}

	@Override
	public void deleteEditorial(Long id) {
		iEditorialDAO.deleteById(id);
	}

}
