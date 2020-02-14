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
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class SensorHumidityActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensorHumidity;
    private Toolbar myToolbar;
    private Set<Detection> detections;

    private ImageView mDrop1;
    private ImageView mDrop2;
    private ImageView mDrop3;
    private ImageView mDrop4;
    private ImageView mDrop5;
    private ImageView mDrop6;
    private ImageView mDrop7;
    private ImageView mDrop8;
    private ImageView mDrop9;
    private ImageView mDrop10;
    private ImageView mDrop11;
    private ImageView mDrop12;
    private ImageView mDrop13;
    private ImageView mDrop14;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_humidity);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        myToolbar.setTitle(R.string.lightSensor);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorOnPrimary));
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        detections = new HashSet<Detection>();

        //Getting drops references
        mDrop1 = (ImageView) findViewById(R.id.drop1);
        mDrop2 = (ImageView) findViewById(R.id.drop2);
        mDrop3 = (ImageView) findViewById(R.id.drop3);
        mDrop4 = (ImageView) findViewById(R.id.drop4);
        mDrop5 = (ImageView) findViewById(R.id.drop5);
        mDrop6 = (ImageView) findViewById(R.id.drop6);
        mDrop7 = (ImageView) findViewById(R.id.drop7);
        mDrop8 = (ImageView) findViewById(R.id.drop8);
        mDrop9 = (ImageView) findViewById(R.id.drop9);
        mDrop10 = (ImageView) findViewById(R.id.drop10);
        mDrop11 = (ImageView) findViewById(R.id.drop11);
        mDrop12 = (ImageView) findViewById(R.id.drop12);
        mDrop13 = (ImageView) findViewById(R.id.drop13);
        mDrop14 = (ImageView) findViewById(R.id.drop14);



    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {

        final float relativeHumidity = event.values[0];

        if(relativeHumidity > 0 && relativeHumidity <= 14) {
            mDrop1.setVisibility(View.VISIBLE);
            mDrop8.setVisibility(View.VISIBLE);
            mDrop14.setVisibility(View.INVISIBLE);
            mDrop13.setVisibility(View.INVISIBLE);
            mDrop5.setVisibility(View.INVISIBLE);
            mDrop7.setVisibility(View.INVISIBLE);
            mDrop2.setVisibility(View.INVISIBLE);
            mDrop9.setVisibility(View.INVISIBLE);
            mDrop11.setVisibility(View.INVISIBLE);
            mDrop4.setVisibility(View.INVISIBLE);
            mDrop12.setVisibility(View.INVISIBLE);
            mDrop6.setVisibility(View.INVISIBLE);
            mDrop3.setVisibility(View.INVISIBLE);
            mDrop10.setVisibility(View.INVISIBLE);
        } else if(relativeHumidity > 14 && relativeHumidity <= 28){
            mDrop14.setVisibility(View.VISIBLE);
            mDrop13.setVisibility(View.VISIBLE);
            mDrop5.setVisibility(View.INVISIBLE);
            mDrop7.setVisibility(View.INVISIBLE);
            mDrop2.setVisibility(View.INVISIBLE);
            mDrop9.setVisibility(View.INVISIBLE);
            mDrop11.setVisibility(View.INVISIBLE);
            mDrop4.setVisibility(View.INVISIBLE);
            mDrop12.setVisibility(View.INVISIBLE);
            mDrop6.setVisibility(View.INVISIBLE);
            mDrop3.setVisibility(View.INVISIBLE);
            mDrop10.setVisibility(View.INVISIBLE);
        } else if(relativeHumidity > 18 && relativeHumidity <= 32) {
            mDrop5.setVisibility(View.VISIBLE);
            mDrop7.setVisibility(View.VISIBLE);
            mDrop2.setVisibility(View.INVISIBLE);
            mDrop9.setVisibility(View.INVISIBLE);
            mDrop11.setVisibility(View.INVISIBLE);
            mDrop4.setVisibility(View.INVISIBLE);
            mDrop12.setVisibility(View.INVISIBLE);
            mDrop6.setVisibility(View.INVISIBLE);
            mDrop3.setVisibility(View.INVISIBLE);
            mDrop10.setVisibility(View.INVISIBLE);
        } else if(relativeHumidity > 32 && relativeHumidity <= 46) {
            mDrop2.setVisibility(View.VISIBLE);
            mDrop9.setVisibility(View.VISIBLE);
            mDrop11.setVisibility(View.INVISIBLE);
            mDrop4.setVisibility(View.INVISIBLE);
            mDrop12.setVisibility(View.INVISIBLE);
            mDrop6.setVisibility(View.INVISIBLE);
            mDrop3.setVisibility(View.INVISIBLE);
            mDrop10.setVisibility(View.INVISIBLE);
        } else if(relativeHumidity > 46 && relativeHumidity <= 60) {
            mDrop11.setVisibility(View.VISIBLE);
            mDrop4.setVisibility(View.VISIBLE);
            mDrop12.setVisibility(View.INVISIBLE);
            mDrop6.setVisibility(View.INVISIBLE);
            mDrop3.setVisibility(View.INVISIBLE);
            mDrop10.setVisibility(View.INVISIBLE);
        } else if(relativeHumidity > 60 && relativeHumidity <= 74)  {
            mDrop12.setVisibility(View.VISIBLE);
            mDrop6.setVisibility(View.VISIBLE);
            mDrop3.setVisibility(View.INVISIBLE);
            mDrop10.setVisibility(View.INVISIBLE);
        } else if(relativeHumidity > 74)  {
            mDrop3.setVisibility(View.VISIBLE);
            mDrop10.setVisibility(View.VISIBLE);
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
}
