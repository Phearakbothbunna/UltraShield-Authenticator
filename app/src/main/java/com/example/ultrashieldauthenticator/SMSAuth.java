package com.example.ultrashieldauthenticator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class SMSAuth extends AppCompatActivity {
    EditText input_phoneNum, input_otp;
    Button btn_generateOTP, btn_verifyOTP;
    FirebaseAuth smsAuth;
    String verificationID;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsauth);

        input_phoneNum = findViewById(R.id.input_phoneNum);
        input_otp = findViewById(R.id.input_otp);
        btn_generateOTP = findViewById(R.id.btn_generateOTP);
        btn_verifyOTP = findViewById(R.id.btn_verityOTP);
        smsAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);


        // Generate OTP button on SMS authentication page
        btn_generateOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Give an error message if the phone number input field is empty
                if (TextUtils.isEmpty(input_phoneNum.getText().toString())){
                    Toast.makeText(SMSAuth.this, "Please Enter a Valid Phone Number",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    String phoneNum = input_phoneNum.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    sendCode(phoneNum);
                }
            }
        });

        // Verify OTP button on SMS authentication page
        
        btn_verifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(input_phoneNum.getText().toString())){
                    Toast.makeText(SMSAuth.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }
                else {
                    verifyCode(input_otp.getText().toString());
                }

            }
        });

    }

    private void sendCode(String phoneNum) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(smsAuth)
                        .setPhoneNumber("+1" + phoneNum)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            final String code = credential.getSmsCode();
            if (code != null){
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(SMSAuth.this, "Successfully sent code!", Toast.LENGTH_SHORT).show();
            btn_verifyOTP.setEnabled(true);
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(s, token);
            verificationID = s;
            Toast.makeText(SMSAuth.this, "Code Sent!", Toast.LENGTH_SHORT).show();
            btn_verifyOTP.setEnabled(true);
            progressBar.setVisibility(View.INVISIBLE);
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);
        loginCredentials(credential);
    }

    private void loginCredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SMSAuth.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SMSAuth.this, Home.class));
                }
            }
        });
    }
}