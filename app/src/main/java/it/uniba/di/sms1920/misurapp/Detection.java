package it.uniba.di.sms1920.misurapp;


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

    public static String getFormattedDatetime(long timestamp) {
        String result ;

        Duration duration = Duration.ofNanos(timestamp);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String durationString = duration.toString();
        Date date = new Date();
        try{
            date = dateFormat.parse(durationString);
        } catch(ParseException e) {
            e.printStackTrace();
        }

        result = date.toString();
        return result;
    }

}
