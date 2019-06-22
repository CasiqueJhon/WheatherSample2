package Wheather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jhonisaac.wheathersample2.R;

import java.util.ArrayList;

import Adapters.MinutelyWeatherAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MinutelyActivityWeather extends AppCompatActivity {

    @BindView(R.id.minutely_rv) RecyclerView minutelyRecyclerView;
    @BindView(R.id.tv_minute_no_data) TextView tv_minute_no_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minutely_wheather);
        ButterKnife.bind(this);

        ArrayList<Minute> minutes = new ArrayList<>();

        if (minutes != null && !minutes.isEmpty()) {

            minutelyRecyclerView.setVisibility(View.VISIBLE);
            tv_minute_no_data.setVisibility(View.GONE);


            MinutelyWeatherAdapter minutelyWeatherAdapter = new MinutelyWeatherAdapter(this, minutes);

            //Set the RecyclerView as a List.
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

            minutelyRecyclerView.setAdapter(minutelyWeatherAdapter);

            minutelyRecyclerView.setLayoutManager(layoutManager);

        } else {
            minutelyRecyclerView.setVisibility(View.GONE);
            tv_minute_no_data.setVisibility(View.VISIBLE);
        }
    }
}
