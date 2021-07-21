package com.company.books.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.respoonse.CategoriaResponseRest;
import com.company.books.backend.services.CategoriaService;

//Servicio controlador que devuelve todas las categorias
@RestController
@RequestMapping("/v1")//indica el mapeo de entrada a los metodos de la api
public class CategoriaRestController { //para los clientes que quieran consultar nuestros metodos

	@Autowired //para interceptar objetos que acceden a la BBDD y consultan
	private CategoriaService service;
	
	@GetMapping("/categorias") //método de consulta
	public ResponseEntity<CategoriaResponseRest> consultaCat() {
		
		ResponseEntity<CategoriaResponseRest> response = service.buscarCategorias();
		return response;
	}
	
	@GetMapping("/categorias/{id}")
	public ResponseEntity<CategoriaResponseRest> consultaPorId(@PathVariable long id){
		ResponseEntity<CategoriaResponseRest> response = service.buscarPorId(id);
		return response;
	}
	
	@PostMapping("/categorias")
	public ResponseEntity<CategoriaResponseRest> crearCat(@RequestBody Categoria request) {	
		ResponseEntity<CategoriaResponseRest> response = service.crearCategoria(request);
		return response;
	}
	
	@PutMapping("/categorias/{id}")
	public ResponseEntity<CategoriaResponseRest> actualizarCat(@RequestBody Categoria request, @PathVariable long id) {	
		ResponseEntity<CategoriaResponseRest> response = service.actualizarCategoria(request, id);
		return response;
	}
	
	@DeleteMapping("/categorias/{id}")
	public ResponseEntity<CategoriaResponseRest> eliminarCat(@PathVariable long id) {	
		ResponseEntity<CategoriaResponseRest> response = service.eliminarCategoria(id);
		return response;
	}
}
