package paqueteNaves;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	private Connection conexion;
	
	public void abrirConexion(String bdd, String srv, String user, String password) {
		String ruta = String.format("jdbc:mariadb://%s:3306/%s", srv, bdd);
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			this.conexion = DriverManager.getConnection(ruta, user,password);
			if(this.conexion!=null) {
				System.out.println("Conexi�n realizada con �xito");
			}else {
				System.out.println("NO se ha conectado a la base de datos");
			}
			
		}catch(SQLException sqle) {
			System.out.println("SQLException: "+ sqle.getLocalizedMessage());
			System.out.println("SQLState: "+ sqle.getSQLState());
			System.out.println("C�digo error: "+ sqle.getErrorCode()); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	
	}
	
	public void cerrarConexion() {
		try {
			this.conexion.close();
		}catch (SQLException sqle) {
			System.out.println("Error cerrando la conexi�n: "+sqle.getLocalizedMessage());
		}
	}

	public Connection getConexion() {
		return this.conexion;
	}
}
