package com.example.flover1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText signupUsername, signupName, signupEmail, signupPassword;
    private Button signupButton;
    private TextView loginRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        signupUsername = findViewById(R.id.signup_username);
        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = signupUsername.getText().toString().trim();
                String name = signupName.getText().toString().trim();
                String email = signupEmail.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();
                if (username.isEmpty()) {
                    signupUsername.setError("Username cannot be empty");
                }
                if (name.isEmpty()) {
                    signupName.setError("Name cannot be empty");
                }
                if (email.isEmpty()) {
                    signupEmail.setError("Email cannot be empty");
                }
                if (password.isEmpty()) {
                    signupPassword.setError("Password cannot be empty");
                } else {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                String userId = firebaseUser.getUid();
                                User user = new User(username, name, email);
                                FirebaseDatabase.getInstance().getReference("Users").child(userId).setValue(user)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignUpActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                        } else {
                                            Toast.makeText(SignUpActivity.this, "SignUp Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(SignUpActivity.this, "SignUp Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }
}