package it.uniba.di.sms1920.misurapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SensorLightActivity extends AppCompatActivity implements SensorEventListener {

    private TextView mTxtValue;
    private SensorManager mSensorManager;
    private Sensor mSensorLight;
    private ImageView mSunglasses;
    Toolbar myToolbar;
    private AlphaAnimation mAlphaAnimation;
    private float fromAlpha, toAlpha;
    private Set<Detection> detections;
    private TextView showLightData;
    private Button mBtnHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_light);

        mBtnHistory = (Button) findViewById(R.id.btnHistory);

        mBtnHistory.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                goHistory();
            }
        });

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        myToolbar.setTitle(R.string.lightSensor);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorOnPrimary));
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showLightData = findViewById(R.id.lightData);
        detections = new HashSet<Detection>();

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

        showLightData.setText(String.valueOf(lux) + "  LX");
        //Managing sensor change for persistance

        Detection lightDetection = new Detection();

        lightDetection.setSensorType(event.sensor.getType());
        lightDetection.setDateTimeDetection(Detection.getFormattedDatetime(event.timestamp));
        lightDetection.setValues(event.values[0]);

        detections.add(lightDetection);

        //Setting animation

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
        mSunglasses.startAnimation(mAlphaAnimation);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sensor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.saveIcon) {
            saveDetection();
        } else {
            finish();
        }
        return true;
    }

    private void saveDetection() {
        for(Detection det: detections) {
            DetectionSQLite.add(this, det);
        }
        int quantity = detections.size();
        String message = getResources().getQuantityString(R.plurals.saveMessage, quantity, quantity);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        detections.clear();
    }

    private void goHistory() {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra(DetectionOpenHelper.SENSOR_TYPE, Sensor.TYPE_LIGHT);
        startActivity(intent);
    }

}
