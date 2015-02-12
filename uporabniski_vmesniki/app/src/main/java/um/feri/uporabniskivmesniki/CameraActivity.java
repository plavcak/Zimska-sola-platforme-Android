package um.feri.uporabniskivmesniki;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import um.feri.uporabniskivmesniki.R;

public class CameraActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = "CameraActivity";
    
    private static final int INTENT_REQUEST_GALLERY = 11;
    private static final int INTENT_REQUEST_CAMERA = 12;

    private Button btnCamera;
    private Button btnCameraStore;
    private Button btnStorage;
    private ImageView imageView;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        btnCamera = (Button) findViewById(R.id.button4);
        btnCameraStore = (Button) findViewById(R.id.button7);
        btnStorage = (Button) findViewById(R.id.button5);
        imageView = (ImageView) findViewById(R.id.imageView);

        btnCamera.setOnClickListener(this);
        btnStorage.setOnClickListener(this);
        btnCameraStore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.equals(btnCamera)) {
            pickNewImage();
        }

        else if(v.equals(btnStorage)) {
            pickExistingImage();
        }

        else if (v.equals(btnCameraStore)) {
            pickNewImageThumbnail();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == INTENT_REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
            Log.d(TAG, "Image URI: " + imageUri);
            imageView.setImageURI(imageUri);
        }

        if(requestCode == INTENT_REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
            } else {
                Log.d(TAG, "Image URI: " + imageUri);
                imageView.setImageURI(imageUri);
                galleryAddPic(imageUri);
            }
        }

        if(resultCode != Activity.RESULT_OK) {
            Log.d(TAG, "onActivityResult(): Image NOT created or selected!");
        }


    }

    public void pickExistingImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");

        if (photoPickerIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(photoPickerIntent, INTENT_REQUEST_GALLERY);
        }
    }

    public void pickNewImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        imageUri = createImageUri();
        if(imageUri != null) intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, INTENT_REQUEST_CAMERA);
        }
    }

    public void pickNewImageThumbnail() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, INTENT_REQUEST_CAMERA);
        }
    }

    protected Uri createImageUri() {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {

                String imageFileName = "zimska_sola_" + System.currentTimeMillis();

                File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                storageDir.mkdirs();

                File image = File.createTempFile(imageFileName,".jpg", storageDir);

                return Uri.fromFile(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private void galleryAddPic(Uri imageUri) {
        if(imageUri == null) return;

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(imageUri);
        this.sendBroadcast(mediaScanIntent);
    }

}
