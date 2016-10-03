package clasesDAO;

import java.util.List;

import model.Contenido;


public interface IContenidoDAO {

	public abstract Contenido getContenido(Integer id);
	
	public abstract Contenido getIdContenido(String contenido);
	
	public abstract void guardarContenido(Contenido contenido);
	
	public abstract List<Contenido> listaContenidos();

}
