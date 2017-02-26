package com.example.yuyiz.viewpagerandfragment.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yuyiz.viewpagerandfragment.R;
import com.example.yuyiz.viewpagerandfragment.adapter.MyBusinessListAdapter;
import com.example.yuyiz.viewpagerandfragment.object.Business;
import com.example.yuyiz.viewpagerandfragment.utils.DataUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class FragmentOne extends Fragment implements View.OnClickListener {

    private View view;
    private ListView businessListView;
    private ArrayList<Business> businessArrayList;
    private Context context;
    private DataUtils dataUtils;
    private MyBusinessListAdapter myBusinessListAdapter;
    private View listViewSuspended;
    private PullToRefreshListView pullToRefreshListView;
    private View header;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //businessArrayList存储商户信息
        businessArrayList = new ArrayList<Business>();

        //初始化DataUtils
        dataUtils = new DataUtils(getContext());

        //初始化适配器
        myBusinessListAdapter = new MyBusinessListAdapter(businessArrayList, getContext());

        //获取PullToRefreshListView
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_to_refresh_list_view);

        //从PullToRefreshListView中获取ListView
        businessListView = pullToRefreshListView.getRefreshableView();

        //获取头部,header为ListView上方的复杂UI
        header = View.inflate(getContext(), R.layout.hander, null);

        //悬浮的ListView
        listViewSuspended = view.findViewById(R.id.f1_listview_suspended_);

        //添加头部
        businessListView.addHeaderView(header);


        //跟随ListView滑动的悬浮组件
        View myView = View.inflate(getContext(), R.layout.listview_suspended, null);
        Button button1 = (Button) myView.findViewById(R.id.button1);
        Button button2 = (Button) myView.findViewById(R.id.button2);
        Button button3 = (Button) myView.findViewById(R.id.button3);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);

        //悬浮组件
        Button button4 = (Button) view.findViewById(R.id.button4);
        Button button5 = (Button) view.findViewById(R.id.button5);
        Button button6 = (Button) view.findViewById(R.id.button6);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);

        //添加跟着ListView滚动的悬浮组件
        businessListView.addHeaderView(myView);

        //初始化时第一次查询和加载数据
        dataUtils.query();
        //ListView设置适配器
        businessListView.setAdapter(myBusinessListAdapter);

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                Toast.makeText(getContext(), "下拉刷新", Toast.LENGTH_SHORT).show();
                Log.i("FragmentOne", "onPullDownToRefresh: 下拉刷新");
//                dataUtils.query();
                pullToRefreshListView.onRefreshComplete();
                Log.i("FragmentOne", "onPullDownToRefresh: 下拉刷新完成");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Toast.makeText(getContext(), "上拉加载", Toast.LENGTH_SHORT).show();
                Log.i("FragmentOne", "onPullDownToRefresh: 上拉加载");
//                dataUtils.query();
                pullToRefreshListView.onRefreshComplete();
                Log.i("FragmentOne", "onPullDownToRefresh: 上拉刷新完成");
            }
        });
        //
        dataUtils.setDataCallBack(new DataUtils.DataCallBack() {
            @Override
            public void querySucceed(List<Business> object) {
                businessArrayList.addAll(object);
//                setListViewHeightBasedOnChildren(businessListView);
                myBusinessListAdapter.notifyDataSetChanged();
            }

            @Override
            public void queryFailed() {

            }
        });

        //计算ListView高度
//        setListViewHeightBasedOnChildren(businessListView);

        //ListView监听滑动事件，实现悬浮效果
        businessListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.i("TAG", "ScrollState正在滚动" + firstVisibleItem);
                if (firstVisibleItem >= 2) {
                    if (listViewSuspended.getVisibility() == View.GONE) {
                        Log.i("TAG", "firstVisibleItem >= 1" + firstVisibleItem);
                        listViewSuspended.setVisibility(View.VISIBLE);
                    }
                } else {

                    listViewSuspended.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        Log.i("TAG", "控件长度" + totalHeight);
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                Log.i("TAG", "onClick: 点击了按钮1");
                break;
            case R.id.button2:
                Log.i("TAG", "onClick: 点击了按钮2");
                break;
            case R.id.button3:
                Log.i("TAG", "onClick: 点击了按钮3");
                break;
            case R.id.button4:
                Log.i("TAG", "onClick: 点击了按钮4");
                break;
            case R.id.button5:
                Log.i("TAG", "onClick: 点击了按钮5");
                break;
            case R.id.button6:
                Log.i("TAG", "onClick: 点击了按钮6");
                break;
        }
    }
}
