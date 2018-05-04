package com.ahmetgokhan.unicity.activities.Chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ahmetgokhan.unicity.R;

import org.w3c.dom.Text;

import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<ChatMessage> listItems;
    private Context context;
    private final int VIEW_TYPE_USER = 1;
    private final int VIEW_TYPE_OTHER = 0;

    public ChatAdapter(List<ChatMessage> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;

    }


    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {

        if (viewType==VIEW_TYPE_USER){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right,parent,false);
            return new ViewHolder(itemView);
        }else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,parent,false);
            return new ViewHolder(itemView);
        }

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ChatMessage list_item = listItems.get(position);


        holder.textViewMessage.setText(list_item.getMessage());
        holder.textViewDate.setText(list_item.getDate());


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMsg = listItems.get(position);

        if (chatMsg.getOther() == VIEW_TYPE_USER){

            return VIEW_TYPE_USER;
        }else {

            return VIEW_TYPE_OTHER;
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView textViewMessage;
        public TextView textViewDate;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.chat_date);

            textViewMessage = itemView.findViewById(R.id.chat_text_message);



        }
    }
}
