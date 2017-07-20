package com.example.naresh.pubnubchat.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.naresh.pubnubchat.R;
import com.example.naresh.pubnubchat.adapter.ChatAdapter;
import com.example.naresh.pubnubchat.callback.CustomCallback;
import com.example.naresh.pubnubchat.databinding.ChatFragmentBinding;
import com.example.naresh.pubnubchat.keys.PubNubKeys;
import com.example.naresh.pubnubchat.pojo.Message;
import com.google.gson.Gson;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    private ChatFragmentBinding mChatFragmentBinding;
    private String TAG = ChatFragment.class.getSimpleName();
    private SharedPreferences sharedPreferences;
    private CustomCallback callback;
    private Context context;
    private Pubnub pubnub;
    private ArrayList<String> chatMessageList;
    private ChatAdapter chatAdapter;
    private Gson gson;
    private JSONObject messageObject;
    private String username;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bindAllView(inflater, container);
        setHasOptionsMenu(true);
        initObjects();

        username = sharedPreferences.getString("username", null);
        getActivity().setTitle(username);

        setAdapter();

        pubnub = new Pubnub(PubNubKeys.PUBLISH_KEY, PubNubKeys.SUBSCRIBE_KEY);

        try {

            pubnub.subscribe(PubNubKeys.CHANNEL_NAME, new Callback() {

                @Override
                public void successCallback(String channel, Object message) {
                    super.successCallback(channel, message);

                    chatMessageList.add(message.toString());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            chatAdapter.notifyItemInserted(chatMessageList.size() - 1);
                            mChatFragmentBinding.chatListRecyclerView.scrollToPosition(chatMessageList.size() - 1);
                        }
                    });
                    Log.e(TAG, "successCallback: " + message);
                }

                @Override
                public void successCallback(String channel, Object message, String timetoken) {
                    super.successCallback(channel, message, timetoken);
                }

                @Override
                public void errorCallback(String channel, PubnubError error) {
                    super.errorCallback(channel, error);
                    Log.d("errorCallback", "error " + error);
                }

                @Override
                public void connectCallback(String channel, Object message) {
                    super.connectCallback(channel, message);
                    Log.d("connectCallback", "message " + message);
                }

                @Override
                public void reconnectCallback(String channel, Object message) {
                    super.reconnectCallback(channel, message);
                    Log.d("reconnectCallback", "message " + message);
                }

                @Override
                public void disconnectCallback(String channel, Object message) {
                    super.disconnectCallback(channel, message);
                    Log.d("disconnectCallback", "message " + message);
                }

            });

        } catch (PubnubException pb) {

            Log.e(TAG, pb.toString());
        }

        mChatFragmentBinding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mChatFragmentBinding.btnSend.getId() == v.getId()) {
                    String message = mChatFragmentBinding.edtMessage.getText().toString().trim();
                    if (message.length() != 0) {
                        message = gson.toJson(new Message(username, message));
                        try {
                            messageObject = new JSONObject(message);
                        } catch (JSONException je) {
                            Log.d(TAG, je.toString());
                        }
                        mChatFragmentBinding.edtMessage.setText("");

                        pubnub.publish(PubNubKeys.CHANNEL_NAME, messageObject, new Callback() {
                            @Override
                            public void successCallback(String channel, Object message) {
                                super.successCallback(channel, message);
                                Log.d("successCallback", "message " + message);
                            }

                            @Override
                            public void errorCallback(String channel, PubnubError error) {
                                super.errorCallback(channel, error);
                                Log.d("errorCallback", "error " + error);
                            }
                        });
                    } else {
                        Toast.makeText(context, "Please enter message", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        return mChatFragmentBinding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
        pubnub.unsubscribe(PubNubKeys.CHANNEL_NAME);
        Log.d(TAG, "Un subscribed");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.logout, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Do you really want to logout ??");
            builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    callback.loginActivity(1);
                    sharedPreferences.edit().remove("username").apply();
                    Toast.makeText(getActivity(), "Logged out Successfully", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
            return true;
        }
        return false;
    }

    private void bindAllView(LayoutInflater inflater, ViewGroup container) {
        // Inflate the layout for this fragment
        mChatFragmentBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
    }

    private void initObjects() {
        context = getActivity();
        callback = (CustomCallback) context;
        sharedPreferences = context.getSharedPreferences("details", Context.MODE_PRIVATE);
        gson = new Gson();
    }

    private void setAdapter() {
        chatMessageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessageList, username);

        mChatFragmentBinding.chatListRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mChatFragmentBinding.chatListRecyclerView.setAdapter(chatAdapter);
    }

}
