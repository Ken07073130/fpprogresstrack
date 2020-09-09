package com.example.fpprogresstrack;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fpprogresstrack.db.Weather;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private List<Weather> mWeatherList;
    private Context mContext;

    public WeatherAdapter(List<Weather> weatherList){
        mWeatherList=weatherList;
    }

    //初始化的时候用
    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivWeather;
        private TextView tvDate;
        private TextView tvNowWeather;
        public ViewHolder(View itemView) {
            super(itemView);
            ivWeather=itemView.findViewById(R.id.image_weather);
            tvDate=itemView.findViewById(R.id.text_date);
            tvNowWeather=itemView.findViewById(R.id.text_now_weather);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item,parent,false);
        mContext=parent.getContext();
        final ViewHolder holder=new ViewHolder(view);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                Weather weather=mWeatherList.get(position);
                Intent intent=new Intent(mContext,WeatherEditActivity.class);
                intent.putExtra("serialWeather",(Serializable) weather);
                mContext.startActivity(intent);
                //Log.d("adapter",weather.getDate());

            }
        });
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Weather weather=mWeatherList.get(position);
        holder.ivWeather.setImageResource(weather.getImageId());
        holder.tvNowWeather.setText(weather.getNow());
        holder.tvDate.setText(weather.getDate());
    }

    @Override
    public int getItemCount() {
        return mWeatherList.size();
    }



}
