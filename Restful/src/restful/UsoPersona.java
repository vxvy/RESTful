package restful;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/usopersona")
public class UsoPersona {
	static ArrayList<OPersona> personas = new ArrayList<OPersona>();
	public static OPersona persona = new OPersona();

	@DefaultValue("valor por defecto")
	@QueryParam("valor") String text="";

	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response setPersona(OPersona persona) {
		return Response.ok(persona).build();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ArrayList<OPersona> getPersona() {
		System.out.print("asdasdasadsd");
		UsoPersona.persona.setId(1);
		UsoPersona.persona.setNombre("Juancho");
		UsoPersona.persona.setCasado(true);
		UsoPersona.persona.setSexo("Male");
		personas.add(UsoPersona.persona);
		return UsoPersona.personas;
	}
}

