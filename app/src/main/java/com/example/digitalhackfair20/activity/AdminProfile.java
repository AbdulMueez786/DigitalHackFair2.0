package com.example.digitalhackfair20.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.digitalhackfair20.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminProfile extends AppCompatActivity {
    private CircleImageView admin_pic;
    private TextView admin_name, admin_email, admin_phone, admin_edit_profile;

    //Firebase
    private FirebaseUser USER;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);
        admin_pic = findViewById(R.id.admin_pic);
        admin_name = findViewById(R.id.admin_name);
        admin_email = findViewById(R.id.admin_email);
        admin_phone = findViewById(R.id.admin_phone);
        admin_edit_profile = findViewById(R.id.admin_edit_profile);

        this.USER = FirebaseAuth.getInstance().getCurrentUser();
        this.databaseReference = FirebaseDatabase.getInstance()
                .getReference("users").child(USER.getUid());

        /*this.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                admin_name.setText(dataSnapshot.child("name").getValue(String.class));
                admin_email.setText(dataSnapshot.child("email").getValue(String.class));
                admin_phone.setText(dataSnapshot.child("phone_no").getValue(String.class));
                Picasso.get().load(dataSnapshot.child("user_profile").getValue(String.class))
                        .into(admin_pic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        admin_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminProfile.this, AdminProfileEdit.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}