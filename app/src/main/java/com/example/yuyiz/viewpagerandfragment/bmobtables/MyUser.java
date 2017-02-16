package com.example.yuyiz.viewpagerandfragment.bmobtables;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobUser;

/**
 * Created by yuyiz on 2017/2/16.
 */

public class MyUser extends BmobUser implements Parcelable {
    public MyUser() {
    }

    protected MyUser(Parcel in) {
    }

    public static final Creator<MyUser> CREATOR = new Creator<MyUser>() {
        @Override
        public MyUser createFromParcel(Parcel in) {
            return new MyUser(in);
        }

        @Override
        public MyUser[] newArray(int size) {
            return new MyUser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
