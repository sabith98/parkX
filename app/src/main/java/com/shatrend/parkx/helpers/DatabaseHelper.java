package com.shatrend.parkx.helpers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.shatrend.parkx.models.Driver;
import com.shatrend.parkx.models.Parking;
import com.shatrend.parkx.models.ParkingInfo;
import com.shatrend.parkx.models.ParkingLocation;
import com.shatrend.parkx.models.ParkingRate;
import com.shatrend.parkx.models.ParkingSlots;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ParkX.db";
    public static final int DATABASE_VERSION = 1;

    // Table names
    public static final String DRIVER_TABLE = "DRIVER_TABLE";
    public static final String PARKING_TABLE = "PARKING_TABLE";
    public static final String PARKING_INFO_TABLE = "PARKING_INFO";
    public static final String PARKING_LOCATION_TABLE = "PARKING_LOCATION";
    public static final String PARKING_RATE_TABLE = "PARKING_RATE";
    public static final String PARKING_SLOTS_TABLE = "PARKING_SLOTS";

    // DRIVER TABLE columns
    public static final String DRIVER_TABLE_COL_1 = "_ID";
    public static final String DRIVER_TABLE_COL_2 = "EMAIL";
    public static final String DRIVER_TABLE_COL_3 = "PASSWORD";

    // PARKING TABLE columns
    public static final String PARKING_TABLE_COL_ID = "_ID";
    public static final String PARKING_TABLE_COL_EMAIL = "EMAIL";
    public static final String PARKING_TABLE_COL_PASSWORD = "PASSWORD";

    // PARKING INFO TABLE columns
    public static final String PARKING_INFO_TABLE_COL_PARKING_ID = "PARKING_ID";
    public static final String PARKING_INFO_TABLE_COL_NAME = "PARKING_NAME";
    public static final String PARKING_INFO_TABLE_COL_IMAGE = "PARKING_IMAGE";
    public static final String PARKING_INFO_TABLE_COL_PHONE = "PARKING_PHONE";

    // PARKING LOCATION TABLE columns
    public static final String PARKING_LOCATION_TABLE_COL_PARKING_ID = "PARKING_ID";
    public static final String PARKING_LOCATION_TABLE_COL_LATITUDE = "LATITUDE";
    public static final String PARKING_LOCATION_TABLE_COL_LONGITUDE = "LONGITUDE";

    // PARKING RATE TABLE columns
    public static final String PARKING_RATE_TABLE_COL_PARKING_ID = "PARKING_ID";
    public static final String PARKING_RATE_TABLE_COL_BIKE_RATE = "BIKE_RATE";
    public static final String PARKING_RATE_TABLE_COL_3WHEELER_RATE = "THREE_WHEELER_RATE";
    public static final String PARKING_RATE_TABLE_COL_4WHEELER_RATE = "FOUR_WHEELER_RATE";

    // PARKING SLOTS TABLE columns
    public static final String PARKING_SLOTS_TABLE_COL_PARKING_ID = "PARKING_ID";
    public static final String PARKING_SLOTS_TABLE_COL_BIKE_SLOTS = "BIKE_SLOTS";
    public static final String PARKING_SLOTS_TABLE_COL_3WHEELER_SLOTS = "THREE_WHEELER_SLOTS";
    public static final String PARKING_SLOTS_TABLE_COL_4WHEELER_SLOTS = "FOUR_WHEELER_SLOTS";

    public static final String PARKING_SLOTS_TABLE_COL_BIKE_FREE_SLOTS = "BIKE_FREE_SLOTS";
    public static final String PARKING_SLOTS_TABLE_COL_3WHEELER_FREE_SLOTS = "THREE_WHEELER_FREE_SLOTS";
    public static final String PARKING_SLOTS_TABLE_COL_4WHEELER_FREE_SLOTS = "FOUR_WHEELER_FREE_SLOTS";

    // Queries for creating new Tables
    public static final String DRIVER_TABLE_CREATE_QUERY = "CREATE TABLE " + DRIVER_TABLE
            + "(" + DRIVER_TABLE_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DRIVER_TABLE_COL_2 + " TEXT, " + DRIVER_TABLE_COL_3 + " TEXT" + ")";

    public static final String CREATE_PARKING_TABLE_QUERY = "CREATE TABLE " + PARKING_TABLE +
            "(" + PARKING_TABLE_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PARKING_TABLE_COL_EMAIL + " TEXT, " + PARKING_TABLE_COL_PASSWORD + " TEXT" + ")";

    public static final String CREATE_PARKING_INFO_TABLE_QUERY = "CREATE TABLE " + PARKING_INFO_TABLE +
            "(" + PARKING_INFO_TABLE_COL_PARKING_ID + " INTEGER PRIMARY KEY, "
                + PARKING_INFO_TABLE_COL_PHONE + " TEXT, "
                + PARKING_INFO_TABLE_COL_NAME + " TEXT, "
                + PARKING_INFO_TABLE_COL_IMAGE + " BLOB, "
                + "FOREIGN KEY(" + PARKING_INFO_TABLE_COL_PARKING_ID + ") "
                + "REFERENCES " + PARKING_TABLE + "( " + PARKING_TABLE_COL_ID + ")"
            + ")";

    public static final String CREATE_PARKING_LOCATION_TABLE_QUERY = "CREATE TABLE " + PARKING_LOCATION_TABLE +
            " (" + PARKING_LOCATION_TABLE_COL_PARKING_ID + " INTEGER PRIMARY KEY, "
            + PARKING_LOCATION_TABLE_COL_LATITUDE + " REAL, "
            + PARKING_LOCATION_TABLE_COL_LONGITUDE + " REAL, "
            + "FOREIGN KEY(" + PARKING_LOCATION_TABLE_COL_PARKING_ID + ") REFERENCES "
            + PARKING_TABLE + "(" + PARKING_TABLE_COL_ID + ")"
            + ")";

    public static final String CREATE_PARKING_RATE_TABLE_QUERY = "CREATE TABLE " + PARKING_RATE_TABLE +
            " (" + PARKING_RATE_TABLE_COL_PARKING_ID + " INTEGER PRIMARY KEY, "
            + PARKING_RATE_TABLE_COL_BIKE_RATE + " REAL, "
            + PARKING_RATE_TABLE_COL_3WHEELER_RATE + " REAL, "
            + PARKING_RATE_TABLE_COL_4WHEELER_RATE + " REAL, "
            + "FOREIGN KEY(" + PARKING_RATE_TABLE_COL_PARKING_ID + ") REFERENCES "
            + PARKING_TABLE + "(" + PARKING_TABLE_COL_ID + ")"
            + ")";

    public static final String CREATE_PARKING_SLOTS_TABLE_QUERY = "CREATE TABLE " + PARKING_SLOTS_TABLE +
            "(" + PARKING_SLOTS_TABLE_COL_PARKING_ID + " INTEGER PRIMARY KEY, "
            + PARKING_SLOTS_TABLE_COL_BIKE_SLOTS + " INTEGER, "
            + PARKING_SLOTS_TABLE_COL_3WHEELER_SLOTS + " INTEGER, "
            + PARKING_SLOTS_TABLE_COL_4WHEELER_SLOTS + " INTEGER, "
            + PARKING_SLOTS_TABLE_COL_BIKE_FREE_SLOTS + " INTEGER, "
            + PARKING_SLOTS_TABLE_COL_3WHEELER_FREE_SLOTS + " INTEGER, "
            + PARKING_SLOTS_TABLE_COL_4WHEELER_FREE_SLOTS + " INTEGER, "
            + "FOREIGN KEY (" + PARKING_SLOTS_TABLE_COL_PARKING_ID + ") "
            + "REFERENCES " + PARKING_TABLE + "( " + PARKING_TABLE_COL_ID + ") "
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON;");
        sqLiteDatabase.execSQL(CREATE_PARKING_TABLE_QUERY);
        sqLiteDatabase.execSQL(DRIVER_TABLE_CREATE_QUERY);
        sqLiteDatabase.execSQL(CREATE_PARKING_INFO_TABLE_QUERY);
        sqLiteDatabase.execSQL(CREATE_PARKING_LOCATION_TABLE_QUERY);
        sqLiteDatabase.execSQL(CREATE_PARKING_RATE_TABLE_QUERY);
        sqLiteDatabase.execSQL(CREATE_PARKING_SLOTS_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DRIVER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PARKING_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PARKING_INFO_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PARKING_LOCATION_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PARKING_RATE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PARKING_SLOTS_TABLE);
        onCreate(sqLiteDatabase);
    }

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

    // Check whether driver email existence
    public boolean isDriverEmailExist(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {DRIVER_TABLE_COL_1};
        String selection = DRIVER_TABLE_COL_2 + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(DRIVER_TABLE, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();

        return count > 0;
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
    public long addParking(Parking parking) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PARKING_TABLE_COL_EMAIL, parking.getEmail());
        contentValues.put(PARKING_TABLE_COL_PASSWORD, parking.getPassword());

        long result = db.insert(PARKING_TABLE, null, contentValues);
        db.close();

//        return result != -1;
        if (result != -1) return result;
        else return -1;
    }

    // Check whether parking email already exists
    public boolean isParkingEmailExist(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {PARKING_TABLE_COL_ID};
        String selection = PARKING_TABLE_COL_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(PARKING_TABLE, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();

        return count > 0;
    }

    // Store parking information in db
    public boolean addParkingInfo(long parkingId, ParkingInfo pi, ParkingLocation pl, ParkingRate pr, ParkingSlots ps) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Insert data into parking info table
        ContentValues parkingInfoValues = new ContentValues();
        parkingInfoValues.put(PARKING_INFO_TABLE_COL_PARKING_ID, parkingId);
        parkingInfoValues.put(PARKING_INFO_TABLE_COL_IMAGE, pi.getParkingImage());
        parkingInfoValues.put(PARKING_INFO_TABLE_COL_NAME, pi.getParkingName());
        parkingInfoValues.put(PARKING_INFO_TABLE_COL_PHONE, pi.getParkingPhone());

        // Insert data into parking location table
        ContentValues parkingLocationValues = new ContentValues();
        parkingLocationValues.put(PARKING_LOCATION_TABLE_COL_PARKING_ID, parkingId);
        parkingLocationValues.put(PARKING_LOCATION_TABLE_COL_LATITUDE, pl.getLatitude());
        parkingLocationValues.put(PARKING_LOCATION_TABLE_COL_LONGITUDE, pl.getLongitude());

        // Insert data into parking rate table
        ContentValues parkingRateValues = new ContentValues();
        parkingRateValues.put(PARKING_RATE_TABLE_COL_PARKING_ID, parkingId);
        parkingRateValues.put(PARKING_RATE_TABLE_COL_BIKE_RATE, pr.getBikeRate());
        parkingRateValues.put(PARKING_RATE_TABLE_COL_3WHEELER_RATE, pr.getThreeWheelerRate());
        parkingRateValues.put(PARKING_RATE_TABLE_COL_4WHEELER_RATE, pr.getFourWheelerRate());

        // Insert data into parking slots table
        ContentValues parkingSlotsValues = new ContentValues();
        parkingSlotsValues.put(PARKING_SLOTS_TABLE_COL_PARKING_ID, parkingId);

        parkingSlotsValues.put(PARKING_SLOTS_TABLE_COL_BIKE_SLOTS, ps.getBikeSlots());
        parkingSlotsValues.put(PARKING_SLOTS_TABLE_COL_3WHEELER_SLOTS, ps.getThreeWheelerSlots());
        parkingSlotsValues.put(PARKING_SLOTS_TABLE_COL_4WHEELER_SLOTS, ps.getFourWheelerSlots());

        parkingSlotsValues.put(PARKING_SLOTS_TABLE_COL_BIKE_FREE_SLOTS, ps.getBikeFreeSlots());
        parkingSlotsValues.put(PARKING_SLOTS_TABLE_COL_3WHEELER_FREE_SLOTS, ps.getThreeWheelerFreeSlots());
        parkingSlotsValues.put(PARKING_SLOTS_TABLE_COL_4WHEELER_FREE_SLOTS, ps.getFourWheelerFreeSlots());

        long resultInfo = db.insert(PARKING_INFO_TABLE, null, parkingInfoValues);
        long resultLocation = db.insert(PARKING_LOCATION_TABLE, null, parkingLocationValues);
        long resultRate = db.insert(PARKING_RATE_TABLE, null, parkingRateValues);
        long resultSlots = db.insert(PARKING_SLOTS_TABLE, null, parkingSlotsValues);

        if (resultInfo != -1 & resultLocation != -1 & resultRate != -1 & resultSlots != -1) return true;
        else return false;
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

    // Retrieve parking slots count details from PARKING_SLOTS table
    public ParkingSlots getParkingSlots(int parkingId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(PARKING_SLOTS_TABLE,
                new String[] {
                        PARKING_SLOTS_TABLE_COL_PARKING_ID,
                        PARKING_SLOTS_TABLE_COL_BIKE_SLOTS,
                        PARKING_SLOTS_TABLE_COL_3WHEELER_SLOTS,
                        PARKING_SLOTS_TABLE_COL_4WHEELER_SLOTS,
                        PARKING_SLOTS_TABLE_COL_BIKE_FREE_SLOTS,
                        PARKING_SLOTS_TABLE_COL_3WHEELER_FREE_SLOTS,
                        PARKING_SLOTS_TABLE_COL_4WHEELER_FREE_SLOTS
                },
                PARKING_SLOTS_TABLE_COL_PARKING_ID + " = ?",
                new String[] {String.valueOf(parkingId)},
                null, null, null, null);

        Log.d("PARKING_SPOTS_ROWS", String.valueOf(cursor.getCount()));

        if (cursor != null) {
            cursor.moveToFirst();
            @SuppressLint("Range") ParkingSlots parkingSlots = new ParkingSlots(
                    cursor.getInt(cursor.getColumnIndexOrThrow(PARKING_SLOTS_TABLE_COL_PARKING_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(PARKING_SLOTS_TABLE_COL_BIKE_SLOTS)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(PARKING_SLOTS_TABLE_COL_3WHEELER_SLOTS)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(PARKING_SLOTS_TABLE_COL_4WHEELER_SLOTS)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(PARKING_SLOTS_TABLE_COL_BIKE_FREE_SLOTS)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(PARKING_SLOTS_TABLE_COL_3WHEELER_FREE_SLOTS)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(PARKING_SLOTS_TABLE_COL_4WHEELER_FREE_SLOTS))
            );
            cursor.close();
            db.close();

            return parkingSlots;
        }
        return null;
    }

    // Get all the nearby parking location data
    public List<ParkingLocation> getNearbyParking(double latitude, double longitude, double distance) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                PARKING_INFO_TABLE + "." + PARKING_INFO_TABLE_COL_PARKING_ID,
                PARKING_LOCATION_TABLE + "." + PARKING_LOCATION_TABLE_COL_LATITUDE,
                PARKING_LOCATION_TABLE + "." + PARKING_LOCATION_TABLE_COL_LONGITUDE
        };
        String selection = "parking_location.LATITUDE > ? AND parking_location.LATITUDE < ? AND parking_location.LONGITUDE > ? AND parking_location.LONGITUDE < ?";
        String[] selectionArgs = {
                String.valueOf(latitude - distance),
                String.valueOf(latitude + distance),
                String.valueOf(longitude - distance),
                String.valueOf(longitude + distance)
        };
        String tables = PARKING_INFO_TABLE + " INNER JOIN " + PARKING_LOCATION_TABLE
                + " ON " + PARKING_INFO_TABLE + "." + PARKING_INFO_TABLE_COL_PARKING_ID + " = "
                + PARKING_LOCATION_TABLE + "." + PARKING_LOCATION_TABLE_COL_PARKING_ID;

        Cursor cursor = db.query(tables, projection, selection, selectionArgs, null, null, null);
        List<ParkingLocation> locations = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("PARKING_ID"));
