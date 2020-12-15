package com.yhw.hwrxdemo.rxOperator;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class RxFilterActivity extends AppCompatActivity {
    private static final String TAG = RxFilterActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        filter();
//        ofType();
//        skip();
//        distinct();
//        take();
//        throttle();
        simple();
    }

    private void filter() {
        // 1. 发送5个事件
        Observable.just(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                // 2. 采用filter（）变换操作符
                .filter(new Predicate<Integer>() {
                    // 根据test()的返回值 对被观察者发送的事件进行过滤 & 筛选
                    // a. 返回true，则继续发送
                    // b. 返回false，则不发送（即过滤）
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer % 2 == 0;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "After Filter Result : " + integer);
                    }
                });
    }

    private void ofType() {
        Observable.just(1, "A", 2, "B", 3, "C")
                .ofType(Integer.class)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "After ofType Result : " + integer);
                    }
                });
    }

    private void skip() {
        Observable.just(1, 2, 3, 4, 5)
                .skip(1)
                .skipLast(1)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "skip first Result : " + integer);
                    }
                });

        Observable.intervalRange(0, 5, 0, 1, TimeUnit.SECONDS)
                .skip(2, TimeUnit.SECONDS)
                .skipLast(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i(TAG, "skip Second Result : " + aLong);
                    }
                });
    }


    private void distinct() {
        Observable.just(1,2, 3, 1, 2, 3)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "distinct result : " + integer);
                    }
                });

        Observable.just(1, 2, 2, 2, 2, 3)
                .distinctUntilChanged()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "distinctUntilChanged result : " + integer);
                    }
                });
    }

    private void take() {
        Observable.just(1, 2, 3, 4)
                .take(2)
                .subscribe(integer -> Log.i(TAG, "take reuslt : " + integer));

        Observable.just(1, 2, 3, 4)
                .takeLast(2)
                .subscribe(integer -> Log.i(TAG, "takeLast reuslt : " + integer));
    }

    private void throttle() {
        Observable.intervalRange(1, 100, 0, 300, TimeUnit.MILLISECONDS)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i(TAG, "throttleFirst result : " + aLong);
                    }
                });
    }

    private void simple() {
        Observable.intervalRange(1, 100, 0, 300, TimeUnit.MILLISECONDS)
                .sample(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i(TAG, "throttleFirst result : " + aLong);
                    }
                });
    }
}
