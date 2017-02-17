package com.example.yuyiz.viewpagerandfragment.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuyiz.viewpagerandfragment.R;
import com.example.yuyiz.viewpagerandfragment.bmobtables.MyUser;
import com.example.yuyiz.viewpagerandfragment.object.Data;
import com.example.yuyiz.viewpagerandfragment.utils.UserUtils;

import static android.app.Activity.RESULT_OK;

public class LoginFragment extends Fragment implements OnClickListener {
    private Activity mainActivity;
    private Context context;
    private View view;
    private EditText etUserName;
    private EditText etPassWord;
    private Button btLogin;
    private TextView clickRegister;
    private TextView clickForgetPassword;
    private String userName;
    private String password;
    private Intent intent;
    private Data data;
    private Bundle bundle;
    private FragmentTransaction fragmentTransaction;
    private UserUtils userUtils;

    public LoginFragment() {
        // Required empty public constructor
    }


    public static LoginFragment newInstance() {
        LoginFragment loginFragment = new LoginFragment();
        Bundle bundle = new Bundle();
        loginFragment.setArguments(bundle);
        return loginFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //设置参数
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        mainActivity = getActivity();
        userUtils = new UserUtils(new MyUser());
        etUserName = (EditText) view.findViewById(R.id.et_username);
        etPassWord = (EditText) view.findViewById(R.id.et_password);
        btLogin = (Button) view.findViewById(R.id.bt_login);
        clickRegister = (TextView) view.findViewById(R.id.text_register);
        clickForgetPassword = (TextView) view.findViewById(R.id.text_forget_password);
        etUserName.setOnClickListener(this);
        etPassWord.setOnClickListener(this);
        btLogin.setOnClickListener(this);
        clickRegister.setOnClickListener(this);
        clickForgetPassword.setOnClickListener(this);
        intent = mainActivity.getIntent();
        bundle = new Bundle();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }

    private void jumpToForgetPassword() {
        fragmentTransaction.replace(R.id.fl_login_and_register, ResetPasswordFragment.newInstance());
        commit();
    }

    private void jumpToRegister() {
        fragmentTransaction.replace(R.id.fl_login_and_register, RegisterFragment.newInstance());
        commit();
    }

    private void login() { /*获取用户输入的用户名和密码*/
        userName = etUserName.getText().toString().trim();
        password = etPassWord.getText().toString().trim();
        if (!userName.equals("") && !password.equals("")) {
            login(userName, password);
        } else {
            Toast.makeText(mainActivity, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }
    }

    private void login(String userName, String password) {
        //TODO 写登陆逻辑

        userUtils.login();
        intent.putExtra("data", "注册成功");
        mainActivity.setResult(RESULT_OK, intent);
        mainActivity.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:/*点击登陆*/
                login();
                break;
            case R.id.text_register:/*点击注册*/
                jumpToRegister();//调到注册页面
                break;
            case R.id.text_forget_password:/*点击忘记密码*/
                jumpToForgetPassword();
                break;
        }

    }

    private void commit() {
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
