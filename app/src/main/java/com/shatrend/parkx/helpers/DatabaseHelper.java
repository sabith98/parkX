package com.shatrend.parkx.helpers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shatrend.parkx.models.Driver;
import com.shatrend.parkx.models.Parking;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ParkX.db";
    public static final int DATABASE_VERSION = 1;

    // Table names
    public static final String DRIVER_TABLE = "DRIVER_TABLE";
    public static final String PARKING_TABLE = "PARKING_TABLE";

    // DRIVER TABLE columns
    public static final String DRIVER_TABLE_COL_1 = "_ID";
    public static final String DRIVER_TABLE_COL_2 = "EMAIL";
    public static final String DRIVER_TABLE_COL_3 = "PASSWORD";

    // PARKING TABLE columns
    public static final String PARKING_TABLE_COL_ID = "_ID";
    public static final String PARKING_TABLE_COL_EMAIL = "EMAIL";
    public static final String PARKING_TABLE_COL_PASSWORD = "PASSWORD";

    // Queries for creating new Tables
//    public static final String DRIVER_TABLE_CREATE_QUERY = "CREATE TABLE " + DRIVER_TABLE
//            + " (" + DRIVER_TABLE_COL_1 + " INTEGER " + "PRIMARY KEY AUTOINCREMENT, "
//            + DRIVER_TABLE_COL_2 + " TEXT, " + DRIVER_TABLE_COL_3 + " TEXT)";
    public static final String DRIVER_TABLE_CREATE_QUERY = "CREATE TABLE " + DRIVER_TABLE
            + "(" + DRIVER_TABLE_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DRIVER_TABLE_COL_2 + " TEXT, " + DRIVER_TABLE_COL_3 + " TEXT" + ")";

    public static final String PARKING_TABLE_CREATE_QUERY = "CREATE TABLE " + PARKING_TABLE
            + "(" + PARKING_TABLE_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PARKING_TABLE_COL_EMAIL + " TEXT, " + PARKING_TABLE_COL_PASSWORD + " TEXT" + ")";

    public static final String CREATE_PARKING_TABLE_QUERY = "CREATE TABLE " + PARKING_TABLE +
            "(" + PARKING_TABLE_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PARKING_TABLE_COL_EMAIL + " TEXT, " + PARKING_TABLE_COL_PASSWORD + " TEXT" + ")";

//    public static final String PARKING_TABLE = "PARKING_TABLE";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PARKING_TABLE_QUERY);
        sqLiteDatabase.execSQL(DRIVER_TABLE_CREATE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DRIVER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PARKING_TABLE);
        onCreate(sqLiteDatabase);
    }

//    public boolean createTask(String name){
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_2, name);
//        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
//        if(result == -1){
//            return false;
//        } else {
//            return true;
//        }
//    }

    // Register new driver account
//    public boolean registerDriver(String email, String password) {
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DRIVER_TABLE_COL_2, email);
//        contentValues.put(DRIVER_TABLE_COL_3, password);
//        long result = sqLiteDatabase.insert(DRIVER_TABLE, null, contentValues);
//        if (result == -1) {
//            return false;
//        }else {
//            return true;
//        }
//    }

    // Register new driver account
    public boolean addDriver(Driver driver) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DRIVER_TABLE_COL_2, driver.getEmail());
        contentValues.put(DRIVER_TABLE_COL_3, driver.getPassword());

        long result = db.insert(DRIVER_TABLE, null, contentValues);
        db.close();

        return result != -1;
    }

    // Retrieve driver details using email for login
    public Driver getDriverByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DRIVER_TABLE,
                new String[] { DRIVER_TABLE_COL_1, DRIVER_TABLE_COL_2, DRIVER_TABLE_COL_3 },
                DRIVER_TABLE_COL_2 + " = ?",
                new String[] { email },
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            @SuppressLint("Range") Driver driver = new Driver(
                    cursor.getInt(cursor.getColumnIndex(DRIVER_TABLE_COL_1)),
                    cursor.getString(cursor.getColumnIndex(DRIVER_TABLE_COL_2)),
                    cursor.getString(cursor.getColumnIndex(DRIVER_TABLE_COL_3))
            );

            cursor.close();
            db.close();

            return driver;
        }

        return null;
    }

    //  Register new parking account
    public boolean addParking(Parking parking) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PARKING_TABLE_COL_EMAIL, parking.getEmail());
        contentValues.put(PARKING_TABLE_COL_PASSWORD, parking.getPassword());

        long result = db.insert(PARKING_TABLE, null, contentValues);
        db.close();

        return result != -1;
    }

    // Retrieve parking details using email for login
    public Parking getParkingByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(PARKING_TABLE,
                new String[] { PARKING_TABLE_COL_ID, PARKING_TABLE_COL_EMAIL, PARKING_TABLE_COL_PASSWORD },
                PARKING_TABLE_COL_EMAIL + " = ?",
                new String[] { email },
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            @SuppressLint("Range") Parking parking = new Parking(
                    cursor.getInt(cursor.getColumnIndex(PARKING_TABLE_COL_ID)),
                    cursor.getString(cursor.getColumnIndex(PARKING_TABLE_COL_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(PARKING_TABLE_COL_PASSWORD))
            );

            cursor.close();
            db.close();

            return parking;
        }

        return null;
    }


//    public Cursor readTask(){
//        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
//        Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME,null);
//        return result;
//    }

//    public boolean updateTasks(String id, String name){
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_1, id);
//        contentValues.put(COL_2, name);
//        long count = sqLiteDatabase.update(TABLE_NAME, contentValues, "Task_ID = ?", new String[]{id});
//        if(count > 0){
//            return false;
//        } else {
//            return true;
//        }
//    }

//    public int deleteTasks(String id){
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        return sqLiteDatabase.delete(TABLE_NAME, "Task_ID = ? ", new String[]{id});
//    }
}
