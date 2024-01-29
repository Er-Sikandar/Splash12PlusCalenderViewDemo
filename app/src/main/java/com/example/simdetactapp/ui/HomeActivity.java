package com.example.simdetactapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.simdetactapp.CalListAdapter;
import com.example.simdetactapp.databinding.ActivityHomeBinding;
import com.example.simdetactapp.models.AttCalModel;
import com.example.simdetactapp.utils.Helper;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements CalListAdapter.SingleClickCalDayListener {
private String TAG="HomeActivity";
private ActivityHomeBinding binding;
    private GregorianCalendar cal_month;
    private String curDate="",dateFormatApi = "";
    private CalListAdapter attCalAdapter;
    private List<AttCalModel> attCalLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        curDate = Helper.getCurrentDate();


        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        dateFormatApi = Helper.calendarFormatDate(cal_month.getTime(), "dd-MMM-yyyy");
        binding.tvMonth.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));

        attCalLists.clear();
        attCalLists.add(new AttCalModel("2024-01-20","Absend","Day"));
        attCalLists.add(new AttCalModel("2024-01-22","Absend","Day"));
        attCalLists.add(new AttCalModel("2024-01-25","Absend","Day"));
        attCalLists.add(new AttCalModel("2024-01-26","Absend","Day"));
        attCalLists.add(new AttCalModel("2024-01-28","Absend","Day"));

        binding.recMonth.setLayoutManager(new GridLayoutManager(this, 7, GridLayoutManager.VERTICAL, false));
        attCalAdapter = new CalListAdapter(HomeActivity.this,curDate, cal_month,attCalLists,this);
        binding.recMonth.setAdapter(attCalAdapter);



        binding.ibPrev.setOnClickListener(v -> {
            setPreviousMonth();


        });
        binding.IbNext.setOnClickListener(v -> {

            setNextMonth();

        });
    }
    public void setPreviousMonth() {
        cal_month.set(GregorianCalendar.MONTH, cal_month.get(GregorianCalendar.MONTH) - 1);
        refreshCalendar();
        binding.tvMonth.setText(Helper.calendarFormatDate(cal_month.getTime(), "MMMM yyyy"));
        dateFormatApi = Helper.calendarFormatDate(cal_month.getTime(), "dd-MMM-yyyy");
        Log.e(TAG, "Pre: " + dateFormatApi);
    }

    public void setNextMonth() {
        cal_month.set(GregorianCalendar.MONTH, cal_month.get(GregorianCalendar.MONTH) + 1);
        refreshCalendar();
        binding.tvMonth.setText(Helper.calendarFormatDate(cal_month.getTime(), "MMMM yyyy"));
        dateFormatApi = Helper.calendarFormatDate(cal_month.getTime(), "dd-MMM-yyyy");
        Log.e(TAG, "Next: " + dateFormatApi);

    }
    public void refreshCalendar() {
        attCalAdapter.refreshDays();
        attCalAdapter.notifyDataSetChanged();
        binding.tvMonth.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
    }



    @Override
    public void onSingleDayClick(View view, int position, String colorCode, AttCalModel attCalModel) {
        Log.e(TAG, "onSingleDayClick: "+attCalModel.getAttendancedate());
    }
}