package com.example.digitalhackfair20.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalhackfair20.R;
import com.example.digitalhackfair20.adapter.UsersRvAdapter;
import com.example.digitalhackfair20.model.report;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class Reports extends AppCompatActivity {
    private View list_view;
    private RecyclerView list_rv;
    private List<report> ls = new ArrayList<report>();
    private FirebaseUser USER;
    private UsersRvAdapter list_adapter;
    private DatabaseReference root;
    private DatabaseReference temp;
    private de.hdodenhof.circleimageview.CircleImageView searchbar_profile;
    private com.google.android.material.textfield.TextInputEditText list_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
    }
}