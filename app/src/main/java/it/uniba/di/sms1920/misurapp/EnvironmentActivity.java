package it.uniba.di.sms1920.misurapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class EnvironmentActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mSensorLight;
    private Sensor mSensorHumidity;
    private String mSensorLightName;
    private String mSensorHumidityName;
    private Button mLightButton;
    private Button mHumidityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        //Setting Light Button
        mLightButton = (Button) findViewById(R.id.btnSensorLight);
        if(mSensorLight != null) {
            mSensorLightName = mSensorLight.getName();
            mLightButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    goSensorLight();
                }
            });
        } else {
            mSensorLightName = new String(getResources().getString(R.string.withoutLightSensor));
        }
        mLightButton.setText(mSensorLightName);

        //Setting Humidity Button
        mHumidityButton = (Button) findViewById(R.id.btnSensorHumidity);
        if(mSensorHumidity != null) {
            mSensorHumidityName = mSensorLight.getName();
            mHumidityButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    goSensorHumidity();
                }
            });
        } else {
            mSensorHumidityName = new String(getResources().getString(R.string.withoutHumiditySensor));
        }
        mHumidityButton.setText(mSensorHumidityName);

    }

    private void goSensorLight() {
        Intent intent = new Intent(this, SensorLightActivity.class);
        startActivity(intent);
    }

    private void goSensorHumidity(){
        Intent intent = new Intent(this, SensorHumidityActivity.class);
        startActivity(intent);
    }
}
