package com.example.shikh.internshalaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shikh.internshalaapp.R;
import com.example.shikh.internshalaapp.models.workshop;

import java.util.ArrayList;

/**
 * Created by shikh on 27-06-2018.
 */

public class dashboardListAdapter extends BaseAdapter {

    ArrayList<workshop> selectedList = new ArrayList<>();
    Context context;

    public dashboardListAdapter(ArrayList<workshop> selectedList, Context context) {
        this.selectedList = selectedList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return selectedList.size();
    }

    @Override
    public workshop getItem(int position) {
        return selectedList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return selectedList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.list_item_dashboard,parent,false);
        }

        workshop currentWorkshop = getItem(position);
        TextView tv_name_dash = convertView.findViewById(R.id.tv_name_dash);

        tv_name_dash.setText(currentWorkshop.getName());

        return convertView;
    }
}
