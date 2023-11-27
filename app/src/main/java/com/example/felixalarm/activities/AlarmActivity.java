package com.example.felixalarm.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.felixalarm.R;
import com.example.felixalarm.skytechdevAPI.LocationUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AlarmActivity extends AppCompatActivity implements LocationUtils.OnLocationListener{

    private static final int OVERLAY_PERMISSION_CODE = 1234;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        // Проверяем, есть ли у нас разрешение
        if (checkLocationPermission()) {
            LocationUtils.getCurrentCity(getApplicationContext(), this);
        } else {
            requestLocationPermission();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_CODE);
            }
        }

        SimpleDateFormat sdf =new SimpleDateFormat("HH:mm", Locale.getDefault());

        ImageView imageAddNewAlarm = findViewById(R.id.imageAddAlarm);
        imageAddNewAlarm.setOnClickListener( v -> {
            MaterialTimePicker materialTimePicker=new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(12)
                    .setMinute(0)
                    .build();

            materialTimePicker.addOnPositiveButtonClickListener(v1 -> {
                Calendar calendar=Calendar.getInstance();
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);
                calendar.set(Calendar.MINUTE, materialTimePicker.getMinute());
                calendar.set(Calendar.HOUR_OF_DAY, materialTimePicker.getHour());

                AlarmManager alarmManager =(AlarmManager) getSystemService(Context.ALARM_SERVICE);

                AlarmManager.AlarmClockInfo alarmClockInfo= new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(),getAlarmInfoPendingIntent());
                Toast.makeText(this,"alarm clock set on "+sdf.format(calendar.getTime()), Toast.LENGTH_LONG).show();

                alarmManager.setAlarmClock(alarmClockInfo, getAlarmActionPendingIntent());

            });

            materialTimePicker.show(getSupportFragmentManager(),"my_picker");


        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_alarm);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_alarm:
                    return true;
                case R.id.nav_notes:
                    Intent intent1 = new Intent(getApplicationContext(), NotesActivity.class);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.r_reciever, R.anim.r_anim);
                    finish();
                    return true;
                case R.id.nav_weather:
                    Intent intent = new Intent(getApplicationContext(), WeatherActivity.class);
                    overridePendingTransition(R.anim.r_reciever, R.anim.r_anim);
                    startActivity(intent);
                    finish();
                    return true;
            }
            return false;
        });
    }
    @SuppressLint("UnspecifiedImmutableFlag")
    private PendingIntent getAlarmInfoPendingIntent() {
        Intent alarmInfoIntent = new Intent(this, AlarmActivity.class);
        alarmInfoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(this, 0, alarmInfoIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private PendingIntent getAlarmActionPendingIntent() {
        Intent intent = new Intent(this, AlarmOnActivity2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onLocationReceived(String city) {
        TextView tw = findViewById(R.id.cityText);
        tw.setText(city);
        if (city.isEmpty()) {
            tw.setText("Failed to load city");
        }
    }

    private boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            // Проверяем, предоставил ли пользователь разрешение
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LocationUtils.getCurrentCity(getApplicationContext(), this);
            } else {
                Toast.makeText(AlarmActivity.this, "No location data!", Toast.LENGTH_LONG).show();
            }
        }
    }
}