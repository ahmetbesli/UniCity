package com.ahmetgokhan.unicity.activities.Chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ahmetgokhan.unicity.R;
import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<ChatMessage> listItems;
    private Context context;

    public ChatAdapter(List<ChatMessage> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;

    }


    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ChatMessage list_item = listItems.get(position);

        holder.textViewName.setText(list_item.getName());
        holder.textViewMessage.setText(list_item.getMessage());


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView textViewMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.chat_text_name);
            textViewMessage = itemView.findViewById(R.id.chat_text_message);

        }
    }
}
