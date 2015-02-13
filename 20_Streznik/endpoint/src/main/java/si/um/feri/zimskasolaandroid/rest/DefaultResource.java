package si.um.feri.zimskasolaandroid.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/client")
@Stateless
public class DefaultResource {
	
	public static List<Client> clients = new ArrayList<Client>();
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response all() {
		return Response.ok().entity(clients).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(Client client) {
		clients.add(client);
		return Response.ok().build();
	}
	
	@GET
	@Path("/clear")
	public Response clear() {
		clients.clear();
		return Response.ok().build();
	}
	
}
