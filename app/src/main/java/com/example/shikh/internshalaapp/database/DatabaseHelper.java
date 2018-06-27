package com.example.shikh.internshalaapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shikh.internshalaapp.database.table.UserTable;
import com.example.shikh.internshalaapp.database.table.WorkshopListTable;
import com.example.shikh.internshalaapp.database.table.WorkshopSelectedTable;

/**
 * Created by shikh on 27-06-2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "internshala1.db";
    public static final int DB_VER = 1;

    public DatabaseHelper (Context context){
        super(context, DB_NAME, null, DB_VER);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserTable.CMD_CREATE_TABLE);
        db.execSQL(WorkshopListTable.CMD_CREATE_TABLE);
        db.execSQL(WorkshopSelectedTable.CMD_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
