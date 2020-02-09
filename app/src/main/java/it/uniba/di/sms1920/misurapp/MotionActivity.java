package it.uniba.di.sms1920.misurapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MotionActivity extends AppCompatActivity {


    private Button buttonProximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion);

        buttonProximity =  (Button) findViewById(R.id.btnSensorProximity);


        buttonProximity.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v){

                goToProximitySensorActivity();

            }

        });


    }

    private void goToProximitySensorActivity(){
        Intent intent = new Intent(this, SensorProximityActivity.class);
        startActivity(intent);
    }

}
