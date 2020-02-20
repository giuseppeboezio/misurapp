package it.uniba.di.sms1920.misurapp;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class VectorialDetectionAdapter extends ScalarDetectionAdapter {

    public VectorialDetectionAdapter(Context context, Cursor c, int sensorType) {
        super(context, c, sensorType);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = layoutInflater.inflate(R.layout.detection_row_vectorial, parent, false);
        return v;
    }

    @Override
    public void bindView(View v, Context context, Cursor c) {
        super.bindView(v,context, c);

        String value2 = c.getString(c.getColumnIndexOrThrow(DetectionOpenHelper.VALUE2));
        String value3 = c.getString(c.getColumnIndexOrThrow(DetectionOpenHelper.VALUE3));
        Log.i("Value2", value2);

        TextView txtValue2 = (TextView) v.findViewById(R.id.value2);
        txtValue2.setText(value2);

        TextView txtValue3 = (TextView) v.findViewById(R.id.value3);
        txtValue3.setText(value3);

    }
}
