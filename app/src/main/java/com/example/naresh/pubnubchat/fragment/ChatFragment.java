package com.example.naresh.pubnubchat.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.naresh.pubnubchat.R;
import com.example.naresh.pubnubchat.callback.CustomCallback;
import com.example.naresh.pubnubchat.databinding.ChatFragmentBinding;

public class ChatFragment extends Fragment {

    private ChatFragmentBinding mChatFragmentBinding;
    private String TAG = ChatFragment.class.getSimpleName();
    private SharedPreferences sharedPreferences;
    private CustomCallback callback;
    private Context context;

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
        // Inflate the layout for this fragment
        mChatFragmentBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        setHasOptionsMenu(true);

        context = getActivity();
        callback = (CustomCallback) context;
        sharedPreferences = context.getSharedPreferences("details", Context.MODE_PRIVATE);
        getActivity().setTitle("Chats");
        return mChatFragmentBinding.getRoot();
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
}
