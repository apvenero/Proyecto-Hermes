package clasesDAOImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import clasesDAO.INotificacionDAO;
import database.OperacionesDB;
import model.Categoria;
import model.Contenido;
import model.Contexto;
import model.Etiqueta;
import model.Ninio;
import model.Notificacion;


public class NotificacionDAO implements INotificacionDAO{

	private OperacionesDB db = new OperacionesDB();

	@Override
	public Notificacion getNotificacion(Integer id) {
		String query = " select notificacion.id, notificacion.fechaEnvio, contenido.descripcion, contexto.descripcion, categoria.descripcion, ninio.nombreyape " +
				" from notificacion " +
				" inner join contenido on (notificacion.idContenido=contenido.id) " +
				" inner join contexto  on (notificacion.idContexto=contexto.id) " +
				" inner join categoria on (notificacion.idCategoria=categoria.id) " +
				" inner join ninio     on (notificacion.idNinio=ninio.id)"+
				" where notificacion.id = '"+id+"'";
		ResultSet rs = db.consultar(query);
		Notificacion notificacion =null;

		try {
			
					notificacion = new Notificacion();
					notificacion.setId(rs.getInt(1));
					
					notificacion.setFechaEnvio(rs.getString(2));
					
					Contenido ct = new Contenido();
					ct.setDescripcion(rs.getString(3));
					notificacion.setContenido(ct);
					
					Contexto cx = new Contexto();
					cx.setDescripcion(rs.getString(4));
					notificacion.setContexto(cx);
					
					Categoria ca = new Categoria();
					ca.setDescripcion(rs.getString(5));
					notificacion.setCategoria(ca);
					
					Ninio ni = new Ninio();
					ni.setNombre(rs.getString(6));
					notificacion.setNinio(ni);
				    db.cerrarConexion();


					//cargo las etiquetas
					ResultSet query_etiquetas = db.consultar("select etiqueta.id, etiqueta.descripcion " +
									" from notificacion_etiqueta "+
									" inner join etiqueta on (notificacion_etiqueta.idEtiqueta = etiqueta.id)" + 
									" where notificacion_etiqueta.idNotificacion = '"+notificacion.getId()+"'");
										
					while(query_etiquetas.next()){
						Etiqueta et = new Etiqueta();
						et.setId(query_etiquetas.getInt(1));
						et.setDescripcion(query_etiquetas.getString(2));
						notificacion.addEtiqueta(et);
					}
				    db.cerrarConexion();


		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return notificacion;	
	}

	
	@Override
	public synchronized int guardarNotificacion(Notificacion n) {
		try {
			String query = "INSERT INTO notificacion (idContenido, idContexto, idCategoria,idNinio)" +
				"VALUES ('"+n.getIdContenido()+"','"+n.getIdContexto()+"','"+n.getIdCategoria()+"','"+n.getIdNinio()+"' )";
			int not_id = db.actualizar(query);
			
			for (Etiqueta e: n.getEtiquetas()){
				String query2 = "INSERT INTO notificacion_etiqueta (idNotificacion, idEtiqueta)" +
						"VALUES ('"+not_id+"','"+e.getId()+"' )";
				db.actualizar(query2);
				}
				return not_id;
			} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return 0;
		}	
	}
	
