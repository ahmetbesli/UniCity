package com.ahmetgokhan.unicity.activities.Search;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.activities.AdvertPage.AdvertPageActivity;

import java.util.List;


public class RecyclerViewAdapterCreated extends RecyclerView.Adapter<RecyclerViewAdapterCreated.ViewHolder> {

        private List<RecyclerViewListItemCreated> listItems;
        private Context context;

        public RecyclerViewAdapterCreated(List<RecyclerViewListItemCreated> listItems, Context context) {
            this.listItems = listItems;
            this.context = context;
        }




        @NonNull
        @Override
        public RecyclerViewAdapterCreated.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_list_item_created, parent, false);

            return new RecyclerViewAdapterCreated.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewAdapterCreated.ViewHolder holder, int position) {

            RecyclerViewListItemCreated list_item = listItems.get(position);
            holder.textViewAdvertName.setText(list_item.getAdvertName());
            holder.textViewAdvertId.setText(list_item.getAdvert_id());
            holder.textViewDescription.setText(list_item.getDescription());
            holder.textViewNumberOfPerson.setText("Applicable for: " + list_item.getNumberOfPerson());
            holder.textViewDate.setText("Created at: " + list_item.getDate());
            holder.textViewCourseName.setText(list_item.getCourseName());
            holder.textViewUser_id.setText(list_item.getUser_id());



        }

        @Override
        public int getItemCount() {
            return listItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{


            private TextView textViewAdvertName;
            private TextView textViewAdvertId;
            private TextView textViewDescription;
            private TextView textViewNumberOfPerson;
            private TextView textViewDate;
            private TextView textViewCourseName;
            private TextView textViewUser_id;
            private Button browseButton;


            public ViewHolder(View itemView) {
                super(itemView);
                textViewCourseName = itemView.findViewById(R.id.created_projects_course_name);
                textViewDate = itemView.findViewById(R.id.created_projects_date);
                textViewDescription = itemView.findViewById(R.id.created_projects_description);
                textViewNumberOfPerson = itemView.findViewById(R.id.created_projects_number_of_person);
                textViewAdvertName = itemView.findViewById(R.id.created_projects_advert_name);
                textViewAdvertId = itemView.findViewById(R.id.created_projects_advert_id_hidden);
                browseButton = itemView.findViewById(R.id.browseButton);
                textViewUser_id = itemView.findViewById(R.id.created_projects_user_id_hidden);

                browseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, AdvertPageActivity.class);
                        intent.putExtra("advert_id",textViewAdvertId.getText().toString());
                        intent.putExtra("buttonText","Your Advert");
                        intent.putExtra("user_id",textViewUser_id.getText().toString());
                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);


                    }
                });


            }


        }
    }

