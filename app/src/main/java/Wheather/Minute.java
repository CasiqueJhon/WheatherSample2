package Wheather;

import android.os.Parcel;
import android.os.Parcelable;

public class Minute implements Parcelable {

    private String minuteWeather;
    private String minuteDescription;

    public Minute() {

    }

    protected Minute(Parcel in) {
        minuteWeather = in.readString();
        minuteDescription = in.readString();
    }

    public static final Creator<Minute> CREATOR = new Creator<Minute>() {
        @Override
        public Minute createFromParcel(Parcel in) {
            return new Minute(in);
        }

        @Override
        public Minute[] newArray(int size) {
            return new Minute[size];
        }
    };

    public String getMinuteWeather() {
        return minuteWeather;
    }

    public void setMinuteWeather(String minuteWeather) {
        this.minuteWeather = minuteWeather;
    }

    public String getMinuteDescription() {
        return minuteDescription;
    }

    public void setMinuteDescription(String minuteDescription) {
        this.minuteDescription = minuteDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(minuteWeather);
        parcel.writeString(minuteDescription);
    }
}
