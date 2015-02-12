package um.feri.uporabniskivmesniki;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * C:\Users\Gregor\.android>"C:\Program Files\Java\jdk1.8.0_20\bin\keytool.exe" -list -v -keystore debug.keystore
 *
 * https://console.developers.google.com/
 *
 */
public class GoogleMapsActivity extends ActionBarActivity implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;

    private LatLng mariborLatLng = new LatLng(46.5522414,15.6413741);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);

        if(savedInstanceState == null) {

            GoogleMapOptions googleMapOptions = new GoogleMapOptions();
            googleMapOptions.mapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMapOptions.mapToolbarEnabled(true);
            googleMapOptions.rotateGesturesEnabled(true);
            googleMapOptions.scrollGesturesEnabled(true);
            googleMapOptions.tiltGesturesEnabled(true);
            googleMapOptions.zoomControlsEnabled(true);
            googleMapOptions.zoomGesturesEnabled(true);

            CameraPosition cameraPosition = CameraPosition.fromLatLngZoom(mariborLatLng, 13);
            googleMapOptions.camera(cameraPosition);

            mapFragment = SupportMapFragment.newInstance(googleMapOptions);
            mapFragment.getMapAsync(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mapFragment, "mapFragment").commit();
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng latLng = new LatLng(46.5590328,15.6380807);

        map.setBuildingsEnabled(true);
        map.setMyLocationEnabled(true);
        map.setTrafficEnabled(true);

        map.addMarker(new MarkerOptions()
            .position(latLng)
            .title("Zimska šola platforme Android"));
    }

    private GoogleMap getGoogleMap() {
        return  mapFragment.getMap();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_google_maps, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        GoogleMap map = getGoogleMap();

        if (id == R.id.action_hybrid) {
            if(map != null) {
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
            return true;
        }
        else if (id == R.id.action_normal) {
            if(map != null) {
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
            return true;
        }

        else if (id == R.id.action_satellite) {
            if(map != null) {
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
            return true;
        }

        else if (id == R.id.action_terrain) {
            if(map != null) {
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
