package com.example.digitalhackfair20.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalhackfair20.R;
import com.example.digitalhackfair20.adapter.UsersRvAdapter;
import com.example.digitalhackfair20.model.user;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Users_list extends AppCompatActivity {
    private View list_view;
    private RecyclerView list_rv;
    private List<user> ls = new ArrayList<user>();
    private FirebaseUser USER;
    private UsersRvAdapter list_adapter;
    private DatabaseReference root;
    private DatabaseReference temp;
    private de.hdodenhof.circleimageview.CircleImageView searchbar_profile;
    private com.google.android.material.textfield.TextInputEditText list_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        USER = FirebaseAuth.getInstance().getCurrentUser();
        this.list_rv = findViewById(R.id.rv);
        list_adapter = new UsersRvAdapter(ls, this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        list_rv.setLayoutManager(lm);
        list_rv.setAdapter(list_adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Users_list.this, UserAdd.class);
                startActivity(intent);
            }
        });

        this.USER = FirebaseAuth.getInstance().getCurrentUser();
        this.searchbar_profile = findViewById(R.id.searchbar_profile);
        this.list_search = findViewById(R.id.list_search);

        this.list_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list_adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        this.root = FirebaseDatabase.getInstance().getReference("users");

        this.root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                ls.clear();
                for (DataSnapshot snapshot1 : dataSnapshot1.getChildren()) {
                    user U = snapshot1.getValue(user.class);
                    if (USER.getUid().matches(U.getId()) != true && U.getEmail().matches("admin@gmail.com") == false) {
                        ls.add(new user(U.getId(), U.getApp_id(), U.getName(), U.getEmail(), U.getPost(), U.getBio(), U.getJoin_date(), U.getGender(), U.getStatus(),
                                U.getUser_profile(), U.getWarning(), U.getAccount_suspended()));
                        list_adapter.notifyDataSetChanged();
                        list_adapter.addlist();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}