package com.example.myapplication.models.viewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreKeeperViewModel {
    @SerializedName("id")
    @Expose
    private Integer Id;

    @SerializedName("fio")
    @Expose
    private String FIO;

    public StoreKeeperViewModel(String FIO, String login,
                                String password) {
        this.FIO = FIO;
        Login = login;
        Password = password;
    }

    @SerializedName("login")
    @Expose
    private String Login;

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
    private String Password;
}
