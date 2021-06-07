package br.com.eduardo.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.eduardo.dscatalog.dto.CategoryDTO;
import br.com.eduardo.dscatalog.entities.Category;
import br.com.eduardo.dscatalog.repositories.CategoryRepository;
import br.com.eduardo.dscatalog.services.exception.DatabaseException;
import br.com.eduardo.dscatalog.services.exception.ResourceNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repo;
	
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
		Page<Category> list = repo.findAll(pageRequest);
		return list.map(x -> new CategoryDTO(x));
	}
	/**
	 * 
	 * @param id
	 * @return An Optional object transformed in Category Entity
	 */
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		 Optional<Category> obj = repo.findById(id);
		 Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
		 return new CategoryDTO(entity);
		
	}
	
	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repo.save(entity);
		return new CategoryDTO(entity);
		
	}
	
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repo.getOne(id);
			entity.setName(dto.getName());
			entity = repo.save(entity);
			return new CategoryDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	
	}
	public void delete(Long id) {
		try {
			repo.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation - resource could not be removed, because it don't exists in database.");
		}
		
	}
	public Page<CategoryDTO> findAllPaged() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
