package com.example.yuyiz.viewpagerandfragment.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuyiz.viewpagerandfragment.R;
import com.example.yuyiz.viewpagerandfragment.object.Data;

public class LoginAndRegister extends AppCompatActivity implements OnClickListener {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_register); /*初始化组件*/
        init();
    }

    private void init() {
        etUserName = (EditText) findViewById(R.id.et_username);
        etPassWord = (EditText) findViewById(R.id.et_password);
        btLogin = (Button) findViewById(R.id.bt_login);
        clickRegister = (TextView) findViewById(R.id.text_regist);
        clickForgetPassword = (TextView) findViewById(R.id.text_forget_password);
        etUserName.setOnClickListener(this);
        etPassWord.setOnClickListener(this);
        btLogin.setOnClickListener(this);
        clickRegister.setOnClickListener(this);
        clickForgetPassword.setOnClickListener(this);
        intent = getIntent();
        bundle = new Bundle();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:/*点击登陆*/
                login();
                break;
            case R.id.text_regist:/*点击注册*/
                jumpToRegister();
                break;
            case R.id.text_forget_password:/*点击忘记密码*/
                jumpToForgetPassword();
                break;
        }
    }

    private void jumpToForgetPassword() {
    }

    private void jumpToRegister() {
    }

    private void login() { /*获取用户输入的用户名和密码*/
        userName = etUserName.getText().toString().trim();
        password = etPassWord.getText().toString().trim();
        if (!userName.equals("") && !password.equals(""))
            login(userName, password);
    }

    private void login(String userName, String password) {
        Toast.makeText(this, "登陆成功！", Toast.LENGTH_SHORT).show();
        data = new Data(0);
        bundle.putParcelable("data", data);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
        /*  BmobQuery bmobQuery = new BmobQuery(); bmobQuery.doSQLQuery("", new SQLQueryListener() { @Override public void done(BmobQueryResult bmobQueryResult, BmobException e) { } @Override public void done(Object o, Object o2) { } });*/
    }
}