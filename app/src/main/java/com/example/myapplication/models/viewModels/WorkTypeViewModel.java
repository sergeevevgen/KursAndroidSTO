package com.example.myapplication.models.viewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class WorkTypeViewModel {
    @SerializedName("id")
    @Expose
    public Integer Id;

    @SerializedName("timeOfWorkId")
    @Expose
    public Integer TimeOfWorkId;

    @SerializedName("executionTime")
    @Expose
    public String ExecutionTime;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getTimeOfWorkId() {
        return TimeOfWorkId;
    }

    public void setTimeOfWorkId(Integer timeOfWorkId) {
        TimeOfWorkId = timeOfWorkId;
    }

    public String getExecutionTime() {
        return ExecutionTime;
    }

    public void setExecutionTime(String executionTime) {
        ExecutionTime = executionTime;
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

    public Map<Integer, Float> getWorkSpareParts() {
        return WorkSpareParts;
    }

    public void setWorkSpareParts(Map<Integer, Float> workSpareParts) {
        WorkSpareParts = workSpareParts;
    }

    public WorkTypeViewModel(Integer timeOfWorkId, String executionTime, String workName,
                             Float price, Float netPrice, Map<Integer,
            Float> workSpareParts) {
        TimeOfWorkId = timeOfWorkId;
        ExecutionTime = executionTime;
        WorkName = workName;
        Price = price;
        NetPrice = netPrice;
        WorkSpareParts = workSpareParts;
    }

    @SerializedName("workName")
    @Expose
    public String WorkName;

    @SerializedName("price")
    @Expose
    public Float Price;

    @SerializedName("netPrice")
    @Expose
    public Float NetPrice;

    /// <summary>
    /// Необходимые детали и расходники (int - id, string - название, decimal, потому что может быть не целое (например, 0.8 л масла))
    /// </summary>
    @SerializedName("workSpareParts")
    @Expose
    public Map<Integer, Float> WorkSpareParts;
}