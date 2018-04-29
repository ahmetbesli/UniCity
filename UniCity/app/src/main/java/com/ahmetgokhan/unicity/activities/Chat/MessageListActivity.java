package com.ahmetgokhan.unicity.activities.Chat;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.activities.Search.RecyclerItemClickListener;
import com.ahmetgokhan.unicity.activities.Search.UsersProfileActivity;
import com.ahmetgokhan.unicity.config.Config;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageListActivity extends AppCompatActivity {
    RecyclerView messageList;
    ArrayList<MessageListData> arrayList = new ArrayList<>();
    MessageListAdapter messageListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        messageList = findViewById(R.id.message_list);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider));
        messageList.setLayoutManager(new LinearLayoutManager(this));
        messageList.addItemDecoration(itemDecorator);
        messageListAdapter = new MessageListAdapter(arrayList,getApplicationContext());


        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ArrayList<UniSocial>> call = apiInterface.getConversations(getApplicationContext().getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getString(Config.TOKEN, ""));
        call.enqueue(new Callback<ArrayList<UniSocial>> () {
            @Override
            public void onResponse(Call<ArrayList<UniSocial>>  call, Response<ArrayList<UniSocial>>  response) {
                for(int i = 0; i < response.body().size(); i++){
                    arrayList.add(new MessageListData(response.body().get(i).getName() + " " + response.body().get(i).getSurname(),"You have a message!",response.body().get(i).getRoom(),response.body().get(i).getProfile_photo()));
                }

                messageList.setAdapter(messageListAdapter);

            }

            @Override
            public void onFailure(Call<ArrayList<UniSocial>>  call, Throwable t) {

            }
        });

        messageList.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), messageList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView txt = view.findViewById(R.id.chat_room_id);
                Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
                intent.putExtra("room_id",txt.getText().toString());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

    }
}
