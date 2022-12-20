package com.example.myapplication.models.bindingModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateWorkBindingModel {
    @SerializedName("storeKeeperId")
    @Expose
    public Integer StoreKeeperId;

    @SerializedName("workTypeId")
    @Expose
    public Integer WorkTypeId;

    @SerializedName("toId")
    @Expose
    public Integer TOId;

    @SerializedName("count")
    @Expose
    public Integer Count;

    public Integer getStoreKeeperId() {
        return StoreKeeperId;
    }

    public void setStoreKeeperId(Integer storeKeeperId) {
        StoreKeeperId = storeKeeperId;
    }

    public Integer getWorkTypeId() {
        return WorkTypeId;
    }

    public void setWorkTypeId(Integer workTypeId) {
        WorkTypeId = workTypeId;
    }

    public Integer getTOId() {
        return TOId;
    }

    public void setTOId(Integer TOId) {
        this.TOId = TOId;
    }

    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }

    public Float getPrice() {
        return Price;
    }

    public void setPrice(Float price) {
        Price = price;
    }

    public Float getNetPrice() {
        return NetPrice;
    }

    public void setNetPrice(Float netPrice) {
        NetPrice = netPrice;
    }

    @SerializedName("price")
    @Expose
    public Float Price;

    @SerializedName("netPrice")
    @Expose
    public Float NetPrice;
}
