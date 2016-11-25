package com.example.dima.lab1;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {

    private ImageView mImageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mImageView1 = (ImageView) findViewById(R.id.mImageView1);

        Bundle extras = getIntent().getExtras();
        Bitmap imageBitmap =  (Bitmap) extras.get("data");
        mImageView1.setImageBitmap(imageBitmap);

    }

}