	@Override
	public List<Notificacion> listaNotificaciones() {
		List<Notificacion> lista =  new ArrayList<Notificacion>();
		ResultSet rs = db.consultar(" select notificacion.id, notificacion.fechaEnvio, contenido.descripcion, contexto.descripcion, categoria.descripcion, ninio.nombreyape " +
									" from notificacion " +
									" inner join contenido on (notificacion.idContenido=contenido.id) " +
									" inner join contexto  on (notificacion.idContexto=contexto.id) " +
									" inner join categoria on (notificacion.idCategoria=categoria.id) " +
									" inner join ninio     on (notificacion.idNinio=ninio.id)");
		try {
			while (rs.next() ) {
				try {
					Notificacion notificacion = new Notificacion();
					notificacion.setId(rs.getInt("id"));
					
					notificacion.setFechaEnvio(rs.getString(2));
					
					Contenido ct = new Contenido();
					ct.setDescripcion(rs.getString(3));
					notificacion.setContenido(ct);
					
					Contexto cx = new Contexto();
					cx.setDescripcion(rs.getString(4));
					notificacion.setContexto(cx);
					
					Categoria ca = new Categoria();
					ca.setDescripcion(rs.getString(5));
					notificacion.setCategoria(ca);
					
					Ninio ni = new Ninio();
					ni.setNombre(rs.getString(6));
					notificacion.setNinio(ni);
					
					//cargo las etiquetas
					ResultSet query_etiquetas = db.consultar("select etiqueta.id, etiqueta.descripcion " +
									" from notificacion_etiqueta "+
									" inner join etiqueta on (notificacion_etiqueta.idEtiqueta = etiqueta.id)" + 
									" where notificacion_etiqueta.idNotificacion = '"+notificacion.getId()+"'");
										
					while(query_etiquetas.next()){
						Etiqueta et = new Etiqueta();
						et.setId(query_etiquetas.getInt(1));
						et.setDescripcion(query_etiquetas.getString(2));
						notificacion.addEtiqueta(et);
					}
					
					//agrego la notificacion a la lista
					lista.add(notificacion);

				} catch (Exception e) {
					System.err.println(e.getClass().getName() + ": " + e.getMessage());
				}
			  }
		    db.cerrarConexion();
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return lista;	
	}

	@Override
	public List<Notificacion> filtrarNotificacion(Notificacion n,Date fechaDesde, Date fechaHasta) {
		List<Notificacion> result =  new ArrayList<Notificacion>();
		
		for (Notificacion noti :listaNotificaciones()){
			boolean contenido = true;
			if(n.getContenido()!=null){
				if (n.getContenido().getDescripcion().equals(noti.getContenido().getDescripcion())){ 			
					contenido = true;
				}else{
					contenido=false;
				}
				
			}
			boolean categoria = true;
			if(n.getCategoria()!=null){
				if (n.getCategoria().getDescripcion().equals(noti.getCategoria().getDescripcion())){ 
					categoria = true;
				}else{
					categoria=false;
				}
			}
			boolean contexto = true;
			if(n.getContexto()!=null){
				if (n.getContexto().getDescripcion().equals(noti.getContexto().getDescripcion())){ 
					contexto = true;
				}else{
					contexto=false;
				}
			}
			boolean niño = true;
			if(n.getNinio()!=null){
				if (n.getNinio().getNombre().equals(noti.getNinio().getNombre())){
					niño = true;
				}else{
					niño=false;
				}
			}
			boolean etiqueta = true;
			if(n.getEtiqueta()!=null){
				etiqueta=false;
				for(int i =0; i<noti.getEtiquetas().size(); i++){
					
					if (n.getEtiqueta().getDescripcion().equals(noti.getEtiquetas().get(i).getDescripcion())){ 
						etiqueta = true;
						i=noti.getEtiquetas().size()+1;
					}else{
						etiqueta=false;
					}
					System.out.println("cumple: " +etiqueta);
				}
			}
			boolean fechas = true;
			if(fechaDesde !=null && fechaHasta !=null){
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date;
				try {
					date = formatter.parse(noti.getFechaEnvio());
					System.out.println("Desde: " + fechaDesde + "   Hasta: "+ fechaHasta + "   Fecha: " + date);
					System.out.println("Desde :"+fechaDesde.before(date) + " Hasta: " + fechaHasta.after(date) );
					
					if (fechaDesde.before(date) && fechaHasta.after(date)) { 
						System.out.println("cumple fechas");
						fechas = true;
					}else{
						System.out.println("no cumple fechas");
						fechas=false;
					}
				}catch(Exception e){
					
				}
				
			}
			if(contenido && categoria && contexto && niño && etiqueta && fechas){
				result.add(noti);
			}
		}//for
		System.out.println("Resultados :" +result.size());
		return result;
	}
	

}
