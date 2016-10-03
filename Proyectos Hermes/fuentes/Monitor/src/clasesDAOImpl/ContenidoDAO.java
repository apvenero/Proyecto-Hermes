package clasesDAOImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Contenido;
import clasesDAO.IContenidoDAO;
import database.OperacionesDB;

public class ContenidoDAO implements IContenidoDAO {

	private OperacionesDB db = new OperacionesDB();

	@Override
	public synchronized Contenido getContenido(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void guardarContenido(Contenido contenido) {
		Boolean existe=false;
		try {
			existe= db.existe("SELECT count(id) as cantidad FROM Contenido WHERE descripcion = '"+ contenido.getDescripcion() +"'");
			if(!existe){
				String query = "INSERT INTO contenido (descripcion)" +
					"VALUES ('"+contenido.getDescripcion()+"')";
				db.actualizar(query);			
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}	
	}

	@Override
	public List<Contenido> listaContenidos() {
		List<Contenido> lista =  new ArrayList<Contenido>();
		ResultSet rs = db.consultar("select * from contenido");
		try {
			while (rs.next() ) {
				try {
					Contenido contenido = new Contenido();
					contenido.setId(rs.getInt("id"));
					contenido.setDescripcion(rs.getString("descripcion"));
				    lista.add(contenido);

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
	public Contenido getIdContenido(String descripcion) {
		ResultSet rs = db.consultar("select * from contenido where descripcion='"+descripcion+"'");
		Contenido co=null;
		try {
				co = new Contenido();
				co.setId(rs.getInt("id"));
				co.setDescripcion(rs.getString("descripcion"));	
			    db.cerrarConexion();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
		return co;
	}
	
}