package com.example.yuyiz.viewpagerandfragment.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.yuyiz.viewpagerandfragment.R;
import com.example.yuyiz.viewpagerandfragment.activity.LoginAndRegister;

public class FragmentThree extends Fragment implements OnClickListener {
    public View view;
    private Button btJumpToLogin;
    private Intent intentJumpToLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_three, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        init();


        super.onActivityCreated(savedInstanceState);

    }

    private void init() {
        btJumpToLogin = (Button) view.findViewById(R.id.bt_jump_to_login);
        btJumpToLogin.setOnClickListener(this);
        intentJumpToLogin = new Intent(getContext(), LoginAndRegister.class);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_jump_to_login:
                //跳转到Activity_login_and_register
                startActivityForResult(intentJumpToLogin, Activity.RESULT_OK);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
//            Bundle dataExtras = data.getExtras();
//            dataExtras.get("data");
            Toast.makeText(getContext(), "登陆成功", Toast.LENGTH_SHORT).show();
        }

    }
}
