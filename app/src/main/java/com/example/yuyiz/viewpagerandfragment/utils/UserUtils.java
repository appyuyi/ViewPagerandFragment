package com.example.yuyiz.viewpagerandfragment.utils;

import android.content.Context;

import com.example.yuyiz.viewpagerandfragment.bmobtables.MyUser;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by yuyiz on 2017/2/16.
 */

public class UserUtils {
    private MyUser myUser;

    public UserUtils(MyUser myUser) {
        this.myUser = myUser;
    }

    //注册用户
    public void registerUser() {
        myUser.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e == null) {
                    //注册成功
                } else {
                    //注册失败
                }
            }
        });
    }

    //用户登陆
    public void login(String userName, String passWord) {
        myUser.setUsername(userName);
        myUser.setPassword(passWord);
        myUser.login(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e == null) {
                    //登陆成功
                } else {
                    //登陆失败
                }
            }
        });
    }

    //查询用户
    public void queryUser() {
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.addWhereEqualTo("username", "lucky");
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> object, BmobException e) {
                if (e == null) {
                    //查询用户成功
                } else {
                    //更新用户信息失败
                }
            }
        });
    }

    //更新用户
    public void updateUser(Context context) {
        MyUser newUser = new MyUser();
        newUser.setEmail("xxx@163.com");
        MyUser myUser = (MyUser) MyUser.getCurrentUser();
        newUser.update(myUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    // 更新用户信息成功
                } else {
                    // 更新用户信息失败
                }
            }
        });
    }

    //退出登陆
    public void loginOut() {
        MyUser.logOut();   //清除缓存用户对象
        MyUser currentUser = (MyUser) MyUser.getCurrentUser(); // 现在的currentUser是null了
    }

    //修改密码
    public void changePassword(String oldPassword, String newPassword) {
        MyUser.updateCurrentUserPassword(oldPassword, newPassword, new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //密码修改成功，可以用新密码进行登录啦
                } else {
                    //失败
                }
            }
        });
    }

    public interface UserCallback {

        //注册用户成功
        void registerUserSuccess();

        //注册用户失败
        void registerUserFailed();

        //登陆成功
        void loginSuccess();

        //登陆失败
        void loginFailed();

        //查询用户成功
        void queryUserSuccess();

        //查询用户失败
        void queryUserFailed();

        //更新用户信息成功
        void updateUserSuccess();

        //更新用户信息失败
        void updateUserFailed();

        //退出登陆成功
        void loginOutSuccess();

        //退出登陆失败
        void loginOutFailed();

        //更新密码成功
        void changePasswordSuccess();

        //更新密码失败
        void changePasswordFailed();
    }
}
