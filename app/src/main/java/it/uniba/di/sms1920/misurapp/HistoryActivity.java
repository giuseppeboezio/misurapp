package it.uniba.di.sms1920.misurapp;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;

import android.widget.Toolbar;

public class HistoryActivity extends ListActivity {

    private SimpleCursorAdapter mAdapter;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
        int sensorType = intent.getIntExtra(DetectionOpenHelper.SENSOR_TYPE, 0);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

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
            case Sensor.TYPE_MAGNETIC_FIELD:
                myToolbar.setTitle(R.string.geomagneticFieldSensor);
                break;
            case Sensor.TYPE_PROXIMITY:
                myToolbar.setTitle(R.string.proximitySensor);
                break;
        }
        myToolbar.setTitle(R.string.lightSensor);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorOnPrimary));
        setActionBar(myToolbar);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        Cursor cursor = DetectionSQLite.getAllDetection(this, sensorType);

        switch(sensorType){
            case Sensor.TYPE_LIGHT:
            case Sensor.TYPE_PROXIMITY:
            case Sensor.TYPE_STEP_COUNTER: //Scalar measures (light, proximity and step counter)
                mAdapter = new SimpleCursorAdapter(this, R.layout.detection_row_scalar, cursor,
                        new String[]{DetectionOpenHelper.ID, DetectionOpenHelper.DATETIME, DetectionOpenHelper.VALUE1},
                        new int[] {R.id.id, R.id.datetime, R.id.value1}, 0);
                break;
            default:
                mAdapter = new SimpleCursorAdapter(this, R.layout.detection_row_vectorial, cursor,
                           new String[]{DetectionOpenHelper.ID, DetectionOpenHelper.DATETIME, DetectionOpenHelper.VALUE1,
                                   DetectionOpenHelper.VALUE2, DetectionOpenHelper.VALUE3}, new int[] {R.id.id, R.id.datetime, R.id.value1,
                        R.id.value2, R.id.value3}, 0);

        }

        setListAdapter(mAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
