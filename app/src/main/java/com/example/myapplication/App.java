package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.models.viewModels.EmployeeViewModel;
import com.example.myapplication.service.STOService;

public class App extends Application {

    private STOService stoService;
    public static EmployeeViewModel Employee;

    public static EmployeeViewModel getEmployee() {
        return Employee;
    }

    public static void setEmployee(EmployeeViewModel employee) {
        Employee = employee;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        stoService = new STOService();
    }

    public STOService getStoService() {
        return stoService;
    }
}
