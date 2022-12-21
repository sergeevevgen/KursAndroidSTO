package com.example.myapplication.models.viewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SparePartViewModel {
    @SerializedName("id")
    @Expose
    private Integer Id;

    public SparePartViewModel(String name, String factoryNumber, Float price, String type, String UMeasurement) {
        Name = name;
        FactoryNumber = factoryNumber;
        Price = price;
        Type = type;
        this.UMeasurement = UMeasurement;
    }

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

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
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
