package com.ahmetgokhan.unicity.adapters;

import java.util.List;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.ahmetgokhan.unicity.R;

public class SubscribeAdapter extends ArrayAdapter<String> {

    private int layout;
    private List<String> mObjects;

    public SubscribeAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        mObjects = objects;
        layout = resource;
    }

    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder mainViewholder = null;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            //viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.list_item_thumbnail);
            viewHolder.title = convertView.findViewById(R.id.listViewText);
            convertView.setTag(viewHolder);
        }

        mainViewholder = (ViewHolder) convertView.getTag();



        mainViewholder.title.setText(getItem(position));

        return convertView;
    }

    public class ViewHolder {

        ImageView thumbnail;
        TextView title;
        Button button;
    }
}