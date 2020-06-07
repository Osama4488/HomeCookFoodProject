package com.example.myfirebasejavaproject.ActivitiesNew.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.AdaptersNew.Common.ScrollTabAdapter;
import com.example.myfirebasejavaproject.ModelsNew.Main_food_model;
import com.example.myfirebasejavaproject.FragmentsNew.User.FragmentOne;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Menu_Profile extends AppCompatActivity {


    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    ImageView mainImage;
    private ViewPager viewPager;
    private ScrollTabAdapter adapter;
    private TabLayout tabLayout;
    DatabaseReference reference;
    ImageView cartopen;
    String url;
   // List<String> titleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__profile);

        initialise();

        prepareDataResource();

        adapter = new ScrollTabAdapter(getSupportFragmentManager(), fragmentList, titleList);
        Picasso.get().load(url)
                .fit()
                .centerCrop()
                .into(mainImage);
        // Bind Adapter to ViewPager.
        viewPager.setAdapter(adapter);

        cartopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu_Profile.this, CartActivity.class));
            }
        });

        // Link ViewPager and TabLayout
        tabLayout.setupWithViewPager(viewPager);


            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                   String title =  titleList.get(tab.getPosition());
                    String positon = Integer.toString(tab.getPosition()) ;
                    SharedPreferences.Editor editor = getSharedPreferences("subFoodItemClick",MODE_PRIVATE).edit();
                    editor.putString("subFoodName",title);
                    editor.commit();
                    SharedPreferences.Editor editor1 = getSharedPreferences("position of tab layout",MODE_PRIVATE).edit();
                    editor.putString("position",positon);
                    editor.commit();

                    int i = viewPager.getCurrentItem();

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    String title =  titleList.get(tab.getPosition());
                    SharedPreferences.Editor editor = getSharedPreferences("subFoodItemClick",MODE_PRIVATE).edit();
                    editor.putString("subFoodName",title);
                    editor.commit();
                }
            });
            FragmentOne one = new FragmentOne();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.viewPager,one,"helloframgne");
        transaction.commit();

    }

    // Initialise Activity Data.
    private void initialise() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        mainImage = findViewById(R.id.menu_profile_main_image);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        cartopen = findViewById(R.id.cartOpen);
    }

    // Let's prepare Data for our Tabs - Fragments and Title List
    private void prepareDataResource() {
        SharedPreferences editor = getSharedPreferences("clickedProfile", Context.MODE_PRIVATE);
        String uid = editor.getString("uid","");
        url =  editor.getString("imageurl","");
    reference = FirebaseDatabase.getInstance().getReference("HomeCooker").child(uid).child("MainFood");
    reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
           // String val = dataSnapshot.getValue().toString();
           // String us = dataSnapshot.child("name").getValue().toString();
            for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                Main_food_model model1 = postSnapshot.getValue(Main_food_model.class);
                String name = model1.getName();
                titleList.add(name);
                fragmentList.add(new FragmentOne());
            }
            adapter.notifyDataSetChanged();


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

//        for (int i =0; i<titleList.size();i++){
//            addData(new FragmentOne(), titleList.get(i));
//        }
//        addData(new FragmentOne(), "ONE");
//        addData(new FragmentOne(), "TWO");

//        addData(new FragmentOne(), "ONE");
//        addData(new FragmentTwo(), "TWO");
//        addData(new FragmentThree(), "THREE");
//        addData(new FragmentFour(), "FOUR");
//        addData(new FragmentFive(), "FIVE");
//        addData(new FragmentSix(), "SIX");

//        addData(new FragmentOne(), "ONE");
//        addData(new FragmentTwo(), "TWO");
//        addData(new FragmentThree(), "THREE");
//        addData(new FragmentFour(), "FOUR");
//        addData(new FragmentFive(), "FIVE");
//        addData(new FragmentSix(), "SIX");
    }

    private void addData(Fragment fragment, String title) {
        fragmentList.add(fragment);
        titleList.add(title);
    }
}
