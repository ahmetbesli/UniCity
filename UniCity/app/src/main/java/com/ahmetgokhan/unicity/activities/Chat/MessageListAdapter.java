package com.ahmetgokhan.unicity.activities.Chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ahmetgokhan.unicity.R;
import java.util.List;


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

        holder.textViewName.setText(list_item.getName());
        holder.textViewMessage.setText(list_item.getMessage());
        holder.textViewChatRoom.setText(list_item.getChat_room());


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewChatRoom;
        public TextView textViewName;
        public TextView textViewMessage;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.message_list_sender_name);
            textViewMessage = itemView.findViewById(R.id.message_list_last_text);
            textViewChatRoom = itemView.findViewById(R.id.chat_room_id);

        }
    }
}
