package com.example.simdetactapp;

import static com.example.simdetactapp.utils.Const.NUMBERS_REQUEST_CODE;
import static com.example.simdetactapp.utils.Const.SPLASH_TIME;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.simdetactapp.databinding.ActivityMainBinding;
import com.example.simdetactapp.ui.HomeActivity;
import com.example.simdetactapp.ui.LoginActivity;
import com.example.simdetactapp.utils.Const;
import com.example.simdetactapp.utils.Helper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String TAG="MainActivity";
    private ActivityMainBinding binding;
    private boolean isGrant=false;

    @Override
    protected void onRestart() {
        checkCusPermissions();
        super.onRestart();
    }
    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(() -> true);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkCusPermissions();

    }
    private void checkCusPermissions(){
        String[] psn;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            psn = Const.permissionsRequired;
        } else {
            psn = Const.permissionsRequired;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (Helper.checkPermissions(this, psn, Const.NUMBERS_REQUEST_CODE) ){
                if (!isGrant) {
                    loginStay();
                }
                Log.e(TAG, "granted: ");
            }
        } else {
            if (!isGrant) {
                loginStay();
            }
            Log.e(TAG, "23 below version");
        }
    }
    private void loginStay() {
       /* Animation slider_up = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_in_left);
        binding.ivImg.startAnimation(slider_up);*/
        new Handler().postDelayed(() -> {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }, SPLASH_TIME);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==NUMBERS_REQUEST_CODE) {
            ArrayList<String> _arPermission = new ArrayList<>();
            if (grantResults.length > 0) {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] != 0) {
                        _arPermission.add(Const.EMPTY + grantResults[i]);
                    }
                }
                if (_arPermission.size() == 0) {
                    isGrant=true;
                    loginStay();
                    Log.e(TAG, "onRequestPermissionsResult: Granted");
                } else {
                    Helper.showPermissionDialog(MainActivity.this, "Phone Number permission necessary", "These Permissions required for this app. Go to settings and enable permissions.");
                }
            }
        }
    }
}