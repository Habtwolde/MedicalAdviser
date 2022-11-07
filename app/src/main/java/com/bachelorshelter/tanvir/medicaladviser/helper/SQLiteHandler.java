package com.bachelorshelter.tanvir.medicaladviser.helper;

/**
 * Created by Tanvir on 2/6/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_api";

    // Login table name
    private static final String TABLE_USER = "user";

    // Login Table Columns names
    private static final String KEY_FNAME = "first_name";
    private static final String KEY_LNAME = "last_name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_APIKEY = "apiKey";
    private static final String MESS_ID = "mess_id";
    private static final String IS_MANAGER = "is_manager";
    //private static final String KEY_GENDER = "gender";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_FNAME + " TEXT," + KEY_LNAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE PRIMARY KEY," + KEY_PHONE + " TEXT,"+ KEY_APIKEY + " TEXT,"+ MESS_ID + " TEXT,"+ IS_MANAGER + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String fname, String lname, String email, String phone, String apiKey, String mess_id,String is_manager) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, fname); // Name
        values.put(KEY_LNAME, lname); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_PHONE, phone); // Phone
        values.put(KEY_APIKEY, apiKey); // apiKey
        values.put(MESS_ID, mess_id); // mess_id
        values.put(IS_MANAGER, is_manager); // mess_id
        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user value: " + values.toString());
        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("fname", cursor.getString(0));
            user.put("lname", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("phone", cursor.getString(3));
            user.put("apiKey", cursor.getString(4));
            user.put("mess_id", cursor.getString(5));
            user.put("is_manager", cursor.getString(6));
        }
        cursor.close();
        db.close();
        // return user
        //Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}
