package com.example.simdetactapp.ui;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.example.simdetactapp.R;
import com.example.simdetactapp.databinding.ActivityDeviceDetailsBinding;

public class DeviceDetailsActivity extends AppCompatActivity {
private ActivityDeviceDetailsBinding binding;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDeviceDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnInfo.setOnClickListener(v -> {
            try {
                String deviceModel = Build.MODEL;
                String device = Build.DEVICE;
                String brand = Build.BRAND;
                String hardware = Build.HARDWARE;
                String manufacturer = Build.MANUFACTURER;
                String id = Build.ID;
                String release = Build.VERSION.RELEASE;
                String sdk = String.valueOf(Build.VERSION.SDK_INT);
                String product = Build.PRODUCT;
             @SuppressLint("HardwareIds")
             String android_id = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
                binding.tvDetails.setText("Model: "+deviceModel+"\nDevice: "+device+"\nBrand: "+brand
                        +"\nHardware: "+hardware+"\nManufacturer: "+manufacturer +"\nId: "+id
                        +"\nAndroid Version: "+release+"\nSdk: "+sdk+"\nProduct: "+product
                        +"\nDevice Id: "+android_id);
            } catch (Exception e) {
                binding.tvDetails.setText("No Data found");
                Log.e(TAG, "Error getting Device INFO");
            }

        });




    }
}