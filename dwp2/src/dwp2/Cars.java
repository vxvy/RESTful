package dwp2;

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

@Path("/cars")
public class Cars {
	static ArrayList<Car> cars = new ArrayList<Car>();
	@DefaultValue("valor por defecto") 
	@QueryParam("valor") String text;
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	/*En el body*/
	public Response getCar(Car car) {
		this.cars.add(car);
		return Response.ok(car).build();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ArrayList<Car> getXML() {
		Car c = new Car();
		c.setMarca("Ford");
		c.setModelo("Focus");
		this.cars.add(c);
		return this.cars;
	}
}