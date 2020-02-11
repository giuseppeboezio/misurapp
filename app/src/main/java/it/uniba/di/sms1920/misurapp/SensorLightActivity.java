package it.uniba.di.sms1920.misurapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class SensorLightActivity extends AppCompatActivity implements SensorEventListener {

    private TextView mTxtValue;
    private SensorManager mSensorManager;
    private Sensor mSensorLight;
    private ImageView mSunglasses;
    private AlphaAnimation mAlphaAnimation;
    private float fromAlpha, toAlpha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_light);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        mSunglasses = (ImageView) findViewById(R.id.imageSunglasses);
        toAlpha = (float) 0.3;

    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        float lux = event.values[0];
        fromAlpha = toAlpha;

        if(lux < 100){
            toAlpha = (float) 0.1;
        } else if(lux >= 100 && lux < 200) {
            toAlpha = (float) 0.3;
        } else if(lux >= 200 && lux < 300) {
            toAlpha = (float) 0.5;
        } else if(lux >= 300 && lux < 400) {
            toAlpha = (float) 0.7;
        } else if(lux >= 400 && lux < 500) {
            toAlpha = (float) 0.8;
        } else if(lux >= 500 && lux < 600 ) {
            toAlpha = (float) 0.9;
        } else if(lux >= 600) {
            toAlpha = (float) 1.0;
        }

        mAlphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        mAlphaAnimation.setDuration(1000);
        mAlphaAnimation.setFillAfter(true);
        mAlphaAnimation.setInterpolator(new LinearInterpolator());
        mSunglasses.setAnimation(mAlphaAnimation);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.unregisterListener(this);
        mSensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
