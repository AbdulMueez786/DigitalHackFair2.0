package com.example.digitalhackfair20.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.example.digitalhackfair20.R;
import com.google.firebase.auth.FirebaseAuth;

public class AdminHome extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private de.hdodenhof.circleimageview.CircleImageView UserProfile;
    private androidx.cardview.widget.CardView users, tasks, reports, setting;

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dor_profile:
                Intent intent = new Intent(AdminHome.this, AdminProfile.class);
                startActivity(intent);
                break;
            case R.id.dor_logout:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(AdminHome.this, SignIn.class);
                startActivity(i);
                finish();
                break;
            default:
                return false;
        }
        return true;
    }

    public void ShowPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.main);
        popup.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        UserProfile = findViewById(R.id.UserProfile);
        users = findViewById(R.id.users);
        tasks = findViewById(R.id.tasks);
        reports = findViewById(R.id.reports);
        setting = findViewById(R.id.setting);


        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, Users_list.class);
                startActivity(intent);
            }
        });
        tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, Tasks.class);
                startActivity(intent);
            }
        });
        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, Reports.class);
                startActivity(intent);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, create_group.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
