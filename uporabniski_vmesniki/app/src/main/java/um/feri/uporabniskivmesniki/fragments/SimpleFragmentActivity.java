package um.feri.uporabniskivmesniki.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import um.feri.uporabniskivmesniki.R;

public class SimpleFragmentActivity extends ActionBarActivity implements MessageFragment.OnFragmentInteractionListener, View.OnClickListener {

    private static final String TAG = "FragmentActivity";

    private Button button1;
    private Button button2;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        int students = intent.getIntExtra("students", 0);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, SimpleFragment.newInstance(), "simple").commit();
        }
    }

    private void addBlankFragment() {
        Fragment simple = getSupportFragmentManager().findFragmentByTag("simple");

        if(simple == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, SimpleFragment.newInstance(), "simple").commit();
        } else {
            Log.d(TAG, "addMessageFragment(): Blank fragment already added!");
        }
    }


    private void addMessageFragment() {
        Fragment message = getSupportFragmentManager().findFragmentByTag("message");

        if(message == null) {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.container, MessageFragment.newInstance("Primer fragmenta"), "message")
                    .addToBackStack(null)
                    .commit();
        } else {
            Log.d(TAG, "addMessageFragment(): Message fragment already added!");
        }
    }

    private void removeFragment() {
        Fragment simple = getSupportFragmentManager().findFragmentByTag("simple");
        Fragment message = getSupportFragmentManager().findFragmentByTag("message");

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if(simple != null) {
            fragmentTransaction.remove(simple);
        } else {
            Log.d(TAG, "removeFragment(): Blank fragment is null!");
        }

        if(message != null) {
            fragmentTransaction.remove(message);
        } else {
            Log.d(TAG, "removeFragment(): Message fragment is null!");
        }

        fragmentTransaction.commit();

        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onMessageToSent(String message) {
        Log.d(TAG, "onMessageToSent(): " + message);

        new AlertDialog.Builder(SimpleFragmentActivity.this).setTitle("Sporočilo").setMessage(message).setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();

    }

    @Override
    public void onClick(View v) {

        if(v.equals(button1)) {
            addBlankFragment();
        }
        else if(v.equals(button2)) {
            addMessageFragment();
        }
        else if(v.equals(button3)) {
            removeFragment();
        }
    }
}
