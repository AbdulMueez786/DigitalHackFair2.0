package com.example.digitalhackfair20.activity;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.digitalhackfair20.R;
import com.example.digitalhackfair20.adapter.ViewPagerAdapter;
import com.example.digitalhackfair20.fragment.Chat;
import com.example.digitalhackfair20.fragment.GroupChat;
import com.google.android.material.tabs.TabLayout;

public class ChatHome extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_home);
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewPager);
        getTabs();
    }

    public void getTabs() {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                System.out.println("Heeeeeelllllllllllllllll");
                viewPagerAdapter.addFragment(Chat.getInstance(), "CHATS");
                viewPagerAdapter.addFragment(GroupChat.getInstance(), "GROUP CHATS");
                //viewPagerAdapter.addFragment(Calls.getInstance(), "CALLS");

                viewPager.setAdapter(viewPagerAdapter);
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }
}