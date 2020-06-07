package com.example.myfirebasejavaproject.ActivitiesNew.HomeCooker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.ModelsNew.UserHelperClass;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;

import com.example.myfirebasejavaproject.FragmentsNew.HomeCooker.setTabs.SectionsPagerAdapter;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

public class HomeCookerProfileTab extends AppCompatActivity {

    String _USERNAME, _EMAIL, _PASSWORD, _PHONENO, _NAME, _KEY,MIMAGEURL;
    TextInputLayout regName,regUsername,regEmail,regPhoneNo,regPassword,regAddress;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cooker_profile_tab);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        iv = findViewById(R.id.profile_image);
        getHomeCookerData();
        //getHomeCookerData();
        Picasso.get().load(MIMAGEURL)
                .fit()
                .centerCrop()
                .into(iv);
        //regName.getEditText().setText(_NAME);


    }
    private void getHomeCookerData() {
        SharedPreferences prefs = getSharedPreferences(UserHelperClass.shared, Context.MODE_PRIVATE);
        MIMAGEURL = prefs.getString("imageurl",null);
    }



}