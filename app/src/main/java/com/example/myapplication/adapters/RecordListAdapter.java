package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.viewModels.ServiceRecordViewModel;

import java.util.ArrayList;

public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.RecordViewHolder> {
    private Context context;
    private ArrayList<ServiceRecordViewModel> records;
    //private ArrayList<CarViewModel> cars_selected;
    private OnItemLongClickListener listener;
    private final String DateB = "Дата начала: ";
    private final String DateE = "Дата окончания: ";
    private final String Description = "Описание:\n";

    public RecordListAdapter(Context c) {
        this.context = c;
        records = new ArrayList<>();
        //cars_selected = new ArrayList<>();
    }

    public void filterList(ArrayList<ServiceRecordViewModel> filterList) {
        records = filterList;
        notifyDataSetChanged();
    }

    public void setData(ArrayList<ServiceRecordViewModel> records) {
        this.records = records;
    }

//    public ArrayList<CarViewModel> getSelected_cars() {
//        return cars_selected;
//    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        if (records != null && records.size() > 0) {
            ServiceRecordViewModel record = records.get(position);
            holder.dateBeginTV.setText(DateB + record.getDateBegin());
            holder.dateEndTV.setText(DateE + record.getDateEnd());
            holder.descriptionTV.setText(Description + record.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        if (records != null)
            return records.size();
        return 0;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item_recyclerview, parent, false);
        return new RecordViewHolder(view);
    }

    class RecordViewHolder extends RecyclerView.ViewHolder {
        private final TextView dateBeginTV;
        private final TextView dateEndTV;
        private final TextView descriptionTV;

        private RecordViewHolder(View itemView) {
            super(itemView);
            dateBeginTV = itemView.findViewById(R.id.record_dateBeginTV);
            dateEndTV = itemView.findViewById(R.id.record_dateEndTV);
            descriptionTV = itemView.findViewById(R.id.record_descriptionTV);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemLongClicked(records.get(position), position);
                    return true;
                }
            });
        }
    }

    public interface OnItemLongClickListener {
        void onItemLongClicked(ServiceRecordViewModel record, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.listener = listener;
    }
}
