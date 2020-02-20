package it.uniba.di.sms1920.misurapp;


import android.content.Context;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class Detection {

    private int sensorType;
    private String dateTimeDetection;
    private float[] values = new float[3];

    public void setSensorType(int sensorType) {
        this.sensorType = sensorType;
    }

    public void setDateTimeDetection(String dateTimeDetection) {
        this.dateTimeDetection = dateTimeDetection;
    }

    public void setValues(float value) {
        values[0] = value;
    }

    public void setValues(float[] values) {
        this.values = values;
    }

    public int getSensorType() {
        return sensorType;
    }

    public String getDateTimeDetection() {
        return dateTimeDetection;
    }

    public float getValues(int index) {
        return values[index];
    }

    public static String getFormattedDatetime(long timestamp, Context context) {
        String result ;

        Duration duration = Duration.ofNanos(timestamp);
        Log.d("ISO", duration.toString());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String durationString = duration.toString();
        Date date = new Date();
        try{
            date = dateFormat.parse(durationString);
        } catch(ParseException e) {
            e.printStackTrace();
        }

        String dateFormatted = date.toString();
        Log.i("DATE", dateFormatted);

        String day = dateFormatted.substring(8, 10);
        String month = dateFormatted.substring(4, 7);
        String year = dateFormatted.substring(24, 28);
        String time = dateFormatted.substring(11, 19);
        result = day + " " + month + " " + year + " " + context.getString(R.string.time)
                + " " + time;

        return result;
    }

}
