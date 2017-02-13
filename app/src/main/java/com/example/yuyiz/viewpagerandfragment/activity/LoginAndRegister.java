package com.example.yuyiz.viewpagerandfragment.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yuyiz.viewpagerandfragment.R;

public class LoginAndRegister extends AppCompatActivity implements OnClickListener {
    private EditText etUserName;
    private EditText etPassWord;
    private Button btLogin;
    private TextView clickRegister;
    private TextView clickForgetPassword;
    private String userName;
    private String password;

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
    }

    @Override
    public void onClick(View v) {
    }
}