package um.feri.uporabniskivmesniki;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import um.feri.uporabniskivmesniki.db.ClientDAO;
import um.feri.uporabniskivmesniki.db.Database;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private Button button;
    private Button button2;
    private Button button3;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);

        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);

        toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        Database database = new Database(getApplicationContext());

        ClientDAO clientDAO = new ClientDAO();

        SQLiteDatabase db = database.getWritableDatabase();

        Toast.makeText(getApplicationContext(), "Count: " + clientDAO.count(db), Toast.LENGTH_SHORT).show();

        db.close();
        database.close();

        writeFile();
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
    }

    private int count;

    private void writeFile() {


        // external storage
        if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            // write external
        }

        Toast.makeText(getApplicationContext(), "" + getFilesDir(), Toast.LENGTH_SHORT).show();

        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(new File(getFilesDir(), "test.txt"));

            OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);

            streamWriter.write("test");

            streamWriter.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readPreferences() {
        SharedPreferences preferences = getSharedPreferences("user_settings", MODE_PRIVATE);

        count = preferences.getInt("count", 0);
        Log.d(TAG, "readPreferences() count=" + count);
    }

    private void writePreferences() {
        SharedPreferences preferences = getSharedPreferences("user_settings", MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("count", count++);
        editor.commit();

        Log.d(TAG, "writePreferences() count=" + count);
    }
}
