package it.uniba.di.sms1920.misurapp;

import android.content.Context;
import android.database.Cursor;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class ScalarDetectionAdapter extends CursorAdapter {

    protected LayoutInflater layoutInflater;
    private Context mContext;
    private int mTypeSensor;

    public ScalarDetectionAdapter(Context context, Cursor c, int sensorType) {
        super(context, c);
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
        mTypeSensor = sensorType;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = layoutInflater.inflate(R.layout.detection_row_scalar, parent, false);
        return v;
    }

    @Override
    public void bindView(View v, Context context, Cursor c) {
        String id = c.getString(c.getColumnIndexOrThrow(DetectionOpenHelper.ID));
        String datetime = c.getString(c.getColumnIndexOrThrow(DetectionOpenHelper.DATETIME));
        String value = c.getString(c.getColumnIndexOrThrow(DetectionOpenHelper.VALUE1));

        TextView txtId = (TextView) v.findViewById(R.id.id);
        txtId.setText(id);

        TextView txtDatetime = (TextView) v.findViewById(R.id.datetime);
        txtDatetime.setText(datetime);

        TextView txtValue = (TextView) v.findViewById(R.id.value1);
        txtValue.setText(value);

        TextView txtUnit = (TextView) v.findViewById(R.id.unit);

        switch(mTypeSensor){
            case Sensor.TYPE_STEP_COUNTER:
                String message = mContext.getResources().getQuantityString(R.plurals.numberSteps, Integer.parseInt(value), Integer.parseInt(value));
                txtUnit.setText(message);
                break;
            case Sensor.TYPE_LIGHT:
                txtUnit.setText(R.string.unit_light);
                break;
            case Sensor.TYPE_PROXIMITY:
                txtUnit.setText(R.string.unit_proximity);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                txtUnit.setText(R.string.unit_accelerometer);
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                txtUnit.setText(R.string.unit_humidity);
                break;
            case Sensor.TYPE_PRESSURE:
                txtUnit.setText(R.string.unit_pressure);
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                txtUnit.setText(R.string.unit_temperature);
                break;
        }
    }
}
