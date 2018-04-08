package com.ahmetgokhan.unicity.activities.Homepage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahmetgokhan.unicity.R;

import java.util.List;

/**
 * Created by gokhankilic on 9.03.2018.
 */


    public class RecyclerViewMyAdapter extends RecyclerView.Adapter<RecyclerViewMyAdapter.ViewHolder> {

        private List<RecyclerViewListItem> listItems;
        private Context context;

        public RecyclerViewMyAdapter(List<RecyclerViewListItem> listItems, Context context) {
            this.listItems = listItems;
            this.context = context;
        }




        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_list_item, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            RecyclerViewListItem list_item = listItems.get(position);
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

            public TextView textViewCourseName;
            public TextView textViewAdvertName;
            public TextView textViewDescription;
            public TextView textViewAdvertDate;
            public TextView textViewNumberOfPerson;

            public ViewHolder(View itemView) {
                super(itemView);
                textViewCourseName = itemView.findViewById(R.id.textViewCourseName);
                textViewAdvertName = itemView.findViewById(R.id.textViewAdvertName);
                textViewDescription = itemView.findViewById(R.id.textViewDescription);
                textViewAdvertDate = itemView.findViewById(R.id.textViewAdvertDate);
                textViewNumberOfPerson = itemView.findViewById(R.id.textViewNumberOfPerson);

            }


        }



}
