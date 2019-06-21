package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhonisaac.wheathersample2.R;

import java.util.ArrayList;

import Wheather.Hour;

public class HourlyWeatherAdapter extends BaseAdapter {

    private ArrayList<Hour> hours;
    private Context mContext;

    public HourlyWeatherAdapter(Context context, ArrayList<Hour> hours) {
        this.mContext = context;
        this.hours = hours;
    }

    @Override
    public int getCount() {
        if (hours == null)
            return 0;
        return hours.size();
    }

    @Override
    public Object getItem(int position) {
        return hours.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        Hour hour = hours.get(position);

        if (view == null) {

            view = LayoutInflater.from(mContext).inflate(R.layout.hourly_list_item, viewGroup, false);
            viewHolder = new ViewHolder();

            viewHolder.hour = view.findViewById(R.id.tv_hour_weather);
            viewHolder.hourDescription = view.findViewById(R.id.tv_hour_description);

            view.setTag(viewHolder);

        }else {

            viewHolder = (ViewHolder) view.getTag();

        }

        viewHolder.hour.setText(hour.getHour());
        viewHolder.hourDescription.setText(hour.getHourDescription());

        return view;
    }

    static class ViewHolder {
        TextView hour;
        TextView hourDescription;
    }

}
