package it.uniba.di.sms1920.misurapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class SensorStepCounterActivity extends AppCompatActivity implements SensorEventListener {

    private final float Y = 0.0f;

    private SensorManager mSensorManager;
    private Sensor mSensorStepCounter;
    private ImageView girlOnBike;

    private float fromX;
    private float toX;

    private float fromX2;
    private float toX2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_step_counter);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        myToolbar.setTitle(R.string.stepCounterSensor);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorOnPrimary));
        setSupportActionBar(myToolbar);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        girlOnBike = (ImageView) findViewById(R.id.girl);


        fromX = 0;
        toX = 0;


    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {

        fromX = toX2;
        toX = fromX + 300;

        TranslateAnimation animation1 = new TranslateAnimation(fromX, toX,
                    Y, Y);
        animation1.setDuration(1000);
        animation1.setFillAfter(true);

        fromX2 = toX;
        toX2 = (toX - fromX)/2;

        TranslateAnimation animation2 = new TranslateAnimation(fromX2, toX2,
                Y, Y);
        animation2.setDuration(1000);
        animation2.setFillAfter(true);

        girlOnBike.startAnimation(animation1);
        girlOnBike.startAnimation(animation2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sensor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorStepCounter, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
