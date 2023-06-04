package com.shanmu.schedulemaker.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.shanmu.schedulemaker.models.TimeSlot;

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
        db.execSQL(createTaskTimeslotTable);
        isDaysTableEmpty(db);
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
        db.execSQL(dropTaskTimeslotTable);
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
        String[] selectionArgs = new String[]{ username };
        Cursor result = db.rawQuery("select username, password from user where username = ?", selectionArgs);

        if (result.moveToFirst()) {
            if (result.getString(1).equals(password) && result.getString(0).equals(username)) {
                return true;
            }
        }
        db.close();

        return false;
    }

    public Cursor retrieveUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where id = 1", null);
        return cursor;
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

        long result = db.insert("user_locations", null, contentValues);
        if (result == -1) {
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }

    }

    public Cursor getUserLocation() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user_locations WHERE user_id = 1", null);
        return cursor;
    }

    public void setupDaysTable(SQLiteDatabase db) {

        String[] days = {"mon", "tue", "wed", "thu", "fri", "sat", "sun"};

        for(String day: days) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("dayname", day);
            db.insert("days", null, contentValues);
        }

    }

    public void isDaysTableEmpty(SQLiteDatabase db) {

        Cursor result = db.rawQuery("SELECT count(*) FROM days",null);
        result.moveToFirst();
        int count = result.getInt(0);
        if (!(count > 0)) {
            setupDaysTable(db);
        }
    }

    public void insertDateIntoDateTable(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);

        db.insert("date", null, contentValues);
    }

    public void insertTimeSlotIntoTimeSlotTable(String from, String to) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("start_time", from);
        contentValues.put("end_time", to);

        db.insert("timeslot", null, contentValues);
    }

    public void insertGoalIntoGoalTable(String name, String from, String to) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("start_date", from);
        contentValues.put("end_date", to);
        contentValues.put("slots_covered", 0);

        db.insert("goal", null, contentValues);
    }

    public void insertSlotCountIntoGoalTable(int slotsCount, String goalName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("slots_count", slotsCount);

        db.update("goal", contentValues, "name=?", new String[]{goalName});
        db.close();
    }

    public void insertCoveredCountIntoGoalTable(int slotsCount, String goalName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("slots_covered", slotsCount);

        db.update("goal", contentValues, "name=?", new String[]{goalName});
        db.close();
    }

    public void insertProgressIntoGoalTable(double progress, String goalName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("progress", progress);

        db.update("goal", contentValues, "name=?", new String[]{goalName});
        db.close();
    }

    public Cursor retrieveCoveredAndSlotCountFromGoalTable(String goalName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT slots_count, slots_covered FROM goal", null);
        return cursor;
    }

    public void insertScheduleIntoScheduleTable(Integer dateId, Integer goalId, Integer timeslotId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date_id", dateId);
        contentValues.put("goal_id", goalId);
        contentValues.put("timeslot_id", timeslotId);

        db.insert("schedule", null, contentValues);
    }

    public void insertIntoTaskTimeslotTable(String goal, String from, String to) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("goal", goal);
        contentValues.put("start_time", from);
        contentValues.put("end_time", to);

        db.insert("task_timeslot", null, contentValues);
    }

    public Integer getDateIdfromDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM date WHERE date = " + date, null);

        if (cursor.moveToFirst()) {
            Log.d("cursor", cursor.getString(0) + " --- " + cursor.getString(1));
            Log.d("cursor row check ", String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow("id"))));
            return Integer.parseInt(cursor.getString(0));
        }

        return null;
    }

    public Integer getGoalIdFromGoalName(String goal) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM goal WHERE name = '" + goal + "'", null);

        if (cursor.moveToFirst()) {
            return Integer.parseInt(cursor.getString(0));
        }

        return null;
    }

    public Integer getTimeslotIdfromTimeSlot(String startTime, String endTime) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM task_timeslot WHERE start_time = '" + startTime + "' AND end_time = '" + endTime + "'", null);

        if (cursor.moveToFirst()) {
            return Integer.parseInt(cursor.getString(0));
        }
        return null;
    }

    public Cursor grabAllDataFromSchedule() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("schedule", null, null, null, null, null, null);
        return cursor;
    }

    public Cursor grabAllDataFromProgress() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("goal", null, null, null, null, null, null);
        return cursor;
    }

    public String getDateFromScheduleInDateTable(Integer dateId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM date WHERE id = " + dateId, null);

        if (cursor.moveToFirst()) {
            return cursor.getString(1);
        }
        return null;
    }


    public String getGoalFromScheduleInGoalTable(Integer goalId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM goal WHERE id = " + goalId, null);

        if (cursor.moveToFirst()) {
            return cursor.getString(1);
        }
        return null;
    }


    public TimeSlot getTimeslotFromScheduleTaskTimeslotTable(Integer taskTimeslotId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM task_timeslot WHERE id = " + taskTimeslotId, null);

        if (cursor.moveToFirst()) {
            TimeSlot timeSlot = new TimeSlot(cursor.getString(2), cursor.getString(3));
            return timeSlot;
        }
        return null;
    }

    public void deleteAllScheduleData() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(dropGoalTable);
        db.execSQL(dropSuccessGoalTable);
        db.execSQL(dropFailureGoalTable);
        db.execSQL(dropTimeslotTable);
        db.execSQL(dropDateTable);
        db.execSQL(dropScheduleTable);
        db.execSQL(dropTaskTimeslotTable);

        db.execSQL(createGoaltable);
        db.execSQL(createSuccessGoaltable);
        db.execSQL(createFailureGoaltable);
        db.execSQL(createTimeslotTable);
        db.execSQL(createDateTable);
        db.execSQL(createScheduleTable);
        db.execSQL(createTaskTimeslotTable);
    }




    // table creation and drop script declarations

    private final static String createUsertable = "CREATE TABLE user(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "username TEXT," +
            "role TEXT," +
            "password TEXT)";
    private final static String dropUserTable = "DROP TABLE IF EXISTS user";


    private final static String createUserLocationTable = "CREATE TABLE user_locations(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " user_id INTEGER," +
            "latitude REAL ," +
            "longitude REAL ," +
            "country TEXT, " +
            "address TEXT," +
            "FOREIGN KEY(user_id) REFERENCES user(id))";
    private final static String dropUserLocationTable = "DROP TABLE IF EXISTS user_locations";

    private final static String createGoaltable = "CREATE TABLE goal(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT," +
            "progress REAL DEFAULT 0.0," +
            "start_date DATE," +
            "end_date DATE," +
            "slots_count INTEGER," +
            "slots_covered INTEGER" +
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

    private final static String createTaskTimeslotTable = "CREATE TABLE task_timeslot(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "goal TEXT, " +
            "start_time TEXT," +
            "end_time TEXT" +
            ")";

    private final static String dropTaskTimeslotTable = "DROP TABLE IF EXISTS task_timeslot";

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
