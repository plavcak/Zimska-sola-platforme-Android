package si.um.feri.zimskasolaandroid.rest;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import si.um.feri.zimskasolaandroid.gcm.GcmDatastoreLocal;

@Path("/gcm")
@Stateless
public class GcmResource {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response get() {
		return Response.ok().entity("GCM Resource").build();
	}
	
	@GET
	@Path("/register")
	public Response registerDevice(@QueryParam("regId") String registrationID) {
		GcmDatastoreLocal.getInstance().register(registrationID);
		return Response.ok().build();
	}
	
	@GET
	@Path("/unregister")
	public Response unregisterDevice(@QueryParam("regId") String registrationID) {
		GcmDatastoreLocal.getInstance().unregister(registrationID);
		return Response.ok().build();
	}
	
	@GET
	@Path("/devices")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
		return Response.ok().entity(GcmDatastoreLocal.getInstance().getDevices()).build();
	}
	
	@GET
	@Path("/publish")
	public Response publish() {
		try {
			GcmDatastoreLocal.getInstance().dispatchAll();
		} catch (Exception e) {
			return Response.serverError().build();
		}
		
		return Response.ok().build();
	}
	
}
