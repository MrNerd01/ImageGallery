package com.example.swarathesh60.imagegallery;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class DisplayImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        String img = getIntent().getStringExtra("image_display");

        ImageView iv = (ImageView) findViewById(R.id.imageView2);

        iv.setImageURI(Uri.parse(img));



    }
}
