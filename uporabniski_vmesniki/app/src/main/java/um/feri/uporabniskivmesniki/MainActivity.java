package um.feri.uporabniskivmesniki;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import um.feri.uporabniskivmesniki.gcm.GcmComponent;
import um.feri.uporabniskivmesniki.lolipop.CardViewActivity;
import um.feri.uporabniskivmesniki.rest.RestClient;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private Button button;
    private Button button2;
    private Button button3;
    private Button button6;
    private Button button8;
    private Button button9;
    private Button button10;
    private Button button11;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button6 = (Button)findViewById(R.id.button6);
        button8 = (Button)findViewById(R.id.button8);
        button9 = (Button)findViewById(R.id.button9);
        button10 = (Button)findViewById(R.id.button10);
        button11 = (Button)findViewById(R.id.button11);

        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button6.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button10.setOnClickListener(this);
        button11.setOnClickListener(this);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RestClient client = new RestClient();

        client.receiveAsync(new RestClient.OnRestResponseListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess()");
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "onFailure() receiveAsync");
            }
        });

        registerGcm();
    }

    private void registerGcm() {
        GcmComponent gcmComponent = new GcmComponent(getApplicationContext());
        gcmComponent.onCreate(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity( new Intent(getApplicationContext(), SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.button) {
            startActivity( new Intent(getApplicationContext(), ViewPagerActivity.class));
        }

        else if(v.getId() == R.id.button2) {
            startActivity( new Intent(getApplicationContext(), SimpleListActivity.class));
        }

        else if(v.getId() == R.id.button3) {
            startActivity( new Intent(getApplicationContext(), AddClientActivity.class));
        }

        else if(v.getId() == R.id.button6) {
            startActivity( new Intent(getApplicationContext(), CameraActivity.class));
        }

        else if(v.getId() == R.id.button8) {
            startActivity( new Intent(getApplicationContext(), GoogleMapsActivity.class));
        }

        else if(v.getId() == R.id.button9) {
            startActivity( new Intent(getApplicationContext(), FacebookActivity.class));
        }

        else if(v.getId() == R.id.button10) {
            startActivity( new Intent(getApplicationContext(), CardViewActivity.class));
        }

        else if(v.getId() == R.id.button11) {
            startActivity( new Intent(getApplicationContext(), StorageActivity.class));
        }
    }
}
