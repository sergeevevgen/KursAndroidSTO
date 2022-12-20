package com.example.myapplication.models.viewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class CarViewModel {
    @SerializedName("id")
    @Expose
    private Integer Id;

    @SerializedName("brand")
    @Expose
    private String Brand;

    public CarViewModel(String brand, String model, String VIN, String ownerPhoneNumber, Map<Integer, String> records) {
        Brand = brand;
        Model = model;
        this.VIN = VIN;
        OwnerPhoneNumber = ownerPhoneNumber;
        Records = records;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public String getOwnerPhoneNumber() {
        return OwnerPhoneNumber;
    }

    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        OwnerPhoneNumber = ownerPhoneNumber;
    }

    public Map<Integer, String> getRecords() {
        return Records;
    }

    public void setRecords(Map<Integer, String> records) {
        Records = records;
    }

    @SerializedName("model")
    @Expose
    private String Model;

    @SerializedName("vin")
    @Expose
    private String VIN;

    @SerializedName("ownerPhoneNumber")
    @Expose
    private String OwnerPhoneNumber;

    /// <summary>
    /// Записи сервисов (номер, ((Дата начала, Дата конца), описание)
    /// </summary>
    @SerializedName("records")
    @Expose
    private Map<Integer, String> Records;
}
