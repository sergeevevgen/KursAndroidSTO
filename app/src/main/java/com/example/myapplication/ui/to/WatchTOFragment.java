package com.example.myapplication.ui.to;

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
import com.example.myapplication.adapters.WorkListAdapter;
import com.example.myapplication.models.viewModels.TOViewModel;
import com.example.myapplication.models.viewModels.WorkViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WatchTOFragment extends Fragment {
    private ArrayList<WorkViewModel> works;
    private RecyclerView recyclerView;
    private WorkListAdapter adapter;
    private TextView watch_to_idTV;
    private TextView watch_to_carnameTV;
    private TextView watch_to_statusTV;
    private TextView watch_to_datecreateTV;
    private TextView watch_to_datebeginTV;
    private TextView watch_to_dateendTV;
    private TextView watch_to_sumTV;
    private TOViewModel to;
    private Integer toId;
    private App app;

    public WatchTOFragment() {
        // Required empty public constructor
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
        View view =  inflater.inflate(R.layout.fragment_watch_t_o, container, false);
        recyclerView = view.findViewById(R.id.watch_to_recyclerview_cars);
        watch_to_idTV = view.findViewById(R.id.watch_to_idTV);
        watch_to_carnameTV = view.findViewById(R.id.watch_to_carnameTV);
        watch_to_statusTV = view.findViewById(R.id.watch_to_statusTV);
        watch_to_datecreateTV = view.findViewById(R.id.watch_to_datecreateTV);
        watch_to_datebeginTV = view.findViewById(R.id.watch_to_datebeginTV);
        watch_to_dateendTV = view.findViewById(R.id.watch_to_dateendTV);
        watch_to_sumTV = view.findViewById(R.id.watch_to_sumTV);

        setRecyclerView();

        return view;
    }

    private void setRecyclerView() {
        Bundle bundle = getArguments();
        if (bundle != null)
            toId = bundle.getInt("toId");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new WorkListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        restore();
    }

    private void restore() {
        Call<List<WorkViewModel>> call = app.getStoService().getApi().getWorksByTO(toId);
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

        Call<TOViewModel> call1 = app.getStoService().getApi().getTO(toId);
        call1.enqueue(new Callback<TOViewModel>() {
            @Override
            public void onResponse(@NonNull Call<TOViewModel> call, @NonNull Response<TOViewModel> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                to = response.body();
                watch_to_idTV.append(String.valueOf(to.getId()));
                watch_to_carnameTV.append(to.getCarName());
                watch_to_statusTV.setText(Html.fromHtml(getStatus(to.getStatus())), TextView.BufferType.SPANNABLE);
                watch_to_datecreateTV.append(getDateNormalFormat(to.getDateCreate()));
                watch_to_datebeginTV.append(getDateNormalFormat(to.getDateImplement()));
                watch_to_dateendTV.append(getDateNormalFormat(to.getDateOver()));
                watch_to_sumTV.append(String.valueOf(to.getSum()));
            }

            @Override
            public void onFailure(@NonNull Call<TOViewModel> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("BBB", t.getMessage());
            }
        });
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
        return "-";
    }
}