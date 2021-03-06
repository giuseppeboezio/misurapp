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
import java.util.Set;

public class SensorLightActivity extends AppCompatActivity implements SensorEventListener {

    private static final int DIVISION_FACTOR = 1000;
    private static final int DURATION = 1000;
    private SensorManager mSensorManager;
    private Sensor mSensorLight;
    private ImageView mSunglasses;
    private Toolbar myToolbar;
    private AlphaAnimation mAlphaAnimation;
    private float fromAlpha, toAlpha;
    private Set<Detection> detections;
    private TextView showLightData;
    private Button mBtnHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_light);

        mBtnHistory = findViewById(R.id.btnHistory);

        mBtnHistory.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                goHistory();
            }
        });

        myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        myToolbar.setTitle(R.string.lightSensor);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorOnPrimary));
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showLightData = findViewById(R.id.lightData);
        detections = new HashSet<Detection>();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        mSunglasses = findViewById(R.id.imageSunglasses);
        toAlpha = (float) 0.3;

    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        float lux = event.values[0];

        showLightData.setText(String.valueOf(lux) + " " + getString(R.string.unit_light));
        //Managing sensor change for persistance

        Detection lightDetection = new Detection();

        lightDetection.setSensorType(event.sensor.getType());
        lightDetection.setDateTimeDetection(Detection.getFormattedDatetime(event.timestamp, this));
        lightDetection.setValues(event.values[0]);

        detections.add(lightDetection);

        //Setting animation

        fromAlpha = toAlpha;

        toAlpha = lux / DIVISION_FACTOR;

        mAlphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        mAlphaAnimation.setDuration(DURATION);
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
