package com.example.myfirebasejavaproject.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myfirebasejavaproject.app.LoginActivity;
import com.example.myfirebasejavaproject.app.Userprofile;
import com.example.myfirebasejavaproject.models.UserHelperClass;
import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.adapters.HomeAdapter.FeaturedAdapter;
import com.example.myfirebasejavaproject.models.FeaturedHelperClass;
import com.example.myfirebasejavaproject.adapters.HomeAdapter.MostViewedAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Variables
    static final float END_SCALE = 0.7f;
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
    LinearLayout contentView;

    // Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);
        mostviewedRecycler = findViewById(R.id.mostviewed_recycler);
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);
        featuredRecycler();
        mostViewedRecycler();


        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout1);
        navigationView = findViewById(R.id.navigation_view1);


        navigationDrawer();

    }



    //Navigation Drawer Functions
    private void navigationDrawer() {

        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);

                else
                    drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {

        drawerLayout.setScrimColor(getResources().getColor(R.color.colorPrimary));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }



    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }


    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_logout:
                startActivity(new Intent(UserDashboard.this, LoginActivity.class));
                SharedPreferences pref = getSharedPreferences(UserHelperClass.shared,MODE_PRIVATE);
                pref.edit().clear().commit();
                finish();
                break;
            case R.id.nav_profile:
                startActivity(new Intent(UserDashboard.this, Userprofile.class));
                break;

                default:
                    return true;
        }

        return true;
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
                   // FeaturedHelperClass model1 = postSnapshot.getValue(FeaturedHelperClass.class);
                    if(model.getType().equals("User")){

                    }
                    else {
                        model.setCookerId(postSnapshot.getKey());
                        mDatalist.add(model);
                    }

                }
                mAdapter = new FeaturedAdapter(mDatalist,UserDashboard.this);
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


//    private void featuredRecycler() {
//
//        featuredRecycler.setHasFixedSize(true);
//        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        mDatalist = new ArrayList<>();
//        refrence = FirebaseDatabase.getInstance().getReference("HomeCooker");
//
//        refrence.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//
//                    UserHelperClass model = postSnapshot.getValue(UserHelperClass.class);
//                    if(model.getType().equals("User")){
//
//                    }
//                    else {
//                        model.setCookerId(postSnapshot.getKey());
//                        mDatalist.add(model);
//                    }
//
//                }
//                mAdapter = new FeaturedAdapter(mDatalist);
//                featuredRecycler.setAdapter(mAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(mContext, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
////        Gson gson = new Gson();
////        String json = mPrefs.getString(PRODUCT_TAG, "");
//        // practiceModel obj = gson.fromJson(json, practiceModel.class);
//
////
////        ArrayList<practiceModel> featuredlocation = new ArrayList<>();
//////        featuredlocation.add(new retrieveDataPractice(R.drawable.mcdoland,"Mcdonald's","ajsd haod aosd noaidsn nadsn nasdn"));
//////        featuredlocation.add(new retrieveDataPractice(R.drawable.profile_image,"Eden Robe","ajsd haod aosd noaidsn nadsn nasdn"));
//////        featuredlocation.add(new retrieveDataPractice(R.drawable.ic_speaker_phone_black_24dp,"Sweet Salty","ajsd haod aosd noaidsn nadsn nasdn"));
////
////        featuredlocation.add(new practiceModel("Mcdonald's", "ajsd haod aosd noaidsn nadsn nasdn"));
////        featuredlocation.add(new practiceModel("Eden Robe", "ajsd haod aosd noaidsn nadsn nasdn"));
////        featuredlocation.add(new practiceModel("Sweet Salty", "ajsd haod aosd noaidsn nadsn nasdn"));
////
////        adapter = new FeaturedAdapter(featuredlocation);
////        featuredRecycler.setAdapter(adapter);
////
////        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});
//
//
//    }

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
