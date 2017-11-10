package com.example.dvpires.guideapp.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dvpires.guideapp.R;
import com.example.dvpires.guideapp.camera.camera.GuideCameraActivity;
import com.example.dvpires.guideapp.camera.camera2.GuideCamera2Activity;


public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    //easy way
    ImageView mImageView;
    Button mCaptureButton;
    Button mEasyCameraButton;
    Button mCapture2Button;
    Button rejectCapture2Button;
    TextView notAvailableText;
    static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        init();
    }

    private void init() {
        mImageView = (ImageView) findViewById(R.id.image_camera_view);
        notAvailableText= (TextView) findViewById(R.id.not_available);
        mEasyCameraButton = (Button) findViewById(R.id.easy_camera_start_button);
        mCaptureButton = (Button) findViewById(R.id.camera_start_button);
        mCapture2Button = (Button) findViewById(R.id.camera2_start_button);
        rejectCapture2Button = (Button) findViewById(R.id.reject_camera2_start_button);

        mCaptureButton.setOnClickListener(this);
        mEasyCameraButton.setOnClickListener(this);

        if (!checkSdkVersion()) {
            mCapture2Button.setVisibility(View.GONE);
            rejectCapture2Button.setPaintFlags(rejectCapture2Button.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            notAvailableText.setVisibility(View.GONE);
            rejectCapture2Button.setVisibility(View.GONE);
            mCapture2Button.setOnClickListener(this);
        }


    }

    private boolean checkSdkVersion() {
        return (android.os.Build.VERSION.SDK_INT >= 21);

    }

    private void startGuideCameraActivity() {
        Intent takePictureIntent = new Intent(this, GuideCameraActivity.class);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 500);
        }
    }


    private void startGuideCamera2Activity() {
        Intent takePictureIntent = new Intent(this, GuideCamera2Activity.class);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 500);
        }
    }


    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        } else if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            byte[] byteArray = extras.getByteArray("data");
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            mImageView.setImageBitmap(rotateImage(90, imageBitmap));
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.camera_start_button:
                startGuideCameraActivity();
                break;
            case R.id.easy_camera_start_button:
                dispatchTakePictureIntent();
                break;
            case R.id.camera2_start_button:
                startGuideCamera2Activity();
            default:
                break;
        }
    }

    public Bitmap rotateImage(int angle, Bitmap bitmapSrc) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bitmapSrc, 0, 0,
                bitmapSrc.getWidth(), bitmapSrc.getHeight(), matrix, true);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


}
