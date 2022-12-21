package com.example.myapplication.ui.works;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.adapters.SparePartListAdapter;
import com.example.myapplication.models.viewModels.SparePartViewModel;
import com.example.myapplication.models.viewModels.WorkTypeViewModel;
import com.example.myapplication.models.viewModels.WorkViewModel;
import com.example.myapplication.models.webViewModels.SparePartWebViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WatchWorkFragment extends Fragment {
    private ArrayList<SparePartWebViewModel> parts;
    private RecyclerView recyclerView;
    private SparePartListAdapter adapter;
    private TextView watch_work_storekeeperfioTV;
    private TextView watch_work_worknameTV;
    private TextView watch_work_priceTV;
    private TextView watch_work_netpriceTV;
    private TextView watch_work_statusTV;
    private TextView watch_work_workbeginTV;
    private TextView watch_work_countTV;
    private WorkViewModel work;
    private Integer workId;
    private App app;

    public WatchWorkFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_watch_work, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_spareparts);
        watch_work_storekeeperfioTV = view.findViewById(R.id.watch_work_storekeeperfioTV);
        watch_work_worknameTV = view.findViewById(R.id.watch_work_worknameTV);
        watch_work_priceTV = view.findViewById(R.id.watch_work_priceTV);
        watch_work_netpriceTV = view.findViewById(R.id.watch_work_netpriceTV);
        watch_work_statusTV = view.findViewById(R.id.watch_work_statusTV);
        watch_work_workbeginTV = view.findViewById(R.id.watch_work_workbeginTV);
        watch_work_countTV = view.findViewById(R.id.watch_work_countTV);

        setRecyclerView();

        return view;
    }

    private void setRecyclerView() {
        Bundle bundle = getArguments();
        if (bundle != null)
            workId = bundle.getInt("workId");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new SparePartListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        restore();
    }

    private void restore() {
        if (workId != null) {
            Call<WorkViewModel> call1 = app.getStoService().getApi().getWork(workId);
            call1.enqueue(new Callback<WorkViewModel>() {
                @Override
                public void onResponse(@NonNull Call<WorkViewModel> call, @NonNull Response<WorkViewModel> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    work = response.body();
                    watch_work_storekeeperfioTV.append(String.valueOf(work.getStoreKeeperFIO()));
                    watch_work_worknameTV.append(work.getWorkName());
                    watch_work_priceTV.append(String.valueOf(work.getPrice()));
                    watch_work_netpriceTV.append(String.valueOf(work.getNetPrice()));
                    watch_work_statusTV.setText(Html.fromHtml(getStatus(work.getWorkStatus())), TextView.BufferType.SPANNABLE);
                    watch_work_workbeginTV.append(getDateNormalFormat(work.getWorkBegin()));
                    watch_work_countTV.append(String.valueOf(work.getCount()));
                    loadParts();
                }

                @Override
                public void onFailure(@NonNull Call<WorkViewModel> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("BBB", t.getMessage());
                }
            });
        }
    }

    private void loadParts() {
        if (work != null) {
            Call<List<SparePartWebViewModel>> call = app.getStoService().getApi().getSparePartsByWorkType(work.getWorkTypeId());
            call.enqueue(new Callback<List<SparePartWebViewModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<SparePartWebViewModel>> call, @NonNull Response<List<SparePartWebViewModel>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    parts = (ArrayList<SparePartWebViewModel>) response.body();
                    adapter.setData(parts);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(@NonNull Call<List<SparePartWebViewModel>> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("BBB", t.getMessage());
                }
            });
        }
    }

    private String getStatus(String s) {
        switch (s) {
            case "Принят":
                return "<font color='black'>Статус: </font><font color='red'>" + s + "</font>";
            case "Выполняется":
                return "<font color='black'>Статус: </font><font color='yellow'>" + s + "</font>";
            case "Готов":
                return "<font color='black'>Статус: </font><font color='blue'>" + s + "</font>";
            default:
                return "<font color='black'>Статус: </font><font color='green'>" + s + "</font>";
        }
    }

    private String getDateNormalFormat(String s) {
        if (!s.equals("-"))
            return s.split("T")[0] + " " + s.split("T")[1].substring(0, 5);
        return "Не в работе";
    }
}