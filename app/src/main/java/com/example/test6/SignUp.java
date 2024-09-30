package com.example.test6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText SignUpEmail, SignUpPassword, SignUpUsername, UserName;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_sign_up);

        SignUpEmail = findViewById(R.id.signup_email);
        SignUpPassword = findViewById(R.id.sign_up_password);
        UserName = findViewById(R.id.sign_up_username);
        registerBtn = findViewById(R.id.regButton);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = SignUpEmail.getText().toString().trim();
                String password = SignUpPassword.getText().toString().trim();
                String username = UserName.getText().toString().trim();

                if(email.isEmpty()){
                    SignUpEmail.setError("Email cannot be empty");
                }
                else if(password.isEmpty()){
                    SignUpPassword.setError("Password cannot be empty");
                }
                else if(username.isEmpty()){
                    UserName.setError("Username cannot be empty");
                }
                else{
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
//                                Toast.makeText(SignUp.this, "user created", Toast.LENGTH_SHORT).show();
//                                -
                            }
                        }
                    });
                }

            }
        });
    }

    public void Login(View view) {
        startActivity(new Intent(this, Signin.class));
    }

    public void ContactUs(View view) {

    }

    public void GoToHome(View view) {
    }
}