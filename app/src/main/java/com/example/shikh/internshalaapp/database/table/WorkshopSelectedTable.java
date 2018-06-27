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

public class WorkshopSelectedTable {

    private WorkshopSelectedTable() {
    }

    public static final String TABLE_NAME = "userworkshops";

    public interface Columns {
        String USERID = "userId";
        String WORKSHOPID = "workshopId";
        String WORKSHOPNAME = "workshopName";
    }

    public static final String CMD_CREATE_TABLE =
            CMD_CREATE_TABLE_INE + TABLE_NAME
                    + LBR
                    + Columns.USERID + TYPE_INT + COMMA
                    + Columns.WORKSHOPID + TYPE_INT + COMMA
                    + Columns.WORKSHOPNAME + TYPE_TEXT
                    + RBR + SEMI;

    public static long insertWorkshop(int userId, workshop w, SQLiteDatabase db) {
        ContentValues newUserWorkshop = new ContentValues();
        Log.d("test","userid: "+userId+" name: "+w.getName()+" id: "+w.getId());
        newUserWorkshop.put(Columns.USERID, userId);
        newUserWorkshop.put(Columns.WORKSHOPID,w.getId());
        newUserWorkshop.put(Columns.WORKSHOPNAME, w.getName());

        return db.insert(TABLE_NAME, null, newUserWorkshop);
    }

    public static ArrayList<workshop> getAllUserWorkshops(SQLiteDatabase db, int userId) {
        Cursor c = db.query(
                TABLE_NAME,
                new String[]{Columns.USERID, Columns.WORKSHOPID,Columns.WORKSHOPNAME},
                Columns.USERID + "=?",
                new String[]{userId+""},
                null,
                null,
                null
        );

        ArrayList<workshop> userWorkshops = new ArrayList<>();
        c.moveToFirst();

        int indexID = c.getColumnIndex(Columns.WORKSHOPID);
        int indexNAME = c.getColumnIndex(Columns.WORKSHOPNAME);

        while (!c.isAfterLast()) {
            userWorkshops.add(new workshop(c.getInt(indexID),c.getString(indexNAME)));
            c.moveToNext();
        }

        return userWorkshops;
    }

    public static boolean checkId(SQLiteDatabase db, int id, int userID){
        String userId = userID +"";
        Cursor c = db.query(
                TABLE_NAME,
                new String[]{Columns.WORKSHOPID},
                Columns.USERID+"=?",
                new String[] {userId},
                null,
                null,
                null
        );

        c.moveToFirst();
        int wID = c.getColumnIndex(Columns.WORKSHOPID);

        while(!c.isAfterLast()){
            if(id == c.getInt(wID)){
                return true;
            }
            c.moveToNext();
        }
        return false;
    }
}
