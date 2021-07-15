package com.company.books.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
}
