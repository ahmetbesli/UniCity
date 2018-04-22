package com.ahmetgokhan.unicity.activities.ProfileLists;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.ahmetgokhan.unicity.R;

import java.util.ArrayList;

public class ProjectsListAdapter extends ArrayAdapter<ProjectsListTypeData> {
    private final Context context;
    private ArrayList<ProjectsListTypeData> list;

    public ProjectsListAdapter(Context context,int resource, ArrayList<ProjectsListTypeData> list) {
        super(context, resource, list);
        this.context = context;
        this.list = list;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_projects_list_item, parent, false);

        TextView projectName = rowView.findViewById(R.id.projectListName);
        projectName.setText(list.get(position).getAdvertName());



        return rowView;
    }
}

