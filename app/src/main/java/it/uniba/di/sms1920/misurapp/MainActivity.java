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
        sensorImages = new int[] {R.drawable.stepcounter_main, R.drawable.image_rotation_sensor,
        R.drawable.light_main, R.drawable.proximity_main, R.drawable.accelerometer_main, R.drawable.window, R.drawable.pressure_main,
                R.drawable.temperature_main};

        sensorNames = new String[] {res.getString(R.string.stepCounterSensor), res.getString(R.string.rotationSensor),
                res.getString(R.string.lightSensor), res.getString(R.string.proximitySensor), res.getString(R.string.accelerometerSensor), res.getString(R.string.humiditySensor), res.getString(R.string.pressureSensor),
                res.getString(R.string.temperatureSensor)};

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
