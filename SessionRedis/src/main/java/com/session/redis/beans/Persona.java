package com.session.redis.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Persona implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2014283470662210198L;
	private String nombre;
	private int edad;
	private Date fechaNacimiento;
	
	
	private List<Direccion> direcciones;

	public List<Direccion> getDirecciones() {
		return direcciones;
	}

	public void setDirecciones(List<Direccion> direcciones) {
		this.direcciones = direcciones;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	

}
