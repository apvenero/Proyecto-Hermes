package clasesDAOImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Etiqueta;
import model.Notificacion;
import clasesDAO.IEtiquetaDAO;
import database.OperacionesDB;

public class EtiquetaDAO implements IEtiquetaDAO {
	
	private OperacionesDB db = new OperacionesDB();

	@Override
	public Etiqueta getEtiqueta(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int guardarEtiqueta(Etiqueta etiqueta) {
		try {
			String query = "INSERT INTO etiqueta (descripcion)" +
				"VALUES ('"+etiqueta.getDescripcion()+"')";
			return db.actualizar(query);			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return 0;
		}	
	}

	@Override
	public List<Etiqueta> listaEtiquetas() {
		List<Etiqueta> lista =  new ArrayList<Etiqueta>();
		ResultSet rs = db.consultar("select * from etiqueta");
		try {
			while (rs.next() ) {
				try {
					Etiqueta etiqueta = new Etiqueta();
					etiqueta.setId(rs.getInt("id"));
					etiqueta.setDescripcion(rs.getString("descripcion"));
				    lista.add(etiqueta);
				    
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
	public void eliminarEtiqueta(Etiqueta etiqueta) {
		try {
			String query = "DELETE FROM etiqueta WHERE (etiqueta.descripcion= '"+etiqueta.getDescripcion()+"')";
			db.actualizar(query);
			String query2= "DELETE FROM notificacion_etiqueta WHERE (notificacion_etiqueta.idEtiqueta= (select id from etiqueta where descripcion = '"+etiqueta.getDescripcion()+"'))";
			db.actualizar(query2);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}			
	}
	
	@Override
	public void eliminarEtiquetaNotificacion(Etiqueta etiqueta) {
		try {
			String query= "DELETE FROM notificacion_etiqueta WHERE (notificacion_etiqueta.idEtiqueta= (select id from etiqueta where descripcion = '"+etiqueta.getDescripcion()+"'))";
			db.actualizar(query);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}			
	}
	
	@Override
	public void eliminarEtiquetaDeNotificacion(Notificacion n ,Etiqueta et) {
		try {
			String query= "DELETE FROM notificacion_etiqueta WHERE (notificacion_etiqueta.idEtiqueta = '"+et.getId()+"' and notificacion_etiqueta.idNotificacion = '"+ n.getId()+"')";
			db.actualizar(query);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}			
	}
	
	public void agregarEtiquetaNotificacion(Notificacion n, Etiqueta et){
		try{
			String query = "INSERT INTO notificacion_etiqueta (idNotificacion, idEtiqueta)" +
					"VALUES ('"+n.getId()+"','"+et.getId()+"' )";
			db.actualizar(query);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	public void actualizarEtiqueta(Etiqueta etiqueta, String nuevoNombre) {
		try {
			String query = "UPDATE etiqueta SET descripcion='"+nuevoNombre+"' WHERE (descripcion= '"+etiqueta.getDescripcion()+"')";
			db.actualizar(query);	
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}	
	}

	@Override
	public Etiqueta getIdEtiqueta(String descripcion) {
		ResultSet rs = db.consultar("select * from etiqueta where descripcion='"+descripcion+"'");
		Etiqueta et=null;
		try {
				et = new Etiqueta();
				et.setId(rs.getInt("id"));
				et.setDescripcion(rs.getString("descripcion"));	
			    db.cerrarConexion();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
		return et;
	}
	
	


}
