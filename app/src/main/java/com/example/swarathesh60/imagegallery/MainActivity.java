package com.example.swarathesh60.imagegallery;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements ActivityCompat.OnRequestPermissionsResultCallback{

    GridView gridView;
    ArrayList<File> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now

                            list = imageReader(Environment.getExternalStorageDirectory());
                            gridView = (GridView) findViewById(R.id.grid);
                            gridView.setAdapter(new GridAdapter());
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }



    private ArrayList<File> imageReader(File externalStorageDirectory) {
        ArrayList<File> lis = new ArrayList<>();
        try {
            File[] files = externalStorageDirectory.listFiles();

            for (int i = 0 ; i < files.length-1 ; i++){
                if (files[i].isDirectory()){
                    lis.addAll(imageReader(files[i]));
                }else{
                    if (files[i].getName().endsWith(".jpg")){
                        lis.add(files[i]);
                    }
                }
            }

        }catch (NullPointerException e){
            Toast.makeText(getApplicationContext(),"null pointer excep",Toast.LENGTH_LONG).show();
        }

        return lis;
    }

    private class GridAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View layout = convertView;
            if (layout == null){
                layout = LayoutInflater.from(MainActivity.this).inflate(R.layout.grid_row,parent,false);
            }

            ImageView image =  layout.findViewById(R.id.imageView);
//            Glide.with(MainActivity.this).load(Uri.parse(getItem(position).toString()))
//                    .into(image);

            Glide.with(MainActivity.this)
                    .load( new File(String.valueOf(Uri.parse(getItem(position).toString()))))
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(image);





            return layout;
        }
    }
}
