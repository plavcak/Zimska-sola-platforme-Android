package um.feri.uporabniskivmesniki;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class StorageActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = "StorageActivity";
    private String name;

    private EditText etPref;
    private Button btnPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        etPref = (EditText) findViewById(R.id.etPreferences);
        btnPref = (Button) findViewById(R.id.btnSavePref);

        btnPref.setOnClickListener(this);

        name = readPreferences();
        etPref.setText(name);

        // external storage
        if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            // write external
            Toast.makeText(getApplicationContext(), "External storage available!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "External storage NOT available!", Toast.LENGTH_SHORT).show();
        }
    }

    private String readPreferences() {
        SharedPreferences preferences = getSharedPreferences("user_settings", MODE_PRIVATE);
        String name = preferences.getString("name", null);
        Log.d(TAG, "readPreferences() name=" + name);
        return name;
    }

    private void savePreferences(String name) {
        SharedPreferences preferences = getSharedPreferences("user_settings", MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", name);
        editor.commit();

        Log.d(TAG, "writePreferences() name=" + name);
    }

    private void writeToFile(String name) {

        FileOutputStream outputStream = null;

        try {
            Toast.makeText(getApplicationContext(), "" + getFilesDir(), Toast.LENGTH_SHORT).show();

            outputStream = new FileOutputStream(new File(getFilesDir(), "test.txt"));

            OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);

            streamWriter.write("test");

            streamWriter.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFileExternal(String name) {

        if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            // write external
            FileOutputStream outputStream = null;

            try {
                outputStream = new FileOutputStream(new File(Environment.getDataDirectory(), "test.txt"));

                OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);

                streamWriter.write("test");

                streamWriter.close();
                outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.equals(btnPref)) {
            name = etPref.getText().toString();
            savePreferences(name);
            writeToFile(name);
            writeToFileExternal(name);
        }
    }


}
