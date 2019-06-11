package com.wings.androidhelper;

import android.os.Bundle;

import com.wings.helper.CompressImageHelper;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String path = "/storage/emulated/0/Pictures/1556621253166.jpg";
//        CompressImageHelper.compressImage(path, 500, 500);
    }
}
