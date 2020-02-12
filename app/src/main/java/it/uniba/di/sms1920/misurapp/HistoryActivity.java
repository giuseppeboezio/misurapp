package it.uniba.di.sms1920.misurapp;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class HistoryActivity extends ListActivity {

    private SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
        int sensorType = intent.getIntExtra(DetectionOpenHelper.SENSOR_TYPE, 0);

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
}
