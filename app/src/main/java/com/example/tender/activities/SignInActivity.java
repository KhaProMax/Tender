package com.example.tender.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.developer.gbuttons.GoogleSignInButton;
import com.example.tender.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {
    private EditText etUser;
    private EditText etPass;
    GoogleSignInButton btnGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);


        Button signUpBtn = findViewById(R.id.btn_signup);
        Button signInBtn = findViewById(R.id.btn_sign_in);
        etUser = findViewById(R.id.et_username);
        etPass = findViewById(R.id.et_password);
        btnGoogle = findViewById(R.id.btn_google);

        // Set click listener for the sign up button
        signUpBtn.setOnClickListener(v -> {
            // Create an Intent to start the SignUpActivity
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        signInBtn.setOnClickListener(v -> {
            if (!validateUsername() | !validatePassword()) {
            } else {
                getUser();
            }
        });

    }

    // Normal login
    public Boolean validateUsername() {
        String val = etUser.getText().toString();
        if (val.isEmpty()) {
            etUser.setError("Username cannot be empty");
            return false;
        } else {
            etUser.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        String val = etPass.getText().toString();
        if (val.isEmpty()) {
            etPass.setError("Password cannot be empty");
            return false;
        } else {
            etPass.setError(null);
            return true;
        }
    }

    public void getUser() {
        String user = etUser.getText().toString();
        String pass = etPass.getText().toString();

        DatabaseReference DatabaseRef = FirebaseDatabase.getInstance().getReference("User");
        Query CheckUser = DatabaseRef.orderByChild("username").equalTo(user);

        CheckUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    etUser.setError(null);
                    // Check if password is correct
                    String passwordFromDB = snapshot.child(user).child("password").getValue(String.class);
                    if (passwordFromDB.equals(pass)) {
                        etUser.setError(null);
                        // Get data from database
                        String nameFromDB = snapshot.child(user).child("name").getValue(String.class);
                        String emailFromDB = snapshot.child(user).child("email").getValue(String.class);
                        String usernameFromDB = snapshot.child(user).child("username").getValue(String.class);

                        //Set intent
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("password", passwordFromDB);
                        startActivity(intent);
                    } else {
                        etPass.setError("Wrong password");
                        etPass.requestFocus();
                    }
                } else {
                    etUser.setError("User does not exist");
                    etUser.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors that occur during the database operations
            }
        });
    }
}

