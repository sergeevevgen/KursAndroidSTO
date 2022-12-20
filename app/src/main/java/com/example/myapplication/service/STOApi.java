package com.example.myapplication.service;

import com.example.myapplication.models.bindingModels.CarBindingModel;
import com.example.myapplication.models.viewModels.CarViewModel;
import com.example.myapplication.models.viewModels.ServiceRecordViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface STOApi {
//    @POST("api/employee/register")
//    void registerEmployee;

//    @GET("employee/login?")


    @GET("Car/CarList")
    Call<List<CarViewModel>> getCars();

    @GET("Car/GetCar")
    Call<CarViewModel> getCar(@Query("carId") int carId);

    @GET("Car/GetServiceRecords")
    Call<List<ServiceRecordViewModel>> getServiceRecords(@Query("carId") int carId);

    @POST("Car/CreateOrUpdateCar")
    Call<Void> createOrUpdateCar(@Body CarBindingModel car);
}