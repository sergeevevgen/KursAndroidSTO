package com.example.myapplication.service;

import com.example.myapplication.models.bindingModels.CarBindingModel;
import com.example.myapplication.models.bindingModels.ChangeTOStatusBindingModel;
import com.example.myapplication.models.bindingModels.CreateTOBindingModel;
import com.example.myapplication.models.bindingModels.ServiceRecordBindingModel;
import com.example.myapplication.models.viewModels.CarViewModel;
import com.example.myapplication.models.viewModels.ServiceRecordViewModel;
import com.example.myapplication.models.viewModels.TOViewModel;

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


    //Авто
    @GET("Car/CarList")
    Call<List<CarViewModel>> getCars();

    @GET("Car/GetCar")
    Call<CarViewModel> getCar(@Query("carId") int carId);

    @GET("Car/GetServiceRecords")
    Call<List<ServiceRecordViewModel>> getServiceRecords(@Query("carId") int carId);

    @POST("Car/CreateOrUpdateCar")
    Call<Void> createOrUpdateCar(@Body CarBindingModel car);

    @POST("Car/DeleteCar")
    Call<Void> deleteCar(@Body CarBindingModel car);

    @GET("Car/GetServiceRecord")
    Call<ServiceRecordViewModel> getServiceRecord(@Query("servicerecordId") int recordId);

    @POST("Car/UpdateServiceRecord")
    Call<Void> updateRecord(@Body ServiceRecordBindingModel record);

    //TO
    @GET("TO/GetTOList")
    Call<List<TOViewModel>> getTOs(@Query("employeeId") Integer employeeId);

    @GET("TO/GetTO")
    Call<TOViewModel> getTO(@Query("tOId") Integer toId);

    @POST("TO/CreateTO")
    Call<Void> createTO(@Body CreateTOBindingModel to);

    @POST("TO/TakeTOInWork")
    Call<Void> takeTOInWork(@Body ChangeTOStatusBindingModel to);

    @POST("TO/FinishTO")
    Call<Void> finishTO(@Body ChangeTOStatusBindingModel to);

    @POST("TO/IssueTO")
    Call<Void> issueTO(@Body ChangeTOStatusBindingModel to);
}