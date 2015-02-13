package si.feri.delavnica.demo;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    protected void onResume() {
    	bindLocation();
    	super.onResume();
    };
    
    @Override
    protected void onPause() {
    	unbindLocation();
    	super.onPause();
    }
    
    LocationReceiver lr=new LocationReceiver();
    
    private void bindLocation() {
		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, lr);
//		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, lr);
		
		Location location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		if (location!=null)
			Toast.makeText(this,
					location.getLatitude()+";"+
					location.getLongitude()+";"+
					location.getAltitude()+";"+
					location.getSpeed()+";"+
					location.getBearing()+";"+
					location.getAccuracy(), Toast.LENGTH_LONG).show();
		
    }
    
    private void unbindLocation() {
    	LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    	locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    	locationManager.removeUpdates(lr);
    }

}
