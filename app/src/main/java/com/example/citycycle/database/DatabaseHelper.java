package com.example.citycycle.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database details
    private static final String DATABASE_NAME = "CityCycleRentals.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_USERS = "Users";
    private static final String TABLE_BIKES = "Bikes";
    private static final String TABLE_STATIONS = "Stations";
    private static final String TABLE_RENTALS = "Rentals";

    // Users Table Columns
    private static final String COL_USER_ID = "user_id";
    private static final String COL_USER_NAME = "name";
    private static final String COL_USER_EMAIL = "email";
    private static final String COL_USER_PASSWORD = "password";
    private static final String COL_USER_PHONE = "phone";
    private static final String COL_USER_PAYMENT = "payment_info";

    // Bikes Table Columns
    private static final String COL_BIKE_ID = "bike_id";
    private static final String COL_BIKE_TYPE = "type";
    private static final String COL_BIKE_STATUS = "status";
    private static final String COL_BIKE_STATION_ID = "station_id";

    // Stations Table Columns
    private static final String COL_STATION_ID = "station_id";
    private static final String COL_STATION_NAME = "name";
    private static final String COL_STATION_LOCATION = "location";

    // Rentals Table Columns
    private static final String COL_RENTAL_ID = "rental_id";
    private static final String COL_RENTAL_USER_ID = "user_id";
    private static final String COL_RENTAL_BIKE_ID = "bike_id";
    private static final String COL_RENTAL_START_STATION = "station_start_id";
    private static final String COL_RENTAL_END_STATION = "station_end_id";
    private static final String COL_RENTAL_START_TIME = "rental_start_time";
    private static final String COL_RENTAL_END_TIME = "rental_end_time";
    private static final String COL_RENTAL_COST = "total_cost";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_NAME + " TEXT, " +
                COL_USER_EMAIL + " TEXT UNIQUE, " +
                COL_USER_PASSWORD + " TEXT, " +
                COL_USER_PHONE + " TEXT, " +
                COL_USER_PAYMENT + " TEXT)";

        String createBikesTable = "CREATE TABLE " + TABLE_BIKES + " (" +
                COL_BIKE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_BIKE_TYPE + " TEXT, " +
                COL_BIKE_STATUS + " TEXT, " +
                COL_BIKE_STATION_ID + " INTEGER)";

        String createStationsTable = "CREATE TABLE " + TABLE_STATIONS + " (" +
                COL_STATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_STATION_NAME + " TEXT, " +
                COL_STATION_LOCATION + " TEXT)";

        String createRentalsTable = "CREATE TABLE " + TABLE_RENTALS + " (" +
                COL_RENTAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_RENTAL_USER_ID + " INTEGER, " +
                COL_RENTAL_BIKE_ID + " INTEGER, " +
                COL_RENTAL_START_STATION + " INTEGER, " +
                COL_RENTAL_END_STATION + " INTEGER, " +
                COL_RENTAL_START_TIME + " TEXT, " +
                COL_RENTAL_END_TIME + " TEXT, " +
                COL_RENTAL_COST + " REAL)";

        db.execSQL(createUsersTable);
        db.execSQL(createBikesTable);
        db.execSQL(createStationsTable);
        db.execSQL(createRentalsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop old tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIKES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RENTALS);
        onCreate(db);
    }

    public  boolean insertUser(){
        return  false;
    }
}
