package com.example.myfirebasejavaproject.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myfirebasejavaproject.models.practiceModel;
import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.models.practiceAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class practiceDetailActivity extends AppCompatActivity {


    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_detail);

        tv = findViewById(R.id.detailtextview);
        String uid = getIntent().getStringExtra(practiceAdapter.USERV_KEY);
        DatabaseReference refrence = FirebaseDatabase.getInstance().getReference("practice").child(uid);

        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                practiceModel model = dataSnapshot.getValue(practiceModel.class);
                tv.setText(model.getTitle());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
