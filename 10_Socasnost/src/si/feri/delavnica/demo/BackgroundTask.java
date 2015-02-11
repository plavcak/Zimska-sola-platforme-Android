package si.feri.delavnica.demo;

import android.os.AsyncTask;
import android.util.Log;

public class BackgroundTask extends AsyncTask<String, Integer, String> {
	
	private BackgroundTaskResponse resp;
	
	public void setBackgroundTaskResponse(BackgroundTaskResponse r) {
		resp=r;
	}
	
	@Override
	protected String doInBackground(String... params) {
		Log.d(this.getClass().getName(),"doInBackground()");
		StringBuilder ret=new StringBuilder();
		
		int progress=0;
		for (String  s : params) {
			publishProgress(progress++);
			ret.append(s);
		}
		
		return ret.toString();
	}
	
	@Override
	protected void onPostExecute(String result) {
		if (resp!=null) resp.finished(result);
		Log.d(this.getClass().getName(),"onPostExecute() "+result);
		super.onPostExecute(result);
	}
	
	@Override
	protected void onPreExecute() {
		Log.d(this.getClass().getName(),"onPreExecute()");
		super.onPreExecute();
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		Log.d(this.getClass().getName(),"onProgressUpdate() "+values);
		super.onProgressUpdate(values);
	}

}
