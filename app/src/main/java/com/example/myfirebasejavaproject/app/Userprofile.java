package com.example.myfirebasejavaproject.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirebasejavaproject.models.UserHelperClass;
import com.example.myfirebasejavaproject.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Userprofile extends AppCompatActivity {


    TextInputLayout fullnaeme, email, password, phonenum;
    TextView fullNameLabel, usernameLabel;

    String _USERNAME, _EMAIL, _PASSWORD, _PHONENO, _NAME;
    DatabaseReference refrence;
    final String LOG_OUT = "event_logout";
    SharedPreferences prefs;
    Button btnUpdate;
    String _KEY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

//        // Register mMessageReceiver to receive messages.
//        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
//                new IntentFilter(LOG_OUT));
        prefs = getSharedPreferences(UserHelperClass.shared, MODE_PRIVATE);
        setData();


        fullnaeme = findViewById(R.id.name_profile);
        email = findViewById(R.id.email_profile);
        phonenum = findViewById(R.id.phoneno_profile);
        password = findViewById(R.id.password_profile);
        fullNameLabel = findViewById(R.id.full_name);
        usernameLabel = findViewById(R.id.user_name);
        _KEY = prefs.getString("homeCookerId","");
        refrence = UserHelperClass.path.child(_KEY);
        btnUpdate = findViewById(R.id.updateUserprofile);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });
//        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
//        if(googleSignInAccount != null) {
//            fullNameLabel.setText(googleSignInAccount.getDisplayName());
//            usernameLabel.setText(googleSignInAccount.getGivenName());
//            fullnaeme.getEditText().setText(googleSignInAccount.getDisplayName());
//            email.getEditText().setText(googleSignInAccount.getEmail());
//        }




        //int idName = prefs.getInt("idName", 0);
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();



//        if(UserHelperClass.whichUser == false){
//
//            if (googleSignInAccount != null) {
//                fullNameLabel.setText(googleSignInAccount.getDisplayName());
//                usernameLabel.setText(googleSignInAccount.getGivenName());
//                fullnaeme.getEditText().setText(googleSignInAccount.getDisplayName());
//                email.getEditText().setText(googleSignInAccount.getEmail());
//                phonenum.getEditText().setText("");
//                password.getEditText().setText("");
//            }
//            else {
//
//            }
//        }
//        else {
           // showUserData();

           // String name =
