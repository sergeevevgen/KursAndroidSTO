package com.example.myapplication.models.bindingModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeBindingModel {
    @SerializedName("id")
    @Expose
    public Integer Id;

    @SerializedName("fio")
    @Expose
    public String FIO;

    @SerializedName("login")
    @Expose
    public String Login;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @SerializedName("password")
    @Expose
    public String Password;
}
