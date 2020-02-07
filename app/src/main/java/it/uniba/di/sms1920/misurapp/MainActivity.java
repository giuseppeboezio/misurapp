package it.uniba.di.sms1920.misurapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {

    private CardView mEnvironmentalSensors;
    private CardView mPositionSensors;
    private CardView mMotionSensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEnvironmentalSensors = (CardView) findViewById(R.id.cardEnvironment);
        mPositionSensors = (CardView) findViewById(R.id.cardPosition);
        mMotionSensors = (CardView) findViewById(R.id.cardMotion);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setLogo(R.mipmap.ic_launcher_round);
        myToolbar.setTitle(R.string.app_name);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorOnPrimary));
        setSupportActionBar(myToolbar);

        mEnvironmentalSensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goEnvironmentalSensors();
            }
        });

        mPositionSensors.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goPositionSensors();
            }
        });

        mMotionSensors.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goMotionSensors();
            }
        });
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
