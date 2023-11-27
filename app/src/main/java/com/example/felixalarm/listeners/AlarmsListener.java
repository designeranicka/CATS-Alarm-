package com.example.felixalarm.listeners;
import android.view.View;

public interface AlarmsListener {
    static void onAlarmClicked(String s, int position) {
    }

    void onAlarmClicked(View alarm, int position);

}
