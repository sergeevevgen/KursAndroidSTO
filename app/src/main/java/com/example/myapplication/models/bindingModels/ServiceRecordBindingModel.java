package com.example.myapplication.models.bindingModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceRecordBindingModel {
    @SerializedName("id")
    @Expose
    public Integer Id;

    @SerializedName("carId")
    @Expose
    public Integer CarId;

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

    public String getDateBegin() {
        return DateBegin;
    }

    public void setDateBegin(String dateBegin) {
        DateBegin = dateBegin;
    }

    public String getDateEnd() {
        return DateEnd;
    }

    public void setDateEnd(String dateEnd) {
        DateEnd = dateEnd;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @SerializedName("dateBegin")
    @Expose
    public String DateBegin;

    @SerializedName("dateEnd")
    @Expose
    public String DateEnd;

    @SerializedName("description")
    @Expose
    public String Description;
}
