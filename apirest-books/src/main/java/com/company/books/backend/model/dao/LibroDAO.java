package com.company.books.backend.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.company.books.backend.model.Libro;

public interface LibroDAO extends CrudRepository<Libro, Long> {

}
