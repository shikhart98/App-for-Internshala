package com.example.shikh.internshalaapp.database.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.shikh.internshalaapp.models.user;

import static com.example.shikh.internshalaapp.database.Consts.*;

/**
 * Created by shikh on 27-06-2018.
 */

public class UserTable {

    private UserTable() {
    }

    public static final String TABLE_NAME = "users1";

    public interface Columns {
        String ID = "id";
        String NAME = "name";
        String EMAIL = "email";
        String PASSWORD = "password";
    }

    public static final String CMD_CREATE_TABLE =
            CMD_CREATE_TABLE_INE + TABLE_NAME
                    + LBR
                    + Columns.ID + TYPE_INT + TYPE_PK_AI + COMMA
                    + Columns.NAME + TYPE_TEXT + COMMA
                    + Columns.EMAIL + TYPE_TEXT + COMMA
                    + Columns.PASSWORD + TYPE_INT
                    + RBR + SEMI;

    public static long insertUser(user User, SQLiteDatabase db) {
        ContentValues newUser = new ContentValues();

        newUser.put(Columns.NAME, User.getName());
        newUser.put(Columns.EMAIL, User.getEmail());
        newUser.put(Columns.PASSWORD, User.getPassword());

        return db.insert(TABLE_NAME, null, newUser);
    }

    public static boolean CheckForDuplicates(String email, SQLiteDatabase db) {
        Cursor c = db.query(
                TABLE_NAME,
                new String[]{Columns.ID,Columns.NAME,Columns.EMAIL,Columns.PASSWORD},
                Columns.EMAIL+"=?",
                new String[] {email},
                null,
                null,
                null
        );
        return c.getCount() > 0;
    }

    public static int authenticate (String email, String password, SQLiteDatabase db){
        Cursor c = db.query(
                TABLE_NAME,
                new String[]{Columns.ID,Columns.NAME,Columns.EMAIL,Columns.PASSWORD},
                Columns.EMAIL+"=?",
                new String[] {email},
                null,
                null,
                null
        );

        if(c.getCount() == 0){
            return -2;   //Email Id not registered
        }
        c.moveToFirst();
        int indexPASS = c.getColumnIndex(Columns.PASSWORD);

        String pass = c.getString(indexPASS);
        int userID = c.getInt(c.getColumnIndex(Columns.ID));

        if(pass.equals(password)){
            return userID;
        }else{
            return -1; // Incorrect password
        }

    }
}
