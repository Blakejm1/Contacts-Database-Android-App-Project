package com.example.contactsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseControl {

    SQLiteDatabase database;
    DatabaseHelper helper;

    public DatabaseControl(Context context) {
        helper = new DatabaseHelper(context);
    }

    public void open() {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    // Insert one bundle into the database
    public boolean insert(String name, String state) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("state", state);
        return database.insert("contact", null, values) > 0;
    }

    // Get something out of the database
    public String getState(String name) {
        // Select state from the database
        String query = "select state from contact where name=\""+name+"\"";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        String state = cursor.getString(0);
        cursor.close();
        return  state;
    }

    public String[] getAllNamesArray() {
        String query = "select name from contact";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        ArrayList<String> list = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            String name = cursor.getString(0);
            list.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        String[] array = new String[list.size()];
        return list.toArray(array);
    }

}
