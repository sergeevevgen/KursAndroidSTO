package com.example.myapplication.ui.cars;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.adapters.CarListAdapter;
import com.example.myapplication.models.bindingModels.CarBindingModel;
import com.example.myapplication.models.viewModels.CarViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarsFragment extends Fragment {
    private CarListAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<CarViewModel> cars;
    private App app;
    private FloatingActionButton fab;
    public CarsFragment() {
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
        View view =  inflater.inflate(R.layout.cars_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_cars);

        fab = view.findViewById(R.id.fab_add_car);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddEditCarFragment addEditCarFragment = new AddEditCarFragment();
                replaceFragment(addEditCarFragment);
            }
        });

        setRecyclerView();
        // Inflate the layout for this fragment
        return view;
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new CarListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        restore();

        //при долгом нажатии на элемент открывается фрагмент его редактирования
        adapter.setOnItemLongClickListener(new CarListAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClicked(CarViewModel car, int position) {
                Bundle bundle = new Bundle();
                Integer id = car.getId();
                bundle.putInt("carId", id);
                AddEditCarFragment addEditCarFragment = new AddEditCarFragment();
                addEditCarFragment.setArguments(bundle);
                replaceFragment(addEditCarFragment);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                CarViewModel car = adapter.getCarAt(viewHolder.getAdapterPosition());
                CarBindingModel car_bind = new CarBindingModel();
                car_bind.setId(car.getId());
                car_bind.setBrand(car.getBrand());
                car_bind.setModel(car.getModel());
                car_bind.setVIN(car.getVIN());
                car_bind.setOwnerPhoneNumber(car.getOwnerPhoneNumber());
                car_bind.setRecords(car.getRecords());
                deleteCar(car_bind);

                Toast.makeText(getContext(), "Авто удалено", Toast.LENGTH_SHORT).show();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                restore();
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void restore() {
        Call<List<CarViewModel>> call = app.getStoService().getApi().getCars();
        call.enqueue(new Callback<List<CarViewModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<CarViewModel>> call, @NonNull Response<List<CarViewModel>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                cars = (ArrayList<CarViewModel>) response.body();
                adapter.setData(cars);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<CarViewModel>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("BBB", t.getMessage());
            }
        });
    }

    private void deleteCar(CarBindingModel car) {
        Call<Void> call = app.getStoService().getApi().deleteCar(car);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
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