package si.um.feri.zimskasolaandroid.gcm;

import java.io.IOException;
import java.util.List;

public interface GcmDatastore {

	public void register(String regId);

	public void unregister(String regId);

	public void updateRegistration(String oldId, String newId);
	
	public List<String> getDevices();
	
	public void dispatch(String deviceId, String objectId, String requestId) throws IOException;
}