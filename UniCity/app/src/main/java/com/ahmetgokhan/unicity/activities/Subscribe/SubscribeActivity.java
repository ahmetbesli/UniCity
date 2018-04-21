package com.ahmetgokhan.unicity.activities.Subscribe;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.activities.Login.LoginActivity;
import com.ahmetgokhan.unicity.adapters.SubscribeAdapter;
import com.ahmetgokhan.unicity.config.Config;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;
import java.util.ArrayList;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;

public class SubscribeActivity extends AppCompatActivity {

    private ArrayList<String> data = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        listView = findViewById(R.id.listViewSubscription1);
        Toast.makeText(this, "Lets complate your profile", Toast.LENGTH_SHORT).show();
        generateListContent();
        checkToken();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),SubscribeActivityStepTwo.class);
                intent.putExtra("faculty",data.get(position));
                startActivity(intent);
            }
        });
    }


    private void generateListContent() {

        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ArrayList<UniSocial>> call = apiInterface.getCourses("Kadir Has Üniversitesi",null,null);
        call.enqueue(new Callback<ArrayList<UniSocial>>() {

            @Override
            public void onResponse(Call<ArrayList<UniSocial>> call, retrofit2.Response<ArrayList<UniSocial>> response) {

                for (int i = 0; i < response.body().size(); i++) {

                    data.add(response.body().get(i).getFaculty());

                }
                listView.setAdapter(new SubscribeAdapter(getApplicationContext(),R.layout.list_item,data));


            }

            @Override
            public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Bir hata oluştu, İnternet bağlantınızı ve lokasyon servisinizi kontrol ediniz", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void checkToken(){
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<UniSocial> callToken = apiInterface.checkToken(getApplicationContext().getSharedPreferences(Config.APP_NAME,MODE_PRIVATE).getString(Config.TOKEN,""));
        callToken.enqueue(new Callback<UniSocial>() {

            @Override
            public void onResponse(Call<UniSocial> call, retrofit2.Response<UniSocial> response) {
                if(response.body().getMessage().equals("true")){
                    getApplicationContext().getSharedPreferences(Config.APP_NAME,MODE_PRIVATE).edit().putBoolean(Config.LOGGING_STATUS,false).apply();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Please login again. Token timeout!",Toast.LENGTH_LONG).show();

                }else{

                    System.out.println("Error");
                }
            }

            @Override
            public void onFailure(Call<UniSocial> call, Throwable t) {

            }

        });
    }
}
