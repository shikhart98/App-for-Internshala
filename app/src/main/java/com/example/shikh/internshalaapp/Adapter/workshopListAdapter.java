package com.example.shikh.internshalaapp.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shikh.internshalaapp.R;
import com.example.shikh.internshalaapp.database.DatabaseHelper;
import com.example.shikh.internshalaapp.database.table.WorkshopSelectedTable;
import com.example.shikh.internshalaapp.login;
import com.example.shikh.internshalaapp.models.workshop;

import java.util.ArrayList;

/**
 * Created by shikh on 27-06-2018.
 */

public class workshopListAdapter extends BaseAdapter {

    ArrayList<workshop> Workshops = new ArrayList<>();
    Context context;
    FragmentActivity activity;
    SharedPreferences pref;

    public workshopListAdapter(ArrayList<workshop> workshops, Context context, FragmentActivity activity) {
        Workshops = workshops;
        this.context = context;
        pref = context.getSharedPreferences("MyData",Context.MODE_PRIVATE);
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return Workshops.size();
    }

    @Override
    public workshop getItem(int position) {
        return Workshops.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Workshops.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.item_workshop,parent,false);
        }
        final workshop currentWorkshop = getItem(position);
        DatabaseHelper helper = new DatabaseHelper(context);
        final SQLiteDatabase read = helper.getReadableDatabase();
        final SQLiteDatabase write = helper.getWritableDatabase();
        TextView wname_workshop = convertView.findViewById(R.id.wname_workshop);
        final TextView status_workshop = convertView.findViewById(R.id.status_workshop);
        final int itemId = currentWorkshop.getId();
        wname_workshop.setText(currentWorkshop.getName());
        status_workshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pref.getBoolean("loggedin",false)) {
                    int userId = context.getSharedPreferences("MyData", Context.MODE_PRIVATE).getInt("userId", -100);
                    if (WorkshopSelectedTable.checkId(read, itemId, userId)) {
                        Toast.makeText(context, "Already applied for this workshop!", Toast.LENGTH_SHORT).show();
                    } else {
                        WorkshopSelectedTable.insertWorkshop(userId, currentWorkshop, write);
                        Toast.makeText(context, "Applied successfully", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context,"Log in first!",Toast.LENGTH_SHORT).show();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new login()).addToBackStack(null).commit();
                }
            }
        });
        return convertView;
    }


}
