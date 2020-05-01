package com.example.myfirebasejavaproject.signupFragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfirebasejavaproject.models.UserHelperClass;
import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.app.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

public class AsHomeCooker extends Fragment {


    private static final int PICK_IMAGE_REQUEST = 1;
    String mImageurl;
    private Button mButtonChooseImage;
    TextInputLayout regName,regUsername,regEmail,regPhoneNo,regPassword,regAdress;
    Button regBtn,regToLoginbtn;
    public static  int check = 1;
    FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference refrence;
    private StorageReference mStorageRef;
    private ProgressBar mProgressBar;

    private Uri mImageUri;
    private StorageTask mUploadTask;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.signuphomecooker,container,false);


        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mButtonChooseImage = view.findViewById(R.id.button_choose_image);
        ///////////////////
        regName = view.findViewById(R.id.name);
        regUsername = view.findViewById(R.id.username);
        regEmail = view.findViewById(R.id.email);
        regPhoneNo = view.findViewById(R.id.phoneNo);
        regPassword = view.findViewById(R.id.password);
        regBtn = view.findViewById(R.id.regBtn);
        regToLoginbtn = view.findViewById(R.id.tologin);
        regAdress = view.findViewById(R.id.address);
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String EEEE = regName.getEditText().toString();
//              Toast.makeText(getActivity().getApplicationContext(), regName.getEditText().getText().toString(), Toast.LENGTH_SHORT).show();


                registerUser();
                if(check == 2) {
                    rootNode = FirebaseDatabase.getInstance();
                    refrence = rootNode.getReference("HomeCooker");

                   final String name = regName.getEditText().getText().toString();
                    final String username = regUsername.getEditText().getText().toString();
                    final String email = regEmail.getEditText().getText().toString();
                    final String phonenum = regPhoneNo.getEditText().getText().toString();
                    final String password = regPassword.getEditText().getText().toString();
                    final String address = regAdress.getEditText().getText().toString();
                    final String type = "Homecooker";

                    if (mUploadTask != null && mUploadTask.isInProgress()) {
                        Toast.makeText(getActivity().getApplicationContext(), "Upload is in progress", Toast.LENGTH_SHORT).show();
                    } else {

                        if (mImageUri != null) {
                            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                                    + "." + getFileExtension(mImageUri));

                            mUploadTask = fileReference.putFile(mImageUri)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                        Handler handler = new Handler();
//                                        handler.postDelayed(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                mProgressBar.setProgress(0);
//                                            }
//                                        }, 500);
                                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                            while (!urlTask.isSuccessful());
                                            Uri downloadUrl = urlTask.getResult();

                                            //Log.d(TAG, "onSuccess: firebase download url: " + downloadUrl.toString()); //use if testing...don't need this line.
//                                            UserHelperClass upload = new UserHelperClass(mEditTextFileName.getText().toString().trim(),downloadUrl.toString());
//
//                                            String uploadId = mDatabaseRef.push().getKey();
//                                            mDatabaseRef.child(uploadId).setValue(upload);



//                                            Toast.makeText(getActivity().getApplicationContext(), "Upload successful", Toast.LENGTH_LONG).show();
                                            Toast.makeText(getActivity().getApplicationContext(), "Sign Up Sucessfully", Toast.LENGTH_LONG).show();
//
                                            mImageurl = taskSnapshot.getUploadSessionUri().toString();
                                            UserHelperClass helperClass = new UserHelperClass(name, username, email, phonenum, password, address, type, downloadUrl.toString());
                                            refrence.push().setValue(helperClass);
                                            getActivity().finish();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
//                                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                                        @Override
//                                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                                            mProgressBar.setProgress((int) progress);
//                                        }
//                                    });
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "No file selected", Toast.LENGTH_SHORT).show();
                        }
                    }


//
//                    Intent intent = new Intent(getActivity().getApplicationContext(), Verify_phone_page.class);
//                    intent.putExtra("phoneNo",phonenum);
//                    startActivity(intent);



//                    Snackbar snackbar = Snackbar
//                            .make(view, "Sign up sucessfull", Snackbar.LENGTH_LONG);
//                    snackbar.show();

                    Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                    startActivity(intent);


                }
            }

        });

        return view;
    }



    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            //Picasso.get().load(mImageUri).into(mImageView);
        }
    }

    private String getFileExtension(Uri uri) {
       ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


//    private void uploadFile() {
//        if (mImageUri != null) {
//            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
//                    + "." + getFileExtension(mImageUri));
//
//            mUploadTask = fileReference.putFile(mImageUri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    mProgressBar.setProgress(0);
//                                }
//                            }, 500);
//
//                            Toast.makeText(getActivity().getApplicationContext(), "Upload successful", Toast.LENGTH_LONG).show();
//                            praticeImageModel upload = new praticeImageModel(mEditTextFileName.getText().toString().trim(),
//                                    taskSnapshot.getUploadSessionUri().toString());
//                            String uploadId = refrence.push().getKey();
//                            refrence.child(uploadId).setValue(upload);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(uploadRetrieveImages.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                            mProgressBar.setProgress((int) progress);
//                        }
//                    });
//        } else {
//            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
//        }
//    }



////////////////////////////////////
    private Boolean validateName(){
        String val = regName.getEditText().getText().toString();
        if(val.isEmpty()){
            regName.setError("Field cannot be empty");
            return false;
        }else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }


    }
    private Boolean validateUsername(){
        String val = regUsername.getEditText().getText().toString();
        //String noWhiteSpace = "(?=\\s+$)";
        if(val.isEmpty()){
            regUsername.setError("Field cannot be empty");
            return false;
        }
        else if(val.length() >=15){
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
    private Boolean validateEmail(){



        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()){
            regEmail.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches(emailPattern)){
            regEmail.setError("Invalid Email address");
            return false;
        }
        else {
            regName.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }


    }
    private Boolean validatePhoneNo(){
        String val = regPhoneNo.getEditText().getText().toString();
        if(val.isEmpty()){
            regPhoneNo.setError("Field cannot be empty");
            return false;
        }else {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }


    }
    private Boolean validatePassword(){
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
        if(val.isEmpty()){
            regPassword.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches(passwordVal)){
            regPassword.setError("Password is too weak");
            return false;
        }
        else {
            regName.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }
    public void registerUser() {
        if(!validateName() |!validatePassword() | !validatePhoneNo() | !validateEmail() | !validateUsername())
        {   check =1;
            return ;
        }
        check =2;

    }

}
