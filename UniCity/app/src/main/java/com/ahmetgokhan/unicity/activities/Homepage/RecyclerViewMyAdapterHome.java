package com.ahmetgokhan.unicity.activities.Homepage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ahmetgokhan.unicity.R;

import java.util.List;

/**
 * Created by gokhankilic on 9.03.2018.
 */


    public class RecyclerViewMyAdapterHome extends RecyclerView.Adapter<com.ahmetgokhan.unicity.activities.Homepage.RecyclerViewMyAdapterHome.ViewHolder> {

        private List<com.ahmetgokhan.unicity.activities.Homepage.RecyclerViewListItemHome> listItems;
        private Context context;

        public RecyclerViewMyAdapterHome(List<com.ahmetgokhan.unicity.activities.Homepage.RecyclerViewListItemHome> listItems, Context context) {
            this.listItems = listItems;
            this.context = context;
        }




        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_list_item_advert, parent, false);
            final Button apply = v.findViewById(R.id.list_view_home_button_apply);
            Button browse = v.findViewById(R.id.list_view_home_button_browse);

            browse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    apply.setText("Apply Sent");
                }
            });
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            com.ahmetgokhan.unicity.activities.Homepage.RecyclerViewListItemHome list_item = listItems.get(position);

            holder.textViewAdvertId.setText(list_item.getAdvert_id());
            holder.textViewCourseName.setText(list_item.getCourseName());
            holder.textViewAdvertName.setText(list_item.getAdvertName());
            holder.textViewDescription.setText(list_item.getDescription());
            holder.textViewAdvertDate.setText(list_item.getAdvertDate());
            holder.textViewNumberOfPerson.setText(list_item.getNumberOfPerson() + "");

        }

        @Override
        public int getItemCount() {
            return listItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView textViewAdvertId;
            public TextView textViewCourseName;
            public TextView textViewAdvertName;
            public TextView textViewDescription;
            public TextView textViewAdvertDate;
            public TextView textViewNumberOfPerson;

            public ViewHolder(View itemView) {
                super(itemView);
                textViewAdvertId = itemView.findViewById(R.id.advert_id);
                textViewCourseName = itemView.findViewById(R.id.textViewCourseName);
                textViewAdvertName = itemView.findViewById(R.id.textViewAdvertName);
                textViewDescription = itemView.findViewById(R.id.textViewDescription);
                textViewAdvertDate = itemView.findViewById(R.id.textViewAdvertDate);
                textViewNumberOfPerson = itemView.findViewById(R.id.textViewNumberOfPerson);

            }


        }



}
