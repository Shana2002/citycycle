package com.example.citycycle.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.citycycle.helpers.LoginResult;
import com.example.citycycle.models.User;

import java.sql.Blob;

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
    private static final String COL_USER_IMAGE = "user_image";

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
                COL_USER_PAYMENT + " TEXT," +
                COL_USER_IMAGE + " BLOB)";

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

    public  boolean insertUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAME,user.getName());
        values.put(COL_USER_EMAIL,user.getEmail());
        values.put(COL_USER_PASSWORD,user.getPassword());
        long result = db.insert(TABLE_USERS,null,values);
        return result != -1;
    }

    public LoginResult loginUser(User user){
        SQLiteDatabase db = this.getReadableDatabase();

//        query
        String query = "SELECT * FROM "+TABLE_USERS+" WHERE "+COL_USER_EMAIL+ " = ?";

//        excute quey
        try (Cursor cursor = db.rawQuery(query,new String[]{user.getEmail()})){
            if (cursor != null && cursor.moveToFirst()){
//                get db password
                String storedPassword = cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_PASSWORD));
//                check password correct or  not
                if (user.getPassword().equals(storedPassword)){
                    return LoginResult.SUCCESS;
                }
                else{
                    return LoginResult.INCORRECT_PASSWORD;
                }
            }
            return LoginResult.USER_NOT_FOUND;
        } catch (Exception e) {
            return LoginResult.ERROR;
        }
        finally {
            db.close();
        }
    }
    public User getLoginUserDetails(String email){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM "+TABLE_USERS + " WHERE " + COL_USER_EMAIL + " = ?";

        try (Cursor cursor = db.rawQuery(query,new String[]{email})){
            if (cursor != null && cursor.moveToFirst()){
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_ID));
                String userEmail = cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_EMAIL));
                String userName = cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_NAME));
                String userPasswrod = cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_PASSWORD));
                String userPhone = cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_PHONE));
                String userPayment = cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_PAYMENT));
                byte[] userImage = cursor.getBlob(cursor.getColumnIndexOrThrow(COL_USER_IMAGE));

                return new User(id,userName,userEmail,userPasswrod,userPhone,userPayment,userImage);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
          db.close();
        }
    }
}
