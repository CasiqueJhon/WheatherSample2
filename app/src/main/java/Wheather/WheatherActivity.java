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

import Root.CurrentWeather;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WheatherActivity extends AppCompatActivity {

    public static final String URL_REQUEST = "https://api.darksky.net/forecast/29ac2f86be434312999df32b81cc69b4/40.251025,-3.702012?units=si";


    @BindView(R.id.iv_icon) ImageView iconImageView;
    @BindView(R.id.tv_temp_description) TextView tv_description_temp;
    @BindView(R.id.tv_current_temp) TextView tv_current_temp;
    @BindView(R.id.tv_max_temp) TextView tv_max_temp;
    @BindView(R.id.tv_min_temp) TextView tv_min_temp;
    @BindDrawable(R.drawable.clear_night) Drawable clearNight;
    @BindView(R.id.tv_humidity) TextView humidity;
    @BindView(R.id.tv_wind) TextView windSpeed;



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
                Toast.makeText(WheatherActivity.this, "That didn't work!",
                        Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }


    @OnClick(R.id.btn_daily)
    public void dailyWeatherClick() {
        Intent dailyIntent = new Intent(WheatherActivity.this, DailyActivityWheather.class);
        startActivity(dailyIntent);

    }

    @OnClick(R.id.btn_hourly)
    public void hourlyListener() {
        Intent hourlyIntent = new Intent(WheatherActivity.this, HourlyActivityWheather.class);
        startActivity(hourlyIntent);
    }

    @OnClick(R.id.btn_minutely)
    public void minutelyListener() {
        Intent minutelyIntent = new Intent(WheatherActivity.this, MinutelyActivityWheather.class);
        startActivity(minutelyIntent);
    }

    private CurrentWeather getCurrentWeather(String json) throws JSONException {


        JSONObject jsonObject = new JSONObject(json);
        JSONObject currentlyWeather = jsonObject.getJSONObject("currently");
        JSONObject dailyWeather = jsonObject.getJSONObject("daily");
        JSONArray dataJsonArray = dailyWeather.getJSONArray("data");
        JSONObject dataDaily = dataJsonArray.getJSONObject(0);

        String summary = currentlyWeather.getString("summary");
        String humidity = currentlyWeather.getString("humidity");
        String wind = currentlyWeather.getString("windSpeed");
        String icon = currentlyWeather.getString("icon");
        String temp = Math.round(currentlyWeather.getDouble("temperature")) + "";
        String maxTemp = Math.round(dataDaily.getDouble("temperatureMax")) + "";
        String minTemp = Math.round(dataDaily.getDouble("temperatureMin")) + "";


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



}
