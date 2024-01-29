package com.example.simdetactapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttCalModel {
    @SerializedName("ATTENDANCEDATE")
    @Expose
    private String attendancedate;
    @SerializedName("ATTENDANCESTATUS")
    @Expose
    private String attendancestatus;
    @SerializedName("ATTENDANCETOOLTIP")
    @Expose
    private String attendancetooltip;


    public AttCalModel(String attendancedate, String attendancestatus, String attendancetooltip) {
        this.attendancedate = attendancedate;
        this.attendancestatus = attendancestatus;
        this.attendancetooltip = attendancetooltip;
    }


    public String getAttendancedate() {
        return attendancedate;
    }

    public String getAttendancestatus() {
        return attendancestatus;
    }

    public String getAttendancetooltip() {
        return attendancetooltip;
    }

}
