package com.example.naresh.pubnubchat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.naresh.pubnubchat.R;
import com.example.naresh.pubnubchat.pojo.Message;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private ArrayList<String> chatMessageList;
    private Gson gson = new Gson();
    private String message;
    private String username;
    private String myUsername;
    private Context context;

    public ChatAdapter(ArrayList<String> chatMessageList, String myUsername) {
        this.chatMessageList = chatMessageList;
        this.myUsername = myUsername;
    }


    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ChatViewHolder(LayoutInflater.from(context).inflate
                (R.layout.chat_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {

        message = chatMessageList.get(position);
        Message messageObject = gson.fromJson(message, Message.class);

        username = messageObject.getUsername();
        holder.username.setText(messageObject.getUsername());
        if (!(username.equals(myUsername))) {
            holder.chatHolder.setBackgroundColor(context.getResources().getColor(R.color.colorBlue_200));
        }
        holder.message.setText(messageObject.getMessage());

    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }


    class ChatViewHolder extends RecyclerView.ViewHolder {

        LinearLayout chatHolder;
        TextView username;
        TextView message;

        private ChatViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);
            message = (TextView) itemView.findViewById(R.id.message);
            chatHolder = (LinearLayout) itemView.findViewById(R.id.chatHolder);
        }
    }
}
