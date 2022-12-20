package com.example.myapplication.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.viewModels.CarViewModel;
import com.example.myapplication.models.viewModels.TOViewModel;
import com.example.myapplication.enums.TOStatus;

import java.util.ArrayList;

public class TOListAdapter extends RecyclerView.Adapter<TOListAdapter.TOViewHolder> {
    private Context context;
    private ArrayList<TOViewModel> tos;
    private OnItemLongClickListener listener;

    public TOListAdapter(Context c) {
        this.context = c;
        tos = new ArrayList<>();
        //cars_selected = new ArrayList<>();
    }

    public void filterList(ArrayList<TOViewModel> filterList) {
        tos = filterList;
        notifyDataSetChanged();
    }

    public void setData(ArrayList<TOViewModel> tos) {
        this.tos = tos;
    }

//    public ArrayList<CarViewModel> getSelected_cars() {
//        return cars_selected;
//    }

    @Override
    public void onBindViewHolder(@NonNull TOViewHolder holder, int position) {
        if (tos != null && tos.size() > 0) {
            TOViewModel to = tos.get(position);
            holder.to_to_and_carNameTV.setText(to.getTOAndCarName());
            String status = to.getStatus();
            holder.to_statusTV.setText(Html.fromHtml(getStatus(status)), TextView.BufferType.SPANNABLE);
            holder.to_sumTV.append(String.valueOf(to.getSum()));
            holder.to_dateCreateTV.append(getDateNormalFormat(to.getDateCreate()));
            holder.to_dateImplementTV.append(getDateNormalFormat(to.getDateImplement()));
            holder.to_dateOverTV.append(getDateNormalFormat(to.getDateOver()));
        }
    }

    private String getDateNormalFormat(String s) {
        return s.split("T")[0] + " " + s.split("T")[1].substring(0, 5);
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

    @Override
    public int getItemCount() {
        if (tos != null)
            return tos.size();
        return 0;
    }

    @NonNull
    @Override
    public TOViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_item_recyclerview, parent, false);
        return new TOViewHolder(view);
    }

    class TOViewHolder extends RecyclerView.ViewHolder {
        private final TextView to_to_and_carNameTV;
        private final TextView to_statusTV;
        private final TextView to_sumTV;
        private final TextView to_dateCreateTV;
        private final TextView to_dateImplementTV;
        private final TextView to_dateOverTV;

        private TOViewHolder(View itemView) {
            super(itemView);
            to_to_and_carNameTV = itemView.findViewById(R.id.to_to_and_carNameTV);
            to_statusTV = itemView.findViewById(R.id.to_statusTV);
            to_sumTV = itemView.findViewById(R.id.to_sumTV);
            to_dateCreateTV = itemView.findViewById(R.id.to_dateCreateTV);
            to_dateImplementTV = itemView.findViewById(R.id.to_dateImplementTV);
            to_dateOverTV = itemView.findViewById(R.id.to_dateOverTV);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemLongClicked(tos.get(position), position);
                    return true;
                }
            });
        }
    }

    public interface OnItemLongClickListener {
        void onItemLongClicked(TOViewModel to, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.listener = listener;
    }
}