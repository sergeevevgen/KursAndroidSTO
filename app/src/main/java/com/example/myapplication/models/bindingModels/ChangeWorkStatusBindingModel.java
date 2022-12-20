package com.example.myapplication.models.bindingModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeWorkStatusBindingModel {
    @SerializedName("workId")
    @Expose
    public Integer WorkId;

    public Integer getWorkId() {
        return WorkId;
    }

    public void setWorkId(Integer workId) {
        WorkId = workId;
    }
}
