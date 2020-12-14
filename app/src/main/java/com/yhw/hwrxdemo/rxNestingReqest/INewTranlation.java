package com.yhw.hwrxdemo.rxNestingReqest;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface INewTranlation {
    @GET("translate?&doctype=json&type=AUTO&i=注册")
    Observable<NewTranlationBean> tranlate1();

    @GET("translate?&doctype=json&type=AUTO&i=登录")
    Observable<NewTranlationBean> tranlate2();

}
