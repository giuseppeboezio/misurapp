package it.uniba.di.sms1920.misurapp;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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

        mAdapter = new SimpleCursorAdapter(this, R.layout.detection_row, cursor,
                new String[]{DetectionOpenHelper.ID, DetectionOpenHelper.DATETIME, DetectionOpenHelper.VALUE1, DetectionOpenHelper.VALUE2,
                DetectionOpenHelper.VALUE3}, new int[] {R.id.id, R.id.datetime, R.id.value1, R.id.value2, R.id.value3}, 0);


        setListAdapter(mAdapter);

    }
}
