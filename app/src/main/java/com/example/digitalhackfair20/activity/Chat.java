package com.example.digitalhackfair20.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalhackfair20.R;
import com.example.digitalhackfair20.adapter.ChatRvAdapter;
import com.example.digitalhackfair20.model.message;
import com.example.digitalhackfair20.model.report;
import com.example.digitalhackfair20.model.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {

    private CircleImageView profile_image;
    private TextView username;
    private FirebaseUser fuser;
    private DatabaseReference reference;
    private ChatRvAdapter messageAdapter;
    private Intent intent;
    private RecyclerView rv;
    private List<message> ls;
    private String userid = "";
    private EditText text_send;
    private ImageView btn_send, btn_send_task, report;
    private int request_code = 200;
    private Uri selectedImage_uri = null;
    private String message_Type = "text";
    private ImageView attach_pic;
    private FirebaseUser current_user;
    private String path;
    private List<String> badwordList;
    Dialog badwordDialog;
    Button understandButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        report = findViewById(R.id.report);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog b = new BottomSheetDialog(
                        Chat.this, R.style.BottomSheetDialogTheme
                );
                View bv = LayoutInflater.from(getApplicationContext()).inflate(
                        R.layout.layout_bottom_sheet, (LinearLayout) findViewById(R.id.bottomSheetContainer)
                );
                String pic = intent.getStringExtra("profile");
                RoundedImageView i = bv.findViewById(R.id.user_pic);
                TextView user_name = bv.findViewById(R.id.user_name);
                TextView user_phone_no = bv.findViewById(R.id.user_phone_no);
                TextView user_gender = bv.findViewById(R.id.user_gender);
                EditText report_text = bv.findViewById(R.id.report_text);

                FirebaseDatabase.getInstance().getReference("users").child(userid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        user u = dataSnapshot.getValue(user.class);
                        user_name.setText(u.getName());
                        user_phone_no.setText(u.getEmail());
                        user_gender.setText(u.getGender());
                        if (u.getUser_profile().equals("default") != true) {
                            Picasso.get().load(u.getUser_profile())
                                    .into(i);
                        } else {
                            Picasso.get().load(u.getUser_profile())
                                    .into(i);
                        }
                        readmessage(fuser.getUid(), userid, u.getUser_profile());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                bv.findViewById(R.id.buttonShare).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println(" --------------------------------------- ");
                        String key = FirebaseDatabase.getInstance().getReference("report").push().getKey();
                        FirebaseDatabase.getInstance().getReference("report").child(key).setValue(
                                new report(key, pic, user_name.getText().toString(), "", userid, FirebaseAuth.getInstance().getCurrentUser().getUid().toString()
                                        , report_text.getText().toString()));

                    }
                });
                b.setContentView(bv);
                b.show();
            }
        });

        badwordDialog = new Dialog(this);
        current_user = FirebaseAuth.getInstance().getCurrentUser();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        this.attach_pic = findViewById(R.id.attach_pic);
        this.attach_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message_Type = "Image";
                openGallery();
            }
        });

        rv = findViewById(R.id.rv);

        rv.setHasFixedSize(true);

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        rv.setLayoutManager(lm);
        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        intent = getIntent();
        this.userid = intent.getStringExtra("userid");

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users").child(userid);
        text_send = findViewById(R.id.text_send);
        btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = text_send.getText().toString();
                if (!msg.equals("")) {
                    message_Type = "text";
                    sendmessage(fuser.getUid(), userid, msg);
                    checkMessageForBadWords();
                } else if (message_Type.matches("Image")) {
                    sendmessage(fuser.getUid(), userid, selectedImage_uri.toString());
                } else {

                }
                text_send.setText("");
            }
        });


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user u = dataSnapshot.getValue(user.class);
                username.setText(u.getName());
                if (u.getUser_profile().equals("default") != true) {
                    Picasso.get().load(u.getUser_profile())
                            .into(profile_image);
                } else {
                    Picasso.get().load(R.drawable.user)
                            .into(profile_image);
                }
                readmessage(fuser.getUid(), userid, u.getUser_profile());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_send_task = findViewById(R.id.btn_send_task);
        btn_send_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = text_send.getText().toString();
                message_Type = "task";
                sendmessage(fuser.getUid(), userid, msg);
                checkMessageForBadWords();
            }
        });
        badwordList = new ArrayList<String>();

        path = this.getApplicationInfo().dataDir + File.separatorChar + "badwords.txt";

        readFile(path);
    }


    void openGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, request_code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            this.selectedImage_uri = data.getData();

            Toast.makeText(Chat.this, "Image selected", Toast.LENGTH_LONG).show();
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Do you want to send this Image?");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //activity4_message.setText("Empty Message");
                    //SendMessage();
                    message_Type = "Image";
                    sendmessage(fuser.getUid(), userid, selectedImage_uri.toString());
                }
            });
            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    message_Type = "text";
                    selectedImage_uri = null;
                }
            });
            AlertDialog d = dialog.create();
            d.show();

        }
    }

    ////////.....CHECKING MESSAGE BAD WORDS CODE STARTS HERE.....////////
    public void readFile(String path) {
        try {
            Resources res = getResources();
            InputStream in_s = res.openRawResource(R.raw.badwords);

            BufferedReader reader = new BufferedReader(new InputStreamReader(in_s));
            String line = reader.readLine();
            while (line != null) {

                line = reader.readLine();
                badwordList.add(line);
            }

        } catch (Exception e) {
            System.out.println("Cant read this");
        }
    }

    public void checkMessageForBadWords() {
        boolean badwordFound = false;
        String message = text_send.getText().toString();
        System.out.println(message);
        String[] spl = message.split(" ");
        for (int x = 0; x < spl.length; x++) {
            for (int y = 0; y < badwordList.size(); y++) {
                if (spl[x].equals(badwordList.get(y))) {
                    badwordFound = true;
                    break;
                }
            }
            if (badwordFound)
                break;
        }
        if (badwordFound)
            this.ShowNegativePopup();

    }

    private void ShowNegativePopup() {
        badwordDialog.setContentView(R.layout.negative_popup);
        understandButton = (Button) badwordDialog.findViewById(R.id.understand_btn);
        understandButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                badwordDialog.dismiss();
            }
        });

        badwordDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        badwordDialog.show();

    }

    private void sendmessage(String sender, String reciever, final String message) {

        if (this.message_Type.matches("text") == true) {

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            HashMap<String, Object> hm = new HashMap<>();

            android.text.format.DateFormat df = new android.text.format.DateFormat();
            CharSequence s = df.format("hh:mm a", new Date());

            /*hm.put("sender", FirebaseAuth.getInstance().getUid());
            hm.put("receiver", userid);
            hm.put("message", message);
            hm.put("Message_Type", message_Type);
            hm.put("Time", s);
            hm.put("Status", "not_seen");
            String key = ref.child("Chats").push().getKey();
            hm.put("Message_id", key);

            ref.child("Chats").child(key).setValue(hm);
            */

            String key = ref.child("Chats").push().getKey();
            ref.child("Chats").child(key).setValue(new message(key,
                    message_Type, message, s.toString(), "unread",
                    FirebaseAuth.getInstance().getUid(), userid));
            ref.child("BackupChats").child(key).setValue(new message(key,
                    message_Type, message, s.toString(), "unread",
                    FirebaseAuth.getInstance().getUid(), userid));

            ref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String appId = dataSnapshot.child(userid).child("app_id").getValue(String.class);
                    final String sender_name = dataSnapshot.child(current_user.getUid()).child("name").getValue(String.class);
                    final String sender_profile = dataSnapshot.child(current_user.getUid()).child("user_profile").getValue(String.class);
                    final String msg;
                    if (message_Type.matches("text") != true) {
                        msg = "photo";
                    } else {
                        msg = text_send.getText().toString();
                    }
                    /*
                    try {

                        OneSignal.postNotification(new JSONObject("{'contents':{'en':'" +

                                        sender_name + " messaged you  : " + message +

                                        "'},'headings':{'en':'" +
                                        "Cheer For Peer" +
                                        "'},'include_player_ids': ['" +
                                        appId +
                                        "']}"),
                                new OneSignal.PostNotificationResponseHandler() {
                                    @Override
                                    public void onSuccess(JSONObject response) {


                                    }

                                    @Override
                                    public void onFailure(JSONObject response) {

                                    }
                                });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        } else if (this.message_Type.matches("task") == true) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            HashMap<String, Object> hm = new HashMap<>();

            android.text.format.DateFormat df = new android.text.format.DateFormat();
            CharSequence s = df.format("hh:mm a", new Date());

            /*hm.put("sender", FirebaseAuth.getInstance().getUid());
            hm.put("receiver", userid);
            hm.put("message", message);
            hm.put("Message_Type", message_Type);
            hm.put("Time", s);
            hm.put("Status", "not_seen");
            String key = ref.child("Chats").push().getKey();
            hm.put("Message_id", key);

            ref.child("Chats").child(key).setValue(hm);
            */
            String key = ref.child("Chats").push().getKey();
            ref.child("Chats").child(key).setValue(new message(key,
                    message_Type, message, s.toString(), "unread",
                    FirebaseAuth.getInstance().getUid(), userid));

            ref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String appId = dataSnapshot.child(userid).child("app_id").getValue(String.class);
                    final String sender_name = dataSnapshot.child(current_user.getUid()).child("name").getValue(String.class);
                    final String sender_profile = dataSnapshot.child(current_user.getUid()).child("user_profile").getValue(String.class);
                    final String msg;
                    if (message_Type.matches("text") != true) {
                        msg = "photo";
                    } else {
                        msg = text_send.getText().toString();
                    }
                    /*
                    try {

                        OneSignal.postNotification(new JSONObject("{'contents':{'en':'" +

                                        sender_name + " messaged you  : " + message +

                                        "'},'headings':{'en':'" +
                                        "Cheer For Peer" +
                                        "'},'include_player_ids': ['" +
                                        appId +
                                        "']}"),
                                new OneSignal.PostNotificationResponseHandler() {
                                    @Override
                                    public void onSuccess(JSONObject response) {


                                    }

                                    @Override
                                    public void onFailure(JSONObject response) {

                                    }
                                });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            String key = ref.push().getKey().toString();
            storageReference = storageReference.child("chat/").child(key);
            storageReference.putFile(this.selectedImage_uri)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Toast.makeText(Chat.this, "complited", Toast.LENGTH_LONG).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            android.text.format.DateFormat df = new android.text.format.DateFormat();
                            CharSequence s = df.format("hh:mm a", new Date());
                            final String dp = uri.toString();
                            String key = ref.child("Chats").push().getKey();
                            ref.child("Chats").child(key).setValue(new message(key,
                                    message_Type, dp, s.toString(), "unread",
                                    FirebaseAuth.getInstance().getUid(), userid));
                            ref.child("BackupChats").child(key).setValue(new message(key,
                                    message_Type, message, s.toString(), "unread",
                                    FirebaseAuth.getInstance().getUid(), userid));
                            /*
                            HashMap<String, Object> hm = new HashMap<>();
                            hm.put("sender", FirebaseAuth.getInstance().getUid());
                            hm.put("receiver", userid);
                            hm.put("message", dp);
                            hm.put("Message_Type", message_Type);
                            hm.put("Time", s);
                            hm.put("Status", "not_seen");
                            String key = ref.child("Chats").push().getKey();
                            hm.put("Message_id", key);
                            ref.child("Chats").child(key).setValue(hm);
                            */


                            ref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String appId = dataSnapshot.child(userid).child("app_id").getValue(String.class);
                                    final String sender_name = dataSnapshot.child(current_user.getUid()).child("name").getValue(String.class);
                                    final String sender_profile = dataSnapshot.child(current_user.getUid()).child("user_profile").getValue(String.class);
                                    final String msg;
                                    if (message_Type.matches("text") != true) {
                                        msg = "photo";
                                    } else {
                                        msg = text_send.getText().toString();
                                    }/*
                                    try {

                                        OneSignal.postNotification(new JSONObject("{'contents':{'en':'" +

                                                        sender_name + " have sent you " + "photo"
                                                        +
                                                        "'},'headings':{'en':'" +
                                                        "Cheer For Peer" +
                                                        "'},'include_player_ids': ['" +
                                                        appId +
                                                        "']}"),
                                                new OneSignal.PostNotificationResponseHandler() {
                                                    @Override
                                                    public void onSuccess(JSONObject response) {


                                                    }

                                                    @Override
                                                    public void onFailure(JSONObject response) {

                                                    }
                                                });
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }*/

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Chat.this, "No internet,Try again", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private void readmessage(final String myid, final String user_id, final String image_Url) {
        ls = new ArrayList<>();
        messageAdapter = new ChatRvAdapter(Chat.this, ls, image_Url);
        rv.setAdapter(messageAdapter);
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ls.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    message c = snapshot.getValue(message.class);

                    if ((c.getReceiver_id().equals(myid) == true && c.getSender_id().equals(user_id) == true) ||
                            (c.getReceiver_id().equals(user_id) && c.getSender_id().equals(myid))
                    ) {
                        ls.add(c);
                    }

                    messageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //status("online");
        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("UserStatus").child(current_user.getUid());
        //ref.onDisconnect().setValue("offline");
        //ref.setValue("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //status("offline");
        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("UserStatus").child(current_user.getUid());
        //ref.onDisconnect().setValue("offline");
        //ref.setValue("offline");
    }
}