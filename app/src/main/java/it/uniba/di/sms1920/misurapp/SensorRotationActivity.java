package it.uniba.di.sms1920.misurapp;


import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.HashSet;
import java.util.Set;

public class SensorRotationActivity extends AppCompatActivity implements SensorEventListener {

    private static final int ROUND_CORNER = 360;
    ImageView compass_img;
    int mAzimuth;
    private SensorManager mSensorManager;
    private Sensor mRotationV;
    float[] rMat = new float[9];
    float[] orientation = new float[3];
    private TextView showRotationData;
    private Set<Detection> detections;
    private Button mBtnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_rotation);

        mBtnHistory = findViewById(R.id.btnHistory);

        mBtnHistory.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                goHistory();
            }
        });

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        myToolbar.setTitle(R.string.rotationSensor);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorOnPrimary));
        setSupportActionBar(myToolbar);

        showRotationData = findViewById(R.id.rotationData);
        detections = new HashSet<Detection>();

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mRotationV = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        compass_img = findViewById(R.id.boat);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        SensorManager.getRotationMatrixFromVector(rMat, event.values);
        mAzimuth = (int) (Math.toDegrees(SensorManager.getOrientation(rMat, orientation)[0]) + ROUND_CORNER) % ROUND_CORNER;

        SensorManager.getOrientation(rMat, orientation);
        mAzimuth = (int) (Math.toDegrees(SensorManager.getOrientation(rMat, orientation)[0]) + ROUND_CORNER) % ROUND_CORNER;

        mAzimuth = Math.round(mAzimuth);
        showRotationData.setText(mAzimuth + getString(R.string.unit_rotation));
        compass_img.setRotation(-mAzimuth);

        Detection rotationDetection = new Detection();
        detections.add(rotationDetection);

        rotationDetection.setSensorType(event.sensor.getType());
        rotationDetection.setDateTimeDetection(Detection.getFormattedDatetime(event.timestamp, this));
        rotationDetection.setValues(event.values);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sensor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.saveIcon) {
            saveDetection();
        } else {
            finish();
        }
        return true;
    }

    private void saveDetection() {
        for(Detection det: detections) {
            DetectionSQLite.add(this, det);
        }
        int quantity = detections.size();
        String message = getResources().getQuantityString(R.plurals.saveMessage, quantity, quantity);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        detections.clear();
    }


    private void goHistory() {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra(DetectionOpenHelper.SENSOR_TYPE, Sensor.TYPE_ROTATION_VECTOR);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mRotationV, SensorManager.SENSOR_DELAY_NORMAL);
    }

}