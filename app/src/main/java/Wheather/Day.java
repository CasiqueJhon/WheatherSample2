package Wheather;

import android.os.Parcel;
import android.os.Parcelable;

public class Day implements Parcelable {

    private String dayName;
    private String dayDescription;
    private String rainProbability;
    private String maxTemp;
    private String minTemp;

    public Day () {

    }

    protected Day(Parcel in) {
        dayName = in.readString();
        dayDescription = in.readString();
        rainProbability = in.readString();
        maxTemp = in.readString();
        minTemp = in.readString();
    }

    public static final Creator<Day> CREATOR = new Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel in) {
            return new Day(in);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getDayDescription() {
        return dayDescription;
    }

    public void setDayDescription(String dayDescription) {
        this.dayDescription = dayDescription;
    }

    public String getRainProbability() {
        return rainProbability;
    }

    public void setRainProbability(String rainProbability) {
        this.rainProbability = rainProbability;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(dayName);
        parcel.writeString(dayDescription);
        parcel.writeString(rainProbability);
        parcel.writeString(maxTemp);
        parcel.writeString(minTemp);
    }
}
