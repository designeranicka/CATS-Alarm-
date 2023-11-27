package com.example.felixalarm.skytechdevAPI;

public class WeatherData {
    private final double temp;
    private final float pressure;
    private final int humidity;
    private final String description, wind, clouds, cityName;

    public double getTemp() { return temp; }
    public float getPressure() { return pressure; }
    public int getHumidity() { return humidity; }
    public String getDescription() { return description; }
    public String getWind() { return wind; }
    public String getClouds() { return clouds; }
    public String getCityName() { return cityName; }

    public WeatherData(double temp, float pressure, int humidity, String description, String wind, String clouds, String cityName) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.description = description;
        this.wind = wind;
        this.clouds = clouds;
        this.cityName = cityName;
    }
}
