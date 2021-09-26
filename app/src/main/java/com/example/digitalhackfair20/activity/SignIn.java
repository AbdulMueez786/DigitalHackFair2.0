package com.example.digitalhackfair20.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.digitalhackfair20.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class SignIn extends AppCompatActivity {
    private com.google.android.material.textfield.TextInputLayout email, password;
    private com.google.android.material.button.MaterialButton login;

    //Firebase
    private FirebaseAuth auth;
    private DatabaseReference db_ref;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        auth = FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(" login - onclick()");
                if (checkDataEntered()) {

                    final String Email = email.getEditText().getText().toString();
                    final String Password = password.getEditText().getText().toString();
                    System.out.println(Email);
                    System.out.println(Password);
                    auth.signInWithEmailAndPassword(Email, Password)
                            .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        if (Email.trim().matches("admin@gmail.com")) {
                                            // for admin
                                            Intent intent = new Intent(SignIn.this, AdminHome.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Intent intent = new Intent(SignIn.this, UserHome.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    } else {
                                        Toast toast = Toast.makeText(SignIn.this, "Incorrect Input", Toast.LENGTH_LONG); // initiate the Toast with context, message and duration for the Toast
                                        toast.show(); // display the Toast
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast toast = Toast.makeText(SignIn.this, "Sorry some error occur please try again", Toast.LENGTH_LONG); // initiate the Toast with context, message and duration for the Toast
                                    toast.show(); // display the Toast
                                }
                            });

                }

            }
        });

    }

    //Varification
    private boolean isEmpty(com.google.android.material.textfield.TextInputLayout obj) {
        CharSequence str = obj.getEditText().getText().toString();
        return TextUtils.isEmpty(str);
    }

    private boolean isEmail(com.google.android.material.textfield.TextInputLayout text) {
        CharSequence email = text.getEditText().getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean checkDataEntered() {
        if (isEmpty(this.email)) {
            this.email.setError("email is required!");
            return false;
        } else if (isEmail(this.email) == false) {
            this.email.setError("invalid email");
            return false;
        } else {
            this.email.setErrorEnabled(false);
        }
        if (isEmpty(this.password)) {
            this.password.setError("password is required!");
            return false;
        } else if (this.password.getEditText().getText().length() > 6) {
            this.password.setError("password size minimum 6 character!");
            return false;
        } else {
            this.password.setErrorEnabled(false);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}