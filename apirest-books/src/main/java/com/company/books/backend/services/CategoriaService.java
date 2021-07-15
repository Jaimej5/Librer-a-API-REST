package com.company.books.backend.services;

import org.springframework.http.ResponseEntity;

import com.company.books.backend.respoonse.CategoriaResponseRest;

public interface CategoriaService {

	public ResponseEntity<CategoriaResponseRest> buscarCategorias(); //devolverá una categoria REST
	//Response entitity agrega distintos estados a los servicios HTTP
}
