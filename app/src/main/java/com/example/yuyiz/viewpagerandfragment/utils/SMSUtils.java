package com.example.yuyiz.viewpagerandfragment.utils;

/**
 * Created by yuyiz on 2017/2/12.
 */

public class SMSUtils {
    private String number;
    private String code;
    private String context;
    private SmsCallback smsCallback;

    public interface SmsCallback {
        void requestCodeSuccess();

        void requestCodeFailed();

        void verifySmsCodeSuccess();

        void verifySmsCodeFailed();
    }

}
