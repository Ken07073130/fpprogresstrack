package com.example.fpprogresstrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.fpprogresstrack.db.Weather;

public class WeatherEditActivity extends AppCompatActivity {
    Weather weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_edit);
        Intent intent=getIntent();
        weather=(Weather) intent.getSerializableExtra("serialWeather");
        //Log.d("WeatherEdit",weather.getNow());
        EditText edtDate= findViewById(R.id.edt_date);
        EditText edtNowWeather= findViewById(R.id.edt_now_weather);
        edtDate.setText(weather.getDate());
        edtNowWeather.setText(weather.getNow());
    }
}