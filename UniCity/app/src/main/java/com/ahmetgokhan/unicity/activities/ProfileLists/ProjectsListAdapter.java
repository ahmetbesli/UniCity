package com.ahmetgokhan.unicity.activities.ProfileLists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import com.ahmetgokhan.unicity.R;

import java.util.ArrayList;

public class ProjectsListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> list;

    public ProjectsListAdapter(Context context,int resource, ArrayList<String> list) {
        super(context, resource, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_projects_list_item, parent, false);


        return rowView;
    }
}

