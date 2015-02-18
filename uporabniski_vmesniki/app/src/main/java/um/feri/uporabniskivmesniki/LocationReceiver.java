package um.feri.uporabniskivmesniki;


import android.location.Location;
        import android.location.LocationListener;
        import android.os.Bundle;
        import android.util.Log;

public class LocationReceiver implements LocationListener {

    @Override
    public void onLocationChanged(Location location) {
        Log.d(this.getClass().getName(),"onLocationChanged()");
        Log.d(this.getClass().getName(),
                location.getLatitude()+";"+
                        location.getLongitude()+";"+
                        location.getAltitude()+";"+
                        location.getSpeed()+";"+
                        location.getBearing()+";"+
                        location.getAccuracy());
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(this.getClass().getName(),"onProviderDisabled()");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(this.getClass().getName(),"onProviderEnabled()");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(this.getClass().getName(),"onStatusChanged()");
    }

}