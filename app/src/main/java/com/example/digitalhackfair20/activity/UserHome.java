package com.example.digitalhackfair20.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.digitalhackfair20.R;
import com.example.digitalhackfair20.adapter.SliderAdapters;
import com.example.digitalhackfair20.model.SliderItems;
import com.example.digitalhackfair20.model.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserHome extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private ViewPager2 viewPager2;
    private CircleImageView user_profile;
    private TextView user_name;
    private Handler sliderHandler = new Handler();


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dor_profile:
                Intent intent = new Intent(UserHome.this, UserProfile.class);
                startActivity(intent);
                break;
            case R.id.dor_logout:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(UserHome.this, SignIn.class);
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
        setContentView(R.layout.activity_user_home);

        user_name = findViewById(R.id.user_name);
        user_profile = findViewById(R.id.profile_image);
        FirebaseDatabase.getInstance().getReference("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot1) {
                for (DataSnapshot snapshot1 : dataSnapshot1.getChildren()) {
                    user U = snapshot1.getValue(user.class);
                    if (FirebaseAuth.getInstance().getCurrentUser().getUid().matches(U.getId()) == true &&
                            U.getEmail().matches("admin@gmail.com") == false) {
                        user_name.setText(U.getName());
                        Picasso.get().load(U.getUser_profile()).into(user_profile);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        viewPager2 = findViewById(R.id.viewPagerImageSlider);

        List<SliderItems> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItems(R.drawable.a1));
        sliderItems.add(new SliderItems(R.drawable.a2));
        sliderItems.add(new SliderItems(R.drawable.a3));
        sliderItems.add(new SliderItems(R.drawable.a4));
        sliderItems.add(new SliderItems(R.drawable.a5));

        viewPager2.setAdapter(new SliderAdapters(sliderItems, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 2000); // slide duration 2 seconds
            }
        });

    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    public void chat(View view) {
        startActivity(new Intent(this, ChatHome.class));
    }

    public void Calendar(View view) {
        startActivity(new Intent(this, Calendar.class));
        
    }

}


