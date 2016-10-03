package clasesDAOImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Ninio;
import clasesDAO.INinioDAO;
import database.OperacionesDB;

public class NinioDAO implements INinioDAO {
	
	private OperacionesDB db = new OperacionesDB();

	@Override
	public synchronized Ninio getNinio(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void guardarNinio(Ninio ninio) {
		Boolean existe=false;
		try {
			existe= db.existe("SELECT count(id) as cantidad FROM Ninio WHERE nombreyape = '"+ ninio.getNombre() +"'");
			if(!existe){
				String query = "INSERT INTO ninio (nombreyape)" +
					"VALUES ('"+ninio.getNombre()+"')";
				db.actualizar(query);	
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}	
	}

	@Override
	public List<Ninio> listaNinios() {
		List<Ninio> lista =  new ArrayList<Ninio>();
		ResultSet rs = db.consultar("select * from ninio");
		try {
			while (rs.next() ) {
				try {
					Ninio ninio = new Ninio();
					ninio.setId(rs.getInt("id"));
					ninio.setNombre(rs.getString("nombreyape"));
				    lista.add(ninio);
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
	public Ninio getIdNinio(String  nombreyape) {
		ResultSet rs = db.consultar("select * from ninio where nombreyape=='"+nombreyape+"'");
		Ninio n=null;
		try {
				n = new Ninio();
				n.setId(rs.getInt("id"));
				n.setNombre(rs.getString("nombreyape"));
			    db.cerrarConexion();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
		return n;
	}
}
