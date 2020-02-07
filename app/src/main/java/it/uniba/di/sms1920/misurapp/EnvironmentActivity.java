package it.uniba.di.sms1920.misurapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

public class EnvironmentActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mSensorLight;
    private Sensor mSensorHumidity;
    private String mSensorLightName;
    private String mSensorHumidityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        if(mSensorLight != null) {
            mSensorLightName = mSensorLight.getName();
        } else {
            mSensorLightName = new String(getResources().getString(R.string.withoutLightSensor));
        }





    }
}
