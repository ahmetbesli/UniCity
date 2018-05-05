package com.ahmetgokhan.unicity.activities.AdvertPage;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.activities.Homepage.HomeActivity;

import com.ahmetgokhan.unicity.activities.RequestsPage.RecyclerViewMyAdapterRequests;
import com.ahmetgokhan.unicity.activities.Subscribe.RecyclerViewListItemSubscription;
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


    public class RecyclerViewMyAdapterAdvertPage extends RecyclerView.Adapter<RecyclerViewMyAdapterAdvertPage.ViewHolder> {

        private List<RecyclerViewListItemAdvertPage> listItems;
        private Context context;
        private ApiInterface apiInterface;

        public RecyclerViewMyAdapterAdvertPage(List<RecyclerViewListItemAdvertPage> listItems, Context context) {

            this.listItems = listItems;
            this.context = context;
        }








        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_list_item_workingusers, parent, false);
            return new ViewHolder(v);

        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

            final RecyclerViewListItemAdvertPage list_item = listItems.get(position);

            holder.workerName.setText(list_item.getWorkerName());


            if(context.getSharedPreferences(Config.APP_NAME,Context.MODE_PRIVATE).getString(Config.CREATOR_ID,"")
                    .equals(context.getSharedPreferences(Config.APP_NAME,Context.MODE_PRIVATE).getString(Config.USER_ID,""))){






            }
            else {
                holder.deleteWorker.setVisibility(View.INVISIBLE);


            }

            AsyncTask<String, Void, Bitmap> profileTask = new RecyclerViewMyAdapterAdvertPage.BitmapTask().execute(list_item.getWorkerProfilePhoto());

            try {

                holder.workerProfilePhoto.setImageBitmap(profileTask.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (Exception e) {
            }





            holder.deleteWorker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

                    Call<UniSocial> call = apiInterface.removeWorker(list_item.getAdvert_id(),list_item.getWorkerID());
                    call.enqueue(new Callback<UniSocial>() {
                        @Override
                        public void onResponse(Call<UniSocial> call, Response<UniSocial> response) {

                        holder.workerName.setText("User was deleted");



                        }

                        @Override
                        public void onFailure(Call<UniSocial> call, Throwable t) {

                        }
                    });



                }
            });



        }











        @Override
        public int getItemCount() {
            return listItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            public TextView workerName;
            public ImageButton deleteWorker;
            public CircleImageView workerProfilePhoto;





            public ViewHolder(final View itemView) {
                super(itemView);
                workerName = itemView.findViewById(R.id.workerName);
                deleteWorker = itemView.findViewById(R.id.deleteWorkerButton);
                workerProfilePhoto = itemView.findViewById(R.id.workerProfilePhoto);







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


