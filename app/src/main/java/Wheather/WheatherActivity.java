package Wheather;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jhonisaac.wheathersample2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import Root.CurrentWeather;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WheatherActivity extends AppCompatActivity {

    public static final String URL_REQUEST = "https://api.darksky.net/forecast/29ac2f86be434312999df32b81cc69b4/40.251025,-3.702012?units=si";
    public static final String DATA = "data";
    public static final String SUMMARY = "summary";
    public static final String DAILY = "daily";
    public static final String HUMIDITY = "humidity";
    public static final String WIND_SPEED = "windSpeed";
    public static final String ICON = "icon";
    public static final String TEMPERATURE = "temperature";
    public static final String TEMPERATURE_MAX = "temperatureMax";
    public static final String TEMPERATURE_MIN = "temperatureMin";
    public static final String CURRENTLY = "currently";
    public static final String TIME = "time";
    public static final String HOURLY = "hourly";
    public static final String TIMEZONE = "timezone";
    public static final String PRECIP_PROBABILITY = "precipProbability";
    public static final String DAYS_ARRAY_LIST = "days";
    public static final String HOURS_ARRAY_LIST = "hours_array_list";

    @BindView(R.id.iv_icon) ImageView iconImageView;
    @BindView(R.id.tv_temp_description) TextView tv_description_temp;
    @BindView(R.id.tv_current_temp) TextView tv_current_temp;
    @BindView(R.id.tv_max_temp) TextView tv_max_temp;
    @BindView(R.id.tv_min_temp) TextView tv_min_temp;
    @BindDrawable(R.drawable.clear_night) Drawable clearNight;
    @BindView(R.id.tv_humidity) TextView humidity;
    @BindView(R.id.tv_wind) TextView windSpeed;

    private ArrayList<Day> days;
    private ArrayList<Hour> hours;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheather);
        ButterKnife.bind(this);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String request = URL_REQUEST;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, request,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            CurrentWeather currentWeather = getCurrentWeather(response);
                            days = getDailyWeatherFromJson(response);
                            hours = getHourlyWeather(response);

                            iconImageView.setImageDrawable(currentWeather.getIconDrawableResource());
                            tv_description_temp.setText(currentWeather.getDescription());
                            tv_current_temp.setText(currentWeather.getCurrentTemperature());
                            tv_max_temp.setText(String.format("Highest: %sº", currentWeather.getMaxTemp()));
                            tv_min_temp.setText(String.format("Lowest: %sº", currentWeather.getMinTemp()));
                            humidity.setText(String.format("Humidity: %s", currentWeather.getHumidity()));
                            windSpeed.setText(String.format("WindSpeed: %s", currentWeather.getWindSpeed()));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WheatherActivity.this, "Connection Error",
                        Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }


    @OnClick(R.id.btn_daily)
    public void dailyWeatherClick() {
        Intent dailyIntent = new Intent(WheatherActivity.this, DailyActivityWheather.class);
        //To put the parcelable content to the dailyWeatherActivity
        dailyIntent.putParcelableArrayListExtra(DAYS_ARRAY_LIST, days);
        startActivity(dailyIntent);

    }

    @OnClick(R.id.btn_hourly)
    public void hourlyListener() {
        Intent hourlyIntent = new Intent(WheatherActivity.this, HourlyActivityWheather.class);
        hourlyIntent.putParcelableArrayListExtra(HOURS_ARRAY_LIST, hours);
        startActivity(hourlyIntent);
    }

    @OnClick(R.id.btn_minutely)
    public void minutelyListener() {
        Intent minutelyIntent = new Intent(WheatherActivity.this, MinutelyActivityWheather.class);
        startActivity(minutelyIntent);
    }

    private CurrentWeather getCurrentWeather(String json) throws JSONException {


        JSONObject jsonObject = new JSONObject(json);
        JSONObject currentlyWeather = jsonObject.getJSONObject(CURRENTLY);
        JSONObject dailyWeather = jsonObject.getJSONObject(DAILY);
        JSONArray dataJsonArray = dailyWeather.getJSONArray(DATA);
        JSONObject dataDaily = dataJsonArray.getJSONObject(0);

        String summary = currentlyWeather.getString(SUMMARY);
        String humidity = currentlyWeather.getString(HUMIDITY);
        String wind = currentlyWeather.getString(WIND_SPEED);
        String icon = currentlyWeather.getString(ICON);
        String temp = Math.round(currentlyWeather.getDouble(TEMPERATURE)) + "";
        String maxTemp = Math.round(dataDaily.getDouble(TEMPERATURE_MAX)) + "";
        String minTemp = Math.round(dataDaily.getDouble(TEMPERATURE_MIN)) + "";


        CurrentWeather mCurrentWeather = new CurrentWeather(this);
        mCurrentWeather.setDescription(summary);
        mCurrentWeather.setIconImage(icon);
        mCurrentWeather.setCurrentTemperature(temp);
        mCurrentWeather.setMaxTemp(maxTemp);
        mCurrentWeather.setMinTemp(minTemp);
        mCurrentWeather.setHumidity(humidity);
        mCurrentWeather.setWindSpeed(wind);

        return mCurrentWeather;
    }

    private ArrayList<Day> getDailyWeatherFromJson(String json) throws JSONException {

        DateFormat dateFormat = new SimpleDateFormat("EEEE");
        ArrayList<Day> days = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);
        String timezone = jsonObject.getString(TIMEZONE);
        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        JSONObject dailyWeather = jsonObject.getJSONObject(DAILY);
        JSONArray dataJsonArray = dailyWeather.getJSONArray(DATA);

        for (int i = 0; i < dataJsonArray.length(); i++) {
            Day day = new Day();

            JSONObject jsonWithDayData = dataJsonArray.getJSONObject(i);

            String rainProbability = "Rain Probability: "+ jsonWithDayData.getDouble(PRECIP_PROBABILITY)*100 + "%";
            String description = jsonWithDayData.getString(SUMMARY);
            //Need to convert time to day format using "EEEE" format with dayFormat
            String dayName = dateFormat.format(jsonWithDayData.getDouble(TIME) * 1000);

            day.setDayName(dayName);
            day.setDayDescription(description);
            day.setRainProbability(rainProbability);

            days.add(day);
        }

        return days;
    }

    public ArrayList<Hour> getHourlyWeather(String json) throws JSONException {

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        ArrayList<Hour> hours = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);
        String timezone = jsonObject.getString(TIMEZONE);
        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        JSONObject hourlyWeather = jsonObject.getJSONObject(HOURLY);
        JSONArray hourlyJsonArray = hourlyWeather.getJSONArray(DATA);

        for (int i=0; i < hourlyJsonArray.length(); i++) {
            Hour hour = new Hour();

           JSONObject hourlyData = hourlyJsonArray.getJSONObject(i);

           String title = dateFormat.format(hourlyData.getDouble(TIME) * 1000);
           String description = hourlyData.getString(SUMMARY);

           hour.setHour(title);
           hour.setHourDescription(description);

           hours.add(hour);
        }
        return hours;
    }

}
