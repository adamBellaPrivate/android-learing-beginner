package com.learn.bella.cameraapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private final static int CAMERA_IMAGE_REQUEST = 12;
    private String mCurrentPhotoPath;
    private final static int CAMERA_PERMISSION_CODE = 2;
    private final static int EXTERNAL_STORAGE_PERMISSION_CODE = 3;
    private Camera mCamera;
    private MyPreview mPreview;
    private FrameLayout previewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        previewLayout =  findViewById(R.id.camera_preview);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        EXTERNAL_STORAGE_PERMISSION_CODE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

        }

        Button cameraButton = findViewById(R.id.open_camera_app);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File photoFile = null;
                try {
                    photoFile = createImageFile();
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", photoFile);
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(cameraIntent, CAMERA_IMAGE_REQUEST);
                    }
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
            }
        });
    }

    @Override
    protected void onStop() {
        stopCamera();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        CAMERA_PERMISSION_CODE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

        }else{
            startCamera();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "PHOTO_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                FileInputStream fis = null;
                try {
                     File imageFile = new File(mCurrentPhotoPath);
                     fis = new FileInputStream(imageFile);
                     Bitmap img = BitmapFactory.decodeStream(fis);
                     ImageView imageViewPhoto = findViewById(R.id.imageView);
                     imageViewPhoto.setImageBitmap(img);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            finally {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    startCamera();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
            }
            case EXTERNAL_STORAGE_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
            }
        }
    }

    private boolean checkCameraHardware() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return true;
        } else {
            return false; }
    }

    //Camera in your app

    public static Camera getCameraInstance(){
        Camera c = null;

        try {
            c = Camera.open();

            Camera.Parameters p = c.getParameters();
            for (Camera.Size s : p.getSupportedPictureSizes()) {
                Log.d("CAM", s.width + "*" + s.height);
            }
        } catch (Exception e){
            e.printStackTrace();
            //You have already used the camera or it is not available.
        }

        return c; // If you have an error, you will get an null object in this point.
    }

    private void startCamera(){
        boolean hasCamera = checkCameraHardware();

        if(hasCamera){
            mCamera = getCameraInstance();
           if(mCamera != null) {
               mPreview = new MyPreview(this, mCamera);
               previewLayout.addView(mPreview);

               Button takeAPictureButton = findViewById(R.id.take_a_picture_button);
               takeAPictureButton.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       mCamera.takePicture(null, null,mPicture);

                   }
               });
           }
        }
    }

    private void stopCamera(){
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            previewLayout.removeView(mPreview);
            mCamera = null;
        }
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.d("CAM", " Size: " + data.length);
            //You should here these codes in background thread, and maybe don't have to call the "startPreview"

            ByteArrayInputStream imageStream = new ByteArrayInputStream(data);
            Bitmap image = BitmapFactory.decodeStream(imageStream);
            ImageView imageViewPhoto = findViewById(R.id.imageView);
            imageViewPhoto.setImageBitmap(image);

            mCamera.startPreview();
        }
    };
}
