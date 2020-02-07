package it.uniba.di.sms1920.misurapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

public class PositionActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mSensorProximity;
    private Sensor mSensorMagneticField;
    private String mSensorProximityName;
    private String  mSensorMagneticFieldName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorMagneticField = mSensorManager.getDefaultSensor((Sensor.TYPE_MAGNETIC_FIELD));

        if(mSensorProximity != null){
            mSensorProximityName = mSensorProximity.getName();
        } else {
            mSensorProximityName = new String(getResources().getString(R.string.withoutProximitySensor));
        }


    }
}
