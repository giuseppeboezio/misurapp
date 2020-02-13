package it.uniba.di.sms1920.misurapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
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

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        myToolbar.setTitle(R.string.motionSensors);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorOnPrimary));
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    private void goSensorAccelerometer() {
        Intent intent = new Intent(this, SensorAccelerometerActivity.class);
        startActivity(intent);
    }

    private void goSensorStepCounter(){
        Intent intent = new Intent(this, SensorStepCounterActivity.class);
        startActivity(intent);
    }

}
