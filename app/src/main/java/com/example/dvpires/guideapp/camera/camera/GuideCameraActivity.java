package com.example.dvpires.guideapp.camera.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.dvpires.guideapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;
import static java.lang.Math.abs;

public class GuideCameraActivity extends Activity {

    private static Camera mCamera;
    private GuideCameraPreview mPreview;
    private Camera.PictureCallback mPicture;
    private FrameLayout preview;

    static final int MEDIA_TYPE_IMAGE = 1;

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "GuideApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = null;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_camera);
        if (checkCameraHardware(this)) {
            // Create an instance of Camera
            mCamera = getCameraInstance();
            Camera.Parameters params = mCamera.getParameters();
            Camera.Size mSize = null;
            preview = (FrameLayout) findViewById(R.id.camera_preview);
            // Check what resolutions are supported by your camera
            List<Camera.Size> sizes = params.getSupportedPreviewSizes();
            mSize = getBestSize(sizes, preview.getWidth(), preview.getHeight());
            params.setPreviewSize(mSize.width, mSize.height);
            params.setPictureSize(mSize.width, mSize.height);
            mCamera.setParameters(params);

            // Create our Preview view and set it as the content of our activity.
            mPreview = new GuideCameraPreview(this, mCamera);
            preview.addView(mPreview);
            init();
            guideTakePicture();
        }
    }

    public void init() {
        Button captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        mCamera.takePicture(null, null, mPicture);

                    }
                }
        );
    }

    /**
     * Check if this device has a camera
     */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance

        } catch (Exception e) {
            Log.e("TAG", e.getMessage());
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();              // release the camera immediately on pause event
    }


    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

    public void guideTakePicture() {

        mPicture = new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

                File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
                if (pictureFile == null) {
                    Log.d(TAG, "Error creating media file, check storage permissions");
                    return;
                }
                FileOutputStream fos = null;
                Intent intent = new Intent();
                intent.putExtra("data", data);
                try {
                    fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                } catch (FileNotFoundException e) {
                    Log.d(TAG, "File not found: " + e.getMessage());
                } catch (IOException e) {
                    Log.d(TAG, "Error accessing file: " + e.getMessage());
                } finally {
                    try {
                        fos.close();
                        releaseCamera();
                        setResult(RESULT_OK, intent);
                        finish();
                    } catch (NullPointerException | IOException e) {

                    }
                }
            }
        };

    }

    private Camera.Size getBestSizeInner(List<Camera.Size> suportedSizes, float targetRatio,
                                         float ratioLimit) {
        int bestArea = 0;
        Camera.Size bestSize = null;
        for (Camera.Size size : suportedSizes) {
            float ratioDiff = abs(((float) size.width / (float) size.height) - targetRatio);
            int area = size.height * size.width;

            if (ratioDiff < ratioLimit && area >= bestArea) {
                bestArea = area;
                bestSize = size;
            }
        }
        return bestSize;
    }

    private float getNewTargetRatio(List<Camera.Size> suportedSizes, float targetRatio) {
        float newTargetRatio = 0;
        boolean foundHigherRatio = false;

        //pega a menor razão que seja maior do que targetRatio, caso não exista, pega a maior razão

        for (Camera.Size size : suportedSizes) {
            float ratio = (float) size.width / (float) size.height;

            if (!foundHigherRatio) {
                if (ratio > targetRatio) {
                    //achou uma razão maior do que o target antigo
                    foundHigherRatio = true;
                    newTargetRatio = ratio;
                } else if (ratio > newTargetRatio) {
                    //achou uma razão maior
                    newTargetRatio = ratio;
                }
            } else if (ratio > targetRatio && ratio < newTargetRatio) {
                newTargetRatio = ratio;
            }
        }

        return newTargetRatio;
    }

    public Camera.Size getBestSize(List<Camera.Size> suportedSizes, int width, int height) {

        float targetRatio = (float) width / (float) height;
        float ratioLimit = 0.16f;

        Camera.Size bestSize = getBestSizeInner(suportedSizes, targetRatio, ratioLimit);

        if (bestSize == null) //nenhum tamanho de câmera respeita a razão, muda o limiar da busca
        {
            //pega um limiar que é garantido estar
            targetRatio = getNewTargetRatio(suportedSizes, targetRatio);
            bestSize = getBestSizeInner(suportedSizes, targetRatio, ratioLimit);
        }

        return bestSize;
    }
}
