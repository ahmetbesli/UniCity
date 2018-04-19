package com.ahmetgokhan.unicity.adapters;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.config.Config;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;

public class SubscribeAdapterWithButton extends ArrayAdapter<String> {
    static int x = 0;
    private int layout;
    ArrayList<String> equals = new ArrayList<>();
    private List<String> mObjects;
    ViewHolder mainViewholder;

    public SubscribeAdapterWithButton(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        mObjects = objects;
        layout = resource;

    }

    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {



        if (convertView == null && x == 0) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            //viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.list_item_thumbnail);

            viewHolder.button = convertView.findViewById(R.id.list_view_button_subscribe);
            viewHolder.title = convertView.findViewById(R.id.listViewText);
            convertView.setTag(viewHolder);
            mainViewholder = (ViewHolder) convertView.getTag();

        }


        mainViewholder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Button b = (Button)v;
                if(b.getText().equals("Subscribe")){

                    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                    Call<UniSocial> call = apiInterface.subscribe(getContext().getSharedPreferences(Config.APP_NAME,Context.MODE_PRIVATE).getString(Config.TOKEN,""),mObjects.get(position));
                    call.enqueue(new Callback<UniSocial>() {

                        @Override
                        public void onResponse(Call<UniSocial> call, retrofit2.Response<UniSocial> response) {

                            if(response.body().getMessage().equals("true")){
                                b.setText("Unsubscribe");
                            }
                        }

                        @Override
                        public void onFailure(Call<UniSocial> call, Throwable t) {
                            Toast.makeText(getContext(), "Bir hata oluştu, İnternet bağlantınızı ve lokasyon servisinizi kontrol ediniz", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                    Call<UniSocial> call = apiInterface.unsubscribe(getContext().getSharedPreferences(Config.APP_NAME,Context.MODE_PRIVATE).getString(Config.TOKEN,""),mObjects.get(position));
                    call.enqueue(new Callback<UniSocial>() {

                        @Override
                        public void onResponse(Call<UniSocial> call, retrofit2.Response<UniSocial> response) {

                            if(response.body().getMessage().equals("true")){
                                b.setText("Subscribe");
                            }
                        }

                        @Override
                        public void onFailure(Call<UniSocial> call, Throwable t) {
                            Toast.makeText(getContext(), "Bir hata oluştu, İnternet bağlantınızı ve lokasyon servisinizi kontrol ediniz", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });

        mainViewholder.title.setText(getItem(position));






        return convertView;
    }





    public class ViewHolder {

        ImageView thumbnail;
        TextView title;
        Button button;

    }
}