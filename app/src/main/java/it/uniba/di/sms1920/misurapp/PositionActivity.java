package it.uniba.di.sms1920.misurapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PositionActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mSensorProximity;
    private Sensor mSensorMagneticField;
    private CardView mCardProximity;
    private CardView mCardGeomagnetic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        myToolbar.setLogo(R.mipmap.ic_launcher_round);
        myToolbar.setTitle(R.string.app_name);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorOnPrimary));
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorMagneticField = mSensorManager.getDefaultSensor((Sensor.TYPE_MAGNETIC_FIELD));

        //Setting Proximity Card

        mCardProximity = (CardView) findViewById(R.id.cardProximity);

        if(mSensorProximity != null){
            mCardProximity.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v){
                    goSensorProximity();
                }
            });
        } else {
            mCardProximity.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PositionActivity.this, getResources().getString(R.string.withoutProximitySensor), Toast.LENGTH_LONG).show();
                }
            });
        }


        //Setting Proximity Card

        mCardGeomagnetic = (CardView) findViewById(R.id.cardGeomagnetic);
        if(mSensorMagneticField != null){
            mCardGeomagnetic.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    goSensorMagneticField();
                }
            });
        } else {
            mCardGeomagnetic.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PositionActivity.this, getResources().getString(R.string.withoutGeomagneticFieldSensor), Toast.LENGTH_LONG).show();
                }
            });
        }



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }


    private void goSensorProximity() {
        Intent intent = new Intent(this, SensorProximityActivity.class);
        startActivity(intent);
    }

    private void goSensorMagneticField() {
        Intent intent = new Intent(this, SensorMagneticFieldActivity.class);
        startActivity(intent);
    }
}
