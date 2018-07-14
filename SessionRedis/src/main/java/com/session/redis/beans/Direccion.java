package com.session.redis.beans;

import java.io.Serializable;

public class Direccion implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3704215152545508188L;
	private String calle;
	private String colonia;
	private String numero;
	
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getColonia() {
		return colonia;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	

}
