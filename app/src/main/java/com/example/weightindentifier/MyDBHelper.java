package com.example.weightindentifier;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class MyDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDB.db";

    public static final String TABLE_NAME = "contacts";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "username";
    public static final String COLUMN_PASSWARD = "passward";
    public static final String COLUMN_EMAIL_ID = "email id";

    public static final String COLUMN_MOBILE_NUMBER = "mobile";

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table contacts ( id integer primary key, username text, passward text, emailID text,mobile text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists contacts");
        onCreate(db);
    }

    public boolean insertContact(String username, String passward, String emailID, String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, username);
        contentValues.put(COLUMN_PASSWARD, passward);
        contentValues.put(COLUMN_EMAIL_ID, emailID);
        contentValues.put(COLUMN_MOBILE_NUMBER, mobile);

        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public ArrayList<String> getData(int id) {
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from contacts where id = " + id + " ", null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            arrayList.add(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            arrayList.add(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWARD)));
            arrayList.add(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
            arrayList.add(cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE_NUMBER)));

            cursor.moveToNext();
        }

        return arrayList;
    }

    public boolean updateContact(int id, String username, String passward, String emailID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, username);
        contentValues.put(COLUMN_PASSWARD, passward);
        contentValues.put(COLUMN_EMAIL_ID, emailID);

        db.update(TABLE_NAME, contentValues, "id = ?", new String[]{Integer.toString(id)});

        return true;
    }

    public boolean deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, "id = ?", new String[]{Integer.toString(id)});

        return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from contacts", null);

        return cursor;
    }

    public ArrayList getAllContactsId() {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from contacts ", null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            arrayList.add(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
            cursor.moveToNext();
        }

        return arrayList;
    }
}
