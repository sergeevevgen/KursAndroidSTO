package com.example.myapplication.models.bindingModels;

import com.example.myapplication.enums.TOStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TOBindingModel {
    @SerializedName("id")
    @Expose
    public Integer Id;

    @SerializedName("carId")
    @Expose
    public Integer CarId;

    @SerializedName("employeeId")
    @Expose
    public Integer EmployeeId;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getCarId() {
        return CarId;
    }

    public void setCarId(Integer carId) {
        CarId = carId;
    }

    public Integer getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        EmployeeId = employeeId;
    }

    public Float getSum() {
        return Sum;
    }

    public void setSum(Float sum) {
        Sum = sum;
    }

    public TOStatus getStatus() {
        return Status;
    }

    public void setStatus(TOStatus status) {
        Status = status;
    }

    public String getDateCreate() {
        return DateCreate;
    }

    public void setDateCreate(String dateCreate) {
        DateCreate = dateCreate;
    }

    public String getDateImplement() {
        return DateImplement;
    }

    public void setDateImplement(String dateImplement) {
        DateImplement = dateImplement;
    }

    public String getDateOver() {
        return DateOver;
    }

    public void setDateOver(String dateOver) {
        DateOver = dateOver;
    }

    @SerializedName("sum")
    @Expose
    public Float Sum;

    @SerializedName("status")
    @Expose
    public TOStatus Status;

    @SerializedName("dateCreate")
    @Expose
    public String DateCreate;

    @SerializedName("dateImplement")
    @Expose
    public String DateImplement;

    @SerializedName("dateOver")
    @Expose
    public String DateOver;
}
