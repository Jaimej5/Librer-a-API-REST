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

	@Override
	@Transactional(readOnly = true)//maneja transacción a BBDD
	public ResponseEntity<CategoriaResponseRest> buscarPorId(long id) {
		log.info("Inicio método buscar por ID");
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> list = new ArrayList<>();
		
		try {
			Optional<Categoria> categoria = categoriaDAO.findById(id);
			
			if(categoria.isPresent()) {
				list.add(categoria.get());
				response.getCategoriaResponse().setCategoria(list);
			} else {
				log.error("Error al consultar categoría");
				response.setMetadata("Respuesta BAD", "-1", "Categoría no encontrada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			log.error("Error al consultar categoría");
			response.setMetadata("Respuesta BAD", "-1", "Error al consultar categoría");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.setMetadata("Respuesta OK", "00", "Respuesta exitosa");
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> crearCategoria(Categoria categoria) {

		log.info("Inicio método crear categoría");
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> list = new ArrayList<>();
		
		try {
			Categoria categoriaGuardada = categoriaDAO.save(categoria);
			
			if(categoriaGuardada!=null) {
				list.add(categoriaGuardada);
				response.getCategoriaResponse().setCategoria(list);
			} else {
				log.error("Error al crear categoría");
				response.setMetadata("Respuesta BAD", "-1", "Categoría no guardada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			log.error("Error al crear categoría");
			response.setMetadata("Respuesta BAD", "-1", "Error al crear categoría");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.setMetadata("Respuesta OK", "00", "Categoría creada");
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> actualizarCategoria(Categoria categoria, long id) {

		log.info("Inicio del método actualizar");
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> list = new ArrayList<>();
		
		try {
			
			Optional<Categoria> categoriaBuscada = categoriaDAO.findById(id);
			
			if(categoriaBuscada.isPresent()) {
				categoriaBuscada.get().setNombre(categoria.getNombre());
				categoriaBuscada.get().setDescripcion(categoria.getDescripcion());
				
				Categoria categoriaActualizada = categoriaDAO.save(categoriaBuscada.get()); //la guardamos
				
				if (categoriaActualizada!=null) {
					response.setMetadata("Respuesta OK", "00", "Categoría actualizada");
					list.add(categoriaActualizada);
					response.getCategoriaResponse().setCategoria(list);
				}else {
					log.error("Error al actualizar la categoría");
					response.setMetadata("Respuesta BAD", "-1", "Categoría no actualizada");
					return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
				}
			}else {
				log.error("Error al actualizar la categoría");
				response.setMetadata("Respuesta BAD", "-1", "Categoría no actualizada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			log.error("Error al actualizar la categoría", e.getMessage());
			e.getStackTrace();
			response.setMetadata("Respuesta BAD", "-1", "Categoría no actualizada");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);		
		}
		
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
		
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> eliminarCategoria(long id) {
		log.info("Inicio del método eliminar");
		CategoriaResponseRest response = new CategoriaResponseRest();
		
		try {
			categoriaDAO.deleteById(id);
			response.setMetadata("Respuesta OK", "00", "Categoría eliminada");
		} catch (Exception e) {
			log.error("Error al eliminar la categoría", e.getMessage());
			e.getStackTrace();
			response.setMetadata("Respuesta BAD", "-1", "Categoría no eliminada");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
	}

}
