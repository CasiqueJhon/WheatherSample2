package Wheather;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class WeatherActivity extends AppCompatActivity {

    //public static final String URL_REQUEST = "https://api.darksky.net/forecast/29ac2f86be434312999df32b81cc69b4/40.251025,-3.702012?units=si";
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
    public static final String MINUTELY = "minutely";
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @BindView(R.id.iv_icon)
    ImageView iconImageView;
    @BindView(R.id.tv_temp_description)
    TextView tv_description_temp;
    @BindView(R.id.tv_current_temp)
    TextView tv_current_temp;
    @BindView(R.id.tv_max_temp)
    TextView tv_max_temp;
    @BindView(R.id.tv_min_temp)
    TextView tv_min_temp;
    @BindDrawable(R.drawable.clear_night)
    Drawable clearNight;
    @BindView(R.id.tv_humidity)
    TextView humidity;
    @BindView(R.id.tv_wind)
    TextView windSpeed;
    @BindView(R.id.tv_city_name)
    TextView cityName;
    @BindView(R.id.tv_country_name)
    TextView countryName;
    @BindView(R.id.refresh_iv)
    ImageButton refresh_iv;

    private ArrayList<Day> days;
    private ArrayList<Hour> hours;
    private ArrayList<Minute> minutes;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private String forecastURL = "https://api.darksky.net/forecast";
    private String units = "units=si";
    private String apiKey = "29ac2f86be434312999df32b81cc69b4";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheather);
        ButterKnife.bind(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            getLocation();

            refresh_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (refresh_iv.isClickable()) {
                        getLocation();
                    }
                }
            });

    }



    private void getLocation() {
        //Checking permissions granted or not
        if (ContextCompat.checkSelfPermission(WeatherActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(WeatherActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Requires Permission")
                        .setMessage("Need your permission to access the feature")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(WeatherActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(WeatherActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else {
            //Permission has already been granted
            mFusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(final Location location) {
                            if (location != null) {
                                final Double mLatitude = location.getLatitude();
                                final Double mLongitude = location.getLongitude();
                                forecastCall(mLatitude.toString(), mLongitude.toString());

                            }
                        }
                    });
        }
    }

    public void forecastCall (String latitude, String longitude) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        String request = forecastURL + "/" +apiKey  + "/" + latitude + "," + longitude + "?" + units;

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
                            windSpeed.setText(String.format("Wind Speed: %s", currentWeather.getWindSpeed() + " km"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WeatherActivity.this, "Connection Error",
                        Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @OnClick(R.id.btn_daily)
    public void dailyWeatherClick() {
        Intent dailyIntent = new Intent(WeatherActivity.this, DailyActivityWeather.class);
        //To put the parcelable content to the dailyWeatherActivity
        dailyIntent.putParcelableArrayListExtra(DAYS_ARRAY_LIST, days);
        startActivity(dailyIntent);

    }

    @OnClick(R.id.btn_hourly)
    public void hourlyListener() {
        Intent hourlyIntent = new Intent(WeatherActivity.this, HourlyActivityWeather.class);
        hourlyIntent.putParcelableArrayListExtra(HOURS_ARRAY_LIST, hours);
        startActivity(hourlyIntent);
    }

    @OnClick(R.id.btn_minutely)
    public void minutelyListener() {
        Intent minutelyIntent = new Intent(WeatherActivity.this, MinutelyActivityWeather.class);
        minutelyIntent.putParcelableArrayListExtra("MINUTELY_ARRAY_LIST" , minutes);
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

    public ArrayList<Minute> getMinutesFromJson (String json) throws JSONException {

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        ArrayList<Minute> minutes = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);
        String timezone = jsonObject.getString(TIMEZONE);
        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        JSONObject minutelyWeather = jsonObject.getJSONObject(MINUTELY);
        JSONArray minutelyArray = minutelyWeather.getJSONArray(DATA);

        for (int i = 0; i < minutelyArray.length(); i++) {

            Minute minute = new Minute();
            JSONObject minutelyArrayData = minutelyArray.getJSONObject(i);

            String minutely = dateFormat.format(minutelyArrayData.getDouble(TIME)*1000);
            String minutelyRainProbability = minutelyArrayData.getString(PRECIP_PROBABILITY);

            minute.setMinuteWeather(minutely);
            minute.setMinuteDescription(minutelyRainProbability);

            minutes.add(minute);

        }

        return minutes;

    }

    /**public void getCityName() throws IOException {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
        cityName.setText(addresses.get(0).getAddressLine(0));
        countryName.setText(addresses.get(0).getAddressLine(2));
    }**/

}
