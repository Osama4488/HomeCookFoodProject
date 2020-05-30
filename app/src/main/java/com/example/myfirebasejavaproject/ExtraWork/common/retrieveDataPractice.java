package com.example.myfirebasejavaproject.ExtraWork.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myfirebasejavaproject.ModelsNew.practiceAdapter;
import com.example.myfirebasejavaproject.ModelsNew.practiceModel;
import com.example.myfirebasejavaproject.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class retrieveDataPractice extends AppCompatActivity {


    FirebaseDatabase rootNode;
    DatabaseReference refrence;
//    ArrayList<practiceModel> list;
    private ChildEventListener mChildListener;
    private static final String TAG = "MyTag";
    //private List<practiceModel> mDatalist;

    private static SharedPreferences mPrefs ;
    private static final String PREFS_TAG = "SharedPrefs";
    private static final String PRODUCT_TAG = "MyProduct";
    Context mContext;
    private RecyclerView mRecyclerView;
    private practiceAdapter mAdapter;
    private List<practiceModel> mDataList;
//            retrieveDataPractice.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_data_practice);
//        rootNode = FirebaseDatabase.getInstance();
//        refrence = rootNode.getReference("practice");

        mDataList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new practiceAdapter(this,mDataList);
        mRecyclerView.setAdapter(mAdapter);
        //mPrefs =  mContext.getSharedPreferences(PREFS_TAG, Context.MODE_PRIVATE);
        String title = "blossom";
        String desc = "bbbbbbbbbb ";


        refrence = FirebaseDatabase.getInstance().getReference("practice");
//        String key = refrence.push().getKey();
//        practiceModel model = new practiceModel(title, desc);
//        refrence.child(key).setValue(model);


       // mPrefs = getPreferences("njn",MODE_PRIVATE);
        readData();
        //startActivity(new Intent(retrieveDataPractice.this, UserDashboard.class));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        refrence.removeEventListener(mChildListener);
    }

    private void readData(){
        mChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                practiceModel model = dataSnapshot.getValue(practiceModel.class);
                model.setUid(dataSnapshot.getKey());
//                Log.d(TAG, "onChildAdded: Title "+model.getTitle());
//                Log.d(TAG, "onChildAdded: Description"+model.getDescription());
                mDataList.add(model);
                mAdapter.notifyDataSetChanged();



//                SharedPreferences.Editor prefsEditor = mPrefs.edit();
//                Gson gson = new Gson();
//                String json = gson.toJson(mDatalist);
//                prefsEditor.putString(PRODUCT_TAG, json);
//                prefsEditor.commit();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
           practiceModel model = dataSnapshot.getValue(practiceModel.class);
           model.setUid(dataSnapshot.getKey());
           mDataList.remove(model);
           mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        refrence.addChildEventListener(mChildListener);

    }

    //        refrence.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//               // List<practiceModel> model = (List<practiceModel>) dataSnapshot.getValue();
//                Map<String,Object>  data= (Map<String, Object>) dataSnapshot.getValue();
//                Log.d(TAG,"onChildAdded: Title  " + data.get("title"));
//                Log.d(TAG,"onChildAdded: Key  " + dataSnapshot.getKey());
//                Log.d(TAG,"onChildAdded: Description  " + data.get("description"));
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });



//        refrence.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
//
//                    Map<String,Object>  data= (Map<String, Object>) snapshot.getValue();
//                    Log.d(TAG,"onChildAdded: Title  " + data.get("title"));
//                    Log.d(TAG,"onChildAdded: Key  " + snapshot.getKey());
//                    Log.d(TAG,"onChildAdded: Description  " + data.get("description"));
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//

}
