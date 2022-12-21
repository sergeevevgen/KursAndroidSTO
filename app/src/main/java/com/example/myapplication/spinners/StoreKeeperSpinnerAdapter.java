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
import com.example.myapplication.models.viewModels.WorkTypeViewModel;

import java.util.ArrayList;

public class StoreKeeperSpinnerAdapter extends ArrayAdapter<StoreKeeperViewModel> {
    private ArrayList<StoreKeeperViewModel> storeKeepers;
    private final Context context;

    public StoreKeeperSpinnerAdapter(@NonNull Context context) {
        super(context, 0);
        this.context = context;
        storeKeepers = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return storeKeepers.size();
    }

    public void setData(ArrayList<StoreKeeperViewModel> objects) {
        storeKeepers = objects;
    }

    @Override
    public StoreKeeperViewModel getItem(int i) {
        return storeKeepers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return storeKeepers.get(i).getId();
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
        if (storeKeepers.size() > 0) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.spinner_storekeeper_item_closed, parent, false);
            }

            TextView textView = convertView.findViewById(R.id.spinner_storekeeper_item_text);
            textView.setText(storeKeepers.get(position).getFIO());
        }
        return convertView;
    }
}
