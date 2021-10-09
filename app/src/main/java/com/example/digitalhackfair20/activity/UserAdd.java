package com.example.digitalhackfair20.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.digitalhackfair20.R;
import com.example.digitalhackfair20.model.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UserAdd extends AppCompatActivity {
    private de.hdodenhof.circleimageview.CircleImageView user_profile;
    private com.google.android.material.floatingactionbutton.FloatingActionButton camera;
    private com.google.android.material.textfield.TextInputLayout fname, lname, email, password,
            confirm_password, phone_no, post, bio;
    private com.google.android.material.textview.MaterialTextView gender_male, gender_female, gender_neither;
    private com.google.android.material.button.MaterialButton save_button;

    private String selected_gender = "none";
    private int request_code = 200;
    private Uri selectedImage_uri = null;

    //Firebase
    private FirebaseUser USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);

        user_profile = findViewById(R.id.user_profile);
        camera = findViewById(R.id.camera);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        phone_no = findViewById(R.id.phone_no);
        post = findViewById(R.id.post);
        bio = findViewById(R.id.bio);

        gender_male = findViewById(R.id.gender_male);
        gender_female = findViewById(R.id.gender_female);
        gender_neither = findViewById(R.id.gender_neither);

        gender_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_gender = "male";
            }
        });
        gender_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_gender = "female";
            }
        });
        gender_neither.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_gender = "none";
            }
        });

        save_button = findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if all fields are filed
                if (checkDataEntered() == true) {
                    StoreProfile();
                }
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

            FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email.getEditText().getText().toString()
                            , password.getEditText().getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                            storageReference = storageReference.child("profile/" + FirebaseAuth.getInstance()
                                    .getCurrentUser().getUid());
                            storageReference.putFile(selectedImage_uri)
                                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            Toast.makeText(UserAdd.this, "Profile pic uploded", Toast.LENGTH_LONG).show();
                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            final String dp = uri.toString();

                                            Calendar calendar = Calendar.getInstance();
                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                                            String date = simpleDateFormat.format(calendar.getTime());


                                            FirebaseDatabase.getInstance().getReference("users")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .setValue(new user(FirebaseAuth.getInstance().getCurrentUser().getUid().toString(),
                                                            "appid", fname.getEditText().getText().toString() + " " + lname.getEditText().getText().toString(),
                                                            email.getEditText().getText().toString(), post.getEditText().getText().toString(), bio.getEditText().getText().toString(),
                                                            date, selected_gender, "offline", dp, "0", "0"
                                                    )).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                    FirebaseAuth.getInstance().signInWithEmailAndPassword("admin@gmail.com", "123456");
                                                    Toast.makeText(UserAdd.this, "data uploaded", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                            Toast.makeText(UserAdd.this, "Successfull", Toast.LENGTH_LONG).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(UserAdd.this, "Failuer", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UserAdd.this, "No internet,Try again", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {

                }
            });
            Toast.makeText(UserAdd.this, "Account on this email already exist", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(UserAdd.this, "Please select your profile pic", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            this.selectedImage_uri = data.getData();
            this.user_profile.setImageURI(this.selectedImage_uri);
        }
    }

    // verification
    boolean isEmpty(com.google.android.material.textfield.TextInputLayout obj) {
        CharSequence str = obj.getEditText().getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean checkDataEntered() {

        if (isEmpty(this.fname)) {
            this.fname.setError("First Name is required!");
            return false;
        } else {
            this.fname.setErrorEnabled(false);
        }
        if (isEmpty(this.lname)) {
            this.lname.setError("Last Name is required!");
            return false;
        } else {
            this.lname.setErrorEnabled(false);
        }
        if (isEmpty(this.email)) {
            this.email.setError("email required!");
            return false;
        } else {
            this.email.setErrorEnabled(false);
        }
        if (this.password.getEditText().getText().toString().length() < 6) {
            this.password.setError("password is less then 6 character");
            return false;
        } else {
            this.password.setErrorEnabled(false);

        }
        if (isEmpty(this.phone_no)) {
            this.phone_no.setError("PhoneNumber is required!");
            return false;
        } else {
            this.phone_no.setErrorEnabled(false);
        }
        if (isEmpty(this.post)) {
            this.post.setError("PhoneNumber is required!");
            return false;
        } else {
            this.post.setErrorEnabled(false);
        }
        if (isEmpty(this.bio)) {
            this.bio.setError("Bio is required!");
            return false;
        } else {
            this.bio.setErrorEnabled(false);

        }
        if (this.confirm_password.getEditText().getText().toString().matches(this.password.getEditText().getText().toString()) == false) {
            this.confirm_password.setError("password not matched");
            return false;
        } else {
            this.confirm_password.setErrorEnabled(false);
        }
        if (this.selected_gender.matches("") == true) {
            Toast.makeText(UserAdd.this, "Please select gender", Toast.LENGTH_LONG).show();
            return false;
        }
        if (selectedImage_uri == null) {
            Toast.makeText(UserAdd.this, "Please select your profile pic", Toast.LENGTH_LONG).show();
            return false;
        }


        return true;
    }

}