package com.example.myfirebasejavaproject.FragmentsNew.HomeCooker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.ModelsNew.UserHelperClass;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;

public class CookerProifleInfoFrag extends Fragment {

    String _USERNAME, _EMAIL, _PASSWORD, _PHONENO, _NAME, _KEY,MIMAGEURL;
    TextInputLayout regName,regUsername,regEmail,regPhoneNo,regPassword;
    Button updateRecord;
    DatabaseReference refrence;
    SharedPreferences prefs;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cooker_proifle_info_frag,container,false);
        prefs = getActivity().getSharedPreferences(UserHelperClass.shared, MODE_PRIVATE);
        getHomeCookerData();
        setData();
        regName = view.findViewById(R.id.homecookername);
        regUsername = view.findViewById(R.id.username);
        regEmail = view.findViewById(R.id.homecookeremail);
        regPhoneNo = view.findViewById(R.id.homecookernamephoneno);
        regPassword = view.findViewById(R.id.homecookernamepassword);
        updateRecord = view.findViewById(R.id.update);
        _KEY = prefs.getString("homeCookerId","");
        refrence = UserHelperClass.path.child(_KEY);
        //refrence = FirebaseDatabase.getInstance().getReference("HomeCooker");

        //setData();
        updateRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });


        return view;
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

                //regUsername.setText(namee);
                regName.getEditText().setText(namee);
                regName.getEditText().setText(namee);
                regEmail.getEditText().setText(emaill);
                regPhoneNo.getEditText().setText(phoneno);
                regPassword.getEditText().setText(passwordd);



                //}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    private boolean checkData(){
//        String name = regName.getEditText().getText().toString();
//        String phone = regPhoneNo.getEditText().getText().toString();
//        String email = regEmail.getEditText().getText().toString();
//        String pass = regPassword.getEditText().getText().toString();
//        if(!name.equals(_NAME) || !email.equals(_EMAIL) || !phone.equals(_PHONENO)  || !pass.equals(_PASSWORD)){
//                refrence.child(_KEY).child("name").setValue(name);
//                refrence.child(_KEY).child("email").setValue(email);
//                refrence.child(_KEY).child("phoneNo").setValue(phone);
//                refrence.child(_KEY).child("password").setValue(pass);
//                Toast.makeText(getActivity().getApplicationContext(), "Data Updated Sucessfully", Toast.LENGTH_SHORT).show();
//                return true;
//        }
//        else {
//            Toast.makeText(getActivity().getApplicationContext(), "Give me something to update", Toast.LENGTH_LONG).show();
//            return false;
//        }
//    }

    private void checkData(){
        String name = regName.getEditText().getText().toString();
        String phone = regPhoneNo.getEditText().getText().toString();
        String emaill = regEmail.getEditText().getText().toString();
        String pass = regPassword.getEditText().getText().toString();
        if(!name.equals(_NAME) || !emaill.equals(_EMAIL) || !phone.equals(_PHONENO)  || !pass.equals(_PASSWORD)){
            refrence.child("name").setValue(name);
            refrence.child("email").setValue(emaill);
            refrence.child("phoneNo").setValue(phone);
            refrence.child("password").setValue(pass);

            updateSharedrefrence();
            Toast.makeText(getContext(), "Data Updated Sucessfully", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Give me something to update", Toast.LENGTH_LONG).show();
        }
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


                SharedPreferences.Editor editor = getActivity().getSharedPreferences(UserHelperClass.shared,MODE_PRIVATE).edit();
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






    private void getHomeCookerData() {
        SharedPreferences prefs = getActivity().getSharedPreferences(UserHelperClass.shared, MODE_PRIVATE);
        _USERNAME = prefs.getString("username", null);
        _NAME = prefs.getString("name", null);
        _EMAIL = prefs.getString("email", null);
        _PHONENO = prefs.getString("phoneno", null);
        _PASSWORD = prefs.getString("password", null);
        _KEY = prefs.getString("homeCookerId", null);
        MIMAGEURL = prefs.getString("imageurl",null);
    }

//    private void setData(){
//        regName.getEditText().setText(_NAME);
//        regEmail.getEditText().setText(_EMAIL);
//        regPhoneNo.getEditText().setText(_PHONENO);
//        regPassword.getEditText().setText(_PASSWORD);
//    }




}
