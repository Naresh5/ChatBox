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
        if (messageObject.getUsername().equals(myUsername)) {
            holder.chatHolderLeft.setVisibility(View.INVISIBLE);
            holder.chatHolder.setVisibility(View.VISIBLE);
            holder.username.setText(messageObject.getUsername());
            holder.message.setText(messageObject.getMessage());
        } else {
            holder.chatHolder.setVisibility(View.INVISIBLE);
            holder.chatHolderLeft.setVisibility(View.VISIBLE);
            holder.usernameLeft.setText(messageObject.getUsername());
            holder.messageLeft.setText(messageObject.getMessage());
        }
    }
    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {

        LinearLayout chatHolder, chatHolderLeft;
        TextView username, usernameLeft;
        TextView message, messageLeft;

        private ChatViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);
            message = (TextView) itemView.findViewById(R.id.message);
            chatHolder = (LinearLayout) itemView.findViewById(R.id.chatHolder);
            usernameLeft = (TextView) itemView.findViewById(R.id.usernameLeft);
            messageLeft = (TextView) itemView.findViewById(R.id.messageLeft);
            chatHolderLeft = (LinearLayout) itemView.findViewById(R.id.chatHolderLeft);
        }
    }
}
