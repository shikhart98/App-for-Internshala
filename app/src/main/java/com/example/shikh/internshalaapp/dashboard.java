package com.example.shikh.internshalaapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shikh.internshalaapp.Adapter.dashboardListAdapter;
import com.example.shikh.internshalaapp.database.DatabaseHelper;
import com.example.shikh.internshalaapp.database.table.WorkshopSelectedTable;
import com.example.shikh.internshalaapp.models.workshop;

import java.util.ArrayList;

public class dashboard extends Fragment implements View.OnClickListener {

    SharedPreferences pref;
    ArrayList<workshop> selectedWorkshops;
    DatabaseHelper helper;
    SQLiteDatabase read;
    ListView listView;
    TextView tv_dash_log_in_out;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
        selectedWorkshops = new ArrayList<>();
        helper = new DatabaseHelper(getContext());
        read = helper.getReadableDatabase();
        if(pref.getBoolean("loggedin", false)) {
            selectedWorkshops = WorkshopSelectedTable.getAllUserWorkshops(read, pref.getInt("userId",-100));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        TextView tv_browse = myView.findViewById(R.id.tv_browse);
        tv_dash_log_in_out = myView.findViewById(R.id.tv_dash_log_in_out);
        listView = myView.findViewById(R.id.listView);
        dashboardListAdapter adapter = new dashboardListAdapter(selectedWorkshops,getContext());
        listView.setAdapter(adapter);
        if (pref.getBoolean("loggedin", false)) {
            tv_dash_log_in_out.setText("LOG OUT");
        } else {
            tv_dash_log_in_out.setText("LOG IN");
        }

        tv_browse.setOnClickListener(this);
        tv_dash_log_in_out.setOnClickListener(this);
        return myView;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_browse:
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, new workshops()).addToBackStack(null).commit();
                break;
            case R.id.tv_dash_log_in_out:
                if(!pref.getBoolean("loggedin", false)) {
                    getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new login()).addToBackStack(null).commit();
                } else {
                    pref.edit().putBoolean("loggedin",false).apply();
                    pref.edit().putInt("userId",-100).apply();
                    tv_dash_log_in_out.setText("LOG IN");
                    selectedWorkshops = new ArrayList<>();
                    dashboardListAdapter adapter = new dashboardListAdapter(selectedWorkshops,getContext());
                    listView.setAdapter(adapter);
                }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(pref.getBoolean("loggedin", false)) {
            selectedWorkshops = WorkshopSelectedTable.getAllUserWorkshops(read, pref.getInt("userId",-100));
            dashboardListAdapter adapter = new dashboardListAdapter(selectedWorkshops,getContext());
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(pref.getBoolean("loggedin", false)) {
            selectedWorkshops = WorkshopSelectedTable.getAllUserWorkshops(read, pref.getInt("userId",-100));
            dashboardListAdapter adapter = new dashboardListAdapter(selectedWorkshops,getContext());
            listView.setAdapter(adapter);
        }
    }
}
