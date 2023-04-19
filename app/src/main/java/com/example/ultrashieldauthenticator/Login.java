package com.example.ultrashieldauthenticator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Login extends AppCompatActivity {
    Button btn_setUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Function of Setup button
        btn_setUp = (Button) findViewById(R.id.btn_setup);
        btn_setUp.setOnClickListener(view -> {
            Intent intent = new Intent(this, AuthenticatorMethod.class);
            startActivity(intent);
        });
    }


}