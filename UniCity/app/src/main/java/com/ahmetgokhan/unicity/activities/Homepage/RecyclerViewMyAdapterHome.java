package com.ahmetgokhan.unicity.activities.Homepage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.config.Config;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

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
        public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_list_item_advert, parent, false);

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
            holder.buttonApply.setText(list_item.getButonText());



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
            public Button buttonApply;
            public Button buttonBrowse;

            public ViewHolder(View itemView) {
                super(itemView);
                buttonApply = itemView.findViewById(R.id.list_view_home_button_apply);
                buttonBrowse = itemView.findViewById(R.id.list_view_home_button_browse);
                textViewAdvertId = itemView.findViewById(R.id.advert_id);
                textViewCourseName = itemView.findViewById(R.id.textViewCourseName);
                textViewAdvertName = itemView.findViewById(R.id.textViewAdvertName);
                textViewDescription = itemView.findViewById(R.id.textViewDescription);
                textViewAdvertDate = itemView.findViewById(R.id.textViewAdvertDate);
                textViewNumberOfPerson = itemView.findViewById(R.id.textViewNumberOfPerson);


                buttonApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(buttonApply.getText().equals("Apply")){

                            ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                            Call<UniSocial> call = apiInterface.getAdvertApply(context.getSharedPreferences(Config.APP_NAME,Context.MODE_PRIVATE).getString(Config.TOKEN,""),textViewAdvertId.getText().toString());
                            call.enqueue(new Callback<UniSocial>() {

                                @Override
                                public void onResponse(Call<UniSocial> call, retrofit2.Response<UniSocial> response) {

                                    if(response.body().getMessage().equals("true")){
                                        buttonApply.setText("Unapply");
                                    }
                                }

                                @Override
                                public void onFailure(Call<UniSocial> call, Throwable t) {
                                }
                            });

                        }else{
                            ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                            Call<UniSocial> call = apiInterface.getAdvertApply(context.getSharedPreferences(Config.APP_NAME,Context.MODE_PRIVATE).getString(Config.TOKEN,""),textViewAdvertId.getText().toString());
                            call.enqueue(new Callback<UniSocial>() {

                                @Override
                                public void onResponse(Call<UniSocial> call, retrofit2.Response<UniSocial> response) {

                                    if(response.body().getMessage().equals("true")){
                                        buttonApply.setText("Apply");
                                    }
                                }

                                @Override
                                public void onFailure(Call<UniSocial> call, Throwable t) {
                                }
                            });

                        }

                    }
                });







            }


        }



}
