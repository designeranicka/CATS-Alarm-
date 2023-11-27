package com.example.felixalarm.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.felixalarm.R;
import com.example.felixalarm.skytechdevAPI.LocationUtils;
import com.example.felixalarm.skytechdevAPI.WeatherData;
import com.example.felixalarm.skytechdevAPI.WeatherUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.text.DecimalFormat;
import pl.droidsonroids.gif.GifImageView;

public class WeatherActivity extends AppCompatActivity implements LocationUtils.OnLocationListener, WeatherUtils.OnWeatherListener {

    private EditText inputCity;
    private TextView cityNameText, temperatureText, conditionText, humidityText, pressureText, windSpeedText;
    private ImageView iconImage;
    private GifImageView gifBack;
    ImageView settingsImage;
    Button btGifs, btImages;

    String descriptionT;
    boolean isThemeChanged = false;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        LocationUtils.getCurrentCity(getApplicationContext(), this);

        inputCity = findViewById(R.id.inputCity);
        cityNameText = findViewById(R.id.textCityName);
        temperatureText = findViewById(R.id.textTemperature);
        conditionText = findViewById(R.id.textCondition);
        iconImage = findViewById(R.id.imageIcon);
        gifBack = findViewById(R.id.gifback);
        humidityText = findViewById(R.id.textHumidity);
        pressureText = findViewById(R.id.textPressure);
        windSpeedText = findViewById(R.id.textWindSpeed);
        settingsImage = findViewById(R.id.imageSettings);
        btGifs = findViewById(R.id.btGifs);
        btImages = findViewById(R.id.btImages);

        Intent intent = getIntent();
        boolean isOpened = intent.getBooleanExtra("flag", false);

        if (isOpened) {
            Intent i = new Intent(getApplicationContext(), AlarmOnActivity.class);
            startActivity(i);
        }

        settingsImage.setOnClickListener(v -> {

            findViewById(R.id.layoutPopUpWindow).setVisibility(View.VISIBLE);

            btGifs.setOnClickListener(v12 -> {
                gifBack.setVisibility(View.VISIBLE);
                switch (descriptionT) {
                    case "clear sky": // сонячно
                        gifBack.setImageResource(R.drawable.sun_clouds);
                        break;
                    case "few clouds": // декілька хмар
                        gifBack.setImageResource(R.drawable.sky_clouds);
                        break;
                    case "scattered clouds": // розсіяні хмари
                    case "broken clouds": // похмуро
                        gifBack.setImageResource(R.drawable.half_gray_clouds); //текстура half gray clouds кривая
                        break;
                    case "overcast clouds": // похмуро
                        gifBack.setImageResource(R.drawable.gray_clouds);//overcast n broker have same icon
                        break;
                    case "light rain": // легкий дощ
                        gifBack.setImageResource(R.drawable.raindrops);
                        break;
                    case "moderate rain": // помірний дощ
                        gifBack.setImageResource(R.drawable.hard_rain);
                        break;
                }
                isThemeChanged = false;
                findViewById(R.id.layoutPopUpWindow).setVisibility(View.INVISIBLE);
            });
            btImages.setOnClickListener(v1 -> {
                gifBack.setVisibility(View.VISIBLE);
                gifBack.setAlpha(0.9F);
                switch (descriptionT) {
                    case "clear sky":
                        gifBack.setImageResource(R.drawable.sunny_w);
                        gifBack.setAlpha(0.5F);
                        break;
                    case "few clouds": // декілька хмар
                    case "scattered clouds":  // розсіяні хмари
                    case "broken clouds": // похмуро
                    case "overcast clouds": // похмуро
                        gifBack.setImageResource(R.drawable.cloudy_w);
                        break;
                    case "light rain": // легкий дощ
                    case "moderate rain": // помірний дощ
                        gifBack.setImageResource(R.drawable.snowy_w);
                        break;
                }
                isThemeChanged = true;
                findViewById(R.id.layoutPopUpWindow).setVisibility(View.INVISIBLE);
            });

        });

        inputCity.setOnEditorActionListener((textView, i, keyEvent) -> {
            getWeather(inputCity.getText().toString().trim());
            return false;
        });

        inputCity.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                getWeather(inputCity.getText().toString().trim());
                return true;
            }
            return false;
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_weather);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_alarm:
                    Intent intent1 = new Intent(getApplicationContext(), AlarmActivity.class);
                    intent1.putExtra("description", descriptionT);
                    intent1.putExtra("theme", isThemeChanged);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.l_reciever, R.anim.l_anim);
                    finish();
                    return true;
                case R.id.nav_notes:
                    Intent intent2 = new Intent(getApplicationContext(), NotesActivity.class);
                    intent2.putExtra("description", descriptionT);
                    intent2.putExtra("theme", isThemeChanged);
                    startActivity(intent2);
                    overridePendingTransition(R.anim.l_reciever, R.anim.l_anim);
                    finish();
                    return true;
                case R.id.nav_weather:
                    return true;
            }
            return false;
        });
    }

    public void getWeather(String city) {
        WeatherUtils.searchWeather (city, this, getApplicationContext());
    }

    @Override
    public void onLocationReceived(String city) {
        inputCity.setText(city);
        if (city != null && !city.isEmpty()) getWeather(city);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onWeatherReceived(WeatherData weatherData) {
        cityNameText.setText(weatherData.getCityName());

        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String tempT = decimalFormat.format(weatherData.getTemp());
        temperatureText.setText(tempT + " °C");
        conditionText.setText(weatherData.getDescription());
        String humidityT=String.valueOf(weatherData.getHumidity());
        humidityText.setText("Humidity: " + humidityT + " %");
        String pressureT = String.valueOf(weatherData.getPressure());
        pressureText.setText("Pressure: " + pressureT + " Pa");
        windSpeedText.setText("Wind speed: " + weatherData.getWind() + " m/s");

        gifBack.setVisibility(View.VISIBLE);
        switch (weatherData.getDescription()) {
            case "clear sky":
                iconImage.setImageResource(R.drawable.sun);
                gifBack.setImageResource(R.drawable.sun_clouds);
                break;
            case "few clouds": //серые тучки, но с солнышком кек
                iconImage.setImageResource(R.drawable.cloudy);
                gifBack.setImageResource(R.drawable.sky_clouds);
                break;
            case "scattered clouds":  //серые тучки без грозы(без черых тучек)
            case "broken clouds":
                iconImage.setImageResource(R.drawable.cloud);
                gifBack.setImageResource(R.drawable.half_gray_clouds); //текстура half gray clouds кривая
                break;
            case "overcast clouds": //с черными тучками!
                iconImage.setImageResource(R.drawable.cloud_black);
                gifBack.setImageResource(R.drawable.gray_clouds);//overcast n broker have same icon
                break;
            case "light rain": //слабенький дождь
                iconImage.setImageResource(R.drawable.cloudy_rain);
                gifBack.setImageResource(R.drawable.raindrops);
                break;
            case "moderate rain": //умеренный дождь
                iconImage.setImageResource(R.drawable.rainy);
                gifBack.setImageResource(R.drawable.hard_rain);
                break;
        }
    }
}
