package it.uniba.di.sms1920.misurapp;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SensorProximityActivity extends AppCompatActivity implements SensorEventListener {

    private static final float PIVOT_X = (float) 0.5;
    private static final float PIVOT_Y = (float) 0.5;

    private SensorManager mSensorManager;
    private Sensor mSensorProximity;
    private ImageView highFiveHand;
    private ScaleAnimation scaleAnimation;
    private float fromX, fromY, toX, toY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_proximity);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        myToolbar.setTitle(R.string.proximitySensor);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorOnPrimary));
        setSupportActionBar(myToolbar);

        highFiveHand = (ImageView) findViewById(R.id.img_hand);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        toX = 1;
        toY = 1;

    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        float distance = event.values[0];

        fromX = toX;
        fromY = toY;

        if (distance >= 4){

            toX = 1;
            toY = 1;
        }
        else if (distance >= 2 && distance < 4) {
            toX = (float) 1.5;
            toY = (float) 1.5;
        }

        else if (distance >= 0 && distance < 2) {
            toX = (float) 1.7;
            toY = (float) 1.7;
        }

        scaleAnimation = new ScaleAnimation(fromX, toX, fromY, toY);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setInterpolator(new LinearInterpolator());

        highFiveHand.setAnimation(scaleAnimation);

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
        mSensorManager.registerListener(this, mSensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
