package um.feri.uporabniskivmesniki.gcm;

import um.feri.uporabniskivmesniki.R;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmService extends IntentService {
	private static final String TAG = GcmService.class.getSimpleName();
	
    public GcmService() {
        super("Google Cloud Messaging Service");
    }
    
    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);
        
        if (!extras.isEmpty()) {
            handleIncomingMessage(intent);
        }
        
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        WakefulBroadcastReceiver.completeWakefulIntent(intent);
    }
    
    protected void handleIncomingMessage(Intent intent) {
        // Get data

    	//String evaluation = intent.getStringExtra("evaluation");

    	sendNotification();

    }
    
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    
	protected void sendNotification() {
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent();
        
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
        
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle("Zimska šola platforme Android")
        .setStyle(new NotificationCompat.BigTextStyle()
        .bigText("Push notification received."))
        .setAutoCancel(true)
        .setContentText("Notification.");
        
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}
}