package com.example.shikh.internshalaapp;


import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.shikh.internshalaapp.database.DatabaseHelper;
import com.example.shikh.internshalaapp.database.table.WorkshopListTable;
import com.example.shikh.internshalaapp.models.workshop;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SharedPreferences pref = null;
    ArrayList<workshop> WS_list;
    DatabaseHelper helper;
    SQLiteDatabase write;
    SQLiteDatabase read;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        WS_list = new ArrayList<>();
        helper = new DatabaseHelper(this);
        write = helper.getWritableDatabase();
        read = helper.getReadableDatabase();

        pref = getSharedPreferences("MyData", MODE_PRIVATE);

        transaction.replace(R.id.fragmentContainer, new dashboard()).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (pref.getBoolean("firstrun", true)) {
            workshop w1 = new workshop(101,"Workshop 1");
            workshop w2 = new workshop(102,"Workshop 2");
            workshop w3 = new workshop(103,"Workshop 3");
            workshop w4 = new workshop(104,"Workshop 4");
            workshop w5 = new workshop(105,"Workshop 5");
            workshop w6 = new workshop(106,"Workshop 6");
            workshop w7 = new workshop(107,"Workshop 7");
            workshop w8 = new workshop(108,"Workshop 8");
            workshop w9 = new workshop(109,"Workshop 9");
            workshop w10 = new workshop(110,"Workshop 10");
            workshop w11 = new workshop(111,"Workshop 11");
            workshop w12 = new workshop(112,"Workshop 12");
            workshop w13 = new workshop(113,"Workshop 13");
            workshop w14 = new workshop(114,"Workshop 14");
            workshop w15 = new workshop(115,"Workshop 15");

            WorkshopListTable.insertWorkshop(w1,write);
            WorkshopListTable.insertWorkshop(w2,write);
            WorkshopListTable.insertWorkshop(w3,write);
            WorkshopListTable.insertWorkshop(w4,write);
            WorkshopListTable.insertWorkshop(w5,write);
            WorkshopListTable.insertWorkshop(w6,write);
            WorkshopListTable.insertWorkshop(w7,write);
            WorkshopListTable.insertWorkshop(w8,write);
            WorkshopListTable.insertWorkshop(w9,write);
            WorkshopListTable.insertWorkshop(w10,write);
            WorkshopListTable.insertWorkshop(w11,write);
            WorkshopListTable.insertWorkshop(w12,write);
            WorkshopListTable.insertWorkshop(w13,write);
            WorkshopListTable.insertWorkshop(w14,write);
            WorkshopListTable.insertWorkshop(w15,write);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            transaction.replace(R.id.fragmentContainer,new login()).addToBackStack(null).commit();

            pref.edit().putBoolean("loggedin",false).commit();
            pref.edit().putBoolean("firstrun", false).commit();
        }
    }
}
