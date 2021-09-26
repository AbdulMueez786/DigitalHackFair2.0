package com.example.digitalhackfair20.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.digitalhackfair20.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminProfileEdit extends AppCompatActivity {
    private TextInputEditText fname, lname, dob, phno, bio;
    private CircleImageView profileImage;
    private FloatingActionButton camera;
    private Button save;
    private int request_code = 200;
    private Uri selectedImage_uri = null;
    private FirebaseUser current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile_edit);
        fname = findViewById(R.id.edit_profile_fname);
        lname = findViewById(R.id.edit_profile_lname);
        dob = findViewById(R.id.edit_profile_dob);
        phno = findViewById(R.id.edit_profile_phone);
        bio = findViewById(R.id.edit_profile_bio);
        profileImage = findViewById(R.id.image_edit_profile);
        camera = findViewById(R.id.edit_profile_camera);
        save = findViewById(R.id.edit_profile_save_button);


        this.current_user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase.getInstance().getReference("profile").child(this.current_user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                fname.setText(dataSnapshot.child("fname").getValue(String.class));
                lname.setText(dataSnapshot.child("lname").getValue(String.class));
                dob.setText(dataSnapshot.child("dob").getValue(String.class));
                phno.setText(dataSnapshot.child("phone_no").getValue(String.class));
                bio.setText(dataSnapshot.child("bio").getValue(String.class));
                Picasso.get().load(dataSnapshot.child("profile").getValue(String.class)).into(profileImage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoreProfile();
            }
        });

    }

    void openGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, request_code);
    }

    void StoreProfile() {
        if (selectedImage_uri != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            storageReference = storageReference.child("profile/" + current_user.getUid());
            storageReference.putFile(this.selectedImage_uri)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            //Toast.makeText(create_profile.this, "complited", Toast.LENGTH_LONG).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String dp = uri.toString();
                            final HashMap hasmap = new HashMap();
                            hasmap.put("bio", bio.getText().toString());
                            hasmap.put("dob", dob.getText().toString());
                            hasmap.put("lname", lname.getText().toString());
                            hasmap.put("fname", fname.getText().toString());
                            hasmap.put("phone_no", phno.getText().toString());
                            hasmap.put("profile", dp);
                            FirebaseDatabase.getInstance().getReference("profile")
                                    .child(current_user.getUid()).updateChildren(hasmap);
                            final HashMap hp = new HashMap();
                            hp.put("name", fname.getText().toString());
                            hp.put("user_profile", dp);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(current_user.getUid()).updateChildren(hp);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminProfileEdit.this, "Failuer", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdminProfileEdit.this, "No internet,Try again", Toast.LENGTH_LONG).show();
                }
            });

        } else {
            Toast.makeText(AdminProfileEdit.this, "Please select your profile pic", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            this.selectedImage_uri = data.getData();
            this.profileImage.setImageURI(this.selectedImage_uri);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}