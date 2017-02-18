package com.example.yuyiz.viewpagerandfragment.utils;

import android.content.Context;
import android.util.Log;

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
    private RegisterCallback registerCallback;
    private UpdateUserCallback updateUserCallback;
    private LoginOutCallback loginOutCallback;
    private LoginCallback loginCallback;
    private UpdatePasswordCallback updatePasswordCallback;

    public UserUtils(MyUser myUser) {
        this.myUser = myUser;
    }

    public MyUser getMyUser() {
        return myUser;
    }

    public void setMyUser(MyUser myUser) {
        this.myUser = myUser;
    }

    public RegisterCallback getRegisterCallback() {
        return registerCallback;
    }

    public void setRegisterCallback(RegisterCallback registerCallback) {
        this.registerCallback = registerCallback;
    }

    public UpdatePasswordCallback getUpdatePasswordCallback() {
        return updatePasswordCallback;
    }

    public void setUpdatePasswordCallback(UpdatePasswordCallback updatePasswordCallback) {
        this.updatePasswordCallback = updatePasswordCallback;
    }

    public LoginCallback getLoginCallback() {
        return loginCallback;
    }

    public void setLoginCallback(LoginCallback loginCallback) {
        this.loginCallback = loginCallback;
    }

    public LoginOutCallback getLoginOutCallback() {
        return loginOutCallback;
    }

    public void setLoginOutCallback(LoginOutCallback loginOutCallback) {
        this.loginOutCallback = loginOutCallback;
    }

    public UpdateUserCallback getUpdateUserCallback() {
        return updateUserCallback;
    }

    public void setUpdateUserCallback(UpdateUserCallback updateUserCallback) {
        this.updateUserCallback = updateUserCallback;
    }

    //注册用户
    public void registerUser(String userName, String passWord, String MobilePhoneNumber) {
        myUser.setUsername(userName);
        myUser.setPassword(passWord);
        myUser.setMobilePhoneNumber(MobilePhoneNumber);
        myUser.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {

                if (e == null) {

                    if (myUser != null) {
                        //注册成功
                        registerCallback.registerUserSuccess();
                    } else {
                        //注册失败
                        registerCallback.registerUserFailed();
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    //用户登陆
    public void login(final String userName, String passWord) {
        myUser.setUsername(userName);
        myUser.setPassword(passWord);
        myUser.login(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e == null) {
                    if (myUser != null) {
                        //登陆成功
                        loginCallback.loginSuccess();
                    } else {
                        //登陆失败
                        loginCallback.loginFailed();
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    //查询用户
/*    public void queryUserByPhoneNum(String mobilePhoneNumber) {
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.addWhereEqualTo("mobilePhoneNumber", mobilePhoneNumber);
        query.count(MyUser.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    //查询用户成功
                    if (integer > 0) {//object.size>0,查询到用户数据
                        registerCallback.queryPhoneSuccess();
                    } else {
                        registerCallback.queryPhoneFailed();
                    }
                } else {
                    //更新用户信息失败
                    e.printStackTrace();
                }
            }
        });
    }*/

    public void queryUserByPhoneNum(String mobilePhoneNumber) {
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.addWhereEqualTo("mobilePhoneNumber", mobilePhoneNumber);
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> object, BmobException e) {
                if (e == null) {
                    if (object.size() > 0) {
                        //查询用户成功
                        Log.i("用户查询", "done:+++++++++++++查询到用户 a");
                        registerCallback.queryPhoneSuccess();
                        Log.i("用户查询", "done:+++++++++++++查询到用户 b");
                    } else {
                        //更新用户信息失败
                        Log.i("用户查询", "done:+++++++++++++查询不到 a");
                        registerCallback.queryPhoneFailed();
                        Log.i("用户查询", "done:+++++++++++++查询不到 b");
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }


    //查询用户
    public void queryUserByUserName(String userName) {
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.addWhereEqualTo("username", userName);
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> object, BmobException e) {
                if (e == null) {
                    if (object.size() > 0) {
                        //查询用户成功
                        Log.i("用户查询", "done:+++++++++++++查询到用户 a");
                        registerCallback.queryUserSuccess();
                        Log.i("用户查询", "done:+++++++++++++查询到用户 b");
                    } else {
                        //更新用户信息失败
                        Log.i("用户查询", "done:+++++++++++++查询不到 a");
                        registerCallback.queryUserFailed();
                        Log.i("用户查询", "done:+++++++++++++查询不到 b");
                    }
                } else {
                    e.printStackTrace();
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
                    updateUserCallback.updateUserSuccess();
                } else {
                    // 更新用户信息失败
                    updateUserCallback.updateUserFailed();
                }
            }
        });
    }

    //退出登陆
    public void loginOut() {
        MyUser.logOut();   //清除缓存用户对象
        loginOutCallback.loginOut();
    }

    //修改密码
    public void changePassword(String oldPassword, String newPassword) {
        MyUser.updateCurrentUserPassword(oldPassword, newPassword, new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //密码修改成功，可以用新密码进行登录啦
                    updatePasswordCallback.changePasswordSuccess();
                } else {
                    //失败
                    updatePasswordCallback.changePasswordFailed();
                }
            }
        });
    }

    public interface RegisterCallback {

        //注册用户成功
        void registerUserSuccess();

        //注册用户失败
        void registerUserFailed();

        //查询用户成功
        void queryUserSuccess();

        //查询用户失败
        void queryUserFailed();

        //查询用户号码成功
        void queryPhoneSuccess();

        //查询用户号码失败
        void queryPhoneFailed();

    }

    interface UpdateUserCallback {

        //更新用户信息成功
        void updateUserSuccess();

        //更新用户信息失败
        void updateUserFailed();
    }

    interface LoginOutCallback {

        //退出登陆成功
        void loginOut();
    }

    public interface LoginCallback {

        //登陆成功
        void loginSuccess();

        //登陆失败
        void loginFailed();
    }

    interface UpdatePasswordCallback {

        //更新密码成功
        void changePasswordSuccess();

        //更新密码失败
        void changePasswordFailed();
    }
}