package com.example.phonebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "PhonebookDatabase";
    public static final  int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "phonebook";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FNAME = "firstname";
    public static final String COLUMN_LNAME = "lastname";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_ADDRESS = "address";


    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table  " + TABLE_NAME + "(" +
                COLUMN_ID + " integer not null constraint employee_pk primary key autoincrement," +
                COLUMN_FNAME + " varchar(200) not null, " +
                COLUMN_LNAME + " varchar(200) not null, " +
                COLUMN_ADDRESS + " varchar(200) not null, " +
                COLUMN_NUMBER + " integer  not null);" ;
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql  = "drop table if exists " + TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }

    boolean addContact(String fname, String lname, String address, int number){


        SQLiteDatabase sqLiteDatabase = getWritableDatabase();


        ContentValues cv = new ContentValues();


        cv.put(COLUMN_FNAME,fname);
        cv.put(COLUMN_LNAME,lname);
        cv.put(COLUMN_ADDRESS,address);
        cv.put(COLUMN_NUMBER,number);

        // insert method return row number if insertion is successful and -1 if un successful

        return sqLiteDatabase.insert(TABLE_NAME,null,cv) != -1;

//        return true;
    }

    Cursor getallcontacts(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("select * from " + TABLE_NAME,null);
    }

    boolean deleteContact(int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.delete(TABLE_NAME,COLUMN_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    boolean updateContact(int id, String fname, String lname, String address, int number){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FNAME,fname);
        cv.put(COLUMN_LNAME,lname);
        cv.put(COLUMN_ADDRESS,address);
        cv.put(COLUMN_NUMBER,number);

        // this method returns the number of rows affected

        return sqLiteDatabase.update(TABLE_NAME,cv,COLUMN_ID + "=?",new String[]{String.valueOf(id)}) > 0;
    }
}
