package com.shanmu.schedulemaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {


    public DbHelper(Context context) {
        super(context, "schedule_maker.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createUsertable);
        db.execSQL(createUserLocationTable);
        db.execSQL(createGoaltable);
        db.execSQL(createSuccessGoaltable);
        db.execSQL(createFailureGoaltable);
        db.execSQL(createDaystable);
        db.execSQL(createTimeslotTable);
        db.execSQL(createDateTable);
        db.execSQL(createScheduleTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(dropUserTable);
        db.execSQL(dropUserLocationTable);
        db.execSQL(dropGoalTable);
        db.execSQL(dropSuccessGoalTable);
        db.execSQL(dropFailureGoalTable);
        db.execSQL(dropDaysTable);
        db.execSQL(dropTimeslotTable);
        db.execSQL(dropDateTable);
        db.execSQL(dropScheduleTable);
    }

    /* user table SQL implementations */
    public Boolean insertUser(String username, String role, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("role", role);
        contentValues.put("password", password);
        long result = db.insert("user", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean loginUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select username, password from user where username = " + username, null);

        if (result.moveToFirst()) {
            Log.d("logintest ", result.getString(0));
        }
        db.close();

        return false;
    }

    public String retrieveUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select username from user where id = " + 1, null);

        if (result.moveToFirst()) {
            String username = result.getString(0);
            result.close();
            db.close();
            return username;
        } else {
            db.close();
            return null;
        }
    }


    public Boolean insertUserLocation(
            Integer user_id,
            double latitude,
            double longitude,
            String country,
            String address
    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("user_id", user_id);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        contentValues.put("country", country);
        contentValues.put("address", address);

        long result = db.insert("userLocations", null, contentValues);
        if (result == -1) {
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }

    }

    // table creation and drop script declarations

    private final static String createUsertable = "CREATE TABLE user(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "username TEXT," +
            "role TEXT," +
            "password TEXT)";
    private final static String dropUserTable = "DROP TABLE IF EXISTS user";


    private final static String createUserLocationTable = "CREATE TABLE userLocations(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " user_id INTEGER," +
            "latitude REAL ," +
            "longitude REAL ," +
            "country TEXT, " +
            "address TEXT," +
            "FOREIGN KEY(user_id) REFERENCES user(id))";
    private final static String dropUserLocationTable = "DROP TABLE IF EXISTS userLocatioons";

    private final static String createGoaltable = "CREATE TABLE goal(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT," +
            "progress REAL," +
            "start_date DATE," +
            "end_date DATE" +
            ")";
    private final static String dropGoalTable = "DROP TABLE IF EXISTS goal";


    private final static String createSuccessGoaltable = "CREATE TABLE success_goal(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "goal_id INTEGER," +
            "FOREIGN KEY (goal_id) REFERENCES goal(id)" +
            ")";

    private final static String dropSuccessGoalTable = "DROP TABLE IF EXISTS success_goal";

    private final static String createFailureGoaltable = "CREATE TABLE failure_goal(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "goal_id INTEGER," +
            "FOREIGN KEY (goal_id) REFERENCES goal(id)" +
            ")";
    private final static String dropFailureGoalTable = "DROP TABLE IF EXISTS failure_goal";

    private final static String createDaystable = "CREATE TABLE days(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "dayname TEXT" +
            ")";
    private final static String dropDaysTable = "DROP TABLE IF EXISTS days";

    private final static String createTimeslotTable = "CREATE TABLE timeslot(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "start_time TEXT," +
            "end_time TEXT" +
            ")";
    private final static String dropTimeslotTable = "DROP TABLE IF EXISTS timeslot";

    private final static String createDaySlotTable = "CREATE TABLE day_slot(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "timeslot_id INTEGER," +
            "day_id INTEGER," +
            "FOREIGN KEY (timeslot_id) REFERENCES timeslot(id)," +
            "FOREIGN KEY (day_id) REFERENCES day(id)" +
            ")";
    private final static String dropDaySlotTable = "DROP TABLE IF EXISTS day_slot";


    private final static String createDateTable = "CREATE TABLE date(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "date DATE" +
            ")";
    private final static String dropDateTable = "DROP TABLE IF EXISTS date";


    private final static String createScheduleTable = "CREATE TABLE schedule(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "date_id INTEGER," +
            "goal_id INTEGER," +
            "timeslot_id INTEGER," +
            "FOREIGN KEY (date_id) REFERENCES date(id)," +
            "FOREIGN KEY (goal_id) REFERENCES goal(id)," +
            "FOREIGN KEY (timeslot_id) REFERENCES timeslot(id)" +
            ")";
    private final static String dropScheduleTable = "DROP TABLE IF EXISTS schedule";
}
