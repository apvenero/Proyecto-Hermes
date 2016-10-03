package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class ConexionDB {
	
		static Connection conexion;
		static Statement consulta;
	    
              
	
	    public static void conectar(){
			try {
		            Class.forName("org.sqlite.JDBC");
		        }
		        catch (ClassNotFoundException e) {
					System.err.println(e.getClass().getName() + ": " + e.getMessage());
		        }	 
				try {
	                   conexion = DriverManager.getConnection("jdbc:sqlite:hermes.db");
	                   consulta = conexion.createStatement();
				} catch (SQLException e) {
						System.err.println(e.getClass().getName() + ": " + e.getMessage());
	                        }
		}
	   
	    public void cerrarConexion() throws SQLException {
			try {
				consulta.close();
				conexion.close();
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				throw e;
			}
		}
}
