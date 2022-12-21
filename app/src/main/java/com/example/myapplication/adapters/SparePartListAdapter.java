package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.viewModels.CarViewModel;
import com.example.myapplication.models.viewModels.SparePartViewModel;
import com.example.myapplication.models.webViewModels.SparePartWebViewModel;

import java.util.ArrayList;

public class SparePartListAdapter extends RecyclerView.Adapter<SparePartListAdapter.SparePartViewHolder> {
    private Context context;
    private ArrayList<SparePartWebViewModel> parts;

    public SparePartListAdapter(Context c) {
        this.context = c;
        parts = new ArrayList<>();
    }

    public void filterList(ArrayList<SparePartWebViewModel> filterList) {
        parts = filterList;
        notifyDataSetChanged();
    }

    public void setData(ArrayList<SparePartWebViewModel> cars) {
        this.parts = cars;
    }

    @Override
    public void onBindViewHolder(@NonNull SparePartViewHolder holder, int position) {
        if (parts != null && parts.size() > 0) {
            SparePartWebViewModel part = parts.get(position);
            holder.sparepart_item_nameTV.setText("Деталь: " + part.getName());
            holder.sparepart_item_factorynumberTV.setText("Заводской номер: " + part.getFactoryNumber());
            holder.sparepart_item_priceTV.setText("Стоимость ед.: " + part.getPrice());
            holder.sparepart_item_typeTV.setText("Тип детали: " + part.getType());
            holder.sparepart_item_measurementTV.setText("Ед. измерения: " + part.getUMeasurement());
            holder.sparepart_item_countTV.setText("Количество: " + part.getCount());
        }
    }

    @Override
    public int getItemCount() {
        if (parts != null)
            return parts.size();
        return 0;
    }

    @NonNull
    @Override
    public SparePartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sparepart_item_recyclerview, parent, false);
        return new SparePartViewHolder(view);
    }

    class SparePartViewHolder extends RecyclerView.ViewHolder{
        private final TextView sparepart_item_nameTV;
        private final TextView sparepart_item_factorynumberTV;
        private final TextView sparepart_item_priceTV;
        private final TextView sparepart_item_typeTV;
        private final TextView sparepart_item_measurementTV;
        private final TextView sparepart_item_countTV;

        private SparePartViewHolder(View itemView) {
            super(itemView);
            sparepart_item_nameTV = itemView.findViewById(R.id.sparepart_item_nameTV);
            sparepart_item_factorynumberTV = itemView.findViewById(R.id.sparepart_item_factorynumberTV);
            sparepart_item_priceTV = itemView.findViewById(R.id.sparepart_item_priceTV);
            sparepart_item_typeTV = itemView.findViewById(R.id.sparepart_item_typeTV);
            sparepart_item_measurementTV = itemView.findViewById(R.id.sparepart_item_measurementTV);
            sparepart_item_countTV = itemView.findViewById(R.id.sparepart_item_countTV);
        }
    }
}
