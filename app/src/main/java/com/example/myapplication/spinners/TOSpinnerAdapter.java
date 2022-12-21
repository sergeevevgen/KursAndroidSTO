package com.example.myapplication.spinners;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.models.viewModels.StoreKeeperViewModel;
import com.example.myapplication.models.viewModels.TOViewModel;

import java.util.ArrayList;

public class TOSpinnerAdapter extends ArrayAdapter<TOViewModel> {
    private ArrayList<TOViewModel> toS;
    private final Context context;

    public TOSpinnerAdapter(@NonNull Context context) {
        super(context, 0);
        this.context = context;
        toS = new ArrayList<>();
    }

    public void setData(ArrayList<TOViewModel> objects) {
        toS = objects;
    }

    @Override
    public int getCount() {
        return toS.size();
    }

    @Override
    public TOViewModel getItem(int i) {
        return toS.get(i);
    }

    @Override
    public long getItemId(int i) {
        return toS.get(i).getId();
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
        if (toS.size() > 0) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.spinner_tos_item_closed, parent, false);
            }
            TOViewModel to = toS.get(position);
            TextView textView = convertView.findViewById(R.id.spinner_to_item_car_and_number);
            String name = to.getTOAndCarName();
            if (name.length() > 8)
                textView.setText(name.substring(0, 8) + '.');
            else
                textView.setText(name);
            TextView textView1 = convertView.findViewById(R.id.spinner_to_item_datecreate);
            textView1.setText("Дата создания: " + to.getDateCreate().split("T")[0] + " " + to.getDateCreate().split("T")[1].substring(0, 5));
        }
        return convertView;
    }
}
