package com.example.digitalhackfair20.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.digitalhackfair20.R;

public class AdminHome extends AppCompatActivity {

    private de.hdodenhof.circleimageview.CircleImageView UserProfile;
    private androidx.cardview.widget.CardView users, tasks, reports, setting;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        UserProfile = findViewById(R.id.UserProfile);
        users = findViewById(R.id.users);
        tasks = findViewById(R.id.tasks);
        reports = findViewById(R.id.reports);
        setting = findViewById(R.id.setting);

        UserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, AdminProfile.class);
                startActivity(intent);
            }
        });

        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
