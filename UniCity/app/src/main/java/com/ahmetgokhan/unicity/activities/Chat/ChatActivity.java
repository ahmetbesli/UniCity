package com.ahmetgokhan.unicity.activities.Chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import de.hdodenhof.circleimageview.CircleImageView;
import io.socket.client.Socket;
import io.socket.client.IO;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.activities.Homepage.HomeActivity;
import com.ahmetgokhan.unicity.activities.Search.UsersProfileActivity;
import com.ahmetgokhan.unicity.config.Config;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ChatActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ChatMessage> arrayList = new ArrayList<>();
    Button sendMessage;
    EditText getMessage;
    Socket socket = null;
    ChatAdapter chatAdapter;
    String to_name = null;
    String to_username = null;
    String thread_id = null;
    String thread_id_sql = null;
    CircleImageView circleImageView;
    String photo_url;
    TextView chatUserName;


    @Override
    protected void onStart() {
        super.onStart();
        circleImageView = findViewById(R.id.imageUserChat);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider));
        recyclerView = findViewById(R.id.recyclerViewChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(itemDecorator);
        to_username = getIntent().getStringExtra("username");
        photo_url = getIntent().getStringExtra("photo");//***

        AsyncTask<String, Void, Bitmap> profileTask = new ChatActivity.BitmapTask().execute(photo_url);


        try {
            circleImageView.setImageBitmap(profileTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        to_name = getIntent().getStringExtra("name"); //***
        chatAdapter = new ChatAdapter(arrayList, getApplicationContext());
        recyclerView.setAdapter(chatAdapter);
        chatUserName = findViewById(R.id.userNameSurnameChat);
        chatUserName.setText(to_name);
        String full = to_username.concat(getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getString(Config.USERNAME, ""));
        int value = 0;
        for (int i = 0; i < full.length(); i++) {
            value += (int) full.charAt(i);
        }

        thread_id = String.valueOf(value);

        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<UniSocial> call = apiInterface.roomExits(to_username, getApplicationContext().getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getString(Config.TOKEN, ""));
        call.enqueue(new Callback<UniSocial>() {
            @Override
            public void onResponse(Call<UniSocial> call, Response<UniSocial> response) {
                if (response.body().getMessage().equals("false")) {
                    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                    Call<UniSocial> call0 = apiInterface.createRoom(to_username, getApplicationContext().getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getString(Config.TOKEN, ""), to_name);
                    call0.enqueue(new Callback<UniSocial>() {
                        @Override
                        public void onResponse(Call<UniSocial> call, Response<UniSocial> response) {
                            if (response.body().getMessage().equals("true")) {
                                thread_id_sql = response.body().getRoom();
                                try {
                                    socket = IO.socket("http://94.54.213.189:4000");
                                    socket.on("connection", new Emitter.Listener() {

                                        @Override
                                        public void call(Object... args) {

                                            final JSONObject json = new JSONObject();
                                            try {

                                                json.put("from_username", getApplicationContext().getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getString(Config.USERNAME, ""));
                                                json.put("to_username", to_username);
                                                json.put("from_name", getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getString(Config.NAME, "") + " " + getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getString(Config.SURNAME, ""));
                                                json.put("to_name", to_name);
                                                json.put("message", getMessage.getText().toString().trim());

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            socket.emit("checkChatUpdates", json);
                                        }
                                    }).on("checkSituationChat", new Emitter.Listener() {
                                        @Override
                                        public void call(Object... args) {
                                            System.out.println(args[0].toString());
                                            thread_id = (String) args[0];
                                            System.out.println(thread_id);
                                        }
                                    }).on("sendMessage", new Emitter.Listener() {
                                        @Override
                                        public void call(Object... args) {
                                            final JSONObject message = (JSONObject) args[0];
                                            System.out.println(message);
                                            try {
                                                if (message.getString("thread_id").equals(thread_id_sql)) {
                                                    ChatMessage cht = new ChatMessage(message.getString("from_name"), message.getString("message"), message.getString("date"));
                                                    if(message.getString("from_name").trim().equals(getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getString(Config.NAME, "") + " " + getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getString(Config.SURNAME, "").trim())){
                                                        cht.setOther(1);
                                                    }else{
                                                        cht.setOther(0);
                                                    }
                                                    arrayList.add(cht);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    chatAdapter.notifyDataSetChanged();
                                                    recyclerView.scrollToPosition(arrayList.size() - 1);
                                                }
                                            });
                                        }
                                    }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                                        @Override
                                        public void call(Object... args) {
                                            socket.disconnect();
                                        }
                                    });
                                    socket.connect();
                                } catch (Exception e) {
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UniSocial> call, Throwable t) {

                        }
                    });
                } else {
                    thread_id_sql = response.body().getRoom();
                    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                    Call<ArrayList<UniSocial>> call0 = apiInterface.getConversations(thread_id);
                    call0.enqueue(new Callback<ArrayList<UniSocial>>() {
                        @Override
                        public void onResponse(Call<ArrayList<UniSocial>> call, final Response<ArrayList<UniSocial>> response) {
                            for (int x = 0; x < response.body().size(); x++) {
                                ChatMessage chatMessage = new ChatMessage(response.body().get(x).getFrom_name(), response.body().get(x).getMessage(), response.body().get(x).getDate());

                                if(response.body().get(x).getFrom_name().trim().equals(getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getString(Config.NAME, "") + " " + getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getString(Config.SURNAME, "").trim())){
                                    chatMessage.setOther(1);
                                }else{
                                    chatMessage.setOther(0);
                                }
                                if(!chatMessage.getMessage().equals("")){
                                    arrayList.add(chatMessage);
                                    chatAdapter.notifyDataSetChanged();
                                    recyclerView.scrollToPosition(arrayList.size() - 1);
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {

                        }
                    });

                    try {
                        socket = IO.socket("http://94.54.213.189:4000");
                        socket.on("connection", new Emitter.Listener() {

                            @Override
                            public void call(Object... args) {

                                final JSONObject json = new JSONObject();
                                try {

                                    json.put("from_username", getApplicationContext().getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getString(Config.USERNAME, ""));
                                    json.put("to_username", to_username);
                                    json.put("from_name", getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getString(Config.NAME, "") + " " + getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getString(Config.SURNAME, ""));
                                    json.put("to_name", to_name);
                                    json.put("message", getMessage.getText().toString().trim());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                socket.emit("checkChatUpdates", json);
                            }
                        }).on("checkSituationChat", new Emitter.Listener() {
                            @Override
                            public void call(Object... args) {
                                System.out.println(args[0].toString());
                                thread_id = (String) args[0];
                                System.out.println(thread_id);
                            }
                        }).on("sendMessage", new Emitter.Listener() {
                            @Override
                            public void call(Object... args) {
                                final JSONObject message = (JSONObject) args[0];

                                try {
                                    if (message.getString("thread_id").equals(thread_id_sql)) {
                                        ChatMessage cht = new ChatMessage(message.getString("from_name"), message.getString("message"), message.getString("date"));
                                        System.out.println(getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getString(Config.NAME, "") + " " + getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getString(Config.SURNAME, ""));
                                        System.out.println(message.getString("from_name"));
                                        if(message.getString("from_name").trim().equals(getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getString(Config.NAME, "") + " " + getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getString(Config.SURNAME, "").trim())){
                                            cht.setOther(1);
                                        }else{
                                            cht.setOther(0);
                                        }
                                        arrayList.add(cht);

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        chatAdapter.notifyDataSetChanged();
                                        recyclerView.scrollToPosition(arrayList.size() - 1);
                                    }
                                });
                            }
                        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                            @Override
                            public void call(Object... args) {
                                socket.disconnect();
                            }
                        });
                        socket.connect();
                    } catch (Exception e) {
                    }
                }


            }

            @Override
            public void onFailure(Call<UniSocial> call, Throwable t) {

            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sendMessage = findViewById(R.id.sendMessageChat);
        getMessage = findViewById(R.id.text_chat_enter_message);



        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (socket != null && !getMessage.getText().toString().equals("")) {
                    final JSONObject json = new JSONObject();
                    try {
                        json.put("thread_id", thread_id);
                        json.put("from_username", getApplicationContext().getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getString(Config.USERNAME, ""));
                        json.put("to_username", to_username);
                        json.put("from_name", getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getString(Config.NAME, "") + " " + getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getString(Config.SURNAME, ""));
                        json.put("to_name", to_name);
                        json.put("message", getMessage.getText().toString().trim());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    socket.emit("sendMessage", json);
                    getMessage.setText("");
                }
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class BitmapTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                return BitmapFactory.decodeStream(new URL(strings[0]).openStream());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (socket != null) socket.disconnect();
    }
}
