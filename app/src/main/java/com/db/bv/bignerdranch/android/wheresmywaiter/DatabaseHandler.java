package com.db.bv.bignerdranch.android.wheresmywaiter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bwest on 4/21/2018.
 */

//TODO: Use a firebase db instead. (Important for Customer-waiter interaction)
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "wheresMyWaiterManager";

    //contacts table name
    private static final String TABLE_SESSIONS = "tableSessions";

    //contacts table column names
    private static final String KEY_ID = "id";
    private static final String KEY_TABLENUMBER = "tableNumber";
    private static final String KEY_PING = "isPinged";
    private static final String KEY_BUSY = "isBusy";

    //create table statement
    private static final String CREATE_SESSIONS_TABLE =
            "CREATE TABLE " +
                    TABLE_SESSIONS +
                    "("  +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"  +
                    KEY_TABLENUMBER + " INT , " +
                    KEY_BUSY + " BOOLEAN , " +
                    KEY_PING + " BOOLEAN " +
                    ")";

    public DatabaseHandler( Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    //create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SESSIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSIONS);
        onCreate(db);
    }

    //All CRUD (Create, Read, Update, Delete) operations

    //Adding a new session
    long addSession(int tableNumber)  {
        boolean isBusy = false;
        boolean isPinged = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TABLENUMBER, tableNumber);
        values.put(KEY_BUSY , isBusy);
        values.put(KEY_PING, isPinged);

        //null for all columns
        long rowid = db.insert(TABLE_SESSIONS, null, values);
        db.close();
        return rowid;
    }


    /*public String getAllSessions() {
        String contactNames = "";

        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //loop through rows, add each name to list
        while (cursor.moveToNext()) {
            contactNames+= (cursor.getString(cursor.getColumnIndex(KEY_NAME)))  + "\n";
        }

        return contactNames;

    }

    public String getContactInfo(int id)  {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS,
                null , //get all columns
                KEY_ID + "=?", new String[]{Integer.toString(id)}, //where clause
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst())  {
            String name =  cursor.getString(cursor.getColumnIndex(KEY_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(KEY_PHONE));
            return name + " " + phone;
        }
        return null;
    }


    public int updateName(String oldName, String newName)  {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(KEY_NAME, newName);
        String [] whereArgs = {oldName};
        int count = db.update(
                TABLE_CONTACTS,
                contentValues,
                KEY_NAME+"=?",
                whereArgs
        );
        return count;
    }

    public int delete() {

        //TODO: Implement delete method
        int count = 0;
        return count;
    }*/

}

