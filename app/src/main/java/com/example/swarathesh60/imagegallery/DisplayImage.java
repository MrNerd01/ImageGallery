package com.example.swarathesh60.imagegallery;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class DisplayImage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        String img = getIntent().getStringExtra("img");

        ImageView iv = (ImageView) findViewById(R.id.imageView2);

        iv.setImageURI(Uri.parse(img));



    }
}
