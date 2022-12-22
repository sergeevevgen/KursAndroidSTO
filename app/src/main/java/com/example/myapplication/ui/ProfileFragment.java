package com.example.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.App;
import com.example.myapplication.LogInRegisterActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.bindingModels.EmployeeBindingModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private EditText profile_fioTV;
    private EditText profile_loginTV;
    private EditText profile_passwordTV;
    private Button profile_button_update_data;
    private Button profile_button_excel_report;
    private Button profile_button_word_report;
    private Button profile_button_pdf_report;
    private Button profile_button_exit;
    private App app;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (App) getActivity().getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        profile_fioTV = view.findViewById(R.id.profile_fioTV);
        profile_loginTV = view.findViewById(R.id.profile_loginTV);
        profile_passwordTV = view.findViewById(R.id.profile_passwordTV);
        profile_button_update_data = view.findViewById(R.id.profile_button_update_data);
        profile_button_excel_report = view.findViewById(R.id.profile_button_excel_report);
        profile_button_word_report = view.findViewById(R.id.profile_button_word_report);
        profile_button_pdf_report = view.findViewById(R.id.profile_button_pdf_report);
        profile_button_exit = view.findViewById(R.id.profile_button_exit);
        loadData();

        profile_button_update_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });

        profile_button_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.setEmployee(null);
                Intent intent = new Intent(getActivity(), LogInRegisterActivity.class);
                startActivity(intent);
            }
        });

        profile_button_excel_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        profile_button_word_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        profile_button_pdf_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    private void loadData() {
        profile_fioTV.setText(App.getEmployee().getFIO());
        profile_loginTV.setText(App.getEmployee().getLogin());
        profile_passwordTV.setText(App.getEmployee().getPassword());
    }

    private void updateProfile() {
        if (!TextUtils.isEmpty(profile_fioTV.getText())
                && !TextUtils.isEmpty(profile_loginTV.getText()) &&
                !TextUtils.isEmpty(profile_passwordTV.getText())) {
            EmployeeBindingModel model = new EmployeeBindingModel();
            model.setId(App.getEmployee().getId());
            model.setFIO(String.valueOf(profile_fioTV.getText()));
            model.setLogin(String.valueOf(profile_loginTV.getText()));
            model.setPassword(String.valueOf(profile_passwordTV.getText()));
            Call<Void> call = app.getStoService().getApi().updateProfile(model);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    App.getEmployee().setFIO(String.valueOf(profile_fioTV.getText()));
                    App.getEmployee().setLogin(String.valueOf(profile_loginTV.getText()));
                    App.getEmployee().setPassword(String.valueOf(profile_passwordTV.getText()));
                    Toast.makeText(getContext(), "Данные изменены", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("BBB", t.getMessage());
                }
            });
        }
    }
}