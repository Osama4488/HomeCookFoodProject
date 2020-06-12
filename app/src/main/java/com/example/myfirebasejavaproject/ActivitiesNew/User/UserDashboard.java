package com.example.myfirebasejavaproject.ActivitiesNew.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myfirebasejavaproject.AdaptersNew.User.GridAdapter;
import com.example.myfirebasejavaproject.AdaptersNew.User.NearByAdapter;
import com.example.myfirebasejavaproject.ActivitiesNew.Common.LoginActivity;
import com.example.myfirebasejavaproject.ModelsNew.UserHelperClass;
import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.AdaptersNew.User.FeaturedAdapter;
import com.example.myfirebasejavaproject.AdaptersNew.User.MostViewedAdapter;
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
    RecyclerView mostviewedRecycler,nearByRecyclerView,gridRecyclerView;
    RecyclerView.Adapter adapter;
    private FeaturedAdapter mAdapter;
    private MostViewedAdapter mostViewedAdapter;
    private NearByAdapter nearByAdapter;
    private GridAdapter gridAdapter;
    private DatabaseReference refrence;
    private List<UserHelperClass> mDatalist;
    private List<UserHelperClass> mostViewdDataList;
    private List<UserHelperClass> NearBydDataList;
    private List<UserHelperClass> gridDataList;
    private static SharedPreferences mPrefs;
    private static final String PREFS_TAG = "SharedPrefs";
    private static final String PRODUCT_TAG = "MyProduct";
    Context mContext;
    LinearLayout contentView;
    private ArrayList<Object> objects = new ArrayList<>();
    // Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);
        mostviewedRecycler = findViewById(R.id.mostviewed_recyclerr);
        nearByRecyclerView = findViewById(R.id.nearByRecyclerr);

      // gridRecyclerView = findViewById(R.id.gridRecycler);
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);
        setUpProgressBar();
        featuredRecycler();
        mostViewedRecycler();
        nearByRecycler();

       // gridRecycler();






        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout1);
        navigationView = findViewById(R.id.navigation_view1);


        navigationDrawer();


    }

    private void setUpProgressBar() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading please wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
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
            AlertDialog dialog = new AlertDialog.Builder(UserDashboard.this)
                    .setTitle("")
                    .setMessage("You sure you want to exit application")

                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setNegativeButton("Cancel",null)
                    .show();
           // super.onBackPressed();
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

//        mostviewedRecycler.setHasFixedSize(true);
//        mostviewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        progressDialog.show();
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

//                mostViewedAdapter = new MostViewedAdapter(mDatalist,UserDashboard.this);
//                mostviewedRecycler.setAdapter(mostViewedAdapter);
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
//        mostviewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//
//        ArrayList<FeaturedHelperClass> mostViewedLocation = new ArrayList<>();
//        mostViewedLocation.add(new FeaturedHelperClass(R.drawable.mcdoland, "Mcdonalds", "asdbkj akjdsnk bands njads nasd"));
//        mostViewedLocation.add(new FeaturedHelperClass(R.drawable.add_icon, "Pitbull", "asdbkj akjdsnk bands njads nasd"));
//        mostViewedLocation.add(new FeaturedHelperClass(R.drawable.restaurant_image, "Delicious", "asdbkj akjdsnk bands njads nasd"));
//
//        adapter = new MostViewedAdapter(mostViewedLocation);
//        mostviewedRecycler.setAdapter(adapter);

        mostviewedRecycler.setHasFixedSize(true);
        mostviewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mostViewdDataList = new ArrayList<>();

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
                        mostViewdDataList.add(model);
//                        if(mostViewdDataList.size() == 1){
//                           mostViewdDataList.remove(0);
//                        }
                        if(mostViewdDataList.size() == 3){
                            mostViewdDataList.remove(0);
                            break;
                        }
                    }

                }
                mostViewedAdapter = new MostViewedAdapter(mostViewdDataList,UserDashboard.this);
                mostviewedRecycler.setAdapter(mostViewedAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(mContext, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

//
    }



    private void nearByRecycler(){
        nearByRecyclerView.setHasFixedSize(true);
        nearByRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        NearBydDataList = new ArrayList<>();

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
                        NearBydDataList.add(model);




                    }

                }
                NearBydDataList.remove(1);
                NearBydDataList.remove(0);
                nearByAdapter = new NearByAdapter(NearBydDataList,UserDashboard.this);
                nearByRecyclerView.setAdapter(nearByAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(mContext, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void gridRecycler(){

        gridRecyclerView.setHasFixedSize(true);
        gridRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        gridDataList = new ArrayList<>();
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
                        gridDataList.add(model);
                    }

                }
                gridAdapter = new GridAdapter(gridDataList,UserDashboard.this);
                gridRecyclerView.setAdapter(gridAdapter);

//                mostViewedAdapter = new MostViewedAdapter(mDatalist,UserDashboard.this);
//                mostviewedRecycler.setAdapter(mostViewedAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(mContext, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
