package com.example.myapplication.models.viewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceRecordViewModel {
    @SerializedName("id")
    @Expose
    private Integer Id;

    @SerializedName("carId")
    @Expose
    private Integer CarId;

    public ServiceRecordViewModel(Integer carId, String carBrandAndName,
                                  String dateBegin, String dateEnd,
                                  String description) {
        CarId = carId;
        CarBrandAndName = carBrandAndName;
        DateBegin = dateBegin.split("T")[0] + " " + dateBegin.split("T")[1].substring(0, 5);
        DateEnd = dateEnd.split("T")[0] + " " + dateEnd.split("T")[1].substring(0, 5);
        Description = description;
    }

    @SerializedName("carBrandAndName")
    @Expose
    private String CarBrandAndName;

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

    public String getCarBrandAndName() {
        return CarBrandAndName;
    }

    public void setCarBrandAndName(String carBrandAndName) {
        CarBrandAndName = carBrandAndName;
    }

    public String getDateBegin() {
        return DateBegin;
    }

    public void setDateBegin(String dateBegin) {
        DateBegin = dateBegin.split("T")[0] + " " + dateBegin.split("T")[1].substring(0, 5);
    }

    public String getDateEnd() {
        return DateEnd;
    }

    public void setDateEnd(String dateEnd) {
        DateEnd = dateEnd.split("T")[0] + " " + dateEnd.split("T")[1].substring(0, 5);
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @SerializedName("dateBegin")
    @Expose
    private String DateBegin;

    @SerializedName("dateEnd")
    @Expose
    private String DateEnd;

    @SerializedName("description")
    @Expose
    private String Description;
}
