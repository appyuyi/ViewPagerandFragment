package com.example.yuyiz.viewpagerandfragment.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.yuyiz.viewpagerandfragment.object.Business;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by yuyiz on 2017/2/22.
 */

public class DataUtils {
    private Context context;
    private DataCallBack dataCallBack;

    public DataUtils(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public DataCallBack getDataCallBack() {
        return dataCallBack;
    }

    public void setDataCallBack(DataCallBack dataCallBack) {
        this.dataCallBack = dataCallBack;
    }

    public void query() {

        BmobQuery<Business> query = new BmobQuery<Business>();
        query.setLimit(20);
        query.findObjects(new FindListener<Business>() {
            @Override
            public void done(List<Business> object, BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "查询成功：共" + object.size() + "条数据。", Toast.LENGTH_SHORT).show();
                    for (Business business : object) {
                        //获得playerName的信息
                        business.getBusinessName(object);

                    }
                    dataCallBack.querySucceed(object);
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    dataCallBack.queryFailed();
                }
            }
        });
    }

    public interface DataCallBack {
        void querySucceed(List<Business> object);

        void queryFailed();
    }
}
