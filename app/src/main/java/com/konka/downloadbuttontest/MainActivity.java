package com.konka.downloadbuttontest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.konka.customui.DownloadButon;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadButon buton = (DownloadButon) findViewById(R.id.button);
        buton.setTotalPosition(360);
        buton.setCurrentPosition(90);
    }
}
