package com.example.myapplication.models.bindingModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeTOStatusBindingModel {
    @SerializedName("toId")
    @Expose
    public Integer TOId;

    public Integer getTOid() {
        return TOId;
    }

    public void setTOid(Integer TOId) {
        this.TOId = TOId;
    }
}
