package com.example.simdetactapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simdetactapp.databinding.CalItemBinding;
import com.example.simdetactapp.models.AttCalModel;
import com.example.simdetactapp.utils.Helper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class CalListAdapter extends RecyclerView.Adapter<CalListAdapter.ViewHolder> {
    private Activity context;
    private Calendar month;
    public GregorianCalendar pmonth;
    public GregorianCalendar pmonthmaxset;
    int firstDay;
    int maxWeeknumber;
    int maxP;
    int calMaxP;
    String itemvalue,curentDateString;
    public static List<String> day_string;
    public List<AttCalModel>  date_collection_arr;
    private String gridvalue;
    private SingleClickCalDayListener singleClickCalDayListener;
    private int selectedPos=-1;
    private Boolean isCureDay=false;

    public CalListAdapter(Activity context, String curDate, Calendar month, List<AttCalModel> date_collection_arr, SingleClickCalDayListener singleClickDayListener) {
        day_string = new ArrayList<String>();
        this.context = context;
        this.curentDateString=curDate;
        this.month = month;
        this.date_collection_arr=date_collection_arr;
        this.singleClickCalDayListener=singleClickDayListener;
        this.month.set(GregorianCalendar.DAY_OF_MONTH, 1);
        refreshDays();
    }

    @NonNull
    @Override
    public CalListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(CalItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @SuppressLint({"UseCompatLoadingForDrawables","RecyclerView"})
    @Override
    public void onBindViewHolder(@NonNull CalListAdapter.ViewHolder holder, int position) {

        String[] separatedTime = day_string.get(position).split("-");
        gridvalue = separatedTime[2].replaceFirst("^0*", "");
        if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
            holder.binding.date.setBackground(context.getResources().getDrawable(R.drawable.circle_white));
            holder.binding.date.setText("");
            holder.binding.date.setClickable(false);
            holder.binding.date.setFocusable(false);
        } else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
            holder.binding.date.setBackground(context.getResources().getDrawable(R.drawable.circle_white));
            holder.binding.date.setText("");
            holder.binding.date.setClickable(false);
            holder.binding.date.setFocusable(false);
        } else {
            holder.binding.date.setSolidColor(context.getResources().getColor(R.color.grey));
            holder.binding.date.setText(gridvalue);
            holder.binding.date.setClickable(true);
            holder.binding.date.setFocusable(true);
            //todo gg
            int len=date_collection_arr.size();
            for (int i = 0; i < len; i++) {
                AttCalModel cal_obj = date_collection_arr.get(i);
                String date = cal_obj.getAttendancedate();
                if (day_string.get(position).equals(date)) {
                    holder.binding.date.setSolidColor(context.getResources().getColor(R.color.app_color));
                }
            }
            if (day_string.get(position).equals(curentDateString)) {
                if (!isCureDay) {
                    selectedPos=position;
                    isCureDay = true;
                    holder.binding.date.setBackground(context.getResources().getDrawable(R.drawable.curday_calendar));
                }
            }else {
                holder.binding.date.setBackgroundColor(Color.TRANSPARENT);
            }
            if(selectedPos==position){
                holder.binding.date.setBackground(context.getResources().getDrawable(R.drawable.curday_calendar));
            }else{
                holder.binding.date.setBackgroundColor(Color.TRANSPARENT);

            }

            Log.e("TAG", "onBindViewHolder: "+position);
        }


        holder.binding.date.setOnClickListener(v1 -> {
            int len=date_collection_arr.size();
            for (int i = 0; i < len; i++) {
                AttCalModel cal_obj = date_collection_arr.get(i);
                String date = cal_obj.getAttendancedate();
                if (day_string.get(position).equals(date)) {
                    singleClickCalDayListener.onSingleDayClick(v1,position,"#E91E63",cal_obj);
                    notifyItemChanged(selectedPos);
                    selectedPos=position;
                    notifyItemChanged(selectedPos);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return day_string.size();
    }


    public void refreshDays() {
        isCureDay=false;
        selectedPos=-1;
        day_string.clear();
        pmonth = (GregorianCalendar) month.clone();
        firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
        maxWeeknumber = getMaxWeekNumber(pmonth);
        maxP = getMaxP();
        calMaxP = maxP - (firstDay - 1);
        pmonthmaxset = (GregorianCalendar) pmonth.clone();
        pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);
        for (int n = 0; n < maxWeeknumber * 7; n++) {
            itemvalue = Helper.calendarFormatDate(pmonthmaxset.getTime(), "yyyy-MM-dd");
            pmonthmaxset.add(GregorianCalendar.DATE, 1);
            day_string.add(itemvalue);
        }
    }
    private int getMaxWeekNumber(GregorianCalendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int totalWeeks = (int) Math.ceil((maxDayOfMonth + firstDayOfWeek - 1) / 7.0);
        return totalWeeks;
    }
    private int getMaxP() {
        int maxP;
        if (month.get(GregorianCalendar.MONTH) == month.getActualMinimum(GregorianCalendar.MONTH)) {
            pmonth.set((month.get(GregorianCalendar.YEAR) - 1), month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            pmonth.set(GregorianCalendar.MONTH, month.get(GregorianCalendar.MONTH) - 1);
        }
        maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        return maxP;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CalItemBinding binding;
        public ViewHolder(@NonNull CalItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    public interface SingleClickCalDayListener {
        void onSingleDayClick(View view, int position, String colorCode, AttCalModel attCalModel);
    }
}