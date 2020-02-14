package it.uniba.di.sms1920.misurapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class SensorTemperatureActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView temperature;
    private TextView showTemperatureData;
    private Set<Detection> detections;
    private SensorManager mSensorManager;
    private Sensor mSensorTemperature;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_temperature);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        myToolbar.setTitle(R.string.temperatureSensor);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorOnPrimary));
        setSupportActionBar(myToolbar);

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


       if(ambientTemperature < -30) {
           temperature.setImageResource(R.drawable.cold5);
       } else if (ambientTemperature >= -30 && ambientTemperature < -20) {
           temperature.setImageResource(R.drawable.cold4);
       } else if (ambientTemperature >= -20 && ambientTemperature < -10) {
           temperature.setImageResource(R.drawable.cold3);
       } else if (ambientTemperature >= -10 && ambientTemperature < 0) {
           temperature.setImageResource(R.drawable.cold2);
       } else if (ambientTemperature >= 0 && ambientTemperature < 10) {
           temperature.setImageResource(R.drawable.cold1);
       } else if (ambientTemperature >= 10 && ambientTemperature < 20) {
           temperature.setImageResource(R.drawable.red1);
       } else if (ambientTemperature >= 20 && ambientTemperature < 30) {
           temperature.setImageResource(R.drawable.red2);
       } else if (ambientTemperature >= 30 && ambientTemperature < 40) {
           temperature.setImageResource(R.drawable.red3);
       } else if (ambientTemperature >= 40 && ambientTemperature < 50) {
           temperature.setImageResource(R.drawable.red4);
       } else {
           temperature.setImageResource(R.drawable.red5);
       }





        showTemperatureData.setText(String.valueOf(ambientTemperature) + "  °C");

        Detection temperatureDetection = new Detection();

        temperatureDetection.setSensorType(event.sensor.getType());
        temperatureDetection.setDateTimeDetection(Detection.getFormattedDatetime(event.timestamp));
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

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorTemperature, 10000000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

}
