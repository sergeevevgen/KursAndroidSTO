package com.example.myapplication.service;

import com.example.myapplication.models.bindingModels.CarBindingModel;
import com.example.myapplication.models.bindingModels.ChangeTOStatusBindingModel;
import com.example.myapplication.models.bindingModels.ChangeWorkStatusBindingModel;
import com.example.myapplication.models.bindingModels.CreateTOBindingModel;
import com.example.myapplication.models.bindingModels.CreateWorkBindingModel;
import com.example.myapplication.models.bindingModels.EmployeeBindingModel;
import com.example.myapplication.models.bindingModels.ServiceRecordBindingModel;
import com.example.myapplication.models.bindingModels.WorkBindingModel;
import com.example.myapplication.models.viewModels.CarViewModel;
import com.example.myapplication.models.viewModels.EmployeeViewModel;
import com.example.myapplication.models.viewModels.ServiceRecordViewModel;
import com.example.myapplication.models.viewModels.StoreKeeperViewModel;
import com.example.myapplication.models.viewModels.TOViewModel;
import com.example.myapplication.models.viewModels.WorkTypeViewModel;
import com.example.myapplication.models.viewModels.WorkViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface STOApi {

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

    @GET("TO/GetNotStartedTOs")
    Call<List<TOViewModel>> getNotStartedTOs(@Query("employeeId") Integer employeeId);

    @GET("TO/GetTO")
    Call<TOViewModel> getTO(@Query("tOId") Integer toId);

    @POST("TO/CreateTO")
    Call<Void> createTO(@Body CreateTOBindingModel to);

    @POST("TO/TakeTOInWork")
    Call<Boolean> takeTOInWork(@Body ChangeTOStatusBindingModel to);

    @POST("TO/FinishTO")
    Call<Boolean> finishTO(@Body ChangeTOStatusBindingModel to);

    @POST("TO/IssueTO")
    Call<Boolean> issueTO(@Body ChangeTOStatusBindingModel to);

    //WorkType
    @GET("Work/GetWorkTypeList")
    Call<List<WorkTypeViewModel>> getWorkTypes();

    @GET("Work/GetWorkType")
    Call<WorkTypeViewModel> getWorkType(@Query("workTypeId") int workTypeId);

    //Work
    @GET("Work/GetWorkListByEmployee")
    Call<List<WorkViewModel>> getWorksByEmployee(@Query("employeeId") int employeeId);

    @POST("Work/CreateWork")
    Call<Void> createWork(@Body CreateWorkBindingModel work);

    @GET("Work/GetWorkListByTO")
    Call<List<WorkViewModel>> getWorksByTO(@Query("toId") Integer toId);

    //StoreKeeper
    @GET("StoreKeeper/GetStoreKeeperList")
    Call<List<StoreKeeperViewModel>> getStoreKeepers();

    //Employee
    @GET("Employee/Login")
    Call<EmployeeViewModel> logIn(@Query("login") String login, @Query("password") String password);

    @POST("Employee/Register")
    Call<Void> singUp(@Body EmployeeBindingModel model);

    @POST("Employee/UpdateData")
    Call<Void> updateProfile(@Body EmployeeBindingModel model);

    @POST("Employee/Delete")
    Call<Void> deleteProfile(@Body EmployeeBindingModel model);
}