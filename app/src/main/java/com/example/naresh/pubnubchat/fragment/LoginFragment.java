package com.example.naresh.pubnubchat.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.naresh.pubnubchat.R;
import com.example.naresh.pubnubchat.callback.CustomCallback;
import com.example.naresh.pubnubchat.databinding.ChatFragmentBinding;
import com.example.naresh.pubnubchat.databinding.LoginFragmentBinding;

public class LoginFragment extends Fragment {

    private LoginFragmentBinding mLoginFragmentBinding;
    SharedPreferences sharedPreferences;
    Context context;
    CustomCallback callback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mLoginFragmentBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        context = getActivity();
        callback = (CustomCallback) context;
        sharedPreferences = context.getSharedPreferences("details", Context.MODE_PRIVATE);
        getActivity().setTitle("Login");

        mLoginFragmentBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = mLoginFragmentBinding.edtUsername.getText().toString().trim();
                if (mLoginFragmentBinding.btnLogin.getId() == v.getId()) {
                    if (username.length() != 0) {
                        callback.loginActivity(0);
                        sharedPreferences.edit().putString("username", username).apply();
                    } else {
                        Toast.makeText(context, "Please Enter username", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return mLoginFragmentBinding.getRoot();
    }

}
