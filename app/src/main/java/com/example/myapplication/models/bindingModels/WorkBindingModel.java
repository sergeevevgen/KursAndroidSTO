package com.example.myapplication.models.bindingModels;

import com.example.myapplication.enums.WorkStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkBindingModel {
    @SerializedName("id")
    @Expose
    public Integer Id;

    @SerializedName("storeKeeperId")
    @Expose
    public Integer StoreKeeperId;

    @SerializedName("workTypeId")
    @Expose
    public Integer WorkTypeId;

    @SerializedName("toId")
    @Expose
    public Integer TOId;

    @SerializedName("workName")
    @Expose
    public String WorkName;

    @SerializedName("price")
    @Expose
    public Float Price;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

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

    public String getWorkName() {
        return WorkName;
    }

    public void setWorkName(String workName) {
        WorkName = workName;
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

    public WorkStatus getWorkStatus() {
        return WorkStatus;
    }

    public void setWorkStatus(WorkStatus workStatus) {
        WorkStatus = workStatus;
    }

    public String getWorkBegin() {
        return WorkBegin;
    }

    public void setWorkBegin(String workBegin) {
        WorkBegin = workBegin;
    }

    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }

    @SerializedName("netPrice")
    @Expose
    public Float NetPrice;

    @SerializedName("workStatus")
    @Expose
    public WorkStatus WorkStatus;

    @SerializedName("workBegin")
    @Expose
    public String WorkBegin;

    @SerializedName("count")
    @Expose
    public Integer Count;
}
