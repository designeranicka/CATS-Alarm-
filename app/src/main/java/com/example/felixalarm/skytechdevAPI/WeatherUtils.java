package com.example.felixalarm.skytechdevAPI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherUtils {
    public interface OnWeatherListener {
        void onWeatherReceived(WeatherData weatherData);
    }

    public static void searchWeather(String city, OnWeatherListener listener, Context context) {
        String tempUrl = "";
        if (city.equals(""))  return;
        else {
            String url = "https://api.openweathermap.org/data/2.5/weather";
            String appid = "d2d4dc6c3e99f743a99857ee56a2e875";
            tempUrl = url + "?q=" + city + "&appid=" + appid;
        }

        @SuppressLint("LongLogTag")
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, response -> {
            Log.d("response", response);

            try {
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray jsonArray = jsonResponse.getJSONArray("weather");

                JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                String description = jsonObjectWeather.getString("description");

                JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                double temp = jsonObjectMain.getDouble("temp") - 273.15;
                float pressure = jsonObjectMain.getInt("pressure");
                int humidity = jsonObjectMain.getInt("humidity");

                JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                String wind = jsonObjectWind.getString("speed");

                JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                String clouds = jsonObjectClouds.getString("all");

                jsonResponse.getJSONObject("sys");
                String cityName = jsonResponse.getString("name");

                listener.onWeatherReceived(new WeatherData(temp, pressure, humidity, description, wind, clouds, cityName));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.e("### PARSING WEATHER ERROR", String.valueOf(error)));

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
