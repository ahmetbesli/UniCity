package com.ahmetgokhan.unicity.activities.Homepage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.activities.AdvertPage.AdvertPageActivity;
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
import retrofit2.Response;

/**
 * Created by gokhankilic on 9.03.2018.
 */


    public class RecyclerViewMyAdapterHome extends RecyclerView.Adapter<com.ahmetgokhan.unicity.activities.Homepage.RecyclerViewMyAdapterHome.ViewHolder> {

        private List<com.ahmetgokhan.unicity.activities.Homepage.RecyclerViewListItemHome> listItems;
        private Context context;
    com.ahmetgokhan.unicity.activities.Homepage.RecyclerViewListItemHome list_item;

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
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

            list_item = listItems.get(position);

            holder.textViewAdvertId.setText(list_item.getAdvert_id());
            holder.textViewCourseName.setText(list_item.getCourseName());
            holder.textViewAdvertName.setText(list_item.getAdvertName());
            holder.textViewDescription.setText(list_item.getDescription());
            holder.textViewAdvertDate.setText(list_item.getAdvertDate());
            holder.textViewNumberOfPerson.setText(list_item.getNumberOfPerson() + "");
            holder.buttonApply.setText(list_item.getButonText());
            holder.user_id.setText(list_item.getUser_id());



            ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
            Call<UniSocial> call = apiInterface.getProfileFromUserID(holder.user_id.getText().toString());
            call.enqueue(new Callback<UniSocial>() {
                @Override
                public void onResponse(Call<UniSocial> call, Response<UniSocial> response) {
                    holder.createrNameAdvert.setText(response.body().getName() + " " + response.body().getSurname());

                    AsyncTask<String, Void, Bitmap> profileTask =  new BitmapTask().execute(response.body().getProfile_photo());

                    try {

                       holder.profilePhotoAdvert.setImageBitmap(profileTask.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                    }

                }

                @Override
                public void onFailure(Call<UniSocial> call, Throwable t) {

                }
            });







        }

        @Override
        public int getItemCount() {
            return listItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView textViewAdvertId;
            public TextView createrNameAdvert;
            public CircleImageView profilePhotoAdvert;
            public TextView textViewCourseName;
            public TextView textViewAdvertName;
            public TextView textViewDescription;
            public TextView user_id;
            public TextView textViewAdvertDate;
            public TextView textViewNumberOfPerson;
            public Button buttonApply;
            public Button buttonBrowse;


            public ViewHolder(final View itemView) {
                super(itemView);
                buttonApply = itemView.findViewById(R.id.list_view_home_button_apply);
                buttonBrowse = itemView.findViewById(R.id.list_view_home_button_browse);
                textViewAdvertId = itemView.findViewById(R.id.advert_id);
                textViewCourseName = itemView.findViewById(R.id.textViewCourseName);
                textViewAdvertName = itemView.findViewById(R.id.textViewAdvertName);
                textViewDescription = itemView.findViewById(R.id.textViewDescription);
                textViewAdvertDate = itemView.findViewById(R.id.textViewAdvertDate);
                textViewNumberOfPerson = itemView.findViewById(R.id.textViewNumberOfPerson);
                user_id = itemView.findViewById(R.id.user_id);
                createrNameAdvert = itemView.findViewById(R.id.nameCreatorAdvert);
                profilePhotoAdvert = itemView.findViewById(R.id.profilePhotoAdvert);







                buttonApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(buttonApply.getText().equals("Apply")){

                            new AlertDialog.Builder(itemView.getRootView().getContext())
                                    .setTitle("Are you want to apply this advet ?")
                                    .setMessage("Click OK button if you want")
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                                            Call<UniSocial> call = apiInterface.getAdvertApply(context.getSharedPreferences(Config.APP_NAME,Context.MODE_PRIVATE).getString(Config.TOKEN,""),textViewAdvertId.getText().toString());
                                            call.enqueue(new Callback<UniSocial>() {

                                                @Override
                                                public void onResponse(Call<UniSocial> call, retrofit2.Response<UniSocial> response) {

                                                    if(response.body().getMessage().equals("true")){
                                                        buttonApply.setText("Unapply");
                                                        list_item.setButonText("Unapply");


                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<UniSocial> call, Throwable t) {
                                                }
                                            });


                                        }
                                    }).create().show();



                        }else if(buttonApply.getText().toString().equals("Your Advert")){
                            Toast.makeText(context,"You can not apply your advert", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            new AlertDialog.Builder(itemView.getRootView().getContext())
                                    .setTitle("Are u sure want to unapply this advert ?")
                                    .setMessage("Click OK button if you want")
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                                            Call<UniSocial> call = apiInterface.unApply(context.getSharedPreferences(Config.APP_NAME,Context.MODE_PRIVATE).getString(Config.TOKEN,""),textViewAdvertId.getText().toString());
                                            call.enqueue(new Callback<UniSocial>() {

                                                @Override
                                                public void onResponse(Call<UniSocial> call, retrofit2.Response<UniSocial> response) {

                                                    if(response.body().getMessage().equals("true")){
                                                        buttonApply.setText("Apply");
                                                        list_item.setButonText("Apply");
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<UniSocial> call, Throwable t) {
                                                }
                                            });






                                        }
                                    }).create().show();

                        }

                    }
                });



                buttonBrowse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, AdvertPageActivity.class);
                        intent.putExtra("advert_id",textViewAdvertId.getText().toString());
                        intent.putExtra("buttonText",buttonApply.getText().toString());
                        intent.putExtra("user_id",user_id.getText().toString());

                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);



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
