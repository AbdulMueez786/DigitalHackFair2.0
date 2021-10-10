package com.example.digitalhackfair20.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalhackfair20.R;
import com.example.digitalhackfair20.activity.GroupChat;
import com.example.digitalhackfair20.model.Group;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupChatUsersRvAdapter extends RecyclerView.Adapter<GroupChatUsersRvAdapter.ViewHolder> {

    private Context c;
    private List<Group> ls;


    public GroupChatUsersRvAdapter(Context c, List<Group> ls) {
        this.c = c;
        this.ls = ls;

    }

    @NonNull
    @Override
    public GroupChatUsersRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.user_chat, parent, false);
        return new GroupChatUsersRvAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GroupChatUsersRvAdapter.ViewHolder holder, int position) {


        holder.username.setText(ls.get(position).getName());
        holder.lastmessage.setText(ls.get(position).getDescription());
        final Group u = ls.get(position);
        /*
        FirebaseDatabase.getInstance().getReference("UserStatus")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String status = dataSnapshot.child(u.getId()).getValue(String.class);

                        System.out.println(status);
                        if (status != null) {
                            if (status.matches("online")) {
                                holder.offline_status.setVisibility(View.GONE);
                                holder.online_status.setVisibility(View.VISIBLE);
                            } else {
                                holder.offline_status.setVisibility(View.VISIBLE);
                                holder.online_status.setVisibility(View.GONE);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/


        if (u.getGroup_pic().equals("default") != true) {
            Picasso.get().load(u.getGroup_pic())
                    .into(holder.profile_image);
        } else {
            Picasso.get().load(R.drawable.users)
                    .into(holder.profile_image);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, GroupChat.class);
                intent.putExtra("groupid", u.getId());
                intent.putExtra("name", u.getName());
                intent.putExtra("group_image", u.getGroup_pic());
                c.startActivity(intent);
            }
        });

 /*
        final String recv_id = ls.get(position).getId();
        FirebaseDatabase.getInstance().getReference("Chats").addListenerForSingleValueEvent(new ValueEventListener() {

            List<Chat> k = new ArrayList<Chat>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean flag = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if ((recv_id.matches(snapshot.child("receiver").getValue(String.class)) &&
                            FirebaseAuth.getInstance().getCurrentUser().getUid().matches(snapshot.child("sender").getValue(String.class))) ||
                            (FirebaseAuth.getInstance().getCurrentUser().getUid().matches(snapshot.child("receiver").getValue(String.class)) &&
                                    recv_id.matches(snapshot.child("sender").getValue(String.class)))
                    ) {
                        k.add(snapshot.getValue(Chat.class));
                        flag = true;
                    }
                }
                if (flag == true) {
                    if (k.get(k.size() - 1).getMessage_Type().matches("text")) {
                        holder.lastmessage.setText(k.get(k.size() - 1).getMessage());
                        holder.lastmessage.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        holder.chat_date.setText(k.get(k.size() - 1).getTime());
                    } else {
                        holder.lastmessage.setText("photo");
                        holder.chat_date.setText(k.get(k.size() - 1).getTime());
                    }
                } else {
                    //holder.l.setVisibility(View.GONE);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username, lastmessage, chat_date;
        public CircleImageView profile_image, online_status, offline_status;
        public LinearLayout l;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
            online_status = itemView.findViewById(R.id.status_online);
            offline_status = itemView.findViewById(R.id.status_offline);
            lastmessage = itemView.findViewById(R.id.lastmessage);
            chat_date = itemView.findViewById(R.id.chat_date);
            l = itemView.findViewById(R.id.l);
        }
    }
}
