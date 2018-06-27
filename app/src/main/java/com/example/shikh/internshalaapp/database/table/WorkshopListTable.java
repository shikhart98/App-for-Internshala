package com.example.shikh.internshalaapp.database.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.shikh.internshalaapp.models.workshop;

import java.util.ArrayList;

import static com.example.shikh.internshalaapp.database.Consts.*;

/**
 * Created by shikh on 27-06-2018.
 */

public class WorkshopListTable {

    private WorkshopListTable() {}

    public static final String TABLE_NAME = "workshoplistTable";

    public interface Columns{
        String ID = "id";
        String NAME = "name";
    }

    public static final String CMD_CREATE_TABLE =
            CMD_CREATE_TABLE_INE + TABLE_NAME
                    + LBR
                    + Columns.ID + TYPE_INT + COMMA
                    + Columns.NAME + TYPE_TEXT
                    + RBR + SEMI;

    public static long insertWorkshop (workshop Workshop, SQLiteDatabase db){
        ContentValues newWorkshop = new ContentValues();

        newWorkshop.put(Columns.ID,Workshop.getId());
        newWorkshop.put(Columns.NAME,Workshop.getName());


        return db.insert(TABLE_NAME,null,newWorkshop);
    }

    public static ArrayList<workshop> getAllWorkshops (SQLiteDatabase db){
        Cursor c = db.query(
                TABLE_NAME,
                new String[] {Columns.ID,Columns.NAME},
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<workshop> workshops = new ArrayList<>();
        c.moveToFirst();

        int indexID = c.getColumnIndex(Columns.ID);
        int indexName = c.getColumnIndex(Columns.NAME);

        while(!c.isAfterLast()){
            workshops.add(new workshop(c.getInt(indexID),c.getString(indexName)));
            c.moveToNext();
        }
        return workshops;
    }

}
