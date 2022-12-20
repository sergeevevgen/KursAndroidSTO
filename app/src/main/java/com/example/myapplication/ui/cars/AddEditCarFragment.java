package com.example.myapplication.ui.cars;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.adapters.CarListAdapter;
import com.example.myapplication.adapters.RecordListAdapter;
import com.example.myapplication.models.bindingModels.CarBindingModel;
import com.example.myapplication.models.viewModels.CarViewModel;
import com.example.myapplication.models.viewModels.ServiceRecordViewModel;
import com.example.myapplication.ui.records.EditRecordFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddEditCarFragment extends Fragment {
    private RecordListAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<ServiceRecordViewModel> records;
    private App app;
    private Integer carId;
    private CarViewModel car;
    private EditText editText_brand;
    private EditText editText_model;
    private EditText editText_vin;
    private EditText editText_phone_num;
    private Button button_car_edit;
    public AddEditCarFragment() {
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
        View view =  inflater.inflate(R.layout.car_add_edit_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_records);

        button_car_edit = view.findViewById(R.id.button_car_edit);

        editText_brand = view.findViewById(R.id.editText_brand);
        editText_model = view.findViewById(R.id.editText_model);
        editText_vin = view.findViewById(R.id.editText_vin);
        editText_phone_num = view.findViewById(R.id.editText_phone_number);

        setRecyclerView();

        button_car_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrUpdate();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                getActivity().getSupportFragmentManager()
                        .popBackStack();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void setRecyclerView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            carId = bundle.getInt("carId");
            button_car_edit.setText("Изменить");
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            records = new ArrayList<>();
            adapter = new RecordListAdapter(getContext());
            recyclerView.setAdapter(adapter);
            restore();
            //при долгом нажатии на элемент открывается фрагмент его редактирования
            adapter.setOnItemLongClickListener(new RecordListAdapter.OnItemLongClickListener() {
                @Override
                public void onItemLongClicked(ServiceRecordViewModel record, int position) {
                    Bundle bundle = new Bundle();
                    Integer id = record.getId();
                    bundle.putInt("recordId", id);
                    EditRecordFragment editRecordFragment = new EditRecordFragment();
                    editRecordFragment.setArguments(bundle);
                    replaceFragment(editRecordFragment);
                }
            });
        }
    }

    private void restore() {
        if (carId != null){
            Call<CarViewModel> call = app.getStoService().getApi().getCar(carId);
            call.enqueue(new Callback<CarViewModel>() {
                @Override
                public void onResponse(@NonNull Call<CarViewModel> call, @NonNull Response<CarViewModel> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    car = response.body();
                    editText_brand.setText(car.getBrand());
                    editText_model.setText(car.getModel());
                    editText_vin.setText(car.getVIN());
                    editText_phone_num.setText(car.getOwnerPhoneNumber());
                }

                @Override
                public void onFailure(@NonNull Call<CarViewModel> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("BBB", t.getMessage());
                }
            });

            Call<List<ServiceRecordViewModel>> call2 = app.getStoService().getApi().getServiceRecords(carId);
            call2.enqueue(new Callback<List<ServiceRecordViewModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<ServiceRecordViewModel>> call, @NonNull Response<List<ServiceRecordViewModel>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    records = (ArrayList<ServiceRecordViewModel>) response.body();
                    adapter.setData(records);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(@NonNull Call<List<ServiceRecordViewModel>> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("BBB", t.getMessage());
                }
            });
        }
    }

    private void createOrUpdate() {
        if (!TextUtils.isEmpty(editText_brand.getText()) &&
                !TextUtils.isEmpty(editText_model.getText()) &&
                !TextUtils.isEmpty(editText_vin.getText()) &&
                !TextUtils.isEmpty(editText_phone_num.getText())) {


            String brand = String.valueOf(editText_brand.getText());
            String model = String.valueOf(editText_model.getText());
            String vin = String.valueOf(editText_vin.getText());
            String phone = String.valueOf(editText_phone_num.getText());

            CarBindingModel bind_car = new CarBindingModel();

            bind_car.setBrand(brand);
            bind_car.setModel(model);
            bind_car.setVIN(vin);
            bind_car.setOwnerPhoneNumber(phone);

            if (car != null) {

                bind_car.setId(car.getId());
                bind_car.setRecords(car.getRecords());

                createOrUpdateCar(bind_car);
            } else {
                createOrUpdateCar(bind_car);
            }
        }

    }

    private void createOrUpdateCar(CarBindingModel bind_car) {
        Call<Void> call = app.getStoService().getApi().createOrUpdateCar(bind_car);
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

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity()
                .getSupportFragmentManager()
                .beginTransaction();

        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }
}