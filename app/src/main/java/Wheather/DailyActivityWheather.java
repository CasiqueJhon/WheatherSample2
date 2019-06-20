package Wheather;

import android.app.ListActivity;
import android.os.Bundle;

import com.jhonisaac.wheathersample2.R;

import java.util.ArrayList;

import Adapters.DailyWeatherAdapter;

public class DailyActivityWheather extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_wheather);

        ArrayList<Day> days = new ArrayList<>();

        for (int i = 0; i<500; i++) {

            Day day = new Day();

            day.setDayName("Monday");
            day.setDayDescription("Rain");
            day.setRainProbability("100%");

            days.add(day);
        }

        DailyWeatherAdapter dailyWeatherAdapter = new DailyWeatherAdapter(this, days);

        setListAdapter(dailyWeatherAdapter);


    }
}
