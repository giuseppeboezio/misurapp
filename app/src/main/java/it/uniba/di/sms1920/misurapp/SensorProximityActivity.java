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

public class SensorProximityActivity extends AppCompatActivity implements SensorEventListener {

    private static final int DURATION = 1000;
    private static final float SPERIMENTAL_A = 0.098f;
    private static final float SPERIMENTAL_B = 1.02f;
    private static final float ADDITIONAL_FACTOR = 0.1f;
    private SensorManager mSensorManager;
    private Sensor mSensorProximity;
    private ImageView highFiveHand;
    private ScaleAnimation scaleAnimation;
    private float fromX, fromY, toX, toY;
    private Set<Detection> detections;
    private TextView showProximityData;
    private Button mBtnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_proximity);

        mBtnHistory = findViewById(R.id.btnHistory);

        mBtnHistory.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                goHistory();
            }
        });

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        myToolbar.setTitle(R.string.proximitySensor);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorOnPrimary));
        setSupportActionBar(myToolbar);

        highFiveHand = findViewById(R.id.img_hand);

        showProximityData = findViewById(R.id.proximityData);
        detections = new HashSet<Detection>();

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

        toX = SPERIMENTAL_A / (distance + ADDITIONAL_FACTOR) + SPERIMENTAL_B;
        toY = SPERIMENTAL_A / (distance + ADDITIONAL_FACTOR) + SPERIMENTAL_B;

        scaleAnimation = new ScaleAnimation(fromX, toX, fromY, toY);
        scaleAnimation.setDuration(DURATION);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setInterpolator(new LinearInterpolator());

        highFiveHand.startAnimation(scaleAnimation);

        showProximityData.setText(distance + " " + getString(R.string.unit_proximity));

        Detection proximityDetection = new Detection();

        proximityDetection.setSensorType(event.sensor.getType());
        proximityDetection.setDateTimeDetection(Detection.getFormattedDatetime(event.timestamp, this));
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

    private void goHistory() {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra(DetectionOpenHelper.SENSOR_TYPE, Sensor.TYPE_PROXIMITY);
        startActivity(intent);
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
