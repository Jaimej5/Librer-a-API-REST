package com.company.books.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.books.backend.model.Libro;
import com.company.books.backend.model.dao.LibroDAO;
import com.company.books.backend.respoonse.LibroResponseRest;

@Service
public class LibroServiceIMPL implements LibroService {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(LibroServiceIMPL.class);
	
	@Autowired
	private LibroDAO libroDAO;
	
	@Override
	@Transactional(readOnly=true)
	public ResponseEntity<LibroResponseRest> buscarLibros() {
		log.info("inicio método buscarLibros()");
		LibroResponseRest response = new LibroResponseRest();
		
		try {
			
			//ir a la BBDD para traer todas sus categorias
			List<Libro> libro = (List<Libro>) libroDAO.findAll();
			response.getLibroResponse().setLibro(libro);
			response.setMetadata("Respuesta OK", "00", "Respuesta exitosa");
			
		} catch (Exception e) {//logs mal
			
			response.setMetadata("Respuesta BAD", "-1", "Error al consultar libros");
			log.error("error al consultar libros: ", e.getMessage());
			e.getStackTrace();
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //500
		}
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK); //200
	}

	@Override
	public ResponseEntity<LibroResponseRest> buscarPorId(long id) {
		log.info("Inicio método buscar por ID");
		LibroResponseRest response = new LibroResponseRest();
		List<Libro> list = new ArrayList<>();
		
		try {
			Optional<Libro> libro = libroDAO.findById(id);
			
			if(libro.isPresent()) {
				list.add(libro.get());
				response.getLibroResponse().setLibro(list);
			} else {
				log.error("Error al consultar categoría");
				response.setMetadata("Respuesta BAD", "-1", "Libro no encontrado");
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			log.error("Error al consultar categoría");
			response.setMetadata("Respuesta BAD", "-1", "Error al consultar libro");
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.setMetadata("Respuesta OK", "00", "Respuesta exitosa");
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> crear(Libro libro) {
		log.info("Inicio método crear libro");
		LibroResponseRest response = new LibroResponseRest();
		List<Libro> list = new ArrayList<>();
		
		try {
			Libro libroGuardada = libroDAO.save(libro);
			
			if(libroGuardada!=null) {
				list.add(libroGuardada);
				response.getLibroResponse().setLibro(list);
			} else {
				log.error("Error al crear el libro");
				response.setMetadata("Respuesta BAD", "-1", "Libro no guardado");
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			log.error("Error al crear libro");
			response.setMetadata("Respuesta BAD", "-1", "Error al crear libro");
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.setMetadata("Respuesta OK", "00", "Libro creado");
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> actualizarLibro(Libro libro, long id) {
		log.info("Inicio del método actualizar");
		LibroResponseRest response = new LibroResponseRest();
		List<Libro> list = new ArrayList<>();
		
		try {
			
			Optional<Libro> libroBuscada = libroDAO.findById(id);
			
			if(libroBuscada.isPresent()) {
				libroBuscada.get().setNombre(libro.getNombre());
				libroBuscada.get().setDescripcion(libro.getDescripcion());
				
				Libro libroActualizada = libroDAO.save(libroBuscada.get()); //la guardamos
				
				if (libroActualizada!=null) {
					response.setMetadata("Respuesta OK", "00", "Libro actualizado");
					list.add(libroActualizada);
					response.getLibroResponse().setLibro(list);
				}else {
					log.error("Error al actualizar el libro");
					response.setMetadata("Respuesta BAD", "-1", "Libro no actualizado");
					return new ResponseEntity<LibroResponseRest>(response, HttpStatus.BAD_REQUEST);
				}
			}else {
				log.error("Error al actualizar el libro");
				response.setMetadata("Respuesta BAD", "-1", "Libro no actualizado");
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			log.error("Error al actualizar el libro", e.getMessage());
			e.getStackTrace();
			response.setMetadata("Respuesta BAD", "-1", "Libro");
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);		
		}
		
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
		
	}

	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> eliminarCategoria(long id) {
		log.info("Inicio del método eliminar");
		LibroResponseRest response = new LibroResponseRest();
		
		try {
			libroDAO.deleteById(id);
			response.setMetadata("Respuesta OK", "00", "Libro eliminado");
		} catch (Exception e) {
			log.error("Error al eliminar el libro", e.getMessage());
			e.getStackTrace();
			response.setMetadata("Respuesta BAD", "-1", "Categoría eliminado");
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
	}

}
