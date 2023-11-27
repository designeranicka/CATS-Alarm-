package com.example.felixalarm.activities;

import android.app.Application;
//
//import com.example.felixalarm..Alarm;

import com.example.felixalarm.entities.Alarm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlarmListApplication extends Application {
    public static int nextId=2;
    public static List<Alarm> alarmList= new ArrayList<>();


    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        AlarmListApplication.nextId = nextId;
    }

    public static List<Alarm> getAlarmList() {
        return alarmList;
    }

    public static void setAlarmList(List<Alarm> alarmList) {
        AlarmListApplication.alarmList = alarmList;
    }
}
