package com.company.books.backend.respoonse;

import java.util.*;

import com.company.books.backend.model.Categoria;

public class CategoriaResponse { //objetos de respuesta al extraer de CRUD

	private List<Categoria> categoria;

	public List<Categoria> getCategoria() {
		return categoria;
	}

	public void setCategoria(List<Categoria> categoria) {
		this.categoria = categoria;
	}
	
}
