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
import java.util.List;

public class EnvironmentActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mSensorLight;
    private CardView mCardLight;
    private Button mButtonLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        myToolbar.setTitle(R.string.environmentalSensors);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorOnPrimary));
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mButtonLight = (Button) findViewById(R.id.btnHistoryLight);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        //Setting Light Card

        mCardLight = (CardView) findViewById(R.id.cardLight);

        if(mSensorLight != null) {
            mCardLight.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    goSensorLight();
                }
            });
        } else {
            mCardLight.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(EnvironmentActivity.this, getResources().getString(R.string.withoutLightSensor), Toast.LENGTH_LONG).show();
                }
            });
        }


        mButtonLight.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                goHistory();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    private void goSensorLight() {
        Intent intent = new Intent(this, SensorLightActivity.class);
        startActivity(intent);
    }

    private void goSensorHumidity(){
        Intent intent = new Intent(this, SensorHumidityActivity.class);
        startActivity(intent);
    }

    private void goHistory() {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra(DetectionOpenHelper.SENSOR_TYPE, Sensor.TYPE_LIGHT);
        startActivity(intent);
    }
}
