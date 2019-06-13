package paqueteD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	
	private Connection connection;
	
	public Connection getConexion() {
		return this.connection;
	}
	
	public void abrirConexion(String bd, String servidor, String usuario, String password) {
		try {
			String url = String.format("jdbc:mariadb://%s:3306/%s", servidor, bd);
		
			Class.forName("org.mariadb.jdbc.Driver");
			this.connection = DriverManager.getConnection(url,usuario,password);
			
			if(this.connection!= null) {
				System.out.println ("Conectado con éxito a la base de datos "+bd+" en "+servidor);
			}
			else {
				System.out.println ("NO se ha conectado a la base de datos "+bd+" en "+servidor);				
			}
			
		}catch(SQLException sqle) {
			System.out.println("SQLException: "+ sqle.getLocalizedMessage());
			System.out.println("SQLState: "+ sqle.getSQLState());
			System.out.println("Código error: "+ sqle.getErrorCode()); 
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}
	
	public void cerrarConexion() {
		try {
			this.connection.close();
		}catch(SQLException sqle) {
			System.out.println("Error cerrando la conexión: "+sqle.getLocalizedMessage());
		}
	}
	
}
