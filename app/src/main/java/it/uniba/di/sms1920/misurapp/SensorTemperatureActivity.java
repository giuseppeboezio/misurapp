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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class SensorTemperatureActivity extends AppCompatActivity implements SensorEventListener {

    private final static float MIN_TEMPERATURE = 186.8f;
    private final static int DIVISION_FACTOR = 100;
    private final static float MULTIPLICATION_FACTOR = 2.41f;
    private final static int MY_DELAY = 10000000;
    private int [] temperatureImages;
    private int mIndex;
    private ImageView temperature;
    private TextView showTemperatureData;
    private Set<Detection> detections;
    private SensorManager mSensorManager;
    private Sensor mSensorTemperature;
    private Button mBtnHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_temperature);

        mBtnHistory = findViewById(R.id.btnHistory);
        mBtnHistory.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                goHistory();
            }
        });

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        myToolbar.setTitle(R.string.temperatureSensor);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorOnPrimary));
        setSupportActionBar(myToolbar);

        temperatureImages = new int[] {R.drawable.cold5, R.drawable.cold4, R.drawable.cold3,
                R.drawable.cold2, R.drawable.cold1, R.drawable.red1, R.drawable.red2, R.drawable.red3,
                R.drawable.red4, R.drawable.red5};

        temperature = findViewById(R.id.img_temp);
        showTemperatureData = findViewById(R.id.temperatureData);
        detections = new HashSet<Detection>();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);



    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
       float ambientTemperature = event.values[0];

        mIndex = Math.round((ambientTemperature + MIN_TEMPERATURE) / DIVISION_FACTOR * MULTIPLICATION_FACTOR);
        if(mIndex < 0) {
            mIndex = 0;
        }

        temperature.setImageResource(temperatureImages[mIndex]);

        showTemperatureData.setText(String.valueOf(ambientTemperature) + getString(R.string.unit_temperature));

        Detection temperatureDetection = new Detection();

        temperatureDetection.setSensorType(event.sensor.getType());
        temperatureDetection.setDateTimeDetection(Detection.getFormattedDatetime(event.timestamp, this));
        temperatureDetection.setValues(event.values[0]);

        detections.add(temperatureDetection);
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
        intent.putExtra(DetectionOpenHelper.SENSOR_TYPE, Sensor.TYPE_AMBIENT_TEMPERATURE);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorTemperature, MY_DELAY);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

}
