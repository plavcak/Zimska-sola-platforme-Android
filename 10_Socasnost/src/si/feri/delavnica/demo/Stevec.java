package si.feri.delavnica.demo;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class Stevec extends Service {
	
	public static final int NOTIFICATION_ID = 112212;
	
	Timer timer;
	int count=0;
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(this.getClass().getName(),"onCreate()");
		
		timer=new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				count++;
				broadcast(count);
				Log.d("Stevec",count+"");
			}
		}, 1000,1000);
		
		
		this.startForeground(NOTIFICATION_ID, getNotification("Storitev",MainActivity.class));
		
	}

	private void broadcast(int i) {
		Intent intent = new Intent("SporociloZaBroadcast");
        intent.putExtra("i", i);
        getApplication().sendBroadcast(intent);
	}
	
	@Override
	public void onDestroy() {
		Log.d(this.getClass().getName(),"onDestroy()");
		timer.cancel();
		super.onDestroy();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(this.getClass().getName(),"onStartCommand()");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(this.getClass().getName(),"onBind()");
		return null;
	}
	
	private Notification getNotification(String msg, Class<?> activity) {
		Intent intent = new Intent(this, activity);
		intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		
		Notification notification = new Notification.Builder(this)
				.setContentTitle("Storitev").setContentText("Stejem")
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentIntent(PendingIntent.getActivity(this, 0, intent, 0))
				.build();
		
		return notification;
	}
	
	
	
}
