package com.yhw.hwrxdemo.rxNestingReqest;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxNestingRequstActivity extends AppCompatActivity {
    private static final String TAG = RxNestingRequstActivity.class.getSimpleName();
    private Observable<NewTranlationBean> observableRegister;
    private Observable<NewTranlationBean> observableLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        INewTranlation requst = retrofit.create(INewTranlation.class);

        observableRegister = requst.tranlate1();
        observableLogin = requst.tranlate2();

//        nestReq();
        zipReq();
    }


    private void nestReq() {
        observableRegister.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<NewTranlationBean>() {
                    @Override
                    public void accept(NewTranlationBean newTranlationBean) throws Exception {
                        Log.i(TAG, "doOnNext Register request success accept newTranlationBean = " + newTranlationBean);
                    }
                })
                .observeOn(Schedulers.io())//切换到IO线程进行登录请求
                // （新被观察者，同时也是新观察者）切换到IO线程去发起登录请求
                // 特别注意：因为flatMap是对初始被观察者作变换，所以对于旧被观察者，它是新观察者，所以通过observeOn切换线程
                // 但对于初始观察者，它则是新的被观察者
                .flatMap(new Function<NewTranlationBean, ObservableSource<NewTranlationBean>>() {
                    @Override
                    public ObservableSource<NewTranlationBean> apply(NewTranlationBean newTranlationBean) throws Exception {
                        // 将网络请求1转换成网络请求2，即发送网络请求2
                        Log.i(TAG, "flatMap apply register success and request Login newTranlationBean = " + newTranlationBean);
                        return observableLogin;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewTranlationBean>() {
                    @Override
                    public void accept(NewTranlationBean newTranlationBean) throws Exception {
                        Log.i(TAG, "Login success accept newTranlationBean = " + newTranlationBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "Login failed  throwable = " + throwable);
                    }
                });
    }

    private void zipReq() {
        Observable.zip(observableRegister.subscribeOn(Schedulers.io()),
                observableLogin.subscribeOn(Schedulers.io()),
                new BiFunction<NewTranlationBean, NewTranlationBean, String>() {
                    @Override
                    public String apply(NewTranlationBean newTranlationBean, NewTranlationBean newTranlationBean2) throws Exception {
                        Log.i(TAG, "zipReq apply newTranlationBean = " + newTranlationBean
                                + " newTranlationBean2 = " + newTranlationBean2);
                        if (newTranlationBean.translateResult == null
                                || newTranlationBean.translateResult.size() == 0
                                || newTranlationBean.translateResult.get(0).size() == 0)
                            return "newTranlationBean is null";
                        if (newTranlationBean2.translateResult == null
                                || newTranlationBean2.translateResult.size() == 0
                                || newTranlationBean2.translateResult.get(0).size() == 0)
                            return "newTranlationBean2 is null";
                        return newTranlationBean.translateResult.get(0).get(0).tgt
                                + ", " + newTranlationBean2.translateResult.get(0).get(0).tgt;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG, "zipReq success result = " + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "zipReq error throwable = " + throwable);
                    }
                });

    }


}
