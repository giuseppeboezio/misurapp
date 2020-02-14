package it.uniba.di.sms1920.misurapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {


    Resources res;
    GridView grid;
    SensorManager mSensorManager;
    Sensor mSensor;
    private int[] sensorImages;
    private String[] sensorNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setLogo(R.mipmap.ic_launcher_round);
        myToolbar.setTitle(R.string.app_name);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorOnPrimary));
        setSupportActionBar(myToolbar);

        res = getResources();

        //Creating gridview to display whole set sensors
        sensorImages = new int[] {R.drawable.biker_in_the_city, R.drawable.image_geomagnetic_sensor,
        R.drawable.policeman, R.drawable.proximity_image, R.drawable.rocket_with_galaxy, R.drawable.window, R.drawable.palloncino};

        sensorNames = new String[] {res.getString(R.string.stepCounterSensor), res.getString(R.string.geomagneticFieldSensor),
                res.getString(R.string.lightSensor), res.getString(R.string.proximitySensor), res.getString(R.string.accelerometerSensor), res.getString(R.string.humiditySensor), res.getString(R.string.pressureSensor)};

        CustomAdapter adapter = new CustomAdapter(getApplicationContext(),sensorNames, sensorImages);
        grid = findViewById(R.id.grid);
        grid.setAdapter(adapter);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //Managing several sensors
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                switch(position){
                    case 0:
                        goThisSensor(Sensor.TYPE_STEP_COUNTER, SensorStepCounterActivity.class, R.string.withoutStepCounterSensor);
                        break;
                    case 1:
                        goThisSensor(Sensor.TYPE_MAGNETIC_FIELD, SensorMagneticFieldActivity.class, R.string.withoutGeomagneticFieldSensor);
                        break;
                    case 2:
                        goThisSensor(Sensor.TYPE_LIGHT, SensorLightActivity.class, R.string.withoutLightSensor);
                        break;
                    case 3:
                        goThisSensor(Sensor.TYPE_PROXIMITY, SensorProximityActivity.class, R.string.withoutProximitySensor);
                        break;
                    case 4:
                        goThisSensor(Sensor.TYPE_ACCELEROMETER, SensorAccelerometerActivity.class, R.string.withoutAccelerometerSensor);
                        break;
                    case 5:
                        goThisSensor(Sensor.TYPE_RELATIVE_HUMIDITY, SensorHumidityActivity.class, R.string.withoutHumiditySensor);
                    case 6:
                        goThisSensor(Sensor.TYPE_PRESSURE, SensorPressureActivity.class, R.string.withoutPressureSensor);
                }
            }
        });


    }

    private void goThisSensor(int type, Class activity, int message) {
        mSensor = mSensorManager.getDefaultSensor(type);
        if(mSensor != null) {
            Intent intent = new Intent(this, activity);
            startActivity(intent);
        } else {
            Toast.makeText(this,res.getString(message), Toast.LENGTH_LONG).show();
        }
    }


}
