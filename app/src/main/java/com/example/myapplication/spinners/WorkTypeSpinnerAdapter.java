package com.example.myapplication.spinners;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.models.viewModels.TOViewModel;
import com.example.myapplication.models.viewModels.WorkTypeViewModel;

import java.util.ArrayList;

public class WorkTypeSpinnerAdapter extends ArrayAdapter<WorkTypeViewModel> {
    private ArrayList<WorkTypeViewModel> workTypes;
    private final Context context;

    public WorkTypeSpinnerAdapter(@NonNull Context context) {
        super(context, 0);
        this.context = context;
        workTypes = new ArrayList<>();
    }

    public void setData(@NonNull ArrayList<WorkTypeViewModel> objects) {
        workTypes = objects;
    }

    @Override
    public int getCount() {
        return workTypes.size();
    }

    @Override
    public WorkTypeViewModel getItem(int i) {
        return workTypes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return workTypes.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return initView(i, view, viewGroup);
    }

    // It gets a View that displays in the drop down popup the data at the specified position
    @Override
    public View getDropDownView(int position, View convertView,
                                @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView,
                          ViewGroup parent) {
        if (workTypes.size() > 0) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.spinner_worktypes_item_closed, parent, false);
            }
            WorkTypeViewModel work = workTypes.get(position);
            TextView textView = convertView.findViewById(R.id.spinner_worktype_item_name);
            textView.setText(work.getWorkName());
            TextView textView1 = convertView.findViewById(R.id.spinner_worktype_item_netprice);
            textView1.setText(String.valueOf(work.getNetPrice()));
        }
        return convertView;
    }
}
