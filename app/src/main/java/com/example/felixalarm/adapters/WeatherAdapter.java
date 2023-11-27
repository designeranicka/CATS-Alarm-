package com.example.felixalarm.adapters;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.felixalarm.R;
import com.example.felixalarm.entities.Note;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<WeatherModal> weather;

    public WeatherAdapter(List<WeatherModal> weather) {
        this.weather = weather;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_weather, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

//        TextView name = holder.itemView.findViewById(R.id.textCityName);
//        name.setText();
    }

    @Override
    public int getItemCount() {
        return weather.size();
    }

    static class WeatherViewHolder extends RecyclerView.ViewHolder {

        TextView textTime, textTemperature, textWind;
        CardView layoutWeather;
        ImageView imageIcon;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            textTime = itemView.findViewById(R.id.textTime);
            textTemperature = itemView.findViewById(R.id.textTemperature);
            textWind = itemView.findViewById(R.id.textWindSpeed);
            layoutWeather = itemView.findViewById(R.id.layoutWeather);
            imageIcon = itemView.findViewById(R.id.imageIcon);
        }

        void setWeather(WeatherModal weather) {
            textTime.setText(weather.getTime());
            textTemperature.setText(weather.getTemperature());
            textWind.setText(weather.getWindSpeed());

//            GradientDrawable gradientDrawable = (GradientDrawable) layoutWeather.getBackground();
//            gradientDrawable.setColor(Color.parseColor("#A15EC5"));

//            imageIcon.setImageBitmap(BitmapFactory.decodeFile(weather.getImagePath()));
            }
        }
    }



