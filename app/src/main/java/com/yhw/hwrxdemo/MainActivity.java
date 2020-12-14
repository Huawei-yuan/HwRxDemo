package com.yhw.hwrxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.yhw.hwrxdemo.rxNestingReqest.RxNestingRequstActivity;
import com.yhw.hwrxdemo.rxNestingReqest.RxRetryActivity;
import com.yhw.hwrxdemo.rxOperator.RxOperatorActivity;
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

        findViewById(R.id.rx_operator_btn).setOnClickListener(v -> {
            startActivity(new Intent(this, RxOperatorActivity.class));
        });

        findViewById(R.id.rx_nesting_request_btn).setOnClickListener(v -> {
            startActivity(new Intent(this, RxNestingRequstActivity.class));
        });

        findViewById(R.id.rx_retry_request_btn).setOnClickListener(v -> {
            startActivity(new Intent(this, RxRetryActivity.class));
        });
    }

}