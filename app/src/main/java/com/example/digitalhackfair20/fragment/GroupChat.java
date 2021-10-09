package com.example.digitalhackfair20.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalhackfair20.R;
import com.example.digitalhackfair20.adapter.GroupChatUsersRvAdapter;
import com.example.digitalhackfair20.model.Group;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupChat extends Fragment {

    private RecyclerView rv;
    private GroupChatUsersRvAdapter userAdapter;
    private List<Group> ls;
    private CircleImageView messages_profile;
    private View Message_View;

    public static GroupChat getInstance() {
        GroupChat groupChatFragment = new GroupChat();
        return groupChatFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Message_View = inflater.inflate(R.layout.fragment_group_chat, container, false);
        rv = Message_View.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        ls = new ArrayList<>();
        this.messages_profile = Message_View.findViewById(R.id.messages_profile);
        this.messages_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openDrawer();
            }
        });
        readUsers();
        return Message_View;

    }

    private void readUsers() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("group");
        final Context c = getContext();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ls.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final Group u = snapshot.getValue(Group.class);
                    assert u != null;
                    assert firebaseUser != null;

                    if (u.getId().matches(firebaseUser.getUid()) == false && u.getName().matches("admin") == false) {

                        ls.add(u);
                        userAdapter = new GroupChatUsersRvAdapter(c, ls);
                        rv.setAdapter(userAdapter);
                        userAdapter.notifyDataSetChanged();
                    /*    FirebaseDatabase.getInstance().getReference("Chats").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    if (snapshot.child("receiver").getValue(String.class).matches(u.getId()) == true
                                            || snapshot.child("sender").getValue(String.class).matches(u.getId()) == true
                                    ) {

                                        ls.add(u);
                                        break;
                                    }
                                }
                                java.util.Collections.reverse(ls);
                                userAdapter = new ChatUsersRvAdapter(c, ls);
                                rv.setAdapter(userAdapter);
                                userAdapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
*/

                    }

                    if (u.getId().matches(firebaseUser.getUid()) == true) {

                        if (u.getGroup_pic().matches("default") != true) {
                            Picasso.get().load(u.getGroup_pic())
                                    .into(messages_profile);
                        } else {
                            Picasso.get().load(R.drawable.user)
                                    .into(messages_profile);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

}