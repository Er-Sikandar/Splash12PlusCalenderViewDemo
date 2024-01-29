package com.example.simdetactapp.ui;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.OutcomeReceiver;
import android.os.ParcelUuid;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.simdetactapp.R;
import com.example.simdetactapp.databinding.ActivityLoginBinding;
import com.example.simdetactapp.databinding.DetactSimLayoutBinding;
import com.example.simdetactapp.utils.Const;
import com.example.simdetactapp.utils.Helper;
import com.example.simdetactapp.utils.Prefs;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

public class LoginActivity extends AppCompatActivity {
    private String TAG = "LoginActivity";
    private ActivityLoginBinding binding;
    private Prefs prefs;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefs = Prefs.getInstance(this);

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
            binding.tvDetails.setText("Manufacturer: "+manufacturer+"\nAndroid Version: "+release +"\nDevice Id: "+android_id);
/*
            binding.tvDetails.setText("Model: "+deviceModel+"\nDevice: "+device+"\nBrand: "+brand
                    +"\nHardware: "+hardware+"\nManufacturer: "+manufacturer +"\nId: "+id
                    +"\nAndroid Version: "+release+"\nSdk: "+sdk+"\nProduct: "+product
                    +"\nDevice Id: "+android_id);
*/
            Log.e(TAG, "onCreate: "+android_id);
        } catch (Exception e) {
            binding.tvDetails.setText("No Data found");
            Log.e(TAG, "Error getting Device INFO");
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},101);
            Log.e(TAG, "onCreate:");

        } else {


        }





       /* PackageManager packageManager = getPackageManager();
        boolean hasGsmTelephony = packageManager.hasSystemFeature(PackageManager.FEATURE_TELEPHONY_GSM);
        if (hasGsmTelephony) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},101);
                Log.e(TAG, "onCreate:");

            } else {
                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                String imei=telephonyManager.getImei();

                Log.e(TAG, "onCreate: Else "+imei);

            }
        }
*/
       /* if (hasGsmTelephony) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 100);
                Log.e(TAG, "onCreate1:");
            } else {
                Log.e(TAG, "onCreate2: Else");

            }
        }*/




       /* if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            String imeiNumber = getIMEINumber();
            Log.e("IMEI", "IMEI Number: " + imeiNumber);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }*/
    }
   /* @RequiresApi(api = Build.VERSION_CODES.O)
    private String getIMEINumber() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
           String imei=telephonyManager.getImei();
               if (imei!=null){
                   return imei;
               }else {
                   return null;
               }
        }else {
            return null;
        }
    }*/


    @Override
    protected void onResume() {
        //getPhoneNumbersFun();
        super.onResume();
    }

    private void getPhoneNumbersFun() {
        int count = -1;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            SubscriptionManager subscriptionManager = SubscriptionManager.from(getApplicationContext());
            List<SubscriptionInfo> subsInfoList = subscriptionManager.getActiveSubscriptionInfoList();
            List<String> strings = new ArrayList<>();
            if (subsInfoList.size() > 0) {
                for (int i = 0; i < subsInfoList.size(); i++) {
                    String number = subsInfoList.get(i).getNumber();
                    String numberCar = String.valueOf(subsInfoList.get(i).getCarrierName());
                    Log.e("TAG", "" + number);
                    Log.e("TAG", "Name: " + numberCar);
                    if (!TextUtils.isEmpty(number) && number.length() >= 10) {
                        strings.add(number);
                    } else {
                        strings.add("0");
                        count = i;
                    }
                }
                if (strings.size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String simNum : strings) {
                        stringBuilder.append("Numbers: " + simNum + "\n");
                    }
                   // isDetectSimDialog(subsInfoList.size(), count, strings);
                    binding.tvNumbers.setText(stringBuilder);
                } else {
                    binding.tvNumbers.setText("Sim Not Found");
                    Log.e("TAG", "No Numbers Founds" + strings);
                }
            } else {
                binding.tvNumbers.setText("Sim Not Found");
                Log.e("TAG", "Both Sim Not Inserted" + strings);
            }


        }

    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void isDetectSimDialog(int simSize, int count, List<String> numData) {
        AtomicInteger simSelected = new AtomicInteger();
        Dialog dialog = new Dialog(this);
        DetactSimLayoutBinding detactBinding = DetactSimLayoutBinding.inflate(getLayoutInflater());
        dialog.setContentView(detactBinding.getRoot());
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (simSize == 1) {
            detactBinding.llSimSecond.setVisibility(View.GONE);
            detactBinding.llSimFirst.setVisibility(View.VISIBLE);

        } else {
            detactBinding.llSimSecond.setVisibility(View.VISIBLE);
            detactBinding.llSimFirst.setVisibility(View.VISIBLE);

        }
        if (count == 0) {
            detactBinding.llSimFirst.setVisibility(View.GONE);
            detactBinding.llSimSecond.setVisibility(View.VISIBLE);

        } else if (count == 1) {
            detactBinding.llSimFirst.setVisibility(View.VISIBLE);
            detactBinding.llSimSecond.setVisibility(View.GONE);

        }


        detactBinding.llSimFirst.setOnClickListener(v -> {
            detactBinding.llSimFirst.setBackground(getResources().getDrawable(R.drawable.border_corner));
            detactBinding.llSimSecond.setBackgroundColor(getResources().getColor(R.color.white));
            simSelected.set(1);
            if (TextUtils.isEmpty(prefs.getPrefsString(Const.SIM_FIRST))) {
                prefs.setPrefsString(Const.SIM_FIRST, numData.get(0));
                startActivity(new Intent(this, HomeActivity.class));
            } else {
                if (prefs.getPrefsString(Const.SIM_FIRST).equals(numData.get(0))) {
                    Helper.showValidationErrorDialog(this, "Already Registered");
                } else {
                    Helper.showValidationErrorDialog(this, "Mobile Number Not Match");
                }
            }
            Log.e(TAG, "isDetectSimDialog: " + numData.get(0));
        });
        detactBinding.llSimSecond.setOnClickListener(v -> {
            detactBinding.llSimSecond.setBackground(getResources().getDrawable(R.drawable.border_corner));
            detactBinding.llSimFirst.setBackgroundColor(getResources().getColor(R.color.white));
            simSelected.set(2);
            if (TextUtils.isEmpty(prefs.getPrefsString(Const.SIM_SECOND))) {
                prefs.setPrefsString(Const.SIM_SECOND, numData.get(1));
                startActivity(new Intent(this, HomeActivity.class));
            } else {
                if (prefs.getPrefsString(Const.SIM_SECOND).equals(numData.get(1))) {
                    Helper.showValidationErrorDialog(this, "Already Registered");
                } else {
                    Helper.showValidationErrorDialog(this, "Mobile Number Not Match");
                }
            }
            Log.e(TAG, "isDetectSimDialog: " + numData.get(1));
        });
       /* detactBinding.ivClose.setOnClickListener(v -> {
            dialog.dismiss();
        });*/
        dialog.show();

    }
}