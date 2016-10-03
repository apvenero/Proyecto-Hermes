package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OperacionesDB extends ConexionDB{
    	
	public ResultSet consultar(String query){
		
		ResultSet result = null;
		try {
			conectar();
			result = consulta.executeQuery(query);
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return result;
	}
	
	public int actualizar(String query) throws Exception{
		int retrieve;
		try {
			conectar();
			consulta.executeUpdate(query);
			retrieve = consulta.getGeneratedKeys().getInt(1);
			cerrarConexion();
			return retrieve;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return 0;
		}
	}
	
	public boolean existe(String query){
		
	    ResultSet rs = null;
	    int cant = 0;
	    
	    try {
	    	conectar();
	    	rs = consulta.executeQuery(query);
		    
	    	while ( rs.next() ) {
			    cant = rs.getInt("cantidad");
			}
	    	rs.close();
	    	conexion.close();
	    }catch(Exception e){
			System.err.println(e.getClass().getName() + ": " + e.getMessage());

		}
	    return cant > 0;     
	}

    public static boolean createDB() {		
		Boolean noExiste = false;

    	try {
			conectar();
			ResultSet rs = consulta.executeQuery( "SELECT name FROM sqlite_master WHERE type='table';" );
			noExiste = !rs.next();

			if(noExiste){
				initTables();
			}			
		    rs.close();
            conexion.close();  
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ":" + e.getMessage());
		}
		return noExiste;
	}
	
    private static void initTables() throws SQLException{
    	
		 String createCategoria = "create table if not exists categoria"+
				 "(id integer primary key autoincrement not null," +
				   "descripcion text not null)";
		 consulta.executeUpdate(createCategoria);
		 
		 String createContenido = "create table if not exists contenido"+
				 "(id integer primary key autoincrement not null," +
				   "descripcion text not null)";
		 consulta.executeUpdate(createContenido);
		 
		 String createContexto = "create table if not exists contexto"+
				 "(id integer primary key autoincrement not null," +
				   "descripcion text not null)";
		 consulta.executeUpdate(createContexto);
		 
		 String createNinio = "create table if not exists ninio"+
				 "(id integer primary key autoincrement not null," +
				   "nombreyape text not null)";
		 consulta.executeUpdate(createNinio);
		 
		 String createEtiqueta = "create table if not exists etiqueta"+
				 "(id integer primary key autoincrement not null," +
				   "descripcion text not null)";
		 consulta.executeUpdate(createEtiqueta);
		 
		 String createNotificacion = "create table if not exists notificacion " +
	                "(id integer primary key autoincrement not null," +
	                " fechaEnvio DATETIME DEFAULT CURRENT_TIMESTAMP, "+
                    " idContenido  integer    not null, " + 
	                " idContexto   integer    not null, " + 
	                " idCategoria  integer    not null, " + 
	                " idNinio      integer    not null, " + 
	                " foreign key	(idContenido)	references contenido(id),"+
	                " foreign key	(idContexto)	references contexto(id),"+
	                " foreign key 	(idCategoria)	references categoria(id),"+
	                " foreign key	(idNinio)		references ninio(id))"; 
		 consulta.executeUpdate(createNotificacion);
		 
		 String createNotiEtiqueta = "create table if not exists notificacion_etiqueta"+
				 "(id integer primary key autoincrement not null," +
				 " idNotificacion 	integer not null, "+
				 " idEtiqueta 		integer not null," +
				 " unique (idNotificacion, idEtiqueta) on conflict ignore,"+
				 " foreign key (idNotificacion)  references notificacion(id)	on delete cascade,"+
				 " foreign key (idEtiqueta)		 references etiqueta(id) 		on delete cascade)";
		 consulta.executeUpdate(createNotiEtiqueta);
		 
	}
    
     }