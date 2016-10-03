package model;

import java.io.Serializable;

public class Ninio implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String nombreYape;
	
	public Ninio(){}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombreYape;
	}
	public void setNombre(String nombre) {
		this.nombreYape = nombre;
	}
	
	public String toString() {
		return "id: " + id + " Nombre y Apellido: " + nombreYape;
	}
	
}
