package com.example.myapplication.models.webViewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SparePartWebViewModel {

    public SparePartWebViewModel(Float count, String name, String factoryNumber, Float price, String type, String UMeasurement) {
        Count = count;
        Name = name;
        FactoryNumber = factoryNumber;
        Price = price;
        Type = type;
        this.UMeasurement = UMeasurement;
    }

    @SerializedName("count")
    @Expose
    private Float Count;

    @SerializedName("name")
    @Expose
    private String Name;

    @SerializedName("factoryNumber")
    @Expose
    private String FactoryNumber;

    @SerializedName("price")
    @Expose
    private Float Price;

    @SerializedName("type")
    @Expose
    private String Type;

    @SerializedName("uMeasurement")
    @Expose
    private String UMeasurement;

    public Float getCount() {
        return Count;
    }

    public void setCount(Float count) {
        Count = count;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFactoryNumber() {
        return FactoryNumber;
    }

    public void setFactoryNumber(String factoryNumber) {
        FactoryNumber = factoryNumber;
    }

    public Float getPrice() {
        return Price;
    }

    public void setPrice(Float price) {
        Price = price;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getUMeasurement() {
        return UMeasurement;
    }

    public void setUMeasurement(String UMeasurement) {
        this.UMeasurement = UMeasurement;
    }
}
