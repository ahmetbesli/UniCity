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

public class RecyclerViewAdapterDone extends RecyclerView.Adapter<RecyclerViewAdapterDone.ViewHolder> {

    private List<RecyclerViewListItemDone> listItems;
    private Context context;

    public RecyclerViewAdapterDone(List<RecyclerViewListItemDone> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }




    @NonNull
    @Override
    public RecyclerViewAdapterDone.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_list_item_done_projects, parent, false);

        return new RecyclerViewAdapterDone.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterDone.ViewHolder holder, int position) {

        RecyclerViewListItemDone list_item = listItems.get(position);
        holder.textViewAdvertName.setText(list_item.getAdvertName());
        holder.textViewAdvertId.setText(list_item.getAdvert_id());
        holder.textViewDescription.setText(list_item.getDescription());
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
        private TextView textViewDate;
        private TextView textViewCourseName;
        private TextView textViewUser_id;
        private Button browseButton;


        public ViewHolder(View itemView) {
            super(itemView);
            textViewCourseName = itemView.findViewById(R.id.done_projects_course_name);
            textViewDate = itemView.findViewById(R.id.done_projects_date);
            textViewDescription = itemView.findViewById(R.id.done_projects_description);
            textViewAdvertName = itemView.findViewById(R.id.done_projects_advert_name);
            textViewAdvertId = itemView.findViewById(R.id.done_projects_advert_id_hidden);
            textViewUser_id = itemView.findViewById(R.id.done_projects_user_id_hidden);
            browseButton = itemView.findViewById(R.id.browseButton);

            browseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AdvertPageActivity.class);
                    intent.putExtra("advert_id",textViewAdvertId.getText().toString());
                    intent.putExtra("buttonText","Done");
                    intent.putExtra("user_id",textViewUser_id.getText().toString());

                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });


        }


    }
}
