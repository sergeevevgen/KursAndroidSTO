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
    private OnItemLongClickListener listener;
    private final String DateB = "Дата начала: ";
    private final String DateE = "Дата окончания: ";
    private final String Description = "Описание:\n";

    public RecordListAdapter(Context c) {
        this.context = c;
        records = new ArrayList<>();
    }

    public void filterList(ArrayList<ServiceRecordViewModel> filterList) {
        records = filterList;
        notifyDataSetChanged();
    }

    public void setData(ArrayList<ServiceRecordViewModel> records) {
        this.records = records;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        if (records != null && records.size() > 0) {
            ServiceRecordViewModel record = records.get(position);
            String date = record.getDateBegin().split("T")[0] + " " + record.getDateBegin().split("T")[1].substring(0, 5);
            holder.dateBeginTV.setText(DateB + date);
            date = record.getDateEnd().split("T")[0] + " " + record.getDateEnd().split("T")[1].substring(0, 5);
            holder.dateEndTV.setText(DateE + date);
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
            dateBeginTV = itemView.findViewById(R.id.to_to_and_carNameTV);
            dateEndTV = itemView.findViewById(R.id.to_statusTV);
            descriptionTV = itemView.findViewById(R.id.to_sumTV);

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
