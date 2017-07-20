package com.example.naresh.pubnubchat.activity;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.naresh.pubnubchat.R;
import com.example.naresh.pubnubchat.callback.CustomCallback;
import com.example.naresh.pubnubchat.databinding.MainActivityBinding;
import com.example.naresh.pubnubchat.fragment.ChatFragment;
import com.example.naresh.pubnubchat.fragment.LoginFragment;

public class MainActivity extends AppCompatActivity implements CustomCallback {
    private SharedPreferences sharedPreferences;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MainActivityBinding mMainActivityBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        sharedPreferences = getSharedPreferences("details", MODE_PRIVATE);
        fragmentManager = getSupportFragmentManager();
    }


    @Override
    protected void onStart() {
        super.onStart();
        changeLogin();
    }

    public void changeLogin() {
        if (sharedPreferences.getString("username", null) == null) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new LoginFragment(), "Login");
            fragmentTransaction.commit();
        } else {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new ChatFragment(), "Chat");
            Toast.makeText(this, "LoggedIn Successfully...!!", Toast.LENGTH_SHORT).show();
            fragmentTransaction.commit();
        }
    }

    @Override
    public void loginActivity(int LOGIN_STATE) {

        if (LOGIN_STATE == 0) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new ChatFragment(), "Chat");
            fragmentTransaction.commit();
        } else {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new LoginFragment(), "Login");
            fragmentTransaction.commit();
        }
    }
}
