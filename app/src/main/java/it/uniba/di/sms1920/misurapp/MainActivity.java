package it.uniba.di.sms1920.misurapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.GridView;


import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {


    Resources res;
    GridView grid;
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

        sensorImages = new int[] {R.drawable.biker_in_the_city, R.drawable.image_geomagnetic_sensor,
        R.drawable.policeman, R.drawable.proximity_image, R.drawable.rocket_with_galaxy};

        sensorNames = new String[] {res.getString(R.string.stepCounterSensor), res.getString(R.string.geomagneticFieldSensor),
                res.getString(R.string.lightSensor), res.getString(R.string.proximitySensor), res.getString(R.string.accelerometerSensor)};

        CustomAdapter adapter = new CustomAdapter(getApplicationContext(),sensorNames, sensorImages);
        grid = findViewById(R.id.grid);
        grid.setAdapter(adapter);


    }

    private void goEnvironmentalSensors() {
        Intent intent = new Intent(this, EnvironmentActivity.class);
        startActivity(intent);
    }

    private void goPositionSensors() {
        Intent intent = new Intent(this, PositionActivity.class);
        startActivity(intent);
    }

    private void goMotionSensors(){
        Intent intent = new Intent(this, MotionActivity.class);
        startActivity(intent);
    }
}
