package com.example.yuyiz.viewpagerandfragment.object;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by yuyiz on 2017/2/22.
 */

public class Business extends BmobObject {
    private int _id;
    private String BusinessName;
    private BmobFile Business;
    private int BusinessType;
    private float Ratings;
    private int monthlySales;
    private int arrivalTime;
    private int priceOfSending;
    private int shippingCharge;
    private int shippingType;
    private int businessLocation;
    private String businessAddress;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getBusinessLocation() {
        return businessLocation;
    }

    public void setBusinessLocation(int businessLocation) {
        this.businessLocation = businessLocation;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public int getShippingType() {
        return shippingType;
    }

    public void setShippingType(int shippingType) {
        this.shippingType = shippingType;
    }

    public int getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(int shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public int getPriceOfSending() {
        return priceOfSending;
    }

    public void setPriceOfSending(int priceOfSending) {
        this.priceOfSending = priceOfSending;
    }

    public int getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(int monthlySales) {
        this.monthlySales = monthlySales;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public float getRatings() {
        return Ratings;
    }

    public void setRatings(float ratings) {
        Ratings = ratings;
    }


    public String getBusinessName(List<Business> object) {
        return BusinessName;
    }

    public void setBusinessName(String businessName) {
        BusinessName = businessName;
    }

    public int getBusinessType() {
        return BusinessType;
    }

    public void setBusinessType(int businessType) {
        BusinessType = businessType;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public BmobFile getBusiness() {
        return Business;
    }

    public void setBusiness(BmobFile business) {
        Business = business;
    }

}
