package com.example.myapplication.ui.to;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.adapters.TOListAdapter;
import com.example.myapplication.models.viewModels.TOViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private TOListAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<TOViewModel> tos;
    private App app;
    private FloatingActionButton fab;

    public HomeFragment() {

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
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_tos);

        fab = view.findViewById(R.id.fab_add_to);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTOFragment addTOFragment = new AddTOFragment();
                replaceFragment(addTOFragment);
            }
        });

        setRecyclerView();
        // Inflate the layout for this fragment
        return view;
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new TOListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        restore();

        //при долгом нажатии на элемент открывается фрагмент его редактирования
        adapter.setOnItemLongClickListener(new TOListAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClicked(TOViewModel to, int position) {
                Bundle bundle = new Bundle();
                Integer id = to.getId();
                bundle.putInt("toId", id);
                bundle.putString("status", to.getStatus());
                WatchTOFragment watchTOFragment = new WatchTOFragment();
                watchTOFragment.setArguments(bundle);
                replaceFragment(watchTOFragment);
            }
        });
    }

    private void restore() {
        //Todo: initialize employee
//        Call<List<TOViewModel>> call = app.getStoService().getApi().getTOs(App.getEmployee().getId());
        Call<List<TOViewModel>> call = app.getStoService().getApi().getTOs(App.getEmployee().getId());
        call.enqueue(new Callback<List<TOViewModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TOViewModel>> call, @NonNull Response<List<TOViewModel>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                tos = (ArrayList<TOViewModel>) response.body();
                adapter.setData(tos);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<TOViewModel>> call, @NonNull Throwable t) {
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