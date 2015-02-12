package um.feri.uporabniskivmesniki;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.widget.FacebookDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FacebookActivity extends ActionBarActivity implements View.OnClickListener {
    private static final String TAG = "FacebookActivity";

	private static final String PERMISSION = "publish_actions";

	private UiLifecycleHelper uiHelper;
	private PendingAction pendingAction = PendingAction.NONE;

    private enum PendingAction { NONE, POST_STATUS_UPDATE }

    private Button btnShare;
    private EditText editText;
    private ProgressDialog ringProgressDialog;

    private boolean canPresentShareDialog;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_facebook);
		
		uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
		btnShare = (Button) findViewById(R.id.btnSend);
        editText = (EditText) findViewById(R.id.editText);

		btnShare.setOnClickListener(this);
		
        canPresentShareDialog = FacebookDialog.canPresentShareDialog(this, FacebookDialog.ShareDialogFeature.SHARE_DIALOG);

        // Generate Facebook KEY HASH
        try {
            PackageInfo info = getPackageManager().getPackageInfo("um.feri.uporabniskivmesniki", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

	}

    @Override
    public void onClick(View v) {
        if(editText.getText().length() > 0) {
            onClickPostStatusUpdate();
        } else {
            Toast.makeText(getApplicationContext(), "Vpiši besedilo", Toast.LENGTH_SHORT).show();
        }
    }

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
    
    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (pendingAction != PendingAction.NONE && (exception instanceof FacebookOperationCanceledException || exception instanceof FacebookAuthorizationException)) {
                new AlertDialog.Builder(this)
                    .setTitle("Cancleled")
                    .setMessage("permission not granted")
                    .setPositiveButton("OK", null)
                    .show();
            pendingAction = PendingAction.NONE;
        } else if (state == SessionState.OPENED_TOKEN_UPDATED || state == SessionState.OPENED) {
            handlePendingAction();
        }
    }
    
    public void performPublish(PendingAction action, boolean allowNoSession) {
        Session session = Session.getActiveSession();
        if (session != null) {
        	
        	pendingAction = action;
            
            if (hasPublishPermission()) {
                handlePendingAction();
                return;
            } else if (session.isOpened()) {
                session.requestNewPublishPermissions(new Session.NewPermissionsRequest(this, PERMISSION));
                return;
            } else if(!session.isOpened()) {
				Session s = Session.openActiveSession(FacebookActivity.this, true, callback);
				Session.setActiveSession(s);
            }
        } else {
        	Log.d(TAG, "Session is null-");
        }
        
        if (allowNoSession) {
            pendingAction = action;
            handlePendingAction();
        }
    }
    
    public void handlePendingAction() {
        PendingAction previouslyPendingAction = pendingAction;
        pendingAction = PendingAction.NONE;
        
        if(PendingAction.POST_STATUS_UPDATE.equals(previouslyPendingAction)) {
        	postStatusUpdate();
        }
    }
    
    public boolean hasPublishPermission() {
        Session session = Session.getActiveSession();
        return session != null && session.isOpened() && session.getPermissions().contains("publish_actions");
    }
    
    private void showPublishResult(String message, GraphObject result, FacebookRequestError error) {
        String title = null;
        String alertMessage = null;
        if (error == null) {
            title = "Facebook";
            @SuppressWarnings("unused")
			String id = result.cast(GraphObjectWithId.class).getId();
            alertMessage = "Sporočilo objavljeno.";
        } else {
            title = "Napaka";
            alertMessage = error.getErrorMessage();
        }
        
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(alertMessage)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
    
    private interface GraphObjectWithId extends GraphObject {
        String getId();
    }
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		uiHelper.onPause();
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		uiHelper.onActivityResult(arg0, arg1, arg2, dialogCallback);
		super.onActivityResult(arg0, arg1, arg2);
	}
	
    private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
        @Override
        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
            Log.d(TAG, String.format("Error: %s", error.toString()));
            for(StackTraceElement e : error.getStackTrace()) {
            	Log.d(TAG, e.toString());
            }

            Log.d(TAG, String.format("Error: %s", error.getMessage()));
            Log.d(TAG, String.format("Error: %s", data));
        }
        
        @Override
        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
            Log.d(TAG, "Success!");
        }
    };
    
    private void onClickPostStatusUpdate() {
        performPublish(PendingAction.POST_STATUS_UPDATE, canPresentShareDialog);
    }

    /**
     *
     * @return
     */
    private FacebookDialog.ShareDialogBuilder createShareDialogBuilderForLink() {
        return new FacebookDialog.ShareDialogBuilder(this)
                .setDescription("Zimska šola platforme Android")
                .setLink("http://ii.uni-mb.si/stud-aktivnosti/pocitniskesole/android-zimska-2015/");
    }


    private void postStatusUpdate() {
        if (canPresentShareDialog) {
            FacebookDialog shareDialog = createShareDialogBuilderForLink().build();
            uiHelper.trackPendingDialogCall(shareDialog.present());
        } else if (hasPublishPermission()) {
        	postStatusUpdate(editText.getText().toString());
            editText.setText(null);
        } else {
            pendingAction = PendingAction.POST_STATUS_UPDATE;
        }
    }

    /**
     * Objava statusa brez nameščene Facebook apliakcije
     * @param message
     */
    private void postStatusUpdate(String message) {
        Log.d(TAG, "postStatusUpdate(): " + message);

        ringProgressDialog = ProgressDialog.show(FacebookActivity.this, "Prosimo počakajte ...",	"Poteka objavljaje na Facebook ...", true, false);

        Bundle postParams = new Bundle();
        postParams.putString("message", message);
        postParams.putString("description", "Zimska šola platforme Android");
        postParams.putString("link", "http://ii.uni-mb.si/stud-aktivnosti/pocitniskesole/android-zimska-2015/");
        
        Request request = new Request(Session.getActiveSession(), "me/feed", postParams, HttpMethod.POST,  new Request.Callback() {
            @Override
            public void onCompleted(Response response) {
                showPublishResult("Response", response.getGraphObject(), response.getError());
                if(ringProgressDialog != null) ringProgressDialog.dismiss();
            }
        });

        request.executeAsync();
    }

}
