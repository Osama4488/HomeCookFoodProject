package com.example.myfirebasejavaproject.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirebasejavaproject.HomeCooker.Home_cooker_DashBoard;
import com.example.myfirebasejavaproject.models.UserHelperClass;
import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.tabs.SignUpTabs;
import com.example.myfirebasejavaproject.user.UserDashboard;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {


    String passwordFromDB;
    String homeCookerId;
    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout username, password;
    private Button signup_btn, loginbtn, googleSignIn;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    SharedPreferences onBoardgingScreen;
boolean check = false;

    @Override
    protected void onStart() {
        super.onStart();
//        FirebaseUser user = mAuth.getCurrentUser();
//        if (user != null) {
//            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
//            startActivity(intent);
//        }
        SharedPreferences editor = getSharedPreferences(UserHelperClass.shared, MODE_PRIVATE);
        String exist = editor.getString("username", "");
        String type = editor.getString("type", "");
        if(!exist.equals("")  && type.equals("User")  ){
            Intent intent = new Intent(getApplicationContext(), UserDashboard.class);// Onboarding Activity pe
            startActivity(intent);
            finish();
        }
        else if(!exist.equals("")  && type.equals("Homecooker")){
            Intent intent = new Intent(getApplicationContext(), Home_cooker_DashBoard.class);// Onboarding Activity pe
            startActivity(intent);
            finish();
        }
        else {

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        googleSignIn = findViewById(R.id.googleIsgnIn);
        signup_btn = findViewById(R.id.newusersignup);
        image = findViewById(R.id.logoImage);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginbtn = findViewById(R.id.btn_login);
        mAuth = FirebaseAuth.getInstance();

        createRequest();
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isUser();
               // loginUser();

            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpTabs.class);

                android.util.Pair[] pairs = new android.util.Pair[7];

                pairs[0] = new android.util.Pair<View, String>(image, "logo_image");


                pairs[1] = new android.util.Pair<View, String>(logoText, "logo_text");
                pairs[2] = new android.util.Pair<View, String>(sloganText, "sub_heading");
                pairs[3] = new android.util.Pair<View, String>(username, "username_tran");
                pairs[4] = new android.util.Pair<View, String>(password, "pass_tran");
                pairs[5] = new android.util.Pair<View, String>(loginbtn, "go_tran");
                pairs[6] = new android.util.Pair<View, String>(signup_btn, "signup_tran");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    //ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
                    ActivityOptions options;
                    options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                }


//                startActivity(intent);
            }
        });
    }

    private void createRequest() {

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                            startActivity(intent);

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Sorry Authentication Failed", Toast.LENGTH_SHORT).show();
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Snackbar.make(findViewById(R.id.activity_login), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }


    private Boolean validateUsername() {
        String val = username.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;

        } else if (val.length() >= 15) {
            username.setError("Username too Long");
            return false;
        }
//        else if(!val.matches(noWhiteSpace)){
//            username.setError("White Spaces are not allowed");
//            return false;
//        }
        else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser() {
        //Validate Login Info
        if (!validateUsername() | !validatePassword()) {
            return;
        } else {
            isUser();
        }
    }

    private void isUser() {
        final String userEnteredUsername = username.getEditText().getText().toString();
        final String userEnteredPassword = password.getEditText().getText().toString();

//        final String userEnteredUsername = "b";
//        final String userEnteredPassword = "Asd@";
        //final String userEnteredEmail =

//        DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference("users");
//        DatabaseReference zone1Ref = zonesRef.child("ZONE_1");
//        DatabaseReference zone1NameRef = zone1Ref.child("ZNAME");


        final DatabaseReference refrence = FirebaseDatabase.getInstance().getReference("HomeCooker");
        refrence.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                                            String us = data.child("username").getValue().toString();
                                                            boolean a = data.child("username").getValue().toString().equals(userEnteredUsername);

//
                                                            if (data.child("username").getValue().toString().equals(userEnteredUsername)) {

                                                                check = true;
                                                                 passwordFromDB = data.child("password").getValue(String.class);
                                                                 homeCookerId = data.getKey();
                                                                 break;

                                                            } else {
                                                                check = false;
                                                            }

                                                        }
                                                        if (dataSnapshot.exists() && check == true) {

                                                            username.setError(null);

                                                            username.setErrorEnabled(false);
                                                            //String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);
                                                            if (passwordFromDB.equals(userEnteredPassword)) {


                                                                username.setError(null);
                                                                username.setErrorEnabled(false);
                                                                String nameFromDb = dataSnapshot.child(homeCookerId).child("name").getValue(String.class);
                                                                String usernameFromDb = dataSnapshot.child(homeCookerId).child("username").getValue(String.class);
                                                                String phonenoFromDb = dataSnapshot.child(homeCookerId).child("phoneNo").getValue(String.class);
                                                                String emailFromDb = dataSnapshot.child(homeCookerId).child("email").getValue(String.class);
                                                                String mImageUri = dataSnapshot.child(homeCookerId).child("mImageUrl").getValue(String.class);
                                                                String typeFromDb = dataSnapshot.child(homeCookerId).child("type").getValue(String.class);
                                                                SharedPreferences.Editor editor = getSharedPreferences(UserHelperClass.shared, MODE_PRIVATE).edit();
                                                                editor.putString("name", nameFromDb);
                                                                editor.putString("username", usernameFromDb);
                                                                editor.putString("phoneno", phonenoFromDb);
                                                                editor.putString("password", passwordFromDB);
                                                                editor.putString("email", emailFromDb);
                                                                editor.putString("imageurl",mImageUri);
                                                                editor.putString("homeCookerId",homeCookerId);
                                                                editor.putString("type",typeFromDb);
                                                                editor.commit();
                                                                UserHelperClass.whichUser = true;
//
                                                                if(typeFromDb.equals("User")){
                                                                    Intent intent = new Intent(getApplicationContext(), UserDashboard.class);// Onboarding Activity pe
                                                                    startActivity(intent);
                                                                    finish();
                                                                }
                                                                else {
                                                                    Intent intent = new Intent(getApplicationContext(), Home_cooker_DashBoard.class);// Onboarding Activity pe
                                                                    startActivity(intent);
                                                                    finish();
                                                                }


                                                                //}

                                                            } else {
                                                                password.setError("Wrong Password");
                                                                password.requestFocus();
                                                            }
                                                        } else {
                                                            username.setError("No such User Exist");
                                                            username.requestFocus();
                                                        }
                                                    }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        }
    }




       // String mkey = refrence.push().getKey();

