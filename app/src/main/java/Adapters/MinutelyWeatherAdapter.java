package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhonisaac.wheathersample2.R;

import java.util.ArrayList;

import Wheather.Minute;

public class MinutelyWeatherAdapter extends RecyclerView.Adapter {

    private ArrayList<Minute> mMinutes;
    private Context mContext;

    public MinutelyWeatherAdapter(Context context, ArrayList<Minute> minutes) {
        this.mContext = context;
        this.mMinutes = minutes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.minutely_list_item, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((ViewHolder)viewHolder).onBind(position);
    }

    @Override
    public int getItemCount() {
        return mMinutes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvMinutes;
            TextView tvMinutesDescription;

        public ViewHolder(@NonNull View view) {
            super(view);

            tvMinutes = view.findViewById(R.id.tv_minutes);
            tvMinutesDescription = view.findViewById(R.id.tv_minutes_description);

        }

        public void onBind(int position) {
            Minute minute = mMinutes.get(position);

            tvMinutes.setText(minute.getMinuteWeather());
            tvMinutesDescription.setText(minute.getMinuteDescription());
        }
    }

}
