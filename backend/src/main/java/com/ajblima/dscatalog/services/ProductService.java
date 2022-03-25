package com.ajblima.dscatalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajblima.dscatalog.dto.CategoryDTO;
import com.ajblima.dscatalog.dto.ProductDTO;
import com.ajblima.dscatalog.entities.Category;
import com.ajblima.dscatalog.entities.Product;
import com.ajblima.dscatalog.repositories.CategoryRepository;
import com.ajblima.dscatalog.repositories.ProductRepository;
import com.ajblima.dscatalog.services.exceptions.DataBaseException;
import com.ajblima.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(Pageable pageable) {
		Page<Product> list = repository.findAll(pageable);

		return list.map(x -> new ProductDTO(x));

	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);

		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {

		try {
			Product entity = repository.getById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ProductDTO(entity);

		} catch (javax.persistence.EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}

	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity Violation");
		}
	}
	
	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setImgurl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
		
		entity.getCategories().clear();
		
		for (CategoryDTO catDto: dto.getCategories()) {
			Category category = categoryRepository.getById(catDto.getId());
			entity.getCategories().add(category);
		}

	}

}