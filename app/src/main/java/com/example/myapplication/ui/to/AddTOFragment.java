package com.example.myapplication.ui.to;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.models.bindingModels.CreateTOBindingModel;
import com.example.myapplication.models.viewModels.CarViewModel;
import com.example.myapplication.spinners.CarSpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTOFragment extends Fragment {
    private ArrayList<CarViewModel> cars;
    private CarSpinnerAdapter adapter;
    private Spinner spinner;
    private App app;
    private CarViewModel car;
    private Button create_button;
    public AddTOFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app =(App) getActivity().getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_to, container, false);
        spinner = view.findViewById(R.id.add_to_spinner_cars);
        create_button = view.findViewById(R.id.add_to_button_create);
        adapter = new CarSpinnerAdapter(getContext());
        spinner.setAdapter(adapter);
        restore();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                car = (CarViewModel) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (createWork()) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    getActivity().getSupportFragmentManager()
                            .popBackStack();

                }
            }
        });
        return view;
    }

    private void restore() {
        //Подгружаем автомобили
        Call<List<CarViewModel>> call = app.getStoService().getApi().getCars();
        call.enqueue(new Callback<List<CarViewModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<CarViewModel>> call, @NonNull Response<List<CarViewModel>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                cars = (ArrayList<CarViewModel>) response.body();
                if (cars != null) {
                    adapter.setData(cars);
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(), "Привлеките клиентов", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CarViewModel>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("BBB", t.getMessage());
            }
        });
    }

    private boolean createWork() {
        if (car != null) {
            CreateTOBindingModel bind_to = new CreateTOBindingModel();

            bind_to.setCarId(car.getId());
            //TOdo: reg and auth
            //bind_to.setEmployeeId(App.getEmployee().getId());
            bind_to.setEmployeeId(1);
            createWork(bind_to);
            return true;
        }
        else {
            Toast.makeText(getContext(), "Введите данные", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void createWork(CreateTOBindingModel model) {
        Call<Void> call = app.getStoService().getApi().createTO(model);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("BBB", t.getMessage());
            }
        });
    }
}