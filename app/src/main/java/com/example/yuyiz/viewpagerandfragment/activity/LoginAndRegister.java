package com.example.yuyiz.viewpagerandfragment.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.yuyiz.viewpagerandfragment.R;
import com.example.yuyiz.viewpagerandfragment.fragment.LoginFragment;

public class LoginAndRegister extends AppCompatActivity {
    private FrameLayout fl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_register);
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentManager fM = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fM.beginTransaction();
        fragmentTransaction.replace(R.id.fl_login_and_register, new LoginFragment());
        fragmentTransaction.addToBackStack("LoginAndRegister");
        fragmentTransaction.commit();
    }

}