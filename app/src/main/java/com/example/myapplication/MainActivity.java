package com.example.myapplication;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.ui.cars.CarsFragment;
import com.example.myapplication.ui.to.HomeFragment;
import com.example.myapplication.ui.ProfileFragment;
import com.example.myapplication.ui.works.WorksFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.navView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.navigation_cars:
                    replaceFragment(new CarsFragment());
                    break;
                case R.id.navigation_works:
                    replaceFragment(new WorksFragment());
                    break;
                case R.id.navigation_profile:
                    replaceFragment(new ProfileFragment());
                    //TOdo: file download
//                    DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse("http://192.168.0.127:7252/api/getfile.xlsx"));
//                    request.setDescription("Test");
//                    manager.enqueue(request);
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}