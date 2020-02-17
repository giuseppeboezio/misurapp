package it.uniba.di.sms1920.misurapp;

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
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashSet;
import java.util.Set;

public class SensorPressureActivity extends AppCompatActivity implements SensorEventListener {

    private Set<Detection> detections;
    private TextView showPressureData;
    private SensorManager mSensorManager;
    private Sensor mSensorPressure;
    private ImageView baloon, boom;
    private ScaleAnimation scaleAnimation;
    private Button mBtnHistory;
    private float fromX, fromY, toX, toY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_pressure);

        mBtnHistory = (Button) findViewById(R.id.btnHistory);

        mBtnHistory.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                goHistory();
            }
        });

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        myToolbar.setTitle(R.string.pressureSensor);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorOnPrimary));
        setSupportActionBar(myToolbar);

        baloon = findViewById(R.id.img_baloon);
        boom = findViewById(R.id.img_boom);

        showPressureData = findViewById(R.id.pressureData);
        detections = new HashSet<Detection>();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {

        float pressure = event.values[0];

        fromX = toX;
        fromY = toY;

        if (pressure >= 900){
            //show boom image
            boom.setVisibility(View.VISIBLE);
        }else{
            boom.setVisibility(View.INVISIBLE);
        }

        if (pressure >= 0 && pressure < 100) {
            toX = (float) 1.1;
            toY = (float) 1.1;
        }

        else if (pressure >= 100 && pressure < 200) {
            toX = (float) 1.2;
            toY = (float) 1.2;
        }

        else if (pressure >= 200 && pressure < 300) {
            toX = (float) 1.3;
            toY = (float) 1.3;
        }

        else if (pressure >= 300 && pressure < 400) {
            toX = (float) 1.4;
            toY = (float) 1.4;
        }

        else if (pressure >= 400 && pressure < 500) {
            toX = (float) 1.5;
            toY = (float) 1.5;
        }

        else if (pressure >= 500 && pressure < 600) {
            toX = (float) 1.6;
            toY = (float) 1.6;
        }

        else if (pressure >= 600 && pressure < 700) {
            toX = (float) 1.7;
            toY = (float) 1.7;
        }

        else if (pressure >= 700 && pressure < 800) {
            toX = (float) 1.8;
            toY = (float) 1.8;
        }

        else if (pressure >= 800 && pressure < 900) {
            toX = (float) 1.9;
            toY = (float) 1.9;
        }

        scaleAnimation = new ScaleAnimation(fromX, toX, fromY, toY);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setInterpolator(new LinearInterpolator());

        baloon.startAnimation(scaleAnimation);

        showPressureData.setText(pressure + "  mPa");

        Detection proximityDetection = new Detection();

        proximityDetection.setSensorType(event.sensor.getType());
        proximityDetection.setDateTimeDetection(Detection.getFormattedDatetime(event.timestamp));
        proximityDetection.setValues(event.values[0]);

        detections.add(proximityDetection);
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

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorPressure, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    private void goHistory() {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra(DetectionOpenHelper.SENSOR_TYPE, Sensor.TYPE_PRESSURE);
        startActivity(intent);
    }
}
