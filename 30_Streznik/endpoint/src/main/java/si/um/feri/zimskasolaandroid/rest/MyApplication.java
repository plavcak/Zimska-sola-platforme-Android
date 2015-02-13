package si.um.feri.zimskasolaandroid.rest;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("resources")
public class MyApplication extends ResourceConfig {

	public MyApplication() {
		packages("um.feri.conferences.backend.rest");
	}
	
}
