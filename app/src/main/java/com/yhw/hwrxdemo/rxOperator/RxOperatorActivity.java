package com.yhw.hwrxdemo.rxOperator;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class RxOperatorActivity extends AppCompatActivity {
    private static final String TAG = RxOperatorActivity.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mapIntegerToString();
//        flatMapIntegerToString();
//        concatIntegerToString();
//        buffer();
//        concat();
//        concatArray();
//        merge();
//        zip();
//        combineLatest();
//        reduce();
        collect();
    }


    private void mapIntegerToString() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            // 1. 被观察者发送事件 = 参数为整型 = 1、2、3
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                    emitter.onNext(1);
                    emitter.onNext(2);
                    emitter.onNext(3);
                    emitter.onComplete();
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "使用 Map变换操作符 将事件" + integer +"的参数从 整型"+integer + " 变换成 字符串类型" + integer;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe  d = " + d);
            }

            @Override
            public void onNext(String value) {
                Log.i(TAG, "onNext  value = " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError  e = " + e);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete");
            }
        });
    }

    private void flatMapIntegerToString() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("我是事件 " + integer + " 拆分出的第 " + i + " 个子事件");
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG, "accept s = " + s);
            }
        });
    }

    private void concatIntegerToString() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("我是事件 " + integer + " 拆分出的第 " + i + " 个子事件");
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG, "accept s = " + s);
            }
        });
    }

    private void buffer() {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .buffer(3, 1)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        Log.d(TAG, " 缓存区里的事件数量 = " +  integers.size());
                        for (Integer value : integers) {
                            Log.d(TAG, " 事件 = " + value);
                        }
                    }
                });
    }


    private void concat() {
        Observable.concat(Observable.just(1,2,3),
                Observable.just(4, 5, 6),
                Observable.just(7, 8, 9))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "concat accept integer = " + integer);

                    }
                });
    }


    private void concatArray() {
        Observable.concatArray(Observable.just(1,2,3),
                Observable.just(4, 5, 6),
                Observable.just(7, 8, 9),
                Observable.just(10, 11, 12),
                Observable.just(13, 14, 15))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "concat accept integer = " + integer);

                    }
                });
    }


    private void merge() {
        Observable.merge(Observable.intervalRange(0, 10, 1, 1, TimeUnit.SECONDS),
                Observable.intervalRange(11, 10, 1, 4, TimeUnit.SECONDS))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i(TAG, "merge accept aLong = " + aLong);
                    }
                });
    }


    private void zip() {
        Observable.zip(Observable.intervalRange(0, 5, 1, 1, TimeUnit.SECONDS),
                Observable.intervalRange(10, 10, 1, 1, TimeUnit.SECONDS),
                new BiFunction<Long, Long, String>() {
                    @Override
                    public String apply(Long aLong, Long aLong2) throws Exception {
                        return "合并 (" + aLong + " , " + aLong2 + ")";
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG, "zip s = " + s);
                    }
                });
    }

    private void combineLatest() {
        Observable.combineLatest(Observable.intervalRange(0, 10, 1, 1, TimeUnit.SECONDS),
                Observable.intervalRange(10, 5, 2, 2, TimeUnit.SECONDS),
                new BiFunction<Long, Long, String>() {
                    @Override
                    public String apply(Long aLong, Long aLong2) throws Exception {
                        return "合并 （" + aLong + ", " + aLong2 + ")";
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG, "result = " + s);
                    }
                });
    }

    private void reduce() {
        Observable.just(1, 2, 4, 4)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        Log.i(TAG, "reduce apply a = " + integer
                                    + " b = " + integer2);
                        return integer + integer2;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "result = " + integer);
                    }
                });
    }

    private void collect() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .collect(new Callable<ArrayList<Integer>>() {
                    @Override
                    public ArrayList<Integer> call() throws Exception {
                        return new ArrayList<>();
                    }
                }, new BiConsumer<ArrayList<Integer>, Integer>() {
                    @Override
                    public void accept(ArrayList<Integer> integers, Integer integer) throws Exception {
                        Log.i(TAG, "collect integers = " + integers
                                + " integer = " + integer);
                        integers.add(integer);
                    }
                }).subscribe(new Consumer<ArrayList<Integer>>() {
            @Override
            public void accept(ArrayList<Integer> integers) throws Exception {
                Log.i(TAG, "collect result =  " + integers);
            }
        });
    }


}

