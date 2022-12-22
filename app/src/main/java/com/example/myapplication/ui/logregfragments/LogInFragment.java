package com.example.myapplication.ui.logregfragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.App;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.viewModels.EmployeeViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInFragment extends Fragment {

    private EditText et_email;
    private EditText et_password;
    private Button btn_login;
    private App app;
    private ImageView swipe_to_register;
    private Boolean flag;

    public LogInFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_log_in, container, false);

        et_email = view.findViewById(R.id.et_email);
        et_password = view.findViewById(R.id.et_password);
        btn_login = view.findViewById(R.id.btn_login);
        swipe_to_register = view.findViewById(R.id.swipe_to_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        swipe_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterFragment registerFragment = new RegisterFragment();
                replaceFragment(registerFragment);
            }
        });
        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frame_layout_entry, fragment);
        fragmentTransaction.commit();
    }

    private void signIn() {
        if (!TextUtils.isEmpty(et_email.getText()) && !TextUtils.isEmpty(et_password.getText())) {
            String mail = String.valueOf(et_email.getText());
            String password = String.valueOf(et_password.getText());
            Call<EmployeeViewModel> call = app.getStoService().getApi().logIn(mail, password);
            call.enqueue(new Callback<EmployeeViewModel>() {
                @Override
                public void onResponse(@NonNull Call<EmployeeViewModel> call, @NonNull Response<EmployeeViewModel> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    App.setEmployee(response.body());
                    if (App.getEmployee() != null) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getContext(), "Неверный пароль или логин!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(@NonNull Call<EmployeeViewModel> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("BBB", t.getMessage());
                }
            });
        }
        else {
            Toast.makeText(getContext(), "Введите данные!", Toast.LENGTH_SHORT).show();
        }
    }
}