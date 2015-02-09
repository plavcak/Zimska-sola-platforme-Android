package kalk.zimska.feri.si.kalkulator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Kalkulator extends Activity {

    private Button izracun;
    private EditText st1;
    private EditText st2;
    private TextView rezultat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalkulator);

        st1 = (EditText) findViewById(R.id.st1);
        st2 = (EditText) findViewById(R.id.st2);
        rezultat = (TextView) findViewById(R.id.rezultat);
        izracun = (Button) findViewById(R.id.izracun);

        izracun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                izracun();
            }
        });
    }

    private void izracun() {
        int a = Integer.parseInt(st1.getText().toString());
        int b = Integer.parseInt(st2.getText().toString());
        rezultat.setText("" + (a + b));
    }

}
