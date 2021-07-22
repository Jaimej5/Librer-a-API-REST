package com.company.books.backend.services;

import org.springframework.http.ResponseEntity;

import com.company.books.backend.model.Libro;
import com.company.books.backend.respoonse.LibroResponseRest;

public interface LibroService {

	public ResponseEntity<LibroResponseRest> buscarLibros();

	public ResponseEntity<LibroResponseRest> buscarPorId(long id);

	public ResponseEntity<LibroResponseRest> crear(Libro request);

	public ResponseEntity<LibroResponseRest> actualizarLibro(Libro request, long id);

	public ResponseEntity<LibroResponseRest> eliminarCategoria(long id);

}
