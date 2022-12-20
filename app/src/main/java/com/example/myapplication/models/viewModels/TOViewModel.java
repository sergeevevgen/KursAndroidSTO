package com.example.myapplication.models.viewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class TOViewModel {
    @SerializedName("id")
    @Expose
    private Integer Id;

    @SerializedName("carId")
    @Expose
    private Integer CarId;

    @SerializedName("carName")
    @Expose
    private String CarName;

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

    public String getCarName() {
        return CarName;
    }

    public void setCarName(String carName) {
        CarName = carName;
    }

    public Integer getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        EmployeeId = employeeId;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getTOAndCarName() {
        return TOAndCarName;
    }

    public void setTOAndCarName(String TOAndCarName) {
        this.TOAndCarName = TOAndCarName;
    }

    public Float getSum() {
        return Sum;
    }

    public void setSum(Float sum) {
        Sum = sum;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
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

    public Map<Integer, String> getWorks() {
        return Works;
    }

    public void setWorks(Map<Integer, String> works) {
        Works = works;
    }

    @SerializedName("employeeId")
    @Expose
    private Integer EmployeeId;

    public TOViewModel(Integer carId, String carName,
                       Integer employeeId, String employeeName,
                       String TOAndCarName, Float sum,
                       String status, String dateCreate,
                       String dateImplement, String dateOver,
                       Map<Integer, String> works) {
        CarId = carId;
        CarName = carName;
        EmployeeId = employeeId;
        EmployeeName = employeeName;
        this.TOAndCarName = TOAndCarName;
        Sum = sum;
        Status = status;
        DateCreate = dateCreate;
        DateImplement = dateImplement;
        DateOver = dateOver;
        Works = works;
    }

    @SerializedName("employeeName")
    @Expose
    private String EmployeeName;

    @SerializedName("toAndCarName")
    @Expose
    private String TOAndCarName;

    @SerializedName("sum")
    @Expose
    private Float Sum;

    @SerializedName("status")
    @Expose
    private String Status;

    @SerializedName("dateCreate")
    @Expose
    private String DateCreate;

    @SerializedName("dateImplement")
    @Expose
    private String DateImplement;

    @SerializedName("dateOver")
    @Expose
    private String DateOver;

    /// <summary>
    /// Работы ТО (номер, (название, (кол-во, стоимость
    /// </summary>
    @SerializedName("works")
    @Expose
    private Map<Integer, String> Works;
}
