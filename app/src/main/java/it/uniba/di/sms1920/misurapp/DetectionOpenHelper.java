package it.uniba.di.sms1920.misurapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DetectionOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "MisurApp";
    public static final String DETECTION_TABLE = "detection";
    public static final String ID = "_id";
    public static final String SENSOR_TYPE = "sensor_type";
    public static final String DATETIME = "datetime";
    public static final String VALUE1 = "value1";
    public static final String VALUE2 = "value2";
    public static final String VALUE3 = "value3";
    public static final String[] PROJECTION = {ID, DATETIME, VALUE1, VALUE2, VALUE3};
    private static final String CREATE_TABLE =
            "CREATE TABLE " + DETECTION_TABLE + "(" +
                    ID + " integer primary key autoincrement, " +
                    SENSOR_TYPE + " integer not null, " +
                    DATETIME + " varchar(50) not null, " +
                    VALUE1 + " decimal(10,2) not null, " +
                    VALUE2 + " decimal(10,2), " +
                    VALUE3 + " decimal(10,2)" +
            ");";

    public DetectionOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersione) {

    }

}
