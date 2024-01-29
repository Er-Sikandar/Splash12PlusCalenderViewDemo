package com.example.simdetactapp.utils;

import android.Manifest;

public interface Const {
    int SPLASH_TIME=3000;
    String EMPTY="";
    String SIM_FIRST="sim_first";
    String SIM_SECOND="sim_second";


    String[] permissionsRequired = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_PHONE_NUMBERS,
    };

    int NUMBERS_REQUEST_CODE=222;
}
