package com.example.myapplication.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.viewModels.TOViewModel;
import com.example.myapplication.models.viewModels.WorkViewModel;

import java.util.ArrayList;

public class WorkListAdapter extends RecyclerView.Adapter<WorkListAdapter.WorkViewHolder> {
    private Context context;
    private ArrayList<WorkViewModel> works;
    private OnItemLongClickListener listener;

    public WorkListAdapter(Context c) {
        this.context = c;
        works = new ArrayList<>();
    }

    public void filterList(ArrayList<WorkViewModel> filterList) {
        works = filterList;
        notifyDataSetChanged();
    }

    public void setData(ArrayList<WorkViewModel> works) {
        this.works = works;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkViewHolder holder, int position) {
        if (works != null && works.size() > 0) {
            WorkViewModel work = works.get(position);
            holder.work_item_storekeeperTV.append(work.getStoreKeeperFIO());
            holder.work_item_nameTV.append(work.getWorkName());
            holder.work_item_priceTV.append(String.valueOf(work.getPrice()));
            holder.work_item_netpriceTV.append(String.valueOf(work.getNetPrice()));
            String status = work.getWorkStatus();
            holder.work_item_statusTV.setText(Html.fromHtml(getStatus(status)), TextView.BufferType.SPANNABLE);
            holder.work_item_datebeginTV.append(getDateNormalFormat(work.getWorkBegin()));
            holder.work_item_countTV.append(String.valueOf(work.getCount()));
        }
    }

    private String getDateNormalFormat(String s) {
        if (!s.equals("-"))
            return s.split("T")[0] + " " + s.split("T")[1].substring(0, 5);
        return "-";
    }

    private String getStatus(String s) {
        switch (s) {
            case "Принят":
                return "<font color='black'>Статус: </font><font color='red'>" + s + "</font>";
            case "Выполняется":
                return "<font color='black'>Статус: </font><font color='yellow'>" + s + "</font>";
            default:
                return "<font color='black'>Статус: </font><font color='blue'>" + s + "</font>";
        }
    }

    @Override
    public int getItemCount() {
        if (works != null)
            return works.size();
        return 0;
    }

    @NonNull
    @Override
    public WorkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_item_recyclerview, parent, false);
        return new WorkViewHolder(view);
    }

    class WorkViewHolder extends RecyclerView.ViewHolder {
        private final TextView work_item_storekeeperTV;
        private final TextView work_item_nameTV;
        private final TextView work_item_priceTV;
        private final TextView work_item_netpriceTV;
        private final TextView work_item_statusTV;
        private final TextView work_item_datebeginTV;
        private final TextView work_item_countTV;

        private WorkViewHolder(View itemView) {
            super(itemView);
            work_item_storekeeperTV = itemView.findViewById(R.id.work_item_storekeeperTV);
            work_item_nameTV = itemView.findViewById(R.id.work_item_nameTV);
            work_item_priceTV = itemView.findViewById(R.id.work_item_priceTV);
            work_item_netpriceTV = itemView.findViewById(R.id.work_item_netpriceTV);
            work_item_statusTV = itemView.findViewById(R.id.work_item_statusTV);
            work_item_datebeginTV = itemView.findViewById(R.id.work_item_datebeginTV);
            work_item_countTV = itemView.findViewById(R.id.work_item_countTV);

            if (listener != null) {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = getAdapterPosition();
                        if (listener != null && position != RecyclerView.NO_POSITION)
                            listener.onItemLongClicked(works.get(position), position);
                        return true;
                    }
                });
            }
        }
    }

    public interface OnItemLongClickListener {
        void onItemLongClicked(WorkViewModel work, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.listener = listener;
    }
}