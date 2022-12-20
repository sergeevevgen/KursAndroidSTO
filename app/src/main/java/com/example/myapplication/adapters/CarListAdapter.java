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

import java.util.ArrayList;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.CarViewHolder> {
    private Context context;
    private ArrayList<CarViewModel> cars;
    //private ArrayList<CarViewModel> cars_selected;
    private OnItemLongClickListener listener;
    private final String VIN = "VIN: ";
    private final String Phone = "Тел.: ";
    public CarListAdapter(Context c) {
        this.context = c;
        cars = new ArrayList<>();
        //cars_selected = new ArrayList<>();
    }

    public void filterList(ArrayList<CarViewModel> filterList) {
        cars = filterList;
        notifyDataSetChanged();
    }

    public void setData(ArrayList<CarViewModel> cars) {
        this.cars = cars;
    }

//    public ArrayList<CarViewModel> getSelected_cars() {
//        return cars_selected;
//    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        if (cars != null && cars.size() > 0) {
            CarViewModel car = cars.get(position);
            holder.brandTV.setText(car.getBrand());
            holder.modelTV.setText(car.getModel());
            holder.vinTV.setText(VIN + car.getVIN());
            holder.phoneTV.setText(Phone + car.getOwnerPhoneNumber());
        }
    }

    @Override
    public int getItemCount() {
        if (cars != null)
            return cars.size();
        return 0;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item_recyclerview, parent, false);
        return new CarViewHolder(view);
    }

    class CarViewHolder extends RecyclerView.ViewHolder{
        private final TextView brandTV;
        private final TextView modelTV;
        private final TextView vinTV;
        private final TextView phoneTV;

        private CarViewHolder(View itemView) {
            super(itemView);
            brandTV = itemView.findViewById(R.id.car_brandTV);
            modelTV = itemView.findViewById(R.id.car_modelTV);
            vinTV = itemView.findViewById(R.id.car_vinTV);
            phoneTV = itemView.findViewById(R.id.car_owner_phone_numberTV);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemLongClicked(cars.get(position), position);
                    return true;
                }
            });
        }
    }

    public interface OnItemLongClickListener {
        void onItemLongClicked(CarViewModel car, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.listener = listener;
    }
}
