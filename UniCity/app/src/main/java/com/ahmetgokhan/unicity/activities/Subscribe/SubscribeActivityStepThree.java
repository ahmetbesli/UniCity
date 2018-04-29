package com.ahmetgokhan.unicity.activities.Subscribe;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.activities.Search.RecyclerItemClickListener;
import com.ahmetgokhan.unicity.adapters.SubscribeAdapterWithButton;
import com.ahmetgokhan.unicity.config.Config;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;
import com.ahmetgokhan.unicity.activities.Search.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscribeActivityStepThree extends AppCompatActivity {

    private ArrayList<String> data = new ArrayList<>();
    private String department;
    private RecyclerView recyclerView;
    private List<RecyclerViewListItemSubscription> listItems;
    private RecyclerView.Adapter adapter;
    private ApiInterface apiInterface;
    String butonText;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_step_three);
        department = getIntent().getStringExtra("department");
        recyclerView = findViewById(R.id.recyclerViewSubs);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems =  new ArrayList<>();



        generateListContent();







    }
    private void generateListContent() {

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ArrayList<UniSocial>> call0 = apiInterface.isSubscribed(getApplicationContext().getSharedPreferences(Config.APP_NAME,Context.MODE_PRIVATE).getString(Config.TOKEN,""));
        call0.enqueue(new Callback<ArrayList<UniSocial>>() {

            @Override
            public void onResponse(Call<ArrayList<UniSocial>> call, final retrofit2.Response<ArrayList<UniSocial>> response) {
                Call<ArrayList<UniSocial>> call1 = apiInterface.getCourses(null,null,department);
                call1.enqueue(new Callback<ArrayList<UniSocial>>() {

                    @Override
                    public void onResponse(Call<ArrayList<UniSocial>> call, retrofit2.Response<ArrayList<UniSocial>> response1) {



                        for (int i = 0; i < response1.body().size(); i++) {
                            butonText = "Subscribe";
                            for (int k = 0; k< response.body().size(); k++){

                                if(response1.body().get(i).getCourses().equals(response.body().get(k).getCourse_name()) ){
                                    butonText = "Unsubscribe";
                                    break;

                                }
                                else{
                                    butonText = "Subscribe";
                                }



                            }



                            RecyclerViewListItemSubscription recyclerViewListItemSubscription = new RecyclerViewListItemSubscription(response1.body().get(i).getCourses(),butonText);
                            listItems.add(recyclerViewListItemSubscription);

                        }
                        adapter = new RecyclerViewMyAdapterSubscription(listItems,getApplicationContext());
                        recyclerView.setAdapter(adapter);


                    }

                    @Override
                    public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Bir hata oluştu, İnternet bağlantınızı ve lokasyon servisinizi kontrol ediniz", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Bir hata oluştu, İnternet bağlantınızı ve lokasyon servisinizi kontrol ediniz", Toast.LENGTH_SHORT).show();

            }
        });



    }

   /* public void checkSituationOfButton(){
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ArrayList<UniSocial>> call0 = apiInterface.isSubscribed(getApplicationContext().getSharedPreferences(Config.APP_NAME,Context.MODE_PRIVATE).getString(Config.TOKEN,""));
        call0.enqueue(new Callback<ArrayList<UniSocial>>() {

            @Override
            public void onResponse(Call<ArrayList<UniSocial>> call, retrofit2.Response<ArrayList<UniSocial>> response) {
                for(int i = 0; i < data.size(); i++){
                    Button b = getViewByPosition(i,listView).findViewById(R.id.list_view_button_subscribe);
                    for(int j = 0; j < response.body().size(); j++){
                        if(b.getText().toString().equals(response.body().get(j).getCourse_name())){
                            b.setText("Unsubscribe");
                        }else{
                            b.setText("Subscribe");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Bir hata oluştu, İnternet bağlantınızı ve lokasyon servisinizi kontrol ediniz", Toast.LENGTH_SHORT).show();

            }
        });
    }*/

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }



}