//            String name = cursor.getString(cursor.getColumnIndexOrThrow(PARKING_INFO_TABLE_COL_NAME));
            double lat = cursor.getDouble(cursor.getColumnIndexOrThrow(PARKING_LOCATION_TABLE_COL_LATITUDE));
            double lon = cursor.getDouble(cursor.getColumnIndexOrThrow(PARKING_LOCATION_TABLE_COL_LONGITUDE));
            locations.add(new ParkingLocation(id, lat, lon));
        }
        cursor.close();
        return locations;
    }

    // Update Bike free slots count
    public int updateFreeBikeCount(int parkingId, int newBikeCount) {
        // Get a writable database instance
        SQLiteDatabase db = this.getWritableDatabase();

        // PARKING SLOTS TABLE columns
//        public static final String PARKING_SLOTS_TABLE_COL_PARKING_ID = "PARKING_ID";
//        public static final String PARKING_SLOTS_TABLE_COL_BIKE_SLOTS = "BIKE_SLOTS";
//        public static final String PARKING_SLOTS_TABLE_COL_3WHEELER_SLOTS = "THREE_WHEELER_SLOTS";
//        public static final String PARKING_SLOTS_TABLE_COL_4WHEELER_SLOTS = "FOUR_WHEELER_SLOTS";
//
//        public static final String PARKING_SLOTS_TABLE_COL_BIKE_FREE_SLOTS = "BIKE_FREE_SLOTS";
//        public static final String PARKING_SLOTS_TABLE_COL_3WHEELER_FREE_SLOTS = "THREE_WHEELER_FREE_SLOTS";
//        public static final String PARKING_SLOTS_TABLE_COL_4WHEELER_FREE_SLOTS = "FOUR_WHEELER_FREE_SLOTS";
//
        // Update the bike count
        ContentValues values = new ContentValues();
        values.put(PARKING_SLOTS_TABLE_COL_BIKE_FREE_SLOTS, newBikeCount);

        String selection = PARKING_SLOTS_TABLE_COL_PARKING_ID + " = ?";
        String[] selectionArgs = {String.valueOf(parkingId)};

        int rowsAffected = db.update(PARKING_SLOTS_TABLE, values, selection, selectionArgs);

        return rowsAffected;
    }

}
