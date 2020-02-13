package it.uniba.di.sms1920.misurapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

        //Creating gridview to display whole set sensors
        sensorImages = new int[] {R.drawable.biker_in_the_city, R.drawable.image_geomagnetic_sensor,
        R.drawable.policeman, R.drawable.proximity_image, R.drawable.rocket_with_galaxy};

        sensorNames = new String[] {res.getString(R.string.stepCounterSensor), res.getString(R.string.geomagneticFieldSensor),
                res.getString(R.string.lightSensor), res.getString(R.string.proximitySensor), res.getString(R.string.accelerometerSensor)};

        CustomAdapter adapter = new CustomAdapter(getApplicationContext(),sensorNames, sensorImages);
        grid = findViewById(R.id.grid);
        grid.setAdapter(adapter);

        //Managing several sensors
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                switch(position){

                }
            }
        });


    }

}
