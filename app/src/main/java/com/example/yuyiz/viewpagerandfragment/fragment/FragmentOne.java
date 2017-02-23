package com.example.yuyiz.viewpagerandfragment.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.yuyiz.viewpagerandfragment.R;
import com.example.yuyiz.viewpagerandfragment.adapter.MyBusinessListAdapter;
import com.example.yuyiz.viewpagerandfragment.object.Business;
import com.example.yuyiz.viewpagerandfragment.object.MyListView;
import com.example.yuyiz.viewpagerandfragment.utils.DataUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.ArrayList;
import java.util.List;

public class FragmentOne extends Fragment {

    PullToRefreshScrollView pullToRefreshScrollView;
    private View view;
    private MyListView businessListView;
    private ArrayList<Business> businessArrayList;
    private DataUtils dataUtils;
    private MyBusinessListAdapter myBusinessListAdapter;
    private View listViewSuspended;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        businessArrayList = new ArrayList<Business>();
        pullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pull_to_refresh_scrollview);
        businessListView = (MyListView) view.findViewById(R.id.f1_listview);
        View hander = View.inflate(getContext(), R.layout.hander, null);
        listViewSuspended = view.findViewById(R.id.f1_listview_suspended_);

//        businessListView.addHeaderView(hander);//添加头部
        businessListView.addHeaderView(View.inflate(getContext(), R.layout.listview_suspended, null));
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
                myBusinessListAdapter.notifyDataSetChanged();
            }

            @Override
            public void queryFailed() {

            }
        });
        dataUtils.query();
        myBusinessListAdapter = new MyBusinessListAdapter(businessArrayList, getContext());
        businessListView.setAdapter(myBusinessListAdapter);
        businessListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.i("TAG", "ScrollState正在滚动" + firstVisibleItem);
//
//                if (firstVisibleItem >= 1) {
//                    Log.i("TAG", "firstVisibleItem >= 1" + firstVisibleItem);
//                    listViewSuspended.setVisibility(View.VISIBLE);
//                } else {
//                    listViewSuspended.setVisibility(View.GONE);
//                }
            }
        });
    }

}
