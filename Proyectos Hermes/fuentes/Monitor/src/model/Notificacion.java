package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Notificacion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String fechaEnvio;
	private int idContenido,idContexto, idCategoria,idNinio;
	private Contenido contenido;
	private Contexto contexto;
	private Categoria categoria;
	private Etiqueta etiqueta;
	private Ninio ninio;
	private List<Etiqueta> etiquetas;
	
	public Notificacion(){
		etiquetas=new ArrayList<Etiqueta>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void addEtiqueta(Etiqueta e){
		etiquetas.add(e);
		
	}

	public int getIdContenido() {
		return idContenido;
	}

	public void setIdContenido(int idContenido) {
		this.idContenido = idContenido;
	}

	public int getIdContexto() {
		return idContexto;
	}

	public void setIdContexto(int idContexto) {
		this.idContexto = idContexto;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public int getIdNinio() {
		return idNinio;
	}

	public void setIdNinio(int idNinio) {
		this.idNinio = idNinio;
	}

	
	public List<Etiqueta> getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(List<Etiqueta> etiquetas) {
		this.etiquetas = etiquetas;
	}

	
	
	public String getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(String fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
	
	public Contenido getContenido() {
		return contenido;
	}

	public void setContenido(Contenido contenido) {
		this.contenido = contenido;
	}

	public Contexto getContexto() {
		return contexto;
	}

	public void setContexto(Contexto contexto) {
		this.contexto = contexto;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Ninio getNinio() {
		return ninio;
	}

	public void setNinio(Ninio ninio) {
		this.ninio = ninio;
	}
	
	

	public Etiqueta getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(Etiqueta etiqueta) {
		this.etiqueta = etiqueta;
	}

	public String toString() {
		return fechaEnvio+" "+contenido+" " + contexto+" "+ categoria+" "+ ninio +""+ etiqueta;
	}

	
	
	
}
