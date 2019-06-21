package Wheather;

import android.os.Parcel;
import android.os.Parcelable;

public class Hour implements Parcelable {

    private String hour;
    private String hourDescription;

    public Hour() {

    }

    protected Hour(Parcel in) {
        hour = in.readString();
        hourDescription = in.readString();
    }

    public static final Creator<Hour> CREATOR = new Creator<Hour>() {
        @Override
        public Hour createFromParcel(Parcel in) {
            return new Hour(in);
        }

        @Override
        public Hour[] newArray(int size) {
            return new Hour[size];
        }
    };

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getHourDescription() {
        return hourDescription;
    }

    public void setHourDescription(String hourDescription) {
        this.hourDescription = hourDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(hour);
        parcel.writeString(hourDescription);
    }
}
