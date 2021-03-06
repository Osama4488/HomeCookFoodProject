package com.example.myfirebasejavaproject.FragmentsNew.User;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfirebasejavaproject.ModelsNew.UserHelperClass;
import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.ActivitiesNew.Common.LoginActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpAsUser extends Fragment {


    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword, regAddress;
    Button regBtn, regToLoginbtn;
    public static int check = 1;

    FirebaseDatabase rootNode;
    DatabaseReference refrence;
    Context context;
    FirebaseAuth mAuth;
    String val;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.sigmupasuser, container, false);

        regName = view.findViewById(R.id.name);
        regUsername = view.findViewById(R.id.username);
        regEmail = view.findViewById(R.id.email);
        regPhoneNo = view.findViewById(R.id.phoneNo);
        regPassword = view.findViewById(R.id.password);
        regBtn = view.findViewById(R.id.regBtn);
        regToLoginbtn = view.findViewById(R.id.tologin);
        regToLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                getActivity().finish();
            }
        });
        regAddress = view.findViewById(R.id.address);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String EEEE = regName.getEditText().toString();
//              Toast.makeText(getActivity().getApplicationContext(), regName.getEditText().getText().toString(), Toast.LENGTH_SHORT).show();

                regToLoginbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                        //getActivity().onBackPressed();

                    }
                });
                registerUser();
                if (check == 2) {
                    rootNode = FirebaseDatabase.getInstance();
//                    refrence = rootNode.getReference("users");
                    refrence = rootNode.getReference("HomeCooker");

                    final String name = regName.getEditText().getText().toString();
                    final String username = regUsername.getEditText().getText().toString();
                    final String email = regEmail.getEditText().getText().toString();
                    final String phonenum = regPhoneNo.getEditText().getText().toString();
                    final String password = regPassword.getEditText().getText().toString();
//                    String newPass =  sha256(password);
                    final String address = regAddress.getEditText().getText().toString();
                    final String type = "User";

//

//                    Intent intent = new Intent(getActivity().getApplicationContext(), Verify_phone_page.class);
//                    intent.putExtra("phoneNo",phonenum);
//                    startActivity(intent);

                    refrence = UserHelperClass.path;
                    refrence.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                String us = data.child("username").getValue().toString();
                                if (us.equals(val)) {
                                    // Toast.makeText(getActivity().getApplicationContext(), "User Already Exists", Toast.LENGTH_SHORT).show();
                                    regUsername.setError("Username already exists");
                                    check = 0;
                                    return;
                                } else {
                                    check = 2;
                                }


                            }
                            check = 2;
                            UserHelperClass helperClass = new UserHelperClass(name, username, email, phonenum, password, address, type);
                            refrence.push().setValue(helperClass);
                            Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            Toast.makeText(getActivity().getApplicationContext(), "User Sign Up Sucessfully", Toast.LENGTH_LONG).show();


                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


//                    UserHelperClass helperClass = new UserHelperClass(name, username, email, phonenum, password,address,type);
//                    refrence.push().setValue(helperClass);
//
//                    Toast.makeText(getActivity().getApplicationContext(), "User Sign Up Sucessfully", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
//                    startActivity(intent);
//                    getActivity().finish();


                }
            }

        });


        return view;
    }
//    public static String sha256(String base) {
//        try{
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            byte[] hash = digest.digest(base.getBytes("UTF-8"));
//            StringBuffer hexString = new StringBuffer();
//
//            for (int i = 0; i < hash.length; i++) {
//                String hex = Integer.toHexString(0xff & hash[i]);
//                if(hex.length() == 1) hexString.append('0');
//                hexString.append(hex);
//            }
//
//            return hexString.toString();
//        } catch(Exception ex){
//            throw new RuntimeException(ex);
//        }
//    }


    private void checkEmailAlreadyRxists() {


    }

    private Boolean validateName() {
        String val = regName.getEditText().getText().toString();
        if (val.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }


    }

    private Boolean validateUsername() {
        val = regUsername.getEditText().getText().toString();
        //String noWhiteSpace = "(?=\\s+$)";
        if (val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            regUsername.setError("Username too long");
            return false;
        }
//        else if(!val.matches(noWhiteSpace)){
//            regUsername.setError("White spaces are not allowed");
//            return false;
//        }
        else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }


    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid Email address");
            return false;
        } else {
            regName.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }


    }

    private Boolean validatePhoneNo() {
        String val = regPhoneNo.getEditText().getText().toString();
        if (val.isEmpty()) {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        } else {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }


    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("Password is too weak");
            return false;
        } else {
            regName.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

    public void registerUser() {
        if (!validateName() | !validatePassword() | !validatePhoneNo() | !validateEmail() | !validateUsername()) {
            check = 1;
            return;
        }
        check = 2;

    }


}
