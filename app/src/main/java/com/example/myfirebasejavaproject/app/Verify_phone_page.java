package com.example.myfirebasejavaproject.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myfirebasejavaproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Verify_phone_page extends AppCompatActivity {


    Button verify_btn;
    EditText phoneNoEnteredByUser;
    ProgressBar progressbar;
    String verificationCodeBySystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_page);
        verify_btn = findViewById(R.id.verify_btn);
        phoneNoEnteredByUser = findViewById(R.id.verification_code_entered_by_user);
        progressbar = findViewById(R.id.progress_bar);
        progressbar.setVisibility(View.GONE);

        String phonenum = getIntent().getStringExtra("phoneNo");
        sendVerificationCodeToUser(phonenum);


        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = phoneNoEnteredByUser.getText().toString();
                if(code.isEmpty() || code.length()<6){
                    phoneNoEnteredByUser.setError("Wrong OTP....");
                    phoneNoEnteredByUser.requestFocus();
                    return;
                }
                progressbar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        });
    }

    private void sendVerificationCodeToUser(String phonenum) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+92"+phonenum,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {

            super.onCodeSent(s, forceResendingToken);

            verificationCodeBySystem = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
       String code = phoneAuthCredential.getSmsCode();
       if(code != null){
           progressbar.setVisibility(View.VISIBLE);
           verifyCode(code);
       }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Verify_phone_page.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };


    private void verifyCode(String codeByUser){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem,codeByUser);
        signInTheUserByCredentials(credential);
    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(Verify_phone_page.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                    Intent intent = new Intent(getApplicationContext(),Userprofile.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                   }
                   else {
                       Toast.makeText(Verify_phone_page.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                   }
                    }
                });
    }
}
