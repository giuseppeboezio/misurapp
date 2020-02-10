package it.uniba.di.sms1920.misurapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MotionActivity extends AppCompatActivity {


    private SensorManager mSensorManager;
    private Sensor mSensorAccelerometer;
    private Sensor mSensorStepCounter;
    private CardView mCardAccelerometer;
    private CardView mCardStepCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        //Setting Accelerometer Card

        mCardAccelerometer = (CardView) findViewById(R.id.cardAccelerometer);

        if(mSensorAccelerometer != null) {
            mCardAccelerometer.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    goSensorAccelerometer();
                }
            });
        } else {
            mCardAccelerometer.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(MotionActivity.this, getResources().getString(R.string.withoutAccelerometerSensor), Toast.LENGTH_LONG).show();
                }
            });
        }

        //Setting Step Counter Card

        mCardStepCounter = (CardView) findViewById(R.id.cardStepCounter);

        if(mCardStepCounter != null) {
            mCardStepCounter.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    goSensorStepCounter();
                }
            });
        } else {
            mCardStepCounter.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(MotionActivity.this, getResources().getString(R.string.withoutStepCounterSensor), Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private void goSensorAccelerometer() {
        Intent intent = new Intent(this, SensorAccelerometerActivity.class);
        startActivity(intent);
    }

    private void goSensorStepCounter(){
        Intent intent = new Intent(this, SensorHumidityActivity.class);
        startActivity(intent);
    }

}