//                _USERNAME = prefs.getString("username",null);
//                _NAME = prefs.getString("name",null);
//                _EMAIL = prefs.getString("email",null);
//                _PHONENO = prefs.getString("phoneno",null);
//                _PASSWORD = prefs.getString("password",null);
//
//                fullNameLabel.setText(_NAME);
//                usernameLabel.setText(_USERNAME);
//                fullnaeme.getEditText().setText(_NAME);
//                email.getEditText().setText(_EMAIL);
//                phonenum.getEditText().setText(_PHONENO);
//                password.getEditText().setText(_PASSWORD);

        //}




    }

    private void checkData(){
        String name = fullnaeme.getEditText().getText().toString();
        String phone = phonenum.getEditText().getText().toString();
        String emaill = email.getEditText().getText().toString();
        String pass = password.getEditText().getText().toString();
        if(!name.equals(_NAME) || !emaill.equals(_EMAIL) || !phone.equals(_PHONENO)  || !pass.equals(_PASSWORD)){
            refrence.child("name").setValue(name);
            refrence.child("email").setValue(emaill);
            refrence.child("phoneNo").setValue(phone);
            refrence.child("password").setValue(pass);

            //

            fullNameLabel.setText(name);
            usernameLabel.setText(name);

            //
            updateSharedrefrence();
            Toast.makeText(Userprofile.this, "Data Updated Sucessfully", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(Userprofile.this, "Give me something to update", Toast.LENGTH_LONG).show();
        }
    }


    private void setData(){
        _KEY = prefs.getString("homeCookerId","");
        refrence = UserHelperClass.path.child(_KEY);
        refrence.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //for(DataSnapshot data : dataSnapshot.getChildren()){
                //UserHelperClass model = data.getClass(UserHelperClass.class);
                String namee = dataSnapshot.child("name").getValue().toString();
                String phoneno = dataSnapshot.child("phoneNo").getValue().toString();
                String emaill = dataSnapshot.child("email").getValue().toString();
                String passwordd = dataSnapshot.child("password").getValue().toString();

                _USERNAME = prefs.getString("username",null);
                _NAME = prefs.getString("name",null);
                _EMAIL = prefs.getString("email",null);
                _PHONENO = prefs.getString("phoneno",null);
                _PASSWORD = prefs.getString("password",null);

                fullNameLabel.setText(namee);
                usernameLabel.setText(namee);
                fullnaeme.getEditText().setText(namee);
                email.getEditText().setText(emaill);
                phonenum.getEditText().setText(phoneno);
                password.getEditText().setText(passwordd);



                //}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateSharedrefrence(){
         _KEY = prefs.getString("homeCookerId","");
        refrence = UserHelperClass.path.child(_KEY);
        refrence.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //for(DataSnapshot data : dataSnapshot.getChildren()){
                    //UserHelperClass model = data.getClass(UserHelperClass.class);
                    String name = dataSnapshot.child("name").getValue().toString();
                    String phoneno = dataSnapshot.child("phoneNo").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();
                    String password = dataSnapshot.child("password").getValue().toString();


                    SharedPreferences.Editor editor = getSharedPreferences(UserHelperClass.shared,MODE_PRIVATE).edit();
                    editor.putString("name",name);
                    editor.putString("email",email);
                    editor.putString("phoneno",phoneno);
                    editor.putString("password",password);
                    editor.apply();

//                SharedPreferences.Editor edit = prefs.edit();
//                edit.putString("name1",name);
//                edit.putString("phonenum",phoneno);
//                edit.putString("password1",password);
//                edit.putString("email1",email);
//                edit.apply();

                _USERNAME = prefs.getString("username",null);
                _NAME = prefs.getString("name",null);
                _EMAIL = prefs.getString("email",null);
                _PHONENO = prefs.getString("phoneno",null);
                _PASSWORD = prefs.getString("password",null);

                //}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void showUserData() {
//        Intent intent = getIntent();
//        if(intent != null) {
//            _USERNAME = intent.getStringExtra("username");
//            _NAME = intent.getStringExtra("name");
//            _EMAIL = intent.getStringExtra("email");
//            _PHONENO = intent.getStringExtra("phoneno");
//            _PASSWORD = intent.getStringExtra("password");
//
//            fullNameLabel.setText(_NAME);
//            usernameLabel.setText(_USERNAME);
//            fullnaeme.getEditText().setText(_NAME);
//            email.getEditText().setText(_EMAIL);
//            phonenum.getEditText().setText(_PHONENO);
//            password.getEditText().setText(_PASSWORD);
//        }
//        else {
//
//        }
        }


    public void updateData(View view) {
        if (isNameChanged() || isPasswordChanged()) {
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_LONG).show();
        }
        else  Toast.makeText(this, "Data is same and cannot be updated", Toast.LENGTH_LONG).show();
    }

    private boolean isPasswordChanged() {

        if (!_PASSWORD.equals(password.getEditText().getText().toString())) {
            refrence.child(_USERNAME).child("password").setValue(password.getEditText().getText().toString());
            _PASSWORD = password.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isNameChanged() {

        if (!_NAME.equals(fullnaeme.getEditText().getText().toString())) {
            refrence.child(_USERNAME).child("name").setValue(fullnaeme.getEditText().getText().toString());
            _NAME = fullnaeme.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }


    private boolean isPhoneNumChanged() {

        if (!_PHONENO.equals(phonenum.getEditText().getText().toString())) {
            refrence.child(_USERNAME).child("name").setValue(fullnaeme.getEditText().getText().toString());
            _NAME = fullnaeme.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }



    // handler for received Intents for logout event
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //do your code snippet here.
            finish();
        }
    };

    @Override
    protected void onDestroy() {
        // Unregister since the activity is not visible
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
}
