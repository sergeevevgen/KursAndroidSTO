package com.example.myapplication.spinners;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.models.viewModels.CarViewModel;
import com.example.myapplication.models.viewModels.TOViewModel;

import java.util.ArrayList;

public class CarSpinnerAdapter extends ArrayAdapter<CarViewModel> {

        private ArrayList<CarViewModel> cars;
        private final Context context;

        public CarSpinnerAdapter(@NonNull Context context) {
                super(context, 0);
                this.context = context;
                cars = new ArrayList<>();
        }

        public void setData(ArrayList<CarViewModel> objects) {
                cars = objects;
        }

        @Override
        public int getCount() {
                return cars.size();
        }

        @Override
        public CarViewModel getItem(int i) {
                return cars.get(i);
        }

        @Override
        public long getItemId(int i) {
                return cars.get(i).getId();
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
                if (cars.size() > 0) {
                        if (convertView == null) {
                                convertView = LayoutInflater.from(context).inflate(R.layout.spinner_cars_item_closed, parent, false);
                        }
                        CarViewModel car = cars.get(position);
                        TextView textView = convertView.findViewById(R.id.spinner_car_item_car);
                        String name = car.getBrand() + " " + car.getModel();
                        if (name.length() > 8)
                                textView.setText(name.substring(0, 8) + '.');
                        else
                                textView.setText(name);
                        TextView textView1 = convertView.findViewById(R.id.spinner_car_item_vin);
                        textView1.setText("VIN:\n" + car.getVIN());
                }
                return convertView;
        }
}