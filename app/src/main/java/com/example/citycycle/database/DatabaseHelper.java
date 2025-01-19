package com.example.citycycle.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.citycycle.helpers.LoginResult;
import com.example.citycycle.helpers.UserSession;
import com.example.citycycle.models.Cycle;
import com.example.citycycle.models.CycleRental;
import com.example.citycycle.models.Promotion;
import com.example.citycycle.models.Station;
import com.example.citycycle.models.User;

import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database details
    private static final String DATABASE_NAME = "CityCycleRentals.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_USERS = "Users";
    private static final String TABLE_BIKES = "Bikes";
    private static final String TABLE_STATIONS = "Stations";
    private static final String TABLE_RENTALS = "Rentals";
    private static final String TABLE_PROMOTION = "promotions";

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
    private static final String COL_BIKE_TITLE = "title";
    private static final String COL_BIKE_DESCRIPTION = "description";
    private static final String COL_BIKE_TYPE = "type";
    private static final String COL_BIKE_STATUS = "status";
    private static final String COL_BIKE_STATION_ID = "station_id";
    private static final String COL_BIKE_IMAGES = "images";


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

    // Promotion Table Colums
    private static final String COL_PROMO_ID = "promotion_id";
    private static final String COL_PROMO_TITLE = "title";
    private static final String COL_PROMO_DESCRIPTION = "description";
    private static final String COL_PROMO_START_DATE = "start_date";
    private static final String COL_PROMO_END_DATE = "end_date";
    private static final String COL_PROMO_IMG = "image";

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
                COL_BIKE_TITLE + " TEXT, " +
                COL_BIKE_DESCRIPTION + " TEXT, " +
                COL_BIKE_TYPE + " TEXT, " +
                COL_BIKE_STATUS + " TEXT, " +
                COL_BIKE_IMAGES + " TEXT, " +
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

        String createPromotionTable = "CREATE TABLE " + TABLE_PROMOTION + "("+
                COL_PROMO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PROMO_TITLE +  " TEXT, " +
                COL_PROMO_DESCRIPTION +  " TEXT, " +
                COL_PROMO_START_DATE +  " TEXT, " +
                COL_PROMO_END_DATE +  " TEXT, "+
                COL_PROMO_IMG + " TEXT )";


        db.execSQL(createUsersTable);
        db.execSQL(createBikesTable);
        db.execSQL(createStationsTable);
        db.execSQL(createRentalsTable);
        db.execSQL(createPromotionTable);

        insertStationData(db);
        insertBikeData(db);
        insertSampleData(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop old tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIKES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RENTALS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROMOTION);
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
                    User currentUser = getLoginUserDetails(user.getEmail());
                    if (currentUser != null){
                        UserSession.getInstance().setUser(currentUser);
                    }
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

    public List<Promotion> getPromotions(){
        SQLiteDatabase db = this.getReadableDatabase();

        // get yesteday
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String yesterday = simpleDateFormat.format(calendar.getTime());

        // Query to select promotions where startDate >= yesterday and endDate <= yesterday
        String query = "SELECT * FROM " + TABLE_PROMOTION ;
//                " WHERE " + COL_PROMO_START_DATE + " >= ? AND " +
//                COL_PROMO_END_DATE + " <= ?";

        List<Promotion> promotions= new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query,new String[]{});
            Log.d("test","hellooooomko");
            if(cursor.moveToFirst()){
                do {

                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_PROMO_ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_PROMO_TITLE));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(COL_PROMO_DESCRIPTION));
                    String startDate = cursor.getString(cursor.getColumnIndexOrThrow(COL_PROMO_START_DATE));
                    String endDate = cursor.getString(cursor.getColumnIndexOrThrow(COL_PROMO_END_DATE));
                    String image = cursor.getString(cursor.getColumnIndexOrThrow(COL_PROMO_IMG));

                    promotions.add(new Promotion(id,name,description,startDate,endDate,image));
                }while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("test",e.toString());
            throw new RuntimeException(e);
        }
        finally {
            db.close();
        }
        return promotions;
    }

    public List<Cycle> getCycle(Integer location,String type,String start_date , String end_date , Boolean available , Integer id){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT b.*, s." + COL_STATION_LOCATION +
                " FROM " + TABLE_BIKES + " AS b " +
                "LEFT JOIN " + TABLE_STATIONS + " AS s ON b." + COL_BIKE_STATION_ID + " = s." + COL_STATION_ID +
                " LEFT JOIN " + TABLE_RENTALS + " AS r ON b." + COL_BIKE_ID + " = r." + COL_RENTAL_BIKE_ID +
                " WHERE 1=1";

        List<String> args = new ArrayList<>();

        if (location != null) {
            query += " AND s." + COL_STATION_ID + " = ?";
            args.add(String.valueOf(location));
        }
        if (type != null){
            query += " AND b." + COL_BIKE_TYPE + " = ?";
            args.add(type);
        }

        if (start_date != null && end_date != null) {
            query += " AND (r." + COL_RENTAL_END_TIME + " IS NULL OR (r." + COL_RENTAL_END_TIME + " < ? OR r." + COL_RENTAL_START_TIME + " > ?))";
            args.add(end_date);
            args.add(start_date);
        }
        if (available) {
            query += " AND b." + COL_BIKE_STATUS + " = ?";
            args.add("Available");
        }
        if (id != null){
            query += "AND b." + COL_BIKE_ID + " = ?";
            args.add(String.valueOf(id));
        }

        List<Cycle> cycles = new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = db.rawQuery(query,args.toArray(new String[0]));
            if (cursor.moveToFirst()){
                do {
                    int bikeId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_BIKE_ID));
                    String bikeTitle = cursor.getString(cursor.getColumnIndexOrThrow(COL_BIKE_TITLE));
                    String bikeDescription = cursor.getString(cursor.getColumnIndexOrThrow(COL_BIKE_DESCRIPTION));
                    String bikeType = cursor.getString(cursor.getColumnIndexOrThrow(COL_BIKE_TYPE));
                    String bikeStatus = cursor.getString(cursor.getColumnIndexOrThrow(COL_BIKE_STATUS));
                    int stationId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_BIKE_STATION_ID));
                    String stationLocation = cursor.getString(cursor.getColumnIndexOrThrow(COL_STATION_LOCATION));
                    String imageString = cursor.getString(cursor.getColumnIndexOrThrow(COL_BIKE_IMAGES));
                    String[] images = imageString.split("[,]");
                    cycles.add(new Cycle(bikeId,bikeTitle,bikeDescription,bikeType,stationLocation,bikeStatus,images));
                }while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("test",e.toString());
            throw new RuntimeException(e);
        }finally {
            db.close();
        }
        return cycles;
    }

    // get Station data
    public List<Station> getStations(){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM "+ TABLE_STATIONS;

        List<Station> stationList = new ArrayList<>();
        try (Cursor cursor = db.rawQuery(query,new String[]{})){
            if (cursor.moveToFirst()){
                do{
                    int stationId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_STATION_ID));
                    String stationName = cursor.getString(cursor.getColumnIndexOrThrow(COL_STATION_NAME));
                    String location = cursor.getString(cursor.getColumnIndexOrThrow(COL_STATION_LOCATION));
                    stationList.add(new Station(stationId,stationName,location));
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        finally {
            db.close();
        }
        return stationList;
    }

    public boolean AddReservation(CycleRental rental){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_RENTAL_USER_ID,UserSession.getInstance().getUser().getUserId());
        values.put(COL_RENTAL_START_STATION,rental.station);
        values.put(COL_RENTAL_END_STATION,rental.endStation);
        values.put(COL_RENTAL_BIKE_ID,rental.cycleId);
        values.put(COL_RENTAL_START_TIME,rental.startTime);
        values.put(COL_RENTAL_END_TIME,rental.endTime);
        values.put(COL_RENTAL_COST,rental.price);
        long result = db.insert(TABLE_USERS,null,values);


        return result != -1;


    }

    public List<CycleRental> rentals (){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM "+TABLE_RENTALS +"  AS r "+
                "LEFT JOIN "+TABLE_BIKES+" AS b ON b.bike_id = r.bike_id "+
                "LEFT JOIN stations AS s On s.station_id = r.station_end_id"+
                " WHERE r.user_id = ?";

        List<CycleRental> cycleRentals = new ArrayList<>();
        try (Cursor cursor = db.rawQuery(query,new String[]{String.valueOf(UserSession.getInstance().getUser().getUserId())})){
            if (cursor.moveToFirst()){
                do{
                    int bikeId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_BIKE_ID));
                    String bikeTitle = cursor.getString(cursor.getColumnIndexOrThrow(COL_BIKE_TITLE));
                    String bikeDescription = cursor.getString(cursor.getColumnIndexOrThrow(COL_BIKE_DESCRIPTION));
                    String bikeType = cursor.getString(cursor.getColumnIndexOrThrow(COL_BIKE_TYPE));
                    String bikeStatus = cursor.getString(cursor.getColumnIndexOrThrow(COL_BIKE_STATUS));
                    int stationId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_BIKE_STATION_ID));
                    String stationLocation = cursor.getString(cursor.getColumnIndexOrThrow(COL_STATION_LOCATION));
                    String imageString = cursor.getString(cursor.getColumnIndexOrThrow(COL_BIKE_IMAGES));
                    String[] images = imageString.split("[,]");
                    int startStation = cursor.getInt(cursor.getColumnIndexOrThrow(COL_RENTAL_START_STATION));
                    int endStation = cursor.getInt(cursor.getColumnIndexOrThrow(COL_RENTAL_END_STATION));
                    String strTime = cursor.getString(cursor.getColumnIndexOrThrow(COL_RENTAL_START_TIME));
                    String strEnd = cursor.getString(cursor.getColumnIndexOrThrow(COL_RENTAL_END_TIME));
                    double cost = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_RENTAL_COST));
                    CycleRental newCyclerental = new CycleRental(bikeId,bikeTitle,bikeDescription,bikeType,stationLocation,bikeStatus,images,strEnd,strTime,strEnd);
                    newCyclerental.setCost(cost);
                    cycleRentals.add(newCyclerental);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        finally {
            db.close();
        }
        return cycleRentals;
    }

    // sample data

    private void insertStationData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        String[] locations = {
                "Colombo 1", "Colombo 2", "Colombo 3", "Colombo 4", "Colombo 5",
                "Kandy", "Galle", "Negombo", "Jaffna", "Matara"
        };

        for (int i = 0; i < locations.length; i++) {
            values.put(COL_STATION_NAME, "Station " + (i + 1));
            values.put(COL_STATION_LOCATION, locations[i]);
            db.insert(TABLE_STATIONS, null, values);
            values.clear();  // Clear previous values to avoid conflicts
        }
    }

    private void insertBikeData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        // Bike Types
        String[] bikeTypes = {
                "Road Bike", "City Bike", "Single Gear Bike", "Electric Bike", "Ladies Bike"
        };

        // Bike Status
        String[] bikeStatuses = {
                "Available", "Not Available", "Maintenance"
        };

        // Insert 20 sample bikes
        for (int i = 1; i <= 20; i++) {
            values.put(COL_BIKE_TITLE, "Bike " + i);
            values.put(COL_BIKE_DESCRIPTION, "Description for Bike " + i);
            values.put(COL_BIKE_TYPE, bikeTypes[i % bikeTypes.length]); // Alternating bike types
            values.put(COL_BIKE_STATUS, "Available"); // Alternating bike statuses
            values.put(COL_BIKE_STATION_ID, (i % 10) + 1); // Assigning station IDs between 1 and 10
            values.put(COL_BIKE_IMAGES, "cycle1,");

            db.insert(TABLE_BIKES, null, values);
            values.clear();  // Clear previous values to avoid conflicts
        }
    }

    private void insertSampleData(SQLiteDatabase db) {
        // Insert sample promotion data
        String insertSampleData1 = "INSERT INTO " + TABLE_PROMOTION + " (" +
                COL_PROMO_TITLE + ", " +
                COL_PROMO_DESCRIPTION + ", " +
                COL_PROMO_START_DATE + ", " +
                COL_PROMO_END_DATE + ", " +
                COL_PROMO_IMG + ") VALUES (" +
                "'Winter Sale', " +
                "'Get up to 50% off on all products during our Winter Sale.', " +
                "'2025-01-01', " +
                "'2025-01-15', " +
                "'prom_img')";  // 'winter_sale_image' is the image name stored in the database

        String insertSampleData2 = "INSERT INTO " + TABLE_PROMOTION + " (" +
                COL_PROMO_TITLE + ", " +
                COL_PROMO_DESCRIPTION + ", " +
                COL_PROMO_START_DATE + ", " +
                COL_PROMO_END_DATE + ", " +
                COL_PROMO_IMG + ") VALUES (" +
                "'Summer Offer', " +
                "'Get 30% off on all items during our Summer Offer.', " +
                "'2025-06-01', " +
                "'2025-06-30', " +
                "'prom_img')";  // 'summer_offer_image' is the image name stored in the database

        db.execSQL(insertSampleData1);
        db.execSQL(insertSampleData2);
        Log.d("Promotion","created");
    }
}

