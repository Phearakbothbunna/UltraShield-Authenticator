package com.example.ultrashieldauthenticator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailAuth extends AppCompatActivity {
    Button btn_sendEmail;
    EditText input_email, input_pw;
    ProgressBar progressBar2;
    FirebaseAuth emailAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_auth);

        emailAuth = FirebaseAuth.getInstance();
        btn_sendEmail = findViewById(R.id.btn_sendEmail);
        input_email = findViewById(R.id.input_email);
        input_pw = findViewById(R.id.input_pw);
        progressBar2 = findViewById(R.id.progressBar2);

        btn_sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar2.setVisibility(View.VISIBLE);
                emailAuth.createUserWithEmailAndPassword(input_email.getText().toString(),
                        input_pw.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar2.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            emailAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(EmailAuth.this, "Check email for verification!"
                                                , Toast.LENGTH_SHORT).show();
                                        input_email.setText("");
                                        input_pw.setText("");
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(EmailAuth.this, task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}