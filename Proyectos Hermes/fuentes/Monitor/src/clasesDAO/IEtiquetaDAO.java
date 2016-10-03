package clasesDAO;

import java.util.List;

import model.Etiqueta;
import model.Notificacion;

public interface IEtiquetaDAO {

	public abstract Etiqueta getEtiqueta(Integer id);
	
	public abstract int guardarEtiqueta(Etiqueta etiqueta);
	
	public abstract Etiqueta getIdEtiqueta(String etiqueta);
	
	public abstract List<Etiqueta> listaEtiquetas();
	
	public abstract void eliminarEtiqueta(Etiqueta etiqueta);

	public abstract void eliminarEtiquetaNotificacion(Etiqueta etiqueta);

	public abstract void actualizarEtiqueta(Etiqueta etiqueta, String nuevoNombre);
	
	public abstract void agregarEtiquetaNotificacion(Notificacion n, Etiqueta et);

	void eliminarEtiquetaDeNotificacion(Notificacion n, Etiqueta e);


}
