package com.example.myapplication.ui.works;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.models.bindingModels.CreateWorkBindingModel;
import com.example.myapplication.models.viewModels.StoreKeeperViewModel;
import com.example.myapplication.models.viewModels.TOViewModel;
import com.example.myapplication.models.viewModels.WorkTypeViewModel;
import com.example.myapplication.spinners.StoreKeeperSpinnerAdapter;
import com.example.myapplication.spinners.TOSpinnerAdapter;
import com.example.myapplication.spinners.WorkTypeSpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateWorkFragment extends Fragment {
    private ArrayList<TOViewModel> tos;
    private ArrayList<WorkTypeViewModel> workTypes;
    private ArrayList<StoreKeeperViewModel> storeKeepers;
    private TOSpinnerAdapter adapterTO;
    private WorkTypeSpinnerAdapter adapterWorkTypes;
    private StoreKeeperSpinnerAdapter adapterStoreKeeper;
    private Spinner spinnerTO;
    private Spinner spinnerWorkType;
    private Spinner spinnerStoreKeeper;
    private App app;
    private EditText create_work_countET;
    private TextView create_work_priceET;
    private TextView create_work_netpriceET;
    private Button create_work_button_work_create;
    private TOViewModel to;
    private WorkTypeViewModel workType;
    private StoreKeeperViewModel storeKeeper;
    private Button create_work_button_calc;

    public CreateWorkFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_create_work, container, false);
        spinnerTO = view.findViewById(R.id.add_to_spinner_cars);
        spinnerWorkType = view.findViewById(R.id.create_work_spinner_work_types);
        spinnerStoreKeeper = view.findViewById(R.id.create_work_spinner_storekeeper);
        create_work_countET = view.findViewById(R.id.watch_work_storekeeperfioTV);
        create_work_priceET = view.findViewById(R.id.create_work_priceET);
        create_work_netpriceET = view.findViewById(R.id.create_work_netpriceET);
        create_work_button_work_create = view.findViewById(R.id.create_work_button_work_create);
        create_work_button_calc = view.findViewById(R.id.create_work_button_calc);
        initUI();

        create_work_button_work_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (createWork()) {
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    getActivity().getSupportFragmentManager()
                            .popBackStack();
                }
            }
        });

        create_work_button_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcNetPrice();
            }
        });

        spinnerTO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                to = (TOViewModel) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerWorkType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                workType = (WorkTypeViewModel) adapterView.getItemAtPosition(i);
                calcNetPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerStoreKeeper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                storeKeeper = (StoreKeeperViewModel) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    private void initUI() {
        adapterTO = new TOSpinnerAdapter(getContext());
        adapterWorkTypes = new WorkTypeSpinnerAdapter(getContext());
        adapterStoreKeeper = new StoreKeeperSpinnerAdapter(getContext());
        spinnerTO.setAdapter(adapterTO);
        spinnerWorkType.setAdapter(adapterWorkTypes);
        spinnerStoreKeeper.setAdapter(adapterStoreKeeper);

        restore();
    }

    private void restore() {
        //Подгружаем ТОшки
        //Todo: регистрация и авторизация
        //Call<List<TOViewModel>> call = app.getStoService().getApi().getTOs(App.getEmployee().getId());
        Call<List<TOViewModel>> call = app.getStoService().getApi().getNotStartedTOs(1);
        call.enqueue(new Callback<List<TOViewModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TOViewModel>> call, @NonNull Response<List<TOViewModel>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                tos = (ArrayList<TOViewModel>) response.body();
                if (tos != null) {
                    adapterTO.setData(tos);
                    adapterTO.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(), "Создайте ТО", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<TOViewModel>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("BBB", t.getMessage());
            }
        });

        //Подгружаем типы услуг
        Call<List<WorkTypeViewModel>> call2 = app.getStoService().getApi().getWorkTypes();
        call2.enqueue(new Callback<List<WorkTypeViewModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<WorkTypeViewModel>> call, @NonNull Response<List<WorkTypeViewModel>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                workTypes = (ArrayList<WorkTypeViewModel>) response.body();
                if (workTypes != null) {
                    adapterWorkTypes.setData(workTypes);
                    adapterWorkTypes.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(), "Создайте типы услуг", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<WorkTypeViewModel>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("BBB", t.getMessage());
            }
        });

        //Подгружаем типы кладовщиков
        Call<List<StoreKeeperViewModel>> call3 = app.getStoService().getApi().getStoreKeepers();
        call3.enqueue(new Callback<List<StoreKeeperViewModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<StoreKeeperViewModel>> call, @NonNull Response<List<StoreKeeperViewModel>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                storeKeepers = (ArrayList<StoreKeeperViewModel>) response.body();
                if (storeKeepers != null) {
                    adapterStoreKeeper.setData(storeKeepers);
                    adapterStoreKeeper.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(), "Возьмите на работу кладовщиков", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<StoreKeeperViewModel>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("BBB", t.getMessage());
            }
        });
    }

    private boolean createWork() {
        if (!TextUtils.isEmpty(create_work_countET.getText()) &&
                !TextUtils.isEmpty(create_work_priceET.getText()) &&
                !TextUtils.isEmpty(create_work_netpriceET.getText()) &&
                to != null && workType != null && storeKeeper != null) {

            Integer count = Integer.valueOf(String.valueOf(create_work_countET.getText()));
            Float price = Float.valueOf(String.valueOf(create_work_priceET.getText()));
            Float netprice = Float.valueOf(String.valueOf(create_work_netpriceET.getText()));

            CreateWorkBindingModel bind_work = new CreateWorkBindingModel();

            bind_work.setCount(count);
            bind_work.setPrice(price);
            bind_work.setNetPrice(netprice);
            bind_work.setTOId(to.getId());
            bind_work.setWorkTypeId(workType.getId());
            bind_work.setStoreKeeperId(storeKeeper.getId());

            createWork(bind_work);
            return true;
        }
        else {
            Toast.makeText(getContext(), "Введите данные", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void createWork(CreateWorkBindingModel model) {
        Call<Void> call = app.getStoService().getApi().createWork(model);
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

    private void calcNetPrice() {
        int k = tryParseInt(String.valueOf(create_work_countET.getText()));
        if (k != 0 && workType != null) {

            Float price = workType.getPrice() * k;
            Float netprice = workType.getNetPrice() * k;
            create_work_priceET.setText(String.valueOf(price));
            create_work_netpriceET.setText(String.valueOf(netprice));
        }
    }

    private int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }
}