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
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashSet;
import java.util.Set;

public class SensorAccelerometerActivity extends AppCompatActivity implements SensorEventListener {

    private static final float MULTIPLY_FACTOR = 30f; //Multiplicative factor obtained sperimentally
    private static final int MIN_ACCELERATION = 20;
    private static final int MIN_SHAKE_Y_LEVEL = -3;
    private final static int DURATION = 7000;
    private final static float PIVOT = 0.5f;
    private SensorManager mSensorManager;
    private Sensor mSensorAccelerometer;
    private  ImageView wheel;
    private float fromDegree, toDegree;
    private Set<Detection> detections;
    private TextView showAccelerometerData;
    private Button mBtnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_accelerometer);

        //Button for showing history details
        mBtnHistory = findViewById(R.id.btnHistory);
        mBtnHistory.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                goHistory();
            }
        });

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        myToolbar.setTitle(R.string.accelerometerSensor);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorOnPrimary));
        setSupportActionBar(myToolbar);

        showAccelerometerData = findViewById(R.id.accelerometerData);
        detections = new HashSet<Detection>();

        wheel = findViewById(R.id.wheel);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        toDegree = 0;

    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float acceleration = Math.abs(x+y+z);

        if(y < MIN_SHAKE_Y_LEVEL && acceleration >= MIN_ACCELERATION) {
            fromDegree = toDegree;
            toDegree = toDegree + acceleration * MULTIPLY_FACTOR;
            RotateAnimation rotateAnimation = new RotateAnimation(fromDegree, toDegree,  Animation.RELATIVE_TO_SELF,
                    PIVOT, Animation.RELATIVE_TO_SELF, PIVOT);
            rotateAnimation.setDuration(DURATION);
            rotateAnimation.setInterpolator(new DecelerateInterpolator());
            rotateAnimation.setFillAfter(true);

           wheel.startAnimation(rotateAnimation);

        }

        showAccelerometerData.setText("X: " + x + " " + getString(R.string.unit_accelerometer) +
                         "\n" + "Y: " +  y + "  " + getString(R.string.unit_accelerometer) + "\n" + "Z: " +  z +
                " " + getString(R.string.unit_accelerometer));

        Detection accelerometerDetection = new Detection();

        accelerometerDetection.setSensorType(event.sensor.getType());
        accelerometerDetection.setDateTimeDetection(Detection.getFormattedDatetime(event.timestamp, this));
        accelerometerDetection.setValues(event.values);

        detections.add(accelerometerDetection);

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
        intent.putExtra(DetectionOpenHelper.SENSOR_TYPE, Sensor.TYPE_ACCELEROMETER);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
