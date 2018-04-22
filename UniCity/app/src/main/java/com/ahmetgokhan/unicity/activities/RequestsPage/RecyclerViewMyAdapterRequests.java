package com.ahmetgokhan.unicity.activities.RequestsPage;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.activities.Homepage.HomeActivity;
import com.ahmetgokhan.unicity.config.Config;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by gokhankilic on 9.03.2018.
 */


    public class RecyclerViewMyAdapterRequests extends RecyclerView.Adapter<com.ahmetgokhan.unicity.activities.RequestsPage.RecyclerViewMyAdapterRequests.ViewHolder> {

        private List<RecyclerViewListItemRequests> listItems;
        private Context context;

        public RecyclerViewMyAdapterRequests(List<RecyclerViewListItemRequests> listItems, Context context) {
            this.listItems = listItems;
            this.context = context;
        }








        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_list_item_requests, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            RecyclerViewListItemRequests recyclerViewListItemRequests = listItems.get(position);


            holder.applyID.setText(recyclerViewListItemRequests.getApplyID());
            holder.advertID.setText(recyclerViewListItemRequests.getAdvert_id());
            holder.advertNameTextView.setText(recyclerViewListItemRequests.getAdvertName());
            holder.requesterNameTextView.setText(recyclerViewListItemRequests.getRequesterName());


            if(recyclerViewListItemRequests.getApplierProfilePhoto() == ""|| recyclerViewListItemRequests.getApplierProfilePhoto() == null){

                System.out.println("Bos yapma");

            }
            else {
                AsyncTask<String, Void, Bitmap> profileTask = new RecyclerViewMyAdapterRequests.BitmapTask().execute(recyclerViewListItemRequests.getApplierProfilePhoto());


                try {

                    holder.requesterProfilePhoto.setImageBitmap(profileTask.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                }

            }






        }

        @Override
        public int getItemCount() {
            return listItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            public TextView requesterNameTextView;
            public TextView advertNameTextView;
            public ImageView requesterProfilePhoto;
            public ImageButton acceptButton;
            public ImageButton refuseButton;
            public TextView advertID;
            public TextView applyID;
            public TextView messageTextView;



            public ViewHolder(View itemView) {
                super(itemView);
                requesterNameTextView = itemView.findViewById(R.id.textViewRequesterName);
                advertNameTextView = itemView.findViewById(R.id.textViewAdvertName);
                requesterProfilePhoto = itemView.findViewById(R.id.requesterProfilePhoto);
                acceptButton = itemView.findViewById(R.id.acceptButton);
                refuseButton = itemView.findViewById(R.id.refuseButton);
                advertID = itemView.findViewById(R.id.textViewAdvertID);
                applyID = itemView.findViewById(R.id.textViewApplyID);
                messageTextView = itemView.findViewById(R.id.message);

                acceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                        Call<UniSocial> call = apiInterface.replyApplies("2",applyID.getText().toString().trim());
                        call.enqueue(new Callback<UniSocial>() {

                            @Override
                            public void onResponse(Call<UniSocial> call, retrofit2.Response<UniSocial> response) {


                            }

                            @Override
                            public void onFailure(Call<UniSocial> call, Throwable t) {

                            }
                        });

                        acceptButton.setVisibility(View.INVISIBLE);
                        refuseButton.setVisibility(View.INVISIBLE);
                        messageTextView.setText("Request was accapted");
                        messageTextView.setVisibility(View.VISIBLE);




                    }
                });




                refuseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                        Call<UniSocial> call = apiInterface.replyApplies("0",applyID.getText().toString().trim());
                        call.enqueue(new Callback<UniSocial>() {

                            @Override
                            public void onResponse(Call<UniSocial> call, retrofit2.Response<UniSocial> response) {


                            }

                            @Override
                            public void onFailure(Call<UniSocial> call, Throwable t) {

                            }
                        });

                        acceptButton.setVisibility(View.INVISIBLE);
                        refuseButton.setVisibility(View.INVISIBLE);
                        messageTextView.setText("Request was refused");
                        messageTextView.setVisibility(View.VISIBLE);


                    }
                });

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
