package si.feri.delavnica.demo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class Delavec extends IntentService {

	public Delavec() {
		super("Delavec");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(this.getClass().getName(),"onHandleIntent()");
	}
	
}
