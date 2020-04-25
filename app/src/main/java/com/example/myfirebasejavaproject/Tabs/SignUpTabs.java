package com.example.myfirebasejavaproject.Tabs;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.SignupFragments.AsHomeCooker;
import com.example.myfirebasejavaproject.SignupFragments.AsUser;
import com.example.myfirebasejavaproject.adapters.SignUpTabsAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class SignUpTabs extends AppCompatActivity {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titletList = new ArrayList<>();
    private ViewPager viewpager;
    private SignUpTabsAdapter adapter;
    private TabLayout tablaoyout;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singuptabs);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initialise();


        prepareDataResource();

         adapter = new SignUpTabsAdapter(getSupportFragmentManager(),fragmentList,titletList,0);
        viewpager.setAdapter(adapter);
        tablaoyout.setupWithViewPager(viewpager);





        //Toast.makeText(SignUpTabs.this, regName.getEditText().toString(), Toast.LENGTH_SHORT).show();



//            @Override
//            public void onClick(View v) {
//        regBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(SignUpTabs.this, regName.getEditText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void initialise() {

//        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            toolbar.setTitle("SignUp");
//        }

        viewpager = findViewById(R.id.viewPager);
        tablaoyout = findViewById(R.id.tabs);


    }

    private void prepareDataResource() {

       addData(new AsUser(),"As User");
       addData(new AsHomeCooker(),"As Home Cooker");


    }

    private void addData(Fragment fragment,String title){

            fragmentList.add(fragment);
            titletList.add(title);

    }
}
