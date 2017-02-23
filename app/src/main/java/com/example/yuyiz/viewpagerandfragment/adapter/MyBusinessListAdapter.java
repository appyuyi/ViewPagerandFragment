package com.example.yuyiz.viewpagerandfragment.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yuyiz.viewpagerandfragment.R;
import com.example.yuyiz.viewpagerandfragment.object.Business;

import java.util.ArrayList;

/**
 * Created by yuyiz on 2017/2/22.
 */

public class MyBusinessListAdapter extends BaseAdapter {
    private ArrayList businessList;
    private LayoutInflater myInflater;
    private ViewHolder viewHolder;
    private Context context;
    private String TAG = "MyBusinessListAdapter";

    public MyBusinessListAdapter(ArrayList<Business> businessList, Context context) {
        this.businessList = businessList;
        this.context = context;
        myInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return businessList.size();
    }

    @Override
    public Object getItem(int position) {
        return businessList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = myInflater.inflate(R.layout.item_f1_shou_business, null);

            viewHolder.businessImage = (ImageView) convertView.findViewById(R.id.f1_business_image);
            viewHolder.businessName = (TextView) convertView.findViewById(R.id.f1_business_name);
            viewHolder.businessDistance = (TextView) convertView.findViewById(R.id.f1_business_distance);
            viewHolder.businessMarkImage = (ImageView) convertView.findViewById(R.id.f1_business_image_mark);
            viewHolder.businessSales = (TextView) convertView.findViewById(R.id.f1_business_sales);
            viewHolder.businessArrivalTime = (TextView) convertView.findViewById(R.id.f1_business_arrival_time);
            viewHolder.businessSendFreeLine = (TextView) convertView.findViewById(R.id.f1_business_send_free_line);
            viewHolder.businessSendCosts = (TextView) convertView.findViewById(R.id.f1_business_send_fees);
            viewHolder.eventsList = (ListView) convertView.findViewById(R.id.f1_business_listView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.businessName.setText(((Business) businessList.get(position)).getBusinessName());
        viewHolder.businessMarkImage.setBackgroundColor(Color.BLUE);
        viewHolder.businessSales.setText("月销售" + ((Business) businessList.get(position)).getMonthlySales() + "件");
        viewHolder.businessArrivalTime.setText(((Business) businessList.get(position)).getArrivalTime() + "分钟送达");
        viewHolder.businessSendFreeLine.setText("起送￥" + ((Business) businessList.get(position)).getPriceOfSending());
        viewHolder.businessSendCosts.setText("配送" + ((Business) businessList.get(position)).getShippingCharge());


        return convertView;

    }

    public class ViewHolder {
        ImageView businessImage;
        TextView businessName;
        TextView businessDistance;
        ImageView businessMarkImage;
        TextView businessSales;
        TextView businessArrivalTime;
        TextView businessSendFreeLine;
        TextView businessSendCosts;
        ListView eventsList;
    }

}
