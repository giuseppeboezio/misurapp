package it.uniba.di.sms1920.misurapp;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;

import android.widget.Toolbar;

public class HistoryActivity extends ListActivity {

    private ScalarDetectionAdapter mAdapter;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
        int sensorType = intent.getIntExtra(DetectionOpenHelper.SENSOR_TYPE, 0);

        myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        Log.i("SENSOR_TYPE", String.valueOf(sensorType));

        switch(sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                myToolbar.setTitle(R.string.accelerometerSensor);
                break;
            case Sensor.TYPE_LIGHT:
                myToolbar.setTitle(R.string.lightSensor);
                break;
            case Sensor.TYPE_STEP_COUNTER:
                myToolbar.setTitle(R.string.stepCounterSensor);
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                myToolbar.setTitle(R.string.rotationSensor);
                break;
            case Sensor.TYPE_PROXIMITY:
                myToolbar.setTitle(R.string.proximitySensor);
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                myToolbar.setTitle(R.string.humiditySensor);
                break;
            case Sensor.TYPE_PRESSURE:
                myToolbar.setTitle(R.string.pressureSensor);
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                myToolbar.setTitle(R.string.temperatureSensor);
                break;
        }
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorOnPrimary));
        setActionBar(myToolbar);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        Cursor cursor = DetectionSQLite.getAllDetection(this, sensorType);

        switch(sensorType){
            case Sensor.TYPE_LIGHT:
            case Sensor.TYPE_PROXIMITY:
            case Sensor.TYPE_STEP_COUNTER:
            case Sensor.TYPE_RELATIVE_HUMIDITY:
            case Sensor.TYPE_PRESSURE:
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                mAdapter = new ScalarDetectionAdapter(this, cursor, sensorType);
                break;
            default:
                mAdapter = new VectorialDetectionAdapter(this, cursor, sensorType);

        }

        setListAdapter(mAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
