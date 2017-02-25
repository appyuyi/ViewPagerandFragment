package com.example.yuyiz.viewpagerandfragment.fragment;


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
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.yuyiz.viewpagerandfragment.R;
import com.example.yuyiz.viewpagerandfragment.adapter.MyBusinessListAdapter;
import com.example.yuyiz.viewpagerandfragment.object.Business;
import com.example.yuyiz.viewpagerandfragment.utils.DataUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.ArrayList;
import java.util.List;

public class FragmentOne extends Fragment implements View.OnClickListener {

    PullToRefreshScrollView pullToRefreshScrollView;
    private View view;
    private ListView businessListView;
    private ArrayList<Business> businessArrayList;
    private DataUtils dataUtils;
    private MyBusinessListAdapter myBusinessListAdapter;
    private View listViewSuspended;
//    private PullToRefreshListView pullToRefreshScrollListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        businessArrayList = new ArrayList<Business>();
        pullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pull_to_refresh_scroll_view);
//        businessListView = (MyListView) view.findViewById(R.id.f1_listview);
        //获取头部
        View hander = View.inflate(getContext(), R.layout.hander, null);
        //悬浮的ListView
        listViewSuspended = view.findViewById(R.id.f1_listview_suspended_);
        //获取ListView
        businessListView = (ListView) view.findViewById(R.id.p1_business_show_list_view);

        //添加头部
//        businessListView.addHeaderView(View.inflate(getContext(), R.layout.hander, null));

        View myView = View.inflate(getContext(), R.layout.listview_suspended, null);
        Button button1 = (Button) myView.findViewById(R.id.button1);
        Button button2 = (Button) myView.findViewById(R.id.button2);
        Button button3 = (Button) myView.findViewById(R.id.button3);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);


        //ListView添加滑动时显示的ListView
        businessListView.addHeaderView(myView);


        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);

        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                Toast.makeText(getContext(), "下拉刷新", Toast.LENGTH_SHORT).show();
                Log.i("FragmentOne", "onPullDownToRefresh: 下拉刷新");
                dataUtils.query();
                pullToRefreshScrollView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                Toast.makeText(getContext(), "上拉加载", Toast.LENGTH_SHORT).show();
                Log.i("FragmentOne", "onPullDownToRefresh: 上拉加载");
                dataUtils.query();
                pullToRefreshScrollView.onRefreshComplete();
            }
        });
        businessArrayList = new ArrayList<Business>();
        dataUtils = new DataUtils(getContext());
        dataUtils.setDataCallBack(new DataUtils.DataCallBack() {
            @Override
            public void querySucceed(List<Business> object) {
                for (Business business : object) {
                    businessArrayList.add(business);
                }
                setListViewHeightBasedOnChildren(businessListView);
                myBusinessListAdapter.notifyDataSetChanged();
            }

            @Override
            public void queryFailed() {

            }
        });
        //初始化时第一次查询和加载数据
        dataUtils.query();
        //初始化适配器
        myBusinessListAdapter = new MyBusinessListAdapter(businessArrayList, getContext());
        //ListView设置适配器
        businessListView.setAdapter(myBusinessListAdapter);

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
//
                if (firstVisibleItem >= 2) {
                    Log.i("TAG", "firstVisibleItem >= 1" + firstVisibleItem);
                    listViewSuspended.setVisibility(View.VISIBLE);
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
        }
    }
}
