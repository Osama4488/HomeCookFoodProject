package com.example.myfirebasejavaproject.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.adapters.sliderAdapter;
import com.example.myfirebasejavaproject.app.DashboardActivity;

public class OnBoarding extends AppCompatActivity {


    ViewPager viewPager;
    LinearLayout dotslayout;
    sliderAdapter adapter;
        TextView[] dots;
        Button lets_get_started_btn;
        Animation animation;
        int currentPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        viewPager = findViewById(R.id.slider);
        dotslayout = findViewById(R.id.dots);
        lets_get_started_btn = findViewById(R.id.get_started);

        adapter = new sliderAdapter(this);
        viewPager.setAdapter(adapter);
        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);

    }

    public void skipFunction(View view){
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }
    public void next(View view){
        viewPager.setCurrentItem(currentPosition+1);
    }


    private void addDots(int position){
        dots = new TextView[4];
        dotslayout.removeAllViews();
        for(int i = 0;  i < dots.length;i++){

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dotslayout.addView(dots[i]);
        }

        if(dots.length >0 ){
            dots[position].setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
addDots(position);
        currentPosition = position;
        if(position == 0){
lets_get_started_btn.setVisibility(View.INVISIBLE);
        }
        else if(position == 1){
            lets_get_started_btn.setVisibility(View.INVISIBLE);
        }
        else if(position == 2){
            lets_get_started_btn.setVisibility(View.INVISIBLE);
        }
        else {
            animation = AnimationUtils.loadAnimation(OnBoarding.this,R.anim.bottom_navigation);
            lets_get_started_btn.setAnimation(animation);
            lets_get_started_btn.setVisibility(View.VISIBLE);
        }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
