package com.example.myapplication.ui.works;

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
import com.example.myapplication.adapters.WorkListAdapter;
import com.example.myapplication.models.viewModels.WorkViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WorksFragment extends Fragment {
    private WorkListAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<WorkViewModel> works;
    private App app;
    private FloatingActionButton fab;

    public WorksFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_works, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_works);

        fab = view.findViewById(R.id.fab_add_work);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateWorkFragment createWorkFragment = new CreateWorkFragment();
                replaceFragment(createWorkFragment);
            }
        });

        setRecyclerView();
        // Inflate the layout for this fragment
        return view;
    }


    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new WorkListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        restore();

        //при долгом нажатии на элемент открывается фрагмент его редактирования
        adapter.setOnItemLongClickListener(new WorkListAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClicked(WorkViewModel work, int position) {
                Bundle bundle = new Bundle();
                Integer id = work.getId();
                bundle.putInt("workId", id);
                WatchWorkFragment watchWorkFragment = new WatchWorkFragment();
                watchWorkFragment.setArguments(bundle);
                replaceFragment(watchWorkFragment);
            }
        });
    }

    private void restore() {
        Call<List<WorkViewModel>> call = app.getStoService().getApi().getWorksByEmployee(App.getEmployee().getId());
        call.enqueue(new Callback<List<WorkViewModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<WorkViewModel>> call, @NonNull Response<List<WorkViewModel>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                works = (ArrayList<WorkViewModel>) response.body();
                adapter.setData(works);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<WorkViewModel>> call, @NonNull Throwable t) {
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