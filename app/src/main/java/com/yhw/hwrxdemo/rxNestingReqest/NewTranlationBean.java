package com.yhw.hwrxdemo.rxNestingReqest;

import androidx.annotation.Keep;

import java.util.List;

@Keep
public class NewTranlationBean {

    public String type;
    public int errorCode;
    public long elapsedTime;
    public List<List<TranslateResultBean>> translateResult;

    @Keep
    public static class TranslateResultBean {
        public String src;
        public String tgt;

        @Override
        public String toString() {
            return "TranslateResultBean{" +
                    "src='" + src + '\'' +
                    ", tgt='" + tgt + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewTranlationBean{" +
                "type='" + type + '\'' +
                ", errorCode=" + errorCode +
                ", elapsedTime=" + elapsedTime +
                ", translateResult=" + translateResult +
                '}';
    }
}
