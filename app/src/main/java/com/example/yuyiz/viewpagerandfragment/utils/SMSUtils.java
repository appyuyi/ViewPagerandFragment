package com.example.yuyiz.viewpagerandfragment.utils;

import android.content.Context;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;

/**
 * Created by yuyiz on 2017/2/12.
 */
public class SMSUtils {
    private final String NAME = "验证";
    private String phoneNumber;
    private String code;
    private Context context;
    private SmsCallback smsCallback;

    public SMSUtils(Context context) {
        this.context = context;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public SmsCallback getSmsCallback() {
        return smsCallback;
    }

    public void setSmsCallback(SmsCallback smsCallback) {
        this.smsCallback = smsCallback;
    }

    /**
     * 请求验证码
     */
    public void requestSmsCode(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        BmobSMS.requestSMSCode(context, this.phoneNumber, NAME, new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {/*请求验证码成功*/
                    smsCallback.requestCodeSuccess();
                } else {/*请求验证码失败*/
                    smsCallback.requestCodeFailed();
                }
            }
        });
    }

    public void verifySmsCode(String code) {
        BmobSMS.verifySmsCode(context, phoneNumber, code, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {/*验证验证码成功*/
                    smsCallback.verifySmsCodeSuccess();
                } else {/*验证验证码失败*/
                    smsCallback.verifySmsCodeFailed();
                }
            }
        });
    }

    public interface SmsCallback {
        void requestCodeSuccess();

        void requestCodeFailed();

        void verifySmsCodeSuccess();

        void verifySmsCodeFailed();
    }
}