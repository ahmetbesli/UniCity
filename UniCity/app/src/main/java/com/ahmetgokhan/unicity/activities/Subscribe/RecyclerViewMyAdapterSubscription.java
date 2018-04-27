package com.ahmetgokhan.unicity.activities.Subscribe;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by gokhankilic on 9.03.2018.
 */


    public class RecyclerViewMyAdapterSubscription extends RecyclerView.Adapter<RecyclerViewMyAdapterSubscription.ViewHolder> {

        private List<RecyclerViewListItemSubscription> listItems;
        private Context context;

        public RecyclerViewMyAdapterSubscription(List<RecyclerViewListItemSubscription> listItems, Context context) {

            this.listItems = listItems;
            this.context = context;
        }








        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_list_item_subscription, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

            final RecyclerViewListItemSubscription list_item = listItems.get(position);


            holder.courseNameTextView.setText(list_item.getCourseName());
            holder.subscribeButton.setText(list_item.getButonText());

            holder.subscribeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.subscribeButton.getText().equals("Subscribe")){

                        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                        Call<UniSocial> call = apiInterface.subscribe(context.getSharedPreferences(Config.APP_NAME,Context.MODE_PRIVATE).getString(Config.TOKEN,""),holder.courseNameTextView.getText().toString());
                        call.enqueue(new Callback<UniSocial>() {

                            @Override
                            public void onResponse(Call<UniSocial> call, retrofit2.Response<UniSocial> response) {

                                if(response.body().getMessage().equals("true")){

                                    list_item.setButonText("Unsubscribe");
                                    holder.subscribeButton.setText("Unsubscribe");

                                }
                            }

                            @Override
                            public void onFailure(Call<UniSocial> call, Throwable t) {
                                Toast.makeText(context, "Bir hata oluştu, İnternet bağlantınızı ve lokasyon servisinizi kontrol ediniz", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else{
                        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                        Call<UniSocial> call = apiInterface.unsubscribe(context.getSharedPreferences(Config.APP_NAME,Context.MODE_PRIVATE).getString(Config.TOKEN,""),holder.courseNameTextView.getText().toString());
                        call.enqueue(new Callback<UniSocial>() {

                            @Override
                            public void onResponse(Call<UniSocial> call, retrofit2.Response<UniSocial> response) {

                                if(response.body().getMessage().equals("true")){

                                   list_item.setButonText("Subscribe");
                                   holder.subscribeButton.setText("Subscribe");

                                }
                            }

                            @Override
                            public void onFailure(Call<UniSocial> call, Throwable t) {
                                Toast.makeText(context, "Bir hata oluştu, İnternet bağlantınızı ve lokasyon servisinizi kontrol ediniz", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            });





        }

        @Override
        public int getItemCount() {
            return listItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            public TextView courseNameTextView;
            public Button subscribeButton;



            public ViewHolder(final View itemView) {
                super(itemView);
                courseNameTextView = itemView.findViewById(R.id.courseName);
                subscribeButton = itemView.findViewById(R.id.subscribeButton);


            }




        }








}
