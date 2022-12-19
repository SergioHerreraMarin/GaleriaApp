package com.example.galeriaapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    private ActivityResultLauncher<Intent> activityResultLauncherCamera;
    ImageView imageView;
    private Button buttonOpen, buttonOpenCamera;
    public static int RC_PHOTO_PICKER = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonOpen = findViewById(R.id.buttonOpen);
        buttonOpenCamera = findViewById(R.id.buttonOpenCamera);
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            Uri uri = data.getData();
                            imageView = findViewById(R.id.img);
                            imageView.setImageURI(uri);
                        }
                    }
                });

        activityResultLauncherCamera = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Bundle extras = data.getExtras();
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            imageView.setImageBitmap(imageBitmap);
                        }
                    }
                });

        buttonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSomeActivityForResult(null);
            }
        });

        buttonOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });

    }

    public void openSomeActivityForResult(View view) {
        //Create Intent
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        //Launch activity to get result
        someActivityResultLauncher.launch(intent);
    }


    public void openCamera(){
        //Create Intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        //Launch activity to get result
        someActivityResultLauncher.launch(intent);
    }





}