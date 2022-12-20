package com.example.myapplication.ui.records;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.models.bindingModels.ServiceRecordBindingModel;
import com.example.myapplication.models.viewModels.ServiceRecordViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditRecordFragment extends Fragment {
    private ServiceRecordViewModel record;
    private App app;
    private Integer recordId;
    private EditText record_edit_editText_description;
    private TextView record_edit_tv_car_brand_and_model;
    private TextView record_edit_tv_date_begin;
    private TextView record_edit_tv_date_end;
    private Button record_edit_button_edit_record_accept;

    public EditRecordFragment() {
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
        View view =  inflater.inflate(R.layout.record_edit_fragment, container, false);

        //UI
        record_edit_tv_car_brand_and_model = view.findViewById(R.id.record_edit_tv_car_brand_and_model);
        record_edit_tv_date_begin = view.findViewById(R.id.record_edit_tv_date_begin);
        record_edit_tv_date_end = view.findViewById(R.id.record_edit_tv_date_end);
        record_edit_editText_description = view.findViewById(R.id.record_edit_editText_description);
        record_edit_button_edit_record_accept = view.findViewById(R.id.record_edit_button_edit_record_accept);

        loadData();

        record_edit_button_edit_record_accept.setOnClickListener(new View.OnClickListener() {
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
        return view;
    }

    private void loadData() {
        Bundle bundle = getArguments();
        recordId = bundle.getInt("recordId");

        Call<ServiceRecordViewModel> call = app.getStoService().getApi().getServiceRecord(recordId);
        call.enqueue(new Callback<ServiceRecordViewModel>() {
            @Override
            public void onResponse(@NonNull Call<ServiceRecordViewModel> call, @NonNull Response<ServiceRecordViewModel> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                record = response.body();
                record_edit_tv_car_brand_and_model.setText("Автомобиль: " + record.getCarBrandAndName());
                String dateb = record.getDateBegin().split("T")[0] + " " + record.getDateBegin().split("T")[1].substring(0, 5);
                record_edit_tv_date_begin.setText("Дата начала ТО: " + dateb);
                dateb = record.getDateEnd().split("T")[0] + " " + record.getDateEnd().split("T")[1].substring(0, 5);
                record_edit_tv_date_end.setText("Дата окончания ТО: " + dateb);
                record_edit_editText_description.setText(record.getDescription());
            }

            @Override
            public void onFailure(@NonNull Call<ServiceRecordViewModel> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("BBB", t.getMessage());
            }
        });
    }

    private void createOrUpdate() {
        String description = String.valueOf(record_edit_editText_description.getText());

        ServiceRecordBindingModel bind_record = new ServiceRecordBindingModel();
        bind_record.setCarId(record.getCarId());
        bind_record.setDateBegin(record.getDateBegin());
        bind_record.setDateEnd(record.getDateEnd());
        bind_record.setDescription(description);
        bind_record.setId(record.getId());

        createOrUpdateRecord(bind_record);
    }

    private void createOrUpdateRecord(ServiceRecordBindingModel bind_record) {
        Call<Void> call = app.getStoService().getApi().updateRecord(bind_record);
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