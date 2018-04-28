package com.ahmetgokhan.unicity.activities.Chat;

import android.graphics.Bitmap;
import android.os.AsyncTask;
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

import io.socket.client.Socket;
import io.socket.client.IO;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.activities.Search.UsersProfileActivity;
import com.ahmetgokhan.unicity.config.Config;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ChatActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ChatMessage> arrayList = new ArrayList<>();
    Button sendMessage;
    EditText getMessage;
    Socket socket = null;
    ChatAdapter chatAdapter;

    String to_username = null;
    String room_id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider));
        sendMessage = findViewById(R.id.sendMessageChat);
        getMessage = findViewById(R.id.text_chat_enter_message);
        recyclerView = findViewById(R.id.recyclerViewChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(itemDecorator);
        chatAdapter = new ChatAdapter(arrayList, getApplicationContext());
        recyclerView.setAdapter(chatAdapter);
        to_username = getIntent().getStringExtra("username");
        room_id = getIntent().getStringExtra("room_id");
        if(room_id != null) {

                try {

                    socket = IO.socket("http://94.54.213.189:4000");

                    socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                        @Override
                        public void call(Object... args) {

                            final JSONObject json = new JSONObject();
                            try {
                                json.put("username", "mehmet");
                                json.put("room", room_id);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            socket.emit("adduser", json);

                        }

                    }).on("updatechat", new Emitter.Listener() {
                        //message is the keyword for communication exchanges
                        @Override
                        public void call(Object... args) {
                            String name = (String) args[0];
                            String message = (String) args[1];

                            arrayList.add(new ChatMessage(name, message));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    chatAdapter.notifyDataSetChanged();
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






        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<UniSocial> call = apiInterface.roomExits(to_username, getApplicationContext().getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getString(Config.TOKEN, ""));
        call.enqueue(new Callback<UniSocial>() {
            @Override
            public void onResponse(Call<UniSocial> call, final Response<UniSocial> response) {
                Log.e("cevappp",response.body().getMessage());
                if (response.body().getMessage().equals("true")) {
                    try {

                        socket = IO.socket("http://94.54.213.189:4000");

                        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                            @Override
                            public void call(Object... args) {

                                final JSONObject json = new JSONObject();
                                try {
                                    json.put("username", "ahmet");
                                    json.put("room", response.body().getRoom());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                socket.emit("adduser", json);

                            }

                        }).on("updatechat", new Emitter.Listener() {
                            //message is the keyword for communication exchanges
                            @Override
                            public void call(Object... args) {
                                String name = (String) args[0];
                                String message = (String) args[1];

                                arrayList.add(new ChatMessage(name, message));
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        chatAdapter.notifyDataSetChanged();
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
                } else {
                    Log.e("logggggeerrr","oda oluşturuyor");

                    try {

                        socket = IO.socket("http://94.54.213.189:4000");

                        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                            @Override
                            public void call(Object... args) {
                                socket.emit("createroom", to_username);

                            }

                        }).on("roomcreated", new Emitter.Listener() {
                            //message is the keyword for communication exchanges
                            @Override
                            public void call(Object... args) {
                                String jsonRoom = (String) args[0];
                                System.out.println(jsonRoom + "oluşturulan oda");


                                ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                                Call<UniSocial> call = apiInterface.createRoom(to_username, getApplicationContext().getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).getString(Config.TOKEN, ""), jsonRoom);
                                call.enqueue(new Callback<UniSocial>() {
                                    @Override
                                    public void onResponse(Call<UniSocial> call, Response<UniSocial> response) {
                                            Log.e("oda oluşturumuna cevap",response.body().getMessage());
                                    }

                                    @Override
                                    public void onFailure(Call<UniSocial> call, Throwable t) {

                                    }
                                });
                                final JSONObject json = new JSONObject();
                                try {
                                    json.put("username", "ahmet");
                                    json.put("room", jsonRoom);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                socket.emit("adduser", json);


                            }

                        }).on("updatechat", new Emitter.Listener() {
                            //message is the keyword for communication exchanges
                            @Override
                            public void call(Object... args) {
                                String name = (String) args[0];
                                String message = (String) args[1];

                                arrayList.add(new ChatMessage(name, message));
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        chatAdapter.notifyDataSetChanged();
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

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    socket.emit("sendchat", getMessage.getText().toString());

                } catch (Exception e) {

                }

            }
        });
    }
}
