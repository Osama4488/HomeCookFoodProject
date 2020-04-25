package com.example.myfirebasejavaproject.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.myfirebasejavaproject.Common.retrieveDataPractice;
import com.example.myfirebasejavaproject.Models.UserHelperClass;
import com.example.myfirebasejavaproject.Models.practiceModel;
import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.adapters.HomeAdapter.FeaturedAdapter;
import com.example.myfirebasejavaproject.adapters.HomeAdapter.FeaturedHelperClass;
import com.example.myfirebasejavaproject.adapters.HomeAdapter.MostViewedAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class UserDashboard extends AppCompatActivity {

    RecyclerView featuredRecycler;
    RecyclerView mostviewedRecycler;
    RecyclerView.Adapter adapter;
    private FeaturedAdapter mAdapter;
    private DatabaseReference refrence;
    private List<UserHelperClass> mDatalist;
    private static SharedPreferences mPrefs;
    private static final String PREFS_TAG = "SharedPrefs";
    private static final String PRODUCT_TAG = "MyProduct";
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);
        mostviewedRecycler = findViewById(R.id.mostviewed_recycler);
        featuredRecycler();
        mostViewedRecycler();


    }

    private void featuredRecycler() {

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mDatalist = new ArrayList<>();
        refrence = FirebaseDatabase.getInstance().getReference("HomeCooker");

        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UserHelperClass model = postSnapshot.getValue(UserHelperClass.class);
                    mDatalist.add(model);
                }
                mAdapter = new FeaturedAdapter(mDatalist);
                featuredRecycler.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mContext, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

//        Gson gson = new Gson();
//        String json = mPrefs.getString(PRODUCT_TAG, "");
        // practiceModel obj = gson.fromJson(json, practiceModel.class);

//
//        ArrayList<practiceModel> featuredlocation = new ArrayList<>();
////        featuredlocation.add(new retrieveDataPractice(R.drawable.mcdoland,"Mcdonald's","ajsd haod aosd noaidsn nadsn nasdn"));
////        featuredlocation.add(new retrieveDataPractice(R.drawable.profile_image,"Eden Robe","ajsd haod aosd noaidsn nadsn nasdn"));
////        featuredlocation.add(new retrieveDataPractice(R.drawable.ic_speaker_phone_black_24dp,"Sweet Salty","ajsd haod aosd noaidsn nadsn nasdn"));
//
//        featuredlocation.add(new practiceModel("Mcdonald's", "ajsd haod aosd noaidsn nadsn nasdn"));
//        featuredlocation.add(new practiceModel("Eden Robe", "ajsd haod aosd noaidsn nadsn nasdn"));
//        featuredlocation.add(new practiceModel("Sweet Salty", "ajsd haod aosd noaidsn nadsn nasdn"));
//
//        adapter = new FeaturedAdapter(featuredlocation);
//        featuredRecycler.setAdapter(adapter);
//
//        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});


    }

    private void mostViewedRecycler() {
        //mostviewedRecycler.setHasFixedSize(true);
        mostviewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedHelperClass> mostViewedLocation = new ArrayList<>();
        mostViewedLocation.add(new FeaturedHelperClass(R.drawable.mcdoland, "Mcdonalds", "asdbkj akjdsnk bands njads nasd"));
        mostViewedLocation.add(new FeaturedHelperClass(R.drawable.add_icon, "Pitbull", "asdbkj akjdsnk bands njads nasd"));
        mostViewedLocation.add(new FeaturedHelperClass(R.drawable.restaurant_image, "Delicious", "asdbkj akjdsnk bands njads nasd"));

        adapter = new MostViewedAdapter(mostViewedLocation);
        mostviewedRecycler.setAdapter(adapter);
    }
}
