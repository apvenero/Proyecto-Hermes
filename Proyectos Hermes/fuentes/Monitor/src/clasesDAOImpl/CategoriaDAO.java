package clasesDAOImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import clasesDAO.ICategoriaDAO;
import database.OperacionesDB;
import model.Categoria;

public class CategoriaDAO implements ICategoriaDAO {

	private OperacionesDB db = new OperacionesDB();

	@Override
	public synchronized Categoria getCategoria(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized void guardarCategoria(Categoria categoria) {
		Boolean existe=false;
		try {
			existe= db.existe("SELECT count(id) as cantidad FROM Categoria WHERE descripcion = '"+ categoria.getDescripcion() +"'");
			if(!existe){
				String query = "INSERT INTO categoria (descripcion)" +
								"VALUES ('"+categoria.getDescripcion()+"')";
				db.actualizar(query);	
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}	
	}
	
	
	@Override
	public synchronized List<Categoria> listaCategorias() {
		List<Categoria> lista =  new ArrayList<Categoria>();
		ResultSet rs = db.consultar("select * from categoria");
		try {
			while (rs.next() ) {
				try {
					Categoria categoria = new Categoria();
					categoria.setId(rs.getInt("id"));
					categoria.setDescripcion(rs.getString("descripcion"));
				    lista.add(categoria);
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
	public synchronized Categoria getIdCategoria(String descripcion) {
		ResultSet rs = db.consultar("select * from categoria where descripcion='"+descripcion+"'");
		Categoria c=null;
		try {
				c = new Categoria();
				c.setId(rs.getInt("id"));
				c.setDescripcion(rs.getString("descripcion"));		
			    db.cerrarConexion();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
		return c;
	}
	
	
}
