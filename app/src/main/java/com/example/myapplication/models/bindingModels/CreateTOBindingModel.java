package com.example.myapplication.models.bindingModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateTOBindingModel {
    @SerializedName("carId")
    @Expose
    public Integer CarId;

    @SerializedName("employeeId")
    @Expose
    public Integer EmployeeId;

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

    @SerializedName("sum")
    @Expose
    public Float Sum;
}
