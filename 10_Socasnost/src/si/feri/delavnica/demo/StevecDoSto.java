package si.feri.delavnica.demo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class StevecDoSto extends Service {
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(this.getClass().getName(),"onCreate()");
	}
	
	@Override
	public void onDestroy() {
		Log.d(this.getClass().getName(),"onDestroy()");
		super.onDestroy();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(this.getClass().getName(),"onStartCommand()");
		
		Runnable stej=new Runnable() {
			@Override
			public void run() {
				for (int i=1;i<=100;i++) {
					if (i%10==0) Log.d(this.getClass().getName(),"Stejem do 100: "+i);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				StevecDoSto.this.stopSelf();
			}
		};
		
		new Thread(stej).start();
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(this.getClass().getName(),"onBind()");
		return null;
	}
	
}
