package com.konka.downloadbuttontest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.konka.customui.DownloadButon;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadButon button = (DownloadButon) findViewById(R.id.button);
        button.setTotalPosition(360);
        button.setCurrentPosition(260);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DownloadButon) view).setCurrentPosition(360);
                Log.i("test","click");
            }
        });
    }
}
