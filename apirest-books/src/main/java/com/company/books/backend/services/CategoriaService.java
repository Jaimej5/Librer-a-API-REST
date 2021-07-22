package com.company.books.backend.services;

import org.springframework.http.ResponseEntity;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.respoonse.CategoriaResponseRest;

public interface CategoriaService {//FIRMAS DE MÉTODOS

	public ResponseEntity<CategoriaResponseRest> buscarCategorias(); //devolverá todas las categorias
	//Response entity agrega distintos estados a los servicios HTTP

	public ResponseEntity<CategoriaResponseRest> buscarPorId(long id);

	public ResponseEntity<CategoriaResponseRest> crearCategoria(Categoria request);
	
	public ResponseEntity<CategoriaResponseRest> actualizarCategoria(Categoria request, long id);
	
	public ResponseEntity<CategoriaResponseRest> eliminarCategoria(long id);
}
