package clasesDAOImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Contexto;
import clasesDAO.IContextoDAO;
import database.OperacionesDB;

public class ContextoDAO implements IContextoDAO {

	private OperacionesDB db = new OperacionesDB();

	@Override
	public Contexto getContexto(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void guardarContexto(Contexto contexto) {
		Boolean existe=false;
		try {
			existe= db.existe("SELECT count(id) as cantidad FROM Contexto WHERE descripcion = '"+ contexto.getDescripcion() +"'");
			if(!existe){
				String query = "INSERT INTO contexto (descripcion)" +
					"VALUES ('"+contexto.getDescripcion()+"')";
				db.actualizar(query);	
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}	
	}

	@Override
	public List<Contexto> listaContextos() {
		List<Contexto> lista =  new ArrayList<Contexto>();
		ResultSet rs = db.consultar("select * from contexto");
		try {
			while (rs.next() ) {
				try {
					Contexto contexto = new Contexto();
					contexto.setId(rs.getInt("id"));
					contexto.setDescripcion(rs.getString("descripcion"));
				    lista.add(contexto);

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
	public Contexto getIdContexto(String descripcion) {
		ResultSet rs = db.consultar("select * from contexto where descripcion='"+descripcion+"'");
		Contexto cx=null;
		try {
				cx = new Contexto();
				cx.setId(rs.getInt("id"));
				cx.setDescripcion(rs.getString("descripcion"));				
			    db.cerrarConexion();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
		return cx;
	}

}
