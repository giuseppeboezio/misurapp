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
import java.util.LinkedList;
import java.util.List;

public class PositionActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mSensorProximity;
    private Sensor mSensorMagneticField;
    private String mSensorProximityName;
    private String  mSensorMagneticFieldName;
    private Button mProximityButton;
    private Button mMagneticFieldButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorMagneticField = mSensorManager.getDefaultSensor((Sensor.TYPE_MAGNETIC_FIELD));

        //Setting Proximity Button
        mProximityButton = (Button) findViewById(R.id.btnSensorProximity);
        if(mSensorProximity != null){
            mSensorProximityName = mSensorProximity.getName();
            mProximityButton.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v){
                    goSensorProximity();
                }
            });
        } else {
            mSensorProximityName = new String(getResources().getString(R.string.withoutProximitySensor));
        }
        mProximityButton.setText(mSensorProximityName);

        //Setting Magnetic Field Button
        mMagneticFieldButton = (Button) findViewById(R.id.btnSensorMagneticField);
        if(mSensorMagneticField != null){
            mSensorMagneticFieldName = mSensorMagneticField.getName();
            mMagneticFieldButton.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    goSensorMagneticField();
                }
            });
        } else {
            mSensorMagneticFieldName = new String(getResources().getString(R.string.withoutGeomagneticFieldSensor));
        }
        mMagneticFieldButton.setText(mSensorMagneticFieldName);




    }
    private void goSensorProximity() {
        Intent intent = new Intent(this, SensorProximityActivity.class);
        startActivity(intent);
    }

    private void goSensorMagneticField() {
        Intent intent = new Intent(this, SensorMagneticFieldActivity.class);
        startActivity(intent);
    }
}
