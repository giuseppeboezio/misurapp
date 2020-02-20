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


    public final static int NUM_SENSORS = 8;
    Resources res;
    GridView grid;
    SensorManager mSensorManager;
    Sensor mSensor;
    private int[] mSensorTypes;
    private int[] mSensorAvailable;
    private int[] mSensorNotAvailable;
    private int[] mSensorMainImages;
    private String[] mSensorNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setLogo(R.mipmap.ic_launcher_round);
        myToolbar.setTitle(R.string.app_name);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorOnPrimary));
        setSupportActionBar(myToolbar);

        res = getResources();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorMainImages = new int[NUM_SENSORS];


        mSensorTypes = new int []{Sensor.TYPE_STEP_COUNTER, Sensor.TYPE_ROTATION_VECTOR, Sensor.TYPE_LIGHT,
        Sensor.TYPE_PROXIMITY, Sensor.TYPE_ACCELEROMETER, Sensor.TYPE_RELATIVE_HUMIDITY, Sensor.TYPE_PRESSURE,
        Sensor.TYPE_AMBIENT_TEMPERATURE};

        mSensorAvailable = new int[] {R.drawable.stepcounter_main, R.drawable.image_rotation_sensor,
        R.drawable.light_main, R.drawable.proximity_main, R.drawable.accelerometer_main, R.drawable.window, R.drawable.pressure_main,
                R.drawable.temperature_main};

        mSensorNotAvailable = new int[] {R.drawable.no_stepcounter_main, R.drawable.no_rotation_main, R.drawable.no_light_main,
        R.drawable.no_proximity_main, R.drawable.no_accelerometer_main, R.drawable.no_humidity_main, R.drawable.no_pressure_main,
        R.drawable.no_temperature_main};

        mSensorNames = new String[] {res.getString(R.string.stepCounterSensor), res.getString(R.string.rotationSensor),
                res.getString(R.string.lightSensor), res.getString(R.string.proximitySensor), res.getString(R.string.accelerometerSensor), res.getString(R.string.humiditySensor), res.getString(R.string.pressureSensor),
                res.getString(R.string.temperatureSensor)};

        for(int i = 0; i < NUM_SENSORS; i++) {
            if(mSensorManager.getDefaultSensor(mSensorTypes[i]) != null) {
                mSensorMainImages[i] = mSensorAvailable[i];
            }
            else {
                mSensorMainImages[i] = mSensorNotAvailable[i];
            }
        }

        CustomAdapter adapter = new CustomAdapter(getApplicationContext(),mSensorNames, mSensorMainImages);
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
                        goThisSensor(Sensor.TYPE_ROTATION_VECTOR, SensorRotationActivity.class, R.string.withoutRotationSensor);
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
                        break;
                    case 6:
                        goThisSensor(Sensor.TYPE_PRESSURE, SensorPressureActivity.class, R.string.withoutPressureSensor);
                        break;
                    case 7:
                        goThisSensor(Sensor.TYPE_AMBIENT_TEMPERATURE, SensorTemperatureActivity.class, R.string.withoutTemperatureSensor );
                        break;
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
