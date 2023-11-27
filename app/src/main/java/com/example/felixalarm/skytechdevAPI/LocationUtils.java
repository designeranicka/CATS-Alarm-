package com.example.felixalarm.skytechdevAPI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationUtils {

    public interface OnLocationListener {
        void onLocationReceived(String city);
    }

    public static void getCurrentCity(Context context, OnLocationListener listener) {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    stopLocationUpdates(fusedLocationProviderClient, this);
                    String city = getCityFromLocation(context, location.getLatitude(), location.getLongitude());
                    if (listener != null) {
                        listener.onLocationReceived(city);
                    }
                }
            }
        };

        requestLocationUpdates(context, fusedLocationProviderClient, locationCallback);
    }

    @SuppressLint("MissingPermission")
    private static void requestLocationUpdates(Context context, FusedLocationProviderClient fusedLocationProviderClient, LocationCallback locationCallback) {
        LocationRequest locationRequest = createLocationRequest();
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private static void stopLocationUpdates(FusedLocationProviderClient fusedLocationProviderClient, LocationCallback locationCallback) {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private static LocationRequest createLocationRequest() {
        return new LocationRequest()
                .setInterval(10000)
                .setFastestInterval(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private static String getCityFromLocation(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                String city = addresses.get(0).getLocality();
                if (city != null) {
                    return city;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Если не удалось получить город
    }
}
