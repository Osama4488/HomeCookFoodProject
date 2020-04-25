package com.example.myfirebasejavaproject.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.myfirebasejavaproject.Models.UserHelperClass;
import com.example.myfirebasejavaproject.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Intent intent;
    TextView drawerHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        //drawerHeader = navigationView.findViewById(R.id.drawer_header);
        View header = LayoutInflater.from(this).inflate(R.layout.header, null);
        navigationView.addHeaderView(header);

        drawerHeader =  header.findViewById(R.id.drawer_header);

        googleSignedInUserData();

    }

    private void googleSignedInUserData() {
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        String name = signInAccount.getDisplayName();
        if(signInAccount != null){
            drawerHeader.setText(signInAccount.getDisplayName());
        }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

        }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        int id = menuItem.getItemId();
        switch (id){
            case R.id.nav_logout:
//                Intent intent = new Intent("CLOSE_ALL");
//                this.sendBroadcast(intent);
                UserHelperClass.whichUser = false;
                final String LOG_OUT = "event_logout";
                Intent intent = new Intent(LOG_OUT);
                //send the broadcast to all activities who are listening
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                FirebaseAuth.getInstance().signOut();
                finish();
                 intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            case R.id.nav_profile:

                     intent = new Intent(getApplicationContext(),Userprofile.class);
                drawerLayout.closeDrawer(GravityCompat.START);
                    startActivity(intent);
            default:
                break;
        }

        return true;
    }
}

