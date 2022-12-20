package com.example.myapplication.models.bindingModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class CarBindingModel {
    @SerializedName("id")
    @Expose
    public Integer Id;

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

    /// <summary>
    /// Бренд авто (VW, Ford)
    /// </summary>
    @SerializedName("brand")
    @Expose
    public String Brand;

    /// <summary>
    /// Модель авто (Polo, Fusion)
    /// </summary>
    @SerializedName("model")
    @Expose
    public String Model;

    /// <summary>
    /// VIN-номер авто (длина 17 символов)
    /// </summary>
    @SerializedName("vin")
    @Expose
    public String VIN;

    /// <summary>
    /// Номер телефона владельца
    /// </summary>
    @SerializedName("ownerPhoneNumber")
    @Expose
    public String OwnerPhoneNumber;

    /// <summary>
    /// Записи сервисов
    /// </summary>
    /// <summary>
    /// Записи сервисов
    /// </summary>
    @SerializedName("records")
    @Expose
    public Map<Integer, String> Records;
}
