package com.ahmetgokhan.unicity.activities.RequestsPage;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.activities.Homepage.HomeActivity;
import com.ahmetgokhan.unicity.config.Config;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by gokhankilic on 9.03.2018.
 */


    public class RecyclerViewMyAdapterRequests extends RecyclerView.Adapter<RecyclerViewMyAdapterRequests.ViewHolder> {

        private List<RecyclerViewListItemRequests> listItems;
        private Context context;

        public RecyclerViewMyAdapterRequests(List<RecyclerViewListItemRequests> listItems, Context context) {
            this.listItems = listItems;
            this.context = context;
        }








        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_list_item_search_profiles, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {







        }

        @Override
        public int getItemCount() {
            return listItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            public TextView nameSurnameTextView;
            public TextView uniNameTextView;
            public TextView departmentNameTextView;
            public ImageView profilePhotoImageView;
            public TextView userNameHiddenTextView;


            public ViewHolder(View itemView) {
                super(itemView);
                nameSurnameTextView = itemView.findViewById(R.id.nameSurnameTextView);
                uniNameTextView = itemView.findViewById(R.id.departmentNameTextView);
                departmentNameTextView = itemView.findViewById(R.id.departmentNameTextView);
                profilePhotoImageView = itemView.findViewById(R.id.profilePhotoImageView);
                userNameHiddenTextView = itemView.findViewById(R.id.userNameHidden);

            }


        }




    private class BitmapTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                return BitmapFactory.decodeStream(new URL(Config.BASE_URL + strings[0]).openStream());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

    }



}
