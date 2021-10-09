package com.example.digitalhackfair20.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.example.digitalhackfair20.R;

public class AdminHome extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private de.hdodenhof.circleimageview.CircleImageView UserProfile;
    private androidx.cardview.widget.CardView users, tasks, reports, setting;

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dor_profile:
                break;
            case R.id.dor_logout:
                break;
            default:
                return false;
        }
        return true;
    }

    public void ShowPopup(View v) {
        System.out.println("99999999999999999999999999999999999999");
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.main);
        popup.show();
    }

    /*
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.dor_profile:
                    break;
                case R.id.dor_logout:
                    break;
            }

            return super.onOptionsItemSelected(item);
        }
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        UserProfile = findViewById(R.id.UserProfile);
        users = findViewById(R.id.users);
        tasks = findViewById(R.id.tasks);
        reports = findViewById(R.id.reports);
        setting = findViewById(R.id.setting);

        //UserProfile.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //   public void onClick(View view) {
        //ShowPopup(R.menu.main);
        //Intent intent = new Intent(AdminHome.this, AdminProfile.class);
        //startActivity(intent);
        //   }
        //});

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
