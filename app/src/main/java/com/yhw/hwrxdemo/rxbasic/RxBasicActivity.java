package com.yhw.hwrxdemo.rxbasic;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RxBasicActivity extends AppCompatActivity {
    private static final String TAG = RxBasicActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //        rxJust();
//        rxFromArray();
//        rxFromIterable();
//        rxOther();
//        rxDeffer();
//        rxTimer();
//        rxInterval();
//        rxIntervalRange();
        rxRange();
    }

    private void rxBasic() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onComplete();
            }
        })/*.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer value) throws Exception {
                Log.i(TAG, "onNext value = " + value);
            }
        });*/.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe");
            }

            @Override
            public void onNext(Integer value) {
                Log.i(TAG, "onNext value = " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError e = " + e);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete");
            }
        });
    }

    private void rxJust() {
        Observable.just(1,2, 3, 4, 5, 6, 7, 8, 9, 10)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.i(TAG, "onNext value = " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError e = " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete");
                    }
                });
    }

    private void rxFromArray() {
        Observable.fromArray(new Integer[]{1,2, 3, 4, 5, 6, 7, 8, 9, 10, 11})
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.i(TAG, "onNext value = " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError e = " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete");
                    }
                });
    }


    private void rxFromIterable() {
        Observable.fromIterable(Arrays.asList(1,2, 3, 4, 5, 6, 7, 8, 9, 10, 11))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.i(TAG, "onNext value = " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError e = " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete");
                    }
                });
    }

    private void rxOther() {

//        Observable<Integer> observable = Observable.empty();
//        Observable<Integer> observable = Observable.error(new RuntimeException());
        Observable<Integer> observable = Observable.never();
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe");
            }

            @Override
            public void onNext(Integer value) {
                Log.i(TAG, "onNext value = " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError e = " + e);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete");
            }
        });
    }

    private Integer i = 0;
    private void rxDeffer() {
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(i);
            }
        });
        i = 1;
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe");
            }

            @Override
            public void onNext(Integer value) {
                Log.i(TAG, "onNext value = " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError e = " + e);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete");
            }
        });
        i = 2;
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe2");
            }

            @Override
            public void onNext(Integer value) {
                Log.i(TAG, "onNext2 value = " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError2 e = " + e);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete2");
            }
        });
    }

    private void rxTimer() {
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.i(TAG, "onNext value = " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError e = " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete");
                    }
                });
    }


    private void rxInterval() {
        Observable.interval(2, 1, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.i(TAG, "onNext value = " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError e = " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete");
                    }
                });
    }


    private void rxIntervalRange() {
        Observable.intervalRange(2, 20, 2, 1, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.i(TAG, "onNext value = " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError e = " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete");
                    }
                });
    }

    private void rxRange() {
        Observable.range(2, 20)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.i(TAG, "onNext value = " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError e = " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete");
                    }
                });
    }
}
