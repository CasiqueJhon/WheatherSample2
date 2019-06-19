package Wheather;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhonisaac.wheathersample2.R;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WheatherActivity extends AppCompatActivity {

    @BindView(R.id.iv_icon) ImageView iconImageView;
    @BindView(R.id.tv_temp_description) TextView tv_description_temp;
    @BindView(R.id.tv_current_temp) TextView tv_current_temp;
    @BindView(R.id.tv_max_temp) TextView tv_max_temp;
    @BindView(R.id.tv_min_temp) TextView tv_min_temp;

    @BindDrawable(R.drawable.clear_night) Drawable clearNight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheather);
        ButterKnife.bind(this);

        iconImageView.setImageDrawable(clearNight);
        tv_description_temp.setText("Clear");
        tv_current_temp.setText("23º");
        tv_max_temp.setText("H:35º");
        tv_min_temp.setText("L:16º");

    }


    @OnClick(R.id.btn_daily)
    public void dailyWheatherClick() {
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
}
