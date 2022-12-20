package com.example.myapplication.models.viewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkViewModel {

    @SerializedName("id")
    @Expose
    public Integer Id;

    @SerializedName("storeKeeperId")
    @Expose
    public Integer StoreKeeperId;

    @SerializedName("storeKeeperFIO")
    @Expose
    public String StoreKeeperFIO;

    @SerializedName("workTypeId")
    @Expose
    public Integer WorkTypeId;

    @SerializedName("toId")
    @Expose
    public Integer TOId;

    @SerializedName("workName")
    @Expose
    public String WorkName;

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

    public String getStoreKeeperFIO() {
        return StoreKeeperFIO;
    }

    public void setStoreKeeperFIO(String storeKeeperFIO) {
        StoreKeeperFIO = storeKeeperFIO;
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

    public String getWorkStatus() {
        return WorkStatus;
    }

    public void setWorkStatus(String workStatus) {
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

    @SerializedName("price")
    @Expose
    public Float Price;

    @SerializedName("netPrice")
    @Expose
    public Float NetPrice;

    public WorkViewModel(Integer storeKeeperId, String storeKeeperFIO, Integer workTypeId,
                         Integer TOId, String workName, Float price,
                         Float netPrice, String workStatus,
                         String workBegin, Integer count) {
        StoreKeeperId = storeKeeperId;
        StoreKeeperFIO = storeKeeperFIO;
        WorkTypeId = workTypeId;
        this.TOId = TOId;
        WorkName = workName;
        Price = price;
        NetPrice = netPrice;
        WorkStatus = workStatus;
        WorkBegin = workBegin;
        Count = count;
    }

    @SerializedName("workStatus")
    @Expose
    public String WorkStatus;

    @SerializedName("workBegin")
    @Expose
    public String WorkBegin;

    @SerializedName("count")
    @Expose
    public Integer Count;
}
