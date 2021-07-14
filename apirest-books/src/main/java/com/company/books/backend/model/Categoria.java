package com.company.books.backend.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name="categorias") //es una entidad que contendrá la info de una tabla de datos "categorias"
public class Categoria implements Serializable { // los bean siempre implementan la interfaz serializable
	
	private static final long serialVersionUID = 123456789;

	@Id //id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //id autogenerado
	private long id;
	private String nombre;
	private String descripcion;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
