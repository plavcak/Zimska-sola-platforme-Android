package si.um.feri.zimskasolaandroid.gcm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class GcmDispatcher {
	
	private static final int MULTICAST_SIZE = 1000;
	
	private Sender sender;
	
	private static final Executor threadPool = Executors.newFixedThreadPool(5);
	
	public GcmDispatcher() {
		init("AIzaSyBlnuo4qMnofa9RN7djryEZMFarJS2L7hE");
	}
	
	public void init(String gcmApiKey) {
		sender = new Sender(gcmApiKey);
	}
	
	public void dispatch(String deviceId, Message message) throws IOException {
		Result result = sender.send(message, deviceId, 5);
		handleResult(deviceId, result);
	}
	
	public void dispatch(List<String> devices) throws IOException {

		String status;

		if (devices.isEmpty()) {
			status = "Message ignored as there is no device registered!";
		} else {
			// NOTE: check below is for demonstration purposes; a real
			// application
			// could always send a multicast, even for just one recipient
			if (devices.size() == 1) {
				// send a single message using plain post
				String registrationId = devices.get(0);
				Message message = new Message.Builder().build();
				Result result = sender.send(message, registrationId, 5);
				status = "Sent message to one device: " + result;
			} else {
				// send a multicast message using JSON
				// must split in chunks of 1000 devices (GCM limit)
				int total = devices.size();
				List<String> partialDevices = new ArrayList<String>(total);
				int counter = 0;
				int tasks = 0;
				for (String device : devices) {
					counter++;
					partialDevices.add(device);
					int partialSize = partialDevices.size();
					if (partialSize == MULTICAST_SIZE || counter == total) {
						asyncSend(partialDevices);
						partialDevices.clear();
						tasks++;
					}
				}
				status = "Asynchronously sending " + tasks
						+ " multicast messages to " + total + " devices";
			}
		}
		
		System.out.println(status);
	}
	
	private void handleResult(String deviceId, Result result) {
		String messageId = result.getMessageId();
		
		if (messageId != null) {
			String canonicalRegId = result.getCanonicalRegistrationId();
			if (canonicalRegId != null) {
				// same device has more than on registration id:
				// update it
//				Datastore.updateRegistration(regId, canonicalRegId);
			}
		} else {
			String error = result.getErrorCodeName();
			if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
				// application has been removed from device -
				// unregister it
//				Datastore.unregister(regId);
			} else {
//				logger.severe("Error sending message to " + regId + ": " + error);
			}
		}
	}
	
	private void asyncSend(List<String> partialDevices) {
		final List<String> devices = new ArrayList<String>(partialDevices);
		
		threadPool.execute(new Runnable() {

			public void run() {
				Message message = new Message.Builder().build();
				
				MulticastResult multicastResult;
				
				try {
					multicastResult = sender.send(message, devices, 5);
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
				
				List<Result> results = multicastResult.getResults();
				
				for (int i = 0; i < devices.size(); i++) {
					handleResult(devices.get(i), results.get(i));
				}
				
			}
		});
	}
	
}
