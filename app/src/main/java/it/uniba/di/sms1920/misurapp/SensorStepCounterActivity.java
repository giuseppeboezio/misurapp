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
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class SensorStepCounterActivity extends AppCompatActivity implements SensorEventListener {

    private final float Y = 0.0f;

    private SensorManager mSensorManager;
    private Sensor mSensorStepCounter;
    private ImageView girlOnBike;

    private float fromX;
    private float toX;

    private TextView mShowDetection;
    private Set<Detection> detections;
    private Button mBtnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_step_counter);

        mBtnHistory = findViewById(R.id.btnHistory);

        mBtnHistory.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                goHistory();
            }
        });

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        myToolbar.setTitle(R.string.stepCounterSensor);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorOnPrimary));
        setSupportActionBar(myToolbar);

        mShowDetection = findViewById(R.id.showSteps);

        detections = new HashSet<Detection>();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        girlOnBike = findViewById(R.id.girl);


        fromX = 0;
        toX = 0;

    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {

        float steps = event.values[0];
        String message = getResources().getQuantityString(R.plurals.numberSteps, (int) steps, (int) steps);

        mShowDetection.setText(message);

        Detection stepsDetection = new Detection();

        stepsDetection.setSensorType(event.sensor.getType());
        stepsDetection.setDateTimeDetection(Detection.getFormattedDatetime(event.timestamp));
        stepsDetection.setValues(event.values[0]);

        detections.add(stepsDetection);


        fromX = toX;
        toX = fromX + 30;

        TranslateAnimation animation1 = new TranslateAnimation(fromX, toX,
                    Y, Y);
        animation1.setDuration(300);
        animation1.setFillAfter(true);
        animation1.setInterpolator(new LinearInterpolator());

        girlOnBike.startAnimation(animation1);

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
        intent.putExtra(DetectionOpenHelper.SENSOR_TYPE, Sensor.TYPE_STEP_COUNTER);
        startActivity(intent);
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
