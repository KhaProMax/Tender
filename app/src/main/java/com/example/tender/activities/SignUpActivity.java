package com.example.tender.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tender.R;
import com.example.tender.models.User;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    private Button btn_sign_up;
    private EditText ETuser;
    private EditText ETpass;
    private EditText ETconfirmPass;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_sign_up = findViewById(R.id.btn_create_acc);
        ETuser = findViewById(R.id.Create_et_username);
        ETpass = findViewById(R.id.Create_et_password);
        ETconfirmPass = findViewById(R.id.Confirm_ET_password);

        db = FirebaseFirestore.getInstance();

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ETuser.getText().toString();
                String password = ETpass.getText().toString();
                String confirmPass = ETconfirmPass.getText().toString();

                // if any of the field is empty
                if (username.isEmpty() || password.isEmpty() || confirmPass.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Fill all fields", Toast.LENGTH_LONG).show();
                    return;
                }
                // if create is successful
                else if (password.equals(confirmPass)) {
                    User user = new User(username, password);
                    DocumentReference userRef = db.collection("users").document(username);
                    userRef.set(user)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(SignUpActivity.this, "You have signed up successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                startActivity(intent);
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(SignUpActivity.this, "Failed to sign up: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}