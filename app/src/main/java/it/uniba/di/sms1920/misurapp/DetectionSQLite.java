package it.uniba.di.sms1920.misurapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DetectionSQLite {

    private static DetectionOpenHelper mDetection;
    private static SQLiteDatabase db;

    public static void add(Context context, Detection detection) {

        mDetection = new DetectionOpenHelper(context);
        db = mDetection.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DetectionOpenHelper.SENSOR_TYPE, detection.getSensorType());
        values.put(DetectionOpenHelper.DATETIME, detection.getDateTimeDetection());
        values.put(DetectionOpenHelper.VALUE1, detection.getValues(0));
        values.put(DetectionOpenHelper.VALUE2, detection.getValues(1));
        values.put(DetectionOpenHelper.VALUE3, detection.getValues(2));

        db.insert(DetectionOpenHelper.DETECTION_TABLE, null, values);

    }

    public static Cursor getAllDetection(Context context, int sensorType) {

        Cursor cursor;

        mDetection = new DetectionOpenHelper(context);
        db = mDetection.getReadableDatabase();

        cursor = db.query(DetectionOpenHelper.DETECTION_TABLE, DetectionOpenHelper.PROJECTION,
                DetectionOpenHelper.SENSOR_TYPE + "=?", new String[]{String.valueOf(sensorType)},null,
                null, DetectionOpenHelper.DATETIME);

        return  cursor;
    }

}
