package com.example.digitalhackfair20.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.digitalhackfair20.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StayConnected extends AppCompatActivity {

    private Button login_button;
    private FirebaseUser user_ref;

    @Override
    protected void onStart() {
        super.onStart();
        user_ref = FirebaseAuth.getInstance().getCurrentUser();

        if (user_ref != null) {

            if (user_ref.getEmail().matches("admin@gmail.com") == true) {
                Intent intent = new Intent(StayConnected.this, AdminHome.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(StayConnected.this, UserHome.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stay_connected);


        login_button = findViewById(R.id.login_button);


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(StayConnected.this, SignIn.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
    }
}