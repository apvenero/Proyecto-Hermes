package clasesDAO;

import java.util.Date;
import java.util.List;

import model.Notificacion;

public interface INotificacionDAO {

	public abstract Notificacion getNotificacion(Integer id);
	
	public abstract int guardarNotificacion(Notificacion n);
	
	public abstract List<Notificacion> listaNotificaciones();
	
	public abstract List<Notificacion> filtrarNotificacion(Notificacion n, Date fechaDesde, Date fechaHasta);
}
