package si.feri.delavnica.demo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	SeekBar sb1;
	SeekBar sb2;
	TextView tw1;
	
	Button delavec;
	Button startAsync;
	Button startDoSto;
	Button start;
	Button stop;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        sb1=(SeekBar)findViewById(R.id.seekBar1);
        sb2=(SeekBar)findViewById(R.id.seekBar2);
        tw1=(TextView)findViewById(R.id.textView1);
        
        OnSeekBarChangeListener osbl=new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				calculate();
			}
		};
		sb1.setOnSeekBarChangeListener(osbl);
		sb2.setOnSeekBarChangeListener(osbl);
		
		start=(Button)findViewById(R.id.buttonStartService);
		stop=(Button)findViewById(R.id.buttonStopService);
		startDoSto=(Button)findViewById(R.id.buttonStartServiceDoSto);
		startAsync=(Button)findViewById(R.id.buttonAsyncTask);
		delavec=(Button)findViewById(R.id.buttonDelavec);
		
		start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				buttonStartServiceClick();
			}
		});
		
		stop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				buttonStopServiceClick();
			}
		});
		
		startDoSto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				buttonStartDoStoClick();
			}
		});
		
		startAsync.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				buttonStartAsyncClick();
			}
		});
		
		delavec.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				buttonDelavecClick();
			}
		});
		
    }

    private void calculate() {
    	int a=sb1.getProgress();
    	int b=sb2.getProgress();
    	tw1.setText((a+b)+" ");
    }
    
    private void buttonDelavecClick(){
    	startService(new Intent(this,Delavec.class));
    }
    
    private void buttonStartDoStoClick(){
    	startService(new Intent(this,StevecDoSto.class));
    }
    
    private void buttonStartServiceClick(){
    	startService(new Intent(this,Stevec.class));
    }
    
    private void buttonStopServiceClick(){
    	stopService(new Intent(this,Stevec.class));
    }
    
    private void buttonStartAsyncClick() {
    	BackgroundTask bt=new BackgroundTask();
    	bt.setBackgroundTaskResponse(new BackgroundTaskResponse() {
    		@Override
    		public void finished(String s) {
    			tw1.setText(s);
    		}
    	});
    	bt.execute("a","b","c");
    }
    
    BroadcastReceiver rec = new BroadcastReceiver() {
    	@Override
    	public void onReceive(Context context, Intent intent) {
    		tw1.setText(intent.getExtras().getInt("i")+"");
    	}
    };
    
    protected void onResume() {
    	registerReceiver(rec, new IntentFilter("SporociloZaBroadcast"));
    	super.onResume();
    };
    
    @Override
    protected void onPause() {
    	unregisterReceiver(rec);
    	super.onPause();
    }

}
