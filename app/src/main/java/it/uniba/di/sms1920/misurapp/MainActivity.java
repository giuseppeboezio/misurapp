package it.uniba.di.sms1920.misurapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {

    ImageView mEnvironmentalSensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEnvironmentalSensors = findViewById(R.id.imageEnvironmentalSensors);

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
    }

    private void goEnvironmentalSensors() {
        Intent intent = new Intent(this, EnvironmentActivity.class);
        startActivity(intent);
    }
}
