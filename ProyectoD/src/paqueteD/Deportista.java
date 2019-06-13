package paqueteD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/deportistas")
public class Deportista {
	
	Conexion cntn = new Conexion();
	ArrayList<ODeportista> deportistas = new ArrayList<>();
	
	//La declaración de estas variables 
	//se hace puramente por legibilidad del código
	Response SIN_CONTENIDO = 
			Response.status(Status.BAD_REQUEST)
			.entity("No se han encontrado datos")
			.type(MediaType.TEXT_PLAIN)
			.build();
	
	Response FALLO_CONSULTA = 
			Response.status(Status.BAD_REQUEST)
			.entity("Fallo en la consulta")
			.type(MediaType.TEXT_PLAIN)
			.build();
	
	Response SIN_CONEXION = 
			Response.status(Status.BAD_REQUEST)
			.entity("Sin conexión")
			.type(MediaType.TEXT_PLAIN)
			.build();

	
	
	//Obtiene el ResultSet a partir de la query proporcionada
	public Response executeQuery(String query) {
			try {
				cntn.abrirConexion("ad_tema6", "localhost", "root", "");
				if(cntn.getConexion()!=null) {
					
					//Necesaria la redefinición del objeto 
					//para que no acumule resultados
					deportistas = new ArrayList<>();
					ODeportista dep = new ODeportista();
//					ArrayList<ODeportista> arlDep = new ArrayList<ODeportista>();
					
					Statement stmt = cntn.getConexion().createStatement();
					ResultSet rs = stmt.executeQuery(query);
					while(rs.next()) {
						dep=extraeDatos(rs);
						if(dep != null) {
							deportistas.add(dep);
						}
					}
					
					if(deportistas.size()==0) {
						return SIN_CONTENIDO;						
					}else if(deportistas.size()==1){
						return Response.ok(
								deportistas.get(0)).build();
					}else {
						return Response.ok(
								new GenericEntity<ArrayList<ODeportista>>(deportistas){}).build();
					}
				}
			}catch(SQLException e) {
				e.printStackTrace();
				return FALLO_CONSULTA;
			}finally {
				if(cntn.getConexion() != null) {
					cntn.cerrarConexion();
				}
			}

		return SIN_CONEXION;
	}

	public Response executeFunctionQuery(String query) {
		Response resp = SIN_CONEXION;
		try {
			this.cntn.abrirConexion("ad_tema6", "localhost", "root", "");
			if(this.cntn.getConexion()!=null) {
				Statement stmt = this.cntn.getConexion().createStatement();
				ArrayList<Object> results = new ArrayList<Object>(); 
				ResultSet rs = stmt.executeQuery(query);

				while(rs.next()) {
					try {
						results.add(rs.getString(1));
					}catch(Exception e) {
						results.add(rs.getInt(1));
					}
				}
				
				if(results.size()<=1) {
					resp = Response.ok(results.get(0)).build();
				}else {
					resp = Response.ok(
							new GenericEntity<ArrayList<Object>>(results){}).build();
				}
			}
		}catch(SQLException sqle) {
			sqle.printStackTrace();
			resp=FALLO_CONSULTA;
		}
		finally {
			if(this.cntn.getConexion()!=null) {
				this.cntn.cerrarConexion();
			}
		}
		return resp;
	}
	
	//
	
	//Extrae los datos de deportistas de la base de datos 
	//en forma de deportista
	public ODeportista extraeDatos(ResultSet rs) {
		
		ODeportista dep = new ODeportista();
		
		try {
			dep.setNombre(rs.getString("nombre"));
			dep.setDeporte(rs.getString("deporte"));
			dep.setGenero(rs.getString("genero"));
			dep.setId(rs.getInt("id"));
			dep.setActivo(rs.getBoolean("activo"));
		}
		catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return dep;
	}
	
	//Ej2, todos los deportistas
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDeportistas() {
		String query="SELECT * FROM deportistas;";
		return executeQuery(query);
	}
	
	//Ej3, deportistaId
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDeportistaById(@PathParam("id") int id) {
		String query="SELECT * FROM deportistas WHERE id="+id+";";
		return executeQuery(query);
	}
	
	//Ej4, busca por deporte
	@Path("/d/{deporte}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDeportistasByDeport(@PathParam("deporte") String deporte) {
		return executeQuery("SELECT * FROM deportistas WHERE deporte LIKE '"+deporte+"';");
	}
	
	//Ej5, busca por activos
	@Path("/activos")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDeportistasActivos() {
		return executeQuery("SELECT * FROM deportistas WHERE activo=1");
	}
	
	//Ej6, busca por retirados
	@Path("/retirados")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDeportistasRetirados() {
		return executeQuery("SELECT * FROM deportistas WHERE activo=0");
	}
	
	//Ej7, 
	@Path("/hombres")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDeportistasHombres() {
		return executeQuery("SELECT * FROM deportistas WHERE genero = 'Masculino'");
	}
	
	//Ej8, 
	@Path("/mujeres")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDeportistasMujeres() {
		return executeQuery("SELECT * FROM deportistas WHERE genero = 'Femenino'");
	}
	
	//Ej9, ambos
	@Path("/xy")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getArrayCon() {
		Xy xy = new Xy();
		Response alHombres = this.getDeportistasHombres();
		Response alMujeres = this.getDeportistasMujeres();
		System.out.println(((ArrayList<ODeportista>)alHombres.getEntity()).size());
		System.out.println(((ArrayList<ODeportista>)alMujeres.getEntity()).size());
		xy.setALhombres((ArrayList<ODeportista>)alHombres.getEntity());
		xy.setALmujeres((ArrayList<ODeportista>)alMujeres.getEntity());
		return Response.ok(xy).build();
	}
	
	//Ej10, activos por deporte
	@Path("/dactivo/{deporte}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response depActivosDeporte(@PathParam("deporte")String deporte) {
		String query = "SELECT * FROM deportistas WHERE activo = 1 AND deporte ='"+deporte+"';";
		return this.executeQuery(query);
	}
	
	
	//Ej11, cuenta depor por deporte
	@Path("/cdep/{ndeporte}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response deporPorDeporte(@PathParam("ndeporte")String ndeporte) {
		return this.executeFunctionQuery("SELECT COUNT(*) FROM deportistas WHERE deporte='"+ndeporte+"';");
	}
	
	
	//Ej12, lista alph deportes 
	@Path("/deportes")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response ordenAlphDeport() {
		return this.executeFunctionQuery("SELECT DISTINCT deporte FROM deportistas ORDER BY deporte ASC;");
	}

	//Ej13, 
	@Path("/dinsert")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response anyadeDeportista(ODeportista dep) {

		String query = "INSERT INTO deportistas (nombre,activo,genero,deporte) "
				+ "VALUES ("+dep.getNombre()+", "+dep.isActivo()+", "+dep.getGenero()+", "+dep.getDeporte()+")\" ";
		
		try {
			this.cntn.abrirConexion("ad_tema6", "127.0.0.1", "root", "");
			if(this.cntn.getConexion()!=null) {
				Statement stmt = this.cntn.getConexion().createStatement();
				int numberUpdates = stmt.executeUpdate(query);
				if(numberUpdates>0) {
					return Response.ok(dep).build();					
				}else {
					return SIN_CONTENIDO;					
				}
			}

		}catch(SQLException sqle) {
			sqle.printStackTrace();
			return FALLO_CONSULTA;
		}
		return SIN_CONEXION;
	}
}
