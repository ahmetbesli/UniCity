package com.ahmetgokhan.unicity.activities.Chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.config.Config;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;


public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    private List<MessageListData> listItems;
    private Context context;

    public MessageListAdapter(List<MessageListData> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;

    }


    @Override
    public MessageListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_message_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        MessageListData list_item = listItems.get(position);
        AsyncTask<String, Void, Bitmap> profileTask = new MessageListAdapter.BitmapTask().execute(list_item.getCircleImageView());
        holder.textViewName.setText(list_item.getName());
        holder.textViewMessage.setText(list_item.getMessage());
        holder.textViewChatRoom.setText(list_item.getThread_id());
        try {
            holder.circleImageView.setImageBitmap(profileTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView circleImageView;
        public TextView textViewChatRoom;
        public TextView textViewName;
        public TextView textViewMessage;

        public ViewHolder(View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.message_list_image);
            textViewName = itemView.findViewById(R.id.message_list_sender_name);
            textViewMessage = itemView.findViewById(R.id.message_list_last_text);
            textViewChatRoom = itemView.findViewById(R.id.chat_room_id);

        }
    }

    @SuppressLint("StaticFieldLeak")
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
