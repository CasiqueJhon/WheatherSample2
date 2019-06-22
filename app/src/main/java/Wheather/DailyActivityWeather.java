package Wheather;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import com.jhonisaac.wheathersample2.R;

import java.util.ArrayList;

import Adapters.DailyWeatherAdapter;

public class DailyActivityWeather extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_wheather);

        Intent intent = getIntent();
        //Getting the ArrayList from intent from WeatherActivity.
        ArrayList<Day> days = intent.getParcelableArrayListExtra(WeatherActivity.DAYS_ARRAY_LIST);
        DailyWeatherAdapter dailyWeatherAdapter = new DailyWeatherAdapter(this, days);
        setListAdapter(dailyWeatherAdapter);
    }
}
