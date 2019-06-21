package Wheather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jhonisaac.wheathersample2.R;

import java.util.ArrayList;

import Adapters.MinutelyWeatherAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MinutelyActivityWheather extends AppCompatActivity {

    @BindView(R.id.minutely_rv) RecyclerView minutelyRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minutely_wheather);
        ButterKnife.bind(this);

        ArrayList<Minute> minutes = new ArrayList<>();

        for (int i=0; i<100; i++) {
            Minute minute = new Minute();

            minute.setMinuteWeather("19:55");
            minute.setMinuteDescription("Rain probability: 0%");

            minutes.add(minute);
        }

        MinutelyWeatherAdapter minutelyWeatherAdapter = new MinutelyWeatherAdapter(this, minutes);

        //Set the RecyclerView as a List.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        minutelyRecyclerView.setAdapter(minutelyWeatherAdapter);

        minutelyRecyclerView.setLayoutManager(layoutManager);
    }
}
