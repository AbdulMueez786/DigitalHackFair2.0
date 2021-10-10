package com.example.digitalhackfair20.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalhackfair20.R;
import com.example.digitalhackfair20.adapter.TaskRvAdapter;
import com.example.digitalhackfair20.model.message;
import com.example.digitalhackfair20.model.task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Tasks extends AppCompatActivity {
    private View list_view;
    private RecyclerView list_rv;
    private List<task> ls = new ArrayList<task>();
    private FirebaseUser USER;
    private TaskRvAdapter list_adapter;
    private DatabaseReference root;
    private DatabaseReference temp;
    private de.hdodenhof.circleimageview.CircleImageView searchbar_profile;
    private com.google.android.material.textfield.TextInputEditText list_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        USER = FirebaseAuth.getInstance().getCurrentUser();
        this.list_rv = findViewById(R.id.rv);
        list_adapter = new TaskRvAdapter(ls, this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        list_rv.setLayoutManager(lm);
        list_rv.setAdapter(list_adapter);

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

        this.root = FirebaseDatabase.getInstance().getReference("Chats");

        this.root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                ls.clear();
                for (DataSnapshot snapshot1 : dataSnapshot1.getChildren()) {
                    message U = snapshot1.getValue(message.class);

                    if (U.getType().matches("task")) {
                        ls.add(new task(U.getId(), U.getText(), "Related software development", U.getTime(), "10/12/2021"));
                    }

                    list_adapter.notifyDataSetChanged();
                    list_adapter.addlist();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    /*
        this.root = FirebaseDatabase.getInstance().getReference("users");

        this.root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                ls.clear();
                for (DataSnapshot snapshot1 : dataSnapshot1.getChildren()) {
                    user U = snapshot1.getValue(user.class);
                    if (USER.getUid().matches(U.getId()) != true && U.getName().matches("admin") == false) {
                        ls.add(new peerModel(U.getId(), U.getName(), U.getCheerpoints(), U.getMedal(), U.getUser_profile()));
                        list_adapter.notifyDataSetChanged();
                        list_adapter.addlist();
                    }
                    if (USER.getUid().matches(U.getId()) == true) {
                        if (U.getUser_profile().matches("default") == true) {
                            Picasso.get().load(R.drawable.user).into(searchbar_profile);
                        } else {
                            Picasso.get().load(U.getUser_profile()).into(searchbar_profile);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/
    }
}