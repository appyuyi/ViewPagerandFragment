package com.example.yuyiz.viewpagerandfragment.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yuyiz on 2017/2/13.
 */

public class Data implements Parcelable {
    private int loginState;

    public int getLoginState() {
        return loginState;
    }

    public void setLoginState(int loginState) {
        this.loginState = loginState;
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public Data(int loginState) {
        this.loginState = loginState;
    }

    protected Data(Parcel in) {
        loginState = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(loginState);
    }
}
