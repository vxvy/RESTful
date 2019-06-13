package paquete;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/persona")
public class Persona {
	
	public static ArrayList<OPersona> personas = new ArrayList<OPersona>();
	public static OPersona p1 = new OPersona();
	public static OPersona p2 = new OPersona();	
	
	// Ej1
	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response pGuardar(OPersona p) {
		personas.add(p);
		return Response.ok(p).build();
	}
	
	// Ej2
	@GET
	@Produces( MediaType.APPLICATION_XML )
	public ArrayList<OPersona> pListar() {
		Persona.personas.add(new OPersona());
		Persona.personas.add(new OPersona());
		return Persona.personas;
	}

	// Ej3
	@GET
	@Path("/{p}")
	@Produces( MediaType.APPLICATION_XML )
	public ArrayList<OPersona> pBuscar(@PathParam("p")String nombre) {
		ArrayList<OPersona> pCoinciden=new ArrayList<>();
		for(OPersona aux:Persona.personas) {
			if(aux.getNombre().equals(nombre)) {
				pCoinciden.add(aux);
			}
		}
		return pCoinciden;
	}
	
	// Ej4
	@GET
	@Path("ic/{p}")
	@Produces( MediaType.APPLICATION_XML )
	public ArrayList<OPersona> pBuscarIgnoreCase(@PathParam("p")String nombre) {
		ArrayList<OPersona> pCoinciden=new ArrayList<>();
		nombre = nombre.toUpperCase();
		for(OPersona aux:Persona.personas) {
			String a = aux.getNombre().toUpperCase();
			if(a.contains(nombre)) {
				pCoinciden.add(aux);
			}
		}
		return pCoinciden;
	}
	
	// Ej6
	//TODO
	@POST
	@Path("/form")
	@Consumes("application/x-www-form-urlencoded")
	@Produces({ MediaType.APPLICATION_XML , MediaType.TEXT_XML, MediaType.TEXT_HTML})
	public Response pFromParam(@FormParam("nombre") String nombre,@FormParam("id") String id, 
							   @FormParam("sexo") String sexo, @FormParam("casado") String casado ) {
//		System.out.println("executeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		OPersona p = new OPersona();	
		try {
			p.setNombre(nombre);
			p.setId(Integer.parseInt(id));
			p.setSexo(sexo);
			p.setCasado(casado.equals("true")? true:false);
			Persona.personas.add(p);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return Response.status(200)
				.entity("pFromParam is called, name : " + p.getNombre() + ", id : " + p.getId())
				.build();
	}
	
	// Ej7
	
//	<oPersonas>
//    <oPersona>
//        <casado>true</casado>
//        <id>1</id>
//        <nombre>Juancho</nombre>
//        <sexo>Male</sexo>
//    </oPersona>
//    <oPersona>
//        <casado>false</casado>
//        <id>0</id> 
//        <nombre>null</nombre>
//        <sexo>no</sexo>
//    </oPersona>
//    <oPersona>
//        <casado>false</casado>
//        <id>0</id>
//        <nombre>null</nombre>
//        <sexo>no</sexo>
//    </oPersona>
//</oPersonas>

	@Path("/add")
	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<OPersona> pAddMultiple(ArrayList<OPersona> p) {
		for(OPersona aux:p) {
			Persona.personas.add(aux);
		}
		return Persona.personas;
	}
	
	//Ej8
	@GET
	@Path("delete/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayList<OPersona> pEliminaPID(@PathParam("id")String id) {
		int idPElimina = Integer.parseInt(id);
		for(OPersona aux:Persona.personas) {
			if(aux.getId()==idPElimina) {
				Persona.personas.remove(aux);
			}
		}
		return Persona.personas;
	}
	
	//Ej9(Ej4)
	//TODO en teoría funciona
	@GET
	@Path("def/{p}")
	@Produces( MediaType.APPLICATION_XML )
	public ArrayList<OPersona> pDefValues(@DefaultValue("0") @PathParam("p")String id) {
		int intId = Integer.parseInt(id);
		for(OPersona aux:Persona.personas) {
			if(intId == aux.getId()) {
				Persona.personas.remove(aux);
			}
		}
		return Persona.personas;
	}
	
	
	//Ej10
	@POST
	@Path("xml/{id}")
	@Consumes( MediaType.APPLICATION_XML )
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public OPersona pXmlAttribute(@PathParam("id")String personaId) {
		OPersona p = null;
		for(OPersona aux:Persona.personas) {
			if(aux.getId() == Integer.parseInt(personaId)) {
				p = aux;
			}
		}
		return p;
	}
}

//https://www.google.com/search?client=firefox-b-d&ei=CSXHXI7gLeTB_Qbc9oPwCw&q=jersey+create+connection+SQL&oq=jersey+create+connection+SQL&gs_l=psy-ab.3..33i22i29i30l8.3863.5094..5332...0.0..0.306.816.2-2j1......0....1..gws-wiz.......0i71.dPjkgSpJyPg
