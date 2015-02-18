package si.feri.um.uporabniskivmesnik;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import si.feri.um.uporabniskivmesnik.fragments.SimpleFragmentActivity;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.button);

        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.equals(btn1)) {

            Intent intent = new Intent(getApplicationContext(), SimpleFragmentActivity.class);
            intent.putExtra("title", "Zimska šola platforme Android");
            intent.putExtra("students", 12);
            startActivity(intent);

            startActivityForResult(intent, 14);

        }

    }
}
