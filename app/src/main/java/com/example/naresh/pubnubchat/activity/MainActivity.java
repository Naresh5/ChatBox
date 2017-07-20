package com.example.naresh.pubnubchat.activity;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.naresh.pubnubchat.R;
import com.example.naresh.pubnubchat.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity {
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
}
