package si.um.feri.zimskasolaandroid.gcm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


import com.google.android.gcm.server.Message;

/**
 * Simple implementation of a data store using standard Java collections.
 * <p>
 * This class is thread-safe but not persistent (it will lost the data when the
 * app is restarted) - it is meant just as an example.
 */
public final class GcmDatastoreLocal implements GcmDatastore {

	private static final List<String> regIds = new ArrayList<String>();
	private static final Logger logger = Logger.getLogger(GcmDatastoreLocal.class.getName());
	
	private static GcmDispatcher dispatcher = new GcmDispatcher();
	
	private static GcmDatastoreLocal instance;
	
	public static GcmDatastoreLocal getInstance() {
		if(instance == null) instance = new GcmDatastoreLocal();
		return instance;
	}
	
	private GcmDatastoreLocal() {
		
	}

	/**
	 * Registers a device.
	 */
	public void register(String regId) {
		logger.info("Registering " + regId);
		synchronized (regIds) {
			regIds.add(regId);
		}
	}

	/**
	 * Unregisters a device.
	 */
	public void unregister(String regId) {
		logger.info("Unregistering " + regId);
		synchronized (regIds) {
			regIds.remove(regId);
		}
	}

	/**
	 * Updates the registration id of a device.
	 */
	public void updateRegistration(String oldId, String newId) {
		logger.info("Updating " + oldId + " to " + newId);
		synchronized (regIds) {
			regIds.remove(oldId);
			regIds.add(newId);
		}
	}
	
	/**
	 * Gets all registered devices.
	 */
	public List<String> getDevices() {
		synchronized (regIds) {
			return new ArrayList<String>(regIds);
		}
	}
	
	/**
	 * Dispatch empty message to all registered devices
	 * @throws IOException
	 */
	public void dispatchAll() throws IOException {
		dispatcher.dispatch(getDevices());
	}

	
	public void dispatch(String deviceId, String objectId, String requestId) throws IOException {
		Message message = new Message.Builder()
		.addData("objectId", objectId)
		.addData("requestId", requestId)
		.build();
		dispatcher.dispatch(deviceId, message);
	}

}