//        final Query checkuser = refrence.orderByChild("username").equalTo(userEnteredUsername);
//
//        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
////                List<String> propertyKeys = new ArrayList<>();
////                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()){
////
////                        propertyKeys.add(userSnapshot.getKey());
////                        String advertise = userSnapshot.getValue(String.class);
////                        //property.add(advertise);
////
////                }
//                //dataStatus.DataIsLoaded(property, propertyKeys);
//
//                if(dataSnapshot.exists()){
//
//                    username.setError(null);
//
//                    username.setErrorEnabled(false);
//                    String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);
//                    if(passwordFromDB.equals(userEnteredPassword)){
//
//
//                        username.setError(null);
//                        username.setErrorEnabled(false);
//                    String nameFromDb =dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
//                    String usernameFromDb =dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
//                    String phonenoFromDb =dataSnapshot.child(userEnteredUsername).child("phoneNo").getValue(String.class);
//                    String emailFromDb =dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);
//                        SharedPreferences.Editor editor = getSharedPreferences(UserHelperClass.shared,MODE_PRIVATE).edit();
//                        editor.putString("name", nameFromDb);
//                        editor.putString("username",usernameFromDb);
//                        editor.putString("phoneno",phonenoFromDb);
//                        editor.putString("password",passwordFromDB);
//                        editor.putString("email",emailFromDb);
//                        editor.commit();
//                        UserHelperClass.whichUser = true;
////                        onBoardgingScreen = getSharedPreferences("onboardingscreen",MODE_PRIVATE);
////                        boolean isFirstTime = onBoardgingScreen.getBoolean("firstTime",true);
////                        if(isFirstTime){
////                            SharedPreferences.Editor editor1 = onBoardgingScreen.edit();
////                            editor1.putBoolean("firstTime",false);
////                            editor1.commit();
////                            Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);// Onboarding Activity pe
//////                        intent.putExtra("simplelogin","yes");
//////                        intent.putExtra("name",nameFromDb);
//////                        intent.putExtra("username",usernameFromDb);
//////                        intent.putExtra("phoneno",phonenoFromDb);
//////                        intent.putExtra("email",emailFromDb);
//////                        intent.putExtra("password",passwordFromDB);
////                            startActivity(intent);
////                            finish();
////                        }
//                        //else {
//                            Intent intent = new Intent(getApplicationContext(), UserDashboard.class);// Onboarding Activity pe
//                            startActivity(intent);
//                            finish();
//
//                        //}
//
//                    }
//                    else {
//                        password.setError("Wrong Password");
//                        password.requestFocus();
//                    }
//                }
//                else {
//                    username.setError("No such User Exist");
//                    username.requestFocus();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

