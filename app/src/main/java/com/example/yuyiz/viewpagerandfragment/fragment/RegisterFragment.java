package com.example.yuyiz.viewpagerandfragment.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yuyiz.viewpagerandfragment.R;
import com.example.yuyiz.viewpagerandfragment.utils.SMSUtils;

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private final static int STATE_BEFORE = 0;
    private final static int STATE_SENDED_CODE = 1;
    private final static int STATE_VERIFED_CODE_SUCCEED = 2;
    private View view;
    private int currentState = STATE_BEFORE;
    private EditText etFirst;
    private EditText etSecond;
    private Button bt;
    private SMSUtils smsUtils;
    private ProgressDialog requestDialog;
    private ProgressDialog verifyDialog;
    private Context context;
    private String phoneNum;
    private String code;

    public RegisterFragment() {
        // Required empty public constructor
    }


    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        context = getActivity();
        etFirst = (EditText) view.findViewById(R.id.et_register_phone);
        etSecond = (EditText) view.findViewById(R.id.et_register_verify_password);
        bt = (Button) view.findViewById(R.id.bt_register);
        bt.setOnClickListener(this);
        //设置初始状态
        etFirst.setHint("请输入手机号码");
        etFirst.setInputType(InputType.TYPE_CLASS_PHONE);
        etSecond.setText("");
        etSecond.setVisibility(View.GONE);
        bt.setText("获取验证码");
        smsUtils = new SMSUtils(context);
        smsUtils.setSmsCallback(new SMSUtils.SmsCallback() {
            @Override
            public void requestCodeSuccess() {
                //请求验证码成功
                //设置布局
                etFirst.setText("");
                etFirst.setHint("请输入验证吗");
                etFirst.setInputType(InputType.TYPE_CLASS_NUMBER);
                bt.setText("提交验证码");
                currentState = STATE_SENDED_CODE;
                if (requestDialog != null && requestDialog.isShowing()) {
                    requestDialog.dismiss();
                }
            }

            @Override
            public void requestCodeFailed() {
                if (requestDialog != null && requestDialog.isShowing()) {
                    requestDialog.dismiss();
                }
                Toast.makeText(context, "请求验证码失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void verifySmsCodeSuccess() {
                //验证验证码成功
                etFirst.setText("");
                etFirst.setHint("请输入密码");
                etSecond.setVisibility(View.VISIBLE);
                etFirst.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                bt.setText("注册");
                currentState = STATE_VERIFED_CODE_SUCCEED;
                if (verifyDialog != null && verifyDialog.isShowing()) {
                    verifyDialog.dismiss();
                }

            }

            @Override
            public void verifySmsCodeFailed() {
                if (verifyDialog != null && verifyDialog.isShowing()) {
                    verifyDialog.dismiss();
                }
                Toast.makeText(context, "验证码验证失败，请检查", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_register:

                switch (currentState) {
                    case STATE_BEFORE://注册时请求发送验证码
                        RequestSMS();
                        break;
                    case STATE_SENDED_CODE://获取到验证码后验证验证码
                        verifyCode();
                        break;
                    case STATE_VERIFED_CODE_SUCCEED://验证码验证后注册用户
                        register();
                        break;
                }
                break;
        }
    }

    private void register() {
//注册
        Toast.makeText(context, "注册中", Toast.LENGTH_SHORT).show();
    }

    private void verifyCode() {
        //获取验证吗
        code = etFirst.getText().toString();
        //验证验证码
        if (!TextUtils.isEmpty(code)) {
            smsUtils.verifySmsCode(code);

            //设置Dialog
            verifyDialog = new ProgressDialog(context);
            verifyDialog.setTitle("正在验证验证码");
            verifyDialog.show();
        } else {
            Toast.makeText(context, "验证码验证错误", Toast.LENGTH_SHORT).show();
        }
    }

    private void RequestSMS() {
        //获取手机号吗
        phoneNum = etFirst.getText().toString();
        Log.i("RequestSMS", "RequestSMS: "+phoneNum);
        //请求验证码
        if (!TextUtils.isEmpty(phoneNum)) {
            //设置Dialog----注意要先设置Dialog
            requestDialog = new ProgressDialog(context);
            requestDialog.setTitle("正在请求发送验证码");
            requestDialog.show();
            smsUtils.requestSmsCode(phoneNum);
        } else {
            Toast.makeText(context, "请输入号码", Toast.LENGTH_SHORT).show();
        }
    }
}
