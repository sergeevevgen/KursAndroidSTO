package com.example.myapplication.ui.logregfragments;

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
import com.example.myapplication.R;
import com.example.myapplication.models.bindingModels.EmployeeBindingModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    private EditText et_name;
    private EditText et_email;
    private EditText et_password;
    private EditText et_repassword;
    private Button btn_register;
    private App app;
    private ImageView swipe_to_login;
    private Boolean flag;

    public RegisterFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_register, container, false);
        et_name = view.findViewById(R.id.et_name);
        et_email = view.findViewById(R.id.et_email);
        et_password = view.findViewById(R.id.et_password);
        et_repassword = view.findViewById(R.id.et_repassword);
        btn_register = view.findViewById(R.id.btn_register);
        swipe_to_login = view.findViewById(R.id.swipe_to_login);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        swipe_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogInFragment logInFragment = new LogInFragment();
                replaceFragment(logInFragment);
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

    private void register() {
        if (!TextUtils.isEmpty(et_email.getText()) && !TextUtils.isEmpty(et_password.getText()) &&
        !TextUtils.isEmpty(et_repassword.getText()) && !TextUtils.isEmpty(et_name.getText())) {

            String password = String.valueOf(et_password.getText());
            String repassword = String.valueOf(et_repassword.getText());

            if (!password.equals(repassword)) {
                Toast.makeText(getContext(), "Пароли должны совпадать!", Toast.LENGTH_SHORT).show();
                return;
            }
            String mail = String.valueOf(et_email.getText());
            String fio = String.valueOf(et_name.getText());
            EmployeeBindingModel model = new EmployeeBindingModel();
            model.setFIO(fio);
            model.setLogin(mail);
            model.setPassword(password);
            Call<Void> call = app.getStoService().getApi().singUp(model);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    LogInFragment logInFragment = new LogInFragment();
                    replaceFragment(logInFragment);
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
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