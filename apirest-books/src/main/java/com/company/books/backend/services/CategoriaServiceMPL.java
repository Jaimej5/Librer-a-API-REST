package com.company.books.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.model.dao.CategoriaDAO;
import com.company.books.backend.respoonse.CategoriaResponseRest;

import java.util.*;

import org.slf4j.LoggerFactory;


import org.slf4j.Logger;

@Service
public class CategoriaServiceMPL implements CategoriaService{ //el servicio categoria con su implementacion correspondiente
	
	//mensajes del estado de la app
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(CategoriaServiceMPL.class);
	//Hace referencia a CRUD y con autowired inyectamos dependencias
	@Autowired
	private CategoriaDAO categoriaDAO;
	
	@Override
	@Transactional(readOnly=true)//va a ir a la bbdd a leer
	public ResponseEntity<CategoriaResponseRest> buscarCategorias() {
		log.info("inicio método buscarCategorias()");
		//respuesta
		CategoriaResponseRest response = new CategoriaResponseRest();
		try {
			
			//ir a la BBDD para traer todas sus categorias
			List<Categoria> categoria = (List<Categoria>) categoriaDAO.findAll();
			response.getCategoriaResponse().setCategoria(categoria);
			response.setMetadata("Respuesta OK", "00", "Respuesta exitosa");
			
		} catch (Exception e) {//logs mal
			
			response.setMetadata("Respuesta BAD", "-1", "Error al consultar categorias");
			log.error("error al consultar categorias: ", e.getMessage());
			e.getStackTrace();
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //500
		}
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK); //200
	}

}
