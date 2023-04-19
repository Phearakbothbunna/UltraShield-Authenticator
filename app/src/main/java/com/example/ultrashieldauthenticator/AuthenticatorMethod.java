package com.example.ultrashieldauthenticator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class AuthenticatorMethod extends AppCompatActivity {

    Button btn_Finish, btn_SMS, btn_Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticator_method);

        // Function of Setup button
        btn_Finish = (Button) findViewById(R.id.btn_finishAuthenMethod);
        btn_Finish.setOnClickListener(view -> {
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        });

        btn_SMS = (Button) findViewById(R.id.btn_setupSMS);
        btn_SMS.setOnClickListener(view -> {
            Intent intent = new Intent(this, SMSAuth.class);
            startActivity(intent);
        });

        btn_Email = (Button) findViewById(R.id.btn_setupEmail);
        btn_Email.setOnClickListener(view -> {
            Intent intent = new Intent(this, EmailAuth.class);
            startActivity(intent);
        });
    }
}