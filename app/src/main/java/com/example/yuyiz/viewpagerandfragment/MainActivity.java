package com.example.yuyiz.viewpagerandfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.yuyiz.viewpagerandfragment.adapter.MyViewPagerAdapter;
import com.example.yuyiz.viewpagerandfragment.fragment.FragmentOne;
import com.example.yuyiz.viewpagerandfragment.fragment.FragmentThree;
import com.example.yuyiz.viewpagerandfragment.fragment.FragmentTwo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private FragmentOne fragmentOne;
    private FragmentTwo fragmentTwo;
    private FragmentThree fragmentThree;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private int page = 0;
    private FragmentPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        init();
        setLisenlear();
    }

    private void setLisenlear() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                page = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void init() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        fragments = new ArrayList<Fragment>();
        fragmentOne = new FragmentOne();
        fragmentTwo = new FragmentTwo();
        fragmentThree = new FragmentThree();
        fragments.add(fragmentOne);
        fragments.add(fragmentTwo);
        fragments.add(fragmentThree);
        pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);

        //底部导航栏
        textView1 = (TextView) findViewById(R.id.tv_a);
        textView2 = (TextView) findViewById(R.id.tv_b);
        textView3 = (TextView) findViewById(R.id.tv_c);

        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_a:
                if (page != 0) {
                    viewPager.setCurrentItem(0);
                    Log.i("page", "点击了1");
                }
                break;
            case R.id.tv_b:
                if (page != 1) {
                    viewPager.setCurrentItem(1);
                    Log.i("page", "点击了2");
                }
                break;
            case R.id.tv_c:
                if (page != 2) {
                    viewPager.setCurrentItem(2);
                    Log.i("page", "点击了3");
                }
                break;
        }
    }
}
