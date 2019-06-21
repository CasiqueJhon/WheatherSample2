package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhonisaac.wheathersample2.R;

import java.util.ArrayList;

import Wheather.Day;

public class DailyWeatherAdapter extends BaseAdapter {

    private ArrayList<Day> days;
    private Context mContext;

    public DailyWeatherAdapter(Context context, ArrayList<Day> days) {
        this.mContext = context;
        this.days = days;
    }

    @Override
    public int getCount() {
        if (days == null)
            return 0;
        return days.size();
    }

    @Override
    public Object getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        Day day = days.get(position);

        if (view == null) {

            view = LayoutInflater.from(mContext).inflate(R.layout.daily_list_item, viewGroup, false);
            //If view is null, create it viewHolder
            viewHolder = new ViewHolder();

            viewHolder.dayTitle = view.findViewById(R.id.tv_day);
            viewHolder.dayDescription = view.findViewById(R.id.tv_day_description);
            viewHolder.rainProbability = view.findViewById(R.id.tv_rain_probability);

            view.setTag(viewHolder);

        }else{
            //If is not null, just use it again.
            //Cast to viewHolder(important)
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.dayTitle.setText(day.getDayName());
        viewHolder.dayDescription.setText(day.getDayDescription());
        viewHolder.rainProbability.setText(day.getRainProbability());

        return view;
    }

    static class ViewHolder{
        TextView dayTitle;
        TextView dayDescription;
        TextView rainProbability;
    }
}
