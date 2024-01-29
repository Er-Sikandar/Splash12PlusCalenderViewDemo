package com.example.simdetactapp.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.simdetactapp.databinding.ShowValidationErrorBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Helper {
    public static void showValidationErrorDialog(Activity activity, String msg) {
        AlertDialog.Builder alertBuild = new AlertDialog.Builder(activity);
        ShowValidationErrorBinding validationErrorBinding=ShowValidationErrorBinding.inflate(activity.getLayoutInflater());
        alertBuild.setView(validationErrorBinding.getRoot());
        validationErrorBinding.tvTitle.setText(msg);
        final AlertDialog dialog = alertBuild.create();
        validationErrorBinding.tvOk.setOnClickListener(v -> dialog.dismiss());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    //Todo permission
    public static boolean checkPermissions(Activity context, String[] permissions,int permissionRequestCode){
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            int result = ContextCompat.checkSelfPermission(context, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),permissionRequestCode );
            return false;
        }
        return true;
    }
    public static void showPermissionDialog(Activity context, String title, String msg) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(false);
        alertBuilder.setTitle(title);
        alertBuilder.setMessage(msg);
        alertBuilder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            dialog.dismiss();
            Intent dialogIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            dialogIntent.setData(uri);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(dialogIntent);

        });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    public static String calendarFormatDate(Date date, String dateFormat) {
        SimpleDateFormat sdf = simpleDateFormat(dateFormat);
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat simpleDateFormat(String dateFormat) {
        SimpleDateFormat sdf = null;
        if (sdf != null) {
            return sdf;
        } else {
            return new SimpleDateFormat(dateFormat, Locale.getDefault());
        }
    }
    public static String getCurrentDate() {
        String currentDate = "";
        try {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = simpleDateFormat("yyyy-MM-dd");
            currentDate = df.format(c);
        } catch (Exception e) {
            System.out.print(e);
        }
        return currentDate;
    }

}
