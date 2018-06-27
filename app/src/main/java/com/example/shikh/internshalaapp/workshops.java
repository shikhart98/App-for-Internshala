package com.example.shikh.internshalaapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shikh.internshalaapp.Adapter.workshopListAdapter;
import com.example.shikh.internshalaapp.database.DatabaseHelper;
import com.example.shikh.internshalaapp.database.table.WorkshopListTable;
import com.example.shikh.internshalaapp.database.table.WorkshopSelectedTable;
import com.example.shikh.internshalaapp.models.workshop;

import java.util.ArrayList;
import java.util.List;

public class workshops extends Fragment {
    DatabaseHelper helper;
    SQLiteDatabase read;
    ArrayList<workshop> Workshops;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Workshops = new ArrayList<>();
        helper = new DatabaseHelper(getContext());
        read = helper.getReadableDatabase();
        Workshops = WorkshopListTable.getAllWorkshops(read);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_workshops, container, false);
        ListView lv_workshops = myView.findViewById(R.id.lv_workshops);
        workshopListAdapter adapter = new workshopListAdapter(Workshops,getContext(),getActivity());
        lv_workshops.setAdapter(adapter);

        return myView;
    }
}

