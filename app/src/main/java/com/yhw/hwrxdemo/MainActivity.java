package com.yhw.hwrxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.yhw.hwrxdemo.rxPollingDemo.RxPollingActivity;
import com.yhw.hwrxdemo.rxbasic.RxBasicActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.rx_basic_btn).setOnClickListener(v -> {
            startActivity(new Intent(this, RxBasicActivity.class));
        });

        findViewById(R.id.rx_polling_btn).setOnClickListener(v -> {
            startActivity(new Intent(this, RxPollingActivity.class));
        });
    }

}