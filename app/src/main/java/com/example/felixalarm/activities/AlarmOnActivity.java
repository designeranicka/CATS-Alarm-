package com.example.felixalarm.activities;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.felixalarm.R;
import com.example.felixalarm.skytechdevAPI.LocationUtils;
import com.example.felixalarm.skytechdevAPI.WeatherData;
import com.example.felixalarm.skytechdevAPI.WeatherUtils;

import pl.droidsonroids.gif.GifImageView;

public class AlarmOnActivity extends AppCompatActivity implements LocationUtils.OnLocationListener, WeatherUtils.OnWeatherListener {
    Ringtone ringtone;
    GifImageView gifBack;
    private ImageView buttonAlarmOff;
    private ImageView bg;
    TextView textTime;
    int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_on);
        buttonAlarmOff = findViewById(R.id.button_alarm_off);
        bg = findViewById(R.id.felix);
        textTime = findViewById(R.id.textView2);
        gifBack = findViewById(R.id.gifBackOnAlarm);

        LocationUtils.getCurrentCity(getApplicationContext(), this);

        Intent i = getIntent();
        int notesAmount = i.getIntExtra("size",0);

        Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone =RingtoneManager.getRingtone(this,notificationUri);

        if (ringtone == null) {
            notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            ringtone =RingtoneManager.getRingtone(this,notificationUri);
        }

        if (ringtone != null) ringtone.play();

        buttonAlarmOff.setOnClickListener(v -> {
            count += 1;

            if (count < 10) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_translate);
                buttonAlarmOff.startAnimation(anim);

                final float xMin = 300, xMax = 800;
                final float yMin = 800, yMax = 1100;
                buttonAlarmOff.setX((float) Math.round( (xMin + Math.random() * (xMax - xMin) ) ));
                buttonAlarmOff.setY((float) Math.round( (yMin + Math.random() * (yMax - yMin) ) ));

                Animation anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_translate2);
                buttonAlarmOff.startAnimation(anim2);
            } else {
                ringtone.stop();

                Intent i1 = new Intent(AlarmOnActivity.this, AlarmActivity.class);
                setContentView(R.layout.activity_notes);

                String text = "It's your notes! Don't forget to read them!";
                Toast.makeText(AlarmOnActivity.this, text, Toast.LENGTH_LONG).show();
                startActivity(i1);

                if (notesAmount != 0) {
                    i1 = new Intent(AlarmOnActivity.this, NotesActivity.class);
                    text = "Now you have " + notesAmount + " notes! Dont forget to read them!";
                    Toast.makeText(AlarmOnActivity.this, text, Toast.LENGTH_LONG).show();
                } else {
                    i1 = new Intent(AlarmOnActivity.this, AlarmActivity.class);
                }
                startActivity(i1);
            }
        });
    }

    @Override
    public void onLocationReceived(String city) {
        WeatherUtils.searchWeather (city, this, getApplicationContext());
    }

    @Override
    public void onWeatherReceived(WeatherData weatherData) {
        switch (weatherData.getDescription()) {
            case "clear sky": // Сонячно
                bg.setImageResource(R.drawable.sunny_w);
                buttonAlarmOff.setImageResource(R.drawable.sunny_w);
                break;
            case "few clouds": // Похмуро
            case "scattered clouds": // Похмуро
            case "broken clouds": // Похмуро
            case "overcast clouds": // Похмуро
                bg.setImageResource(R.drawable.__clouds_bg);
                buttonAlarmOff.setImageResource(R.drawable.__clouds_but);
                break;
            case "light rain": // Дощ
            case "moderate rain": // Дощ
                bg.setImageResource(R.drawable.__rain_bg);
                buttonAlarmOff.setImageResource(R.drawable.__rain_but);
                break;
            case "snowy": // Сніжок
                bg.setImageResource(R.drawable.__snow_bg);
                buttonAlarmOff.setImageResource(R.drawable.__snow_but);
                break;
        }
    }
}