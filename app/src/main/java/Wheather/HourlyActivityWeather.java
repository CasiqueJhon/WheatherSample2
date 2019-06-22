package Wheather;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import com.jhonisaac.wheathersample2.R;

import java.util.ArrayList;

import Adapters.HourlyWeatherAdapter;

public class HourlyActivityWeather extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_wheather);

        Intent intent = getIntent();
        ArrayList<Hour> hours = intent.getParcelableArrayListExtra(WeatherActivity.HOURS_ARRAY_LIST);
        HourlyWeatherAdapter hourlyWeatherAdapter = new HourlyWeatherAdapter(this, hours);
        setListAdapter(hourlyWeatherAdapter);
    }
}
