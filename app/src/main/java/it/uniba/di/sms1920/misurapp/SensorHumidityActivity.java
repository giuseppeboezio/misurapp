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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SensorHumidityActivity extends AppCompatActivity implements SensorEventListener {

    private static final int NUM_DROPS = 14;
    private static final float MULTIPLICATION_FACTOR = 0.13f;
    private int mIndex;
    private SensorManager mSensorManager;
    private Sensor mSensorHumidity;
    private Toolbar myToolbar;
    private Set<Detection> detections;

    private TextView showHumidityData;
    private Button mBtnHistory;

    List<ImageView> drops = new ArrayList<ImageView>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_humidity);

        myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        myToolbar.setTitle(R.string.humiditySensor);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorOnPrimary));
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showHumidityData = findViewById(R.id.humidityData);
        mBtnHistory = findViewById(R.id.btnHistory);
        mBtnHistory.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                goHistory();
            }
        });

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        detections = new HashSet<Detection>();

        //Getting drops references
        drops.add((ImageView) findViewById(R.id.drop1));
        drops.add((ImageView) findViewById(R.id.drop8));
        drops.add((ImageView) findViewById(R.id.drop14));
        drops.add((ImageView) findViewById(R.id.drop13));
        drops.add((ImageView) findViewById(R.id.drop5));
        drops.add((ImageView) findViewById(R.id.drop7));
        drops.add((ImageView) findViewById(R.id.drop2));
        drops.add((ImageView) findViewById(R.id.drop9));
        drops.add((ImageView) findViewById(R.id.drop11));
        drops.add((ImageView) findViewById(R.id.drop4));
        drops.add((ImageView) findViewById(R.id.drop12));
        drops.add((ImageView) findViewById(R.id.drop6));
        drops.add((ImageView) findViewById(R.id.drop3));
        drops.add((ImageView) findViewById(R.id.drop10));


    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {

        final float relativeHumidity = event.values[0];

        showHumidityData.setText(String.valueOf(relativeHumidity) + " " + getString(R.string.unit_humidity));

        Detection humidityDetection = new Detection();

        humidityDetection.setSensorType(event.sensor.getType());
        humidityDetection.setDateTimeDetection(Detection.getFormattedDatetime(event.timestamp, this));
        humidityDetection.setValues(event.values[0]);

        detections.add(humidityDetection);

        mIndex = Math.round(relativeHumidity * MULTIPLICATION_FACTOR);

        for (int i = 0; i <= mIndex; i++){
            drops.get(i).setVisibility(View.VISIBLE);
        }
        for(int i = mIndex; i < NUM_DROPS; i++) {
            drops.get(i).setVisibility(View.INVISIBLE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorHumidity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
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
        intent.putExtra(DetectionOpenHelper.SENSOR_TYPE, Sensor.TYPE_RELATIVE_HUMIDITY);
        startActivity(intent);
    }

}
