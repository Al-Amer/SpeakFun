package com.apptoolsversion1.speakfun;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


public class FlashActivity extends AppCompatActivity {

    TextView textViewTitle;
    ImageView imageFlashLight;
    Button buttonBack;
    boolean flashOnOff;
    boolean hasCameraFlash = false;
    boolean flashOn = false;
    CameraManager  cameraManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        textViewTitle = findViewById(R.id.titkeFlashlight);
        imageFlashLight = findViewById(R.id.imageViewFlashFlashLight);
        buttonBack = findViewById(R.id.flashbuttonBack);
        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
            if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
                Toast.makeText(FlashActivity.this, getText(R.string.flash), Toast.LENGTH_SHORT).show();
                imageFlashLight.setEnabled(true);
            }else{
                Toast.makeText(FlashActivity.this, getText(R.string.NoFlash), Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(FlashActivity.this, getText(R.string.NoCamera), Toast.LENGTH_SHORT).show();
        }
        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        imageFlashLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hasCameraFlash){
                    if(flashOn){
                        flashOn = false;
                        imageFlashLight.setImageResource(R.drawable.flashoff);
                        try {
                            flashLightOff();
                            Toast.makeText(FlashActivity.this, getText(R.string.LightOff), Toast.LENGTH_SHORT).show();
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }else{
                        flashOn = true;
                        imageFlashLight.setImageResource(R.drawable.flahon);
                        try {
                            flashLightOn();
                            Toast.makeText(FlashActivity.this, getText(R.string.LightOn), Toast.LENGTH_SHORT).show();
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    Toast.makeText(FlashActivity.this, getText(R.string.NoFlash), Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent integerBack = new Intent(FlashActivity.this,MainActivity.class);
                startActivity(integerBack);
            }
        });


    }

    private void flashLightOn() throws CameraAccessException{
        assert cameraManager != null;
        String cameraId = cameraManager.getCameraIdList()[0];
        try{
            cameraManager.setTorchMode(cameraId,true);
        }catch (CameraAccessException e){
            e.printStackTrace();
        }

        Toast.makeText(FlashActivity.this, getText(R.string.LightOn), Toast.LENGTH_SHORT).show();
    }
    private void flashLightOff() throws CameraAccessException{

        cameraManager.setTorchMode("0",false);
        Toast.makeText(FlashActivity.this, getText(R.string.LightOff), Toast.LENGTH_SHORT).show();
    }

}