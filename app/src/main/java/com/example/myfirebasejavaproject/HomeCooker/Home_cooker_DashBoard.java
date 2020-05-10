package com.example.myfirebasejavaproject.HomeCooker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirebasejavaproject.app.LoginActivity;
import com.example.myfirebasejavaproject.models.Main_food_model;
import com.example.myfirebasejavaproject.models.Sub_food_Model;
import com.example.myfirebasejavaproject.models.UserHelperClass;
import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.adapters.dialogsAdapter.DialogAddMainFoodAdapter;
import com.example.myfirebasejavaproject.adapters.dialogsAdapter.DialogAddSubFoodAdapter;
import com.example.myfirebasejavaproject.adapters.dialogsAdapter.DialogUpdateMainFoodAdapter;
import com.example.myfirebasejavaproject.adapters.dialogsAdapter.DialogUpdateSubFoodAdapter;
import com.example.myfirebasejavaproject.adapters.HomeCookerAdapter.MainAdapter;
import com.example.myfirebasejavaproject.tabs.HomeCookerProfileTab;
import com.example.myfirebasejavaproject.user.UserDashboard;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home_cooker_DashBoard extends AppCompatActivity implements DialogAddSubFoodAdapter.DialogListener, DialogAddMainFoodAdapter.DialogListener, DialogUpdateMainFoodAdapter.DialogListener, DialogUpdateSubFoodAdapter.DialogListener, NavigationView.OnNavigationItemSelectedListener {

    String _USERNAME, _EMAIL, _PASSWORD, _PHONENO, _NAME, _KEY;
    DatabaseReference reference;
    private TextView textViewUsername;
    private FloatingActionButton floatingActionButton;
    private Button button;
    private RecyclerView main_food_recycler;
    private MainAdapter mAdapter;
    List<Main_food_model> mDatalist;
    //DatabaseReference refrence;
    private MainAdapter mainAdapter;
    //private CardView cardview;
    String mainFoodid;
    String getItemClickPosition = "clicked";

    private DrawerLayout drawer;
    private NavigationView navigation;
    private ImageView menuIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cooker__dash_board);
        main_food_recycler = findViewById(R.id.main_food_recycler);

        drawer = findViewById(R.id.drawer_layout2);
        navigation = findViewById(R.id.navigation_view2);

        menuIcon = findViewById(R.id.menu_icon);
        navigationDrawer();


        floatingActionButton = findViewById(R.id.addMainFood);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAddMainFood();
            }
        });
        getHomeCookerData();


        reference = FirebaseDatabase.getInstance().getReference("HomeCooker");

        setRecyclerView();
        //   setMainFoodRecycler();

//        cardview = findViewById(R.id.card_viewMainFood);
//        cardview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                openDialogCardView();
//            }
//        });
    }

    private void navigationDrawer() {

        //Navigation Drawer
        navigation.bringToFront();
        navigation.setNavigationItemSelectedListener(this);
        navigation.setCheckedItem(R.id.nav_home);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);

                else
                    drawer.openDrawer(GravityCompat.START);

            }
        });

        //animateNavigationDrawer();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerVisible(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void setRecyclerView() {
        mDatalist = new ArrayList<>();
        main_food_recycler.setHasFixedSize(true);
        main_food_recycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MainAdapter(this, mDatalist);
        main_food_recycler.setAdapter(mAdapter);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("HomeCooker").child(_KEY).child("MainFood");
        usersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // for (DataSnapshot datas : dataSnapshot.getChildren()) {
//                    Main_food_model model = datas.getValue(Main_food_model.class);
//                    model.setMainFoodId(datas.getKey());
                //int count =   (int) dataSnapshot.getChildrenCount() ;
                Main_food_model model = dataSnapshot.getValue(Main_food_model.class);
                model.setMainFoodId(dataSnapshot.getKey());


                mDatalist.add(model);
                //mAdapter.notifyItemInserted(count);
                mAdapter.notifyDataSetChanged();
                //}

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                SharedPreferences prefs = getSharedPreferences(getItemClickPosition, MODE_PRIVATE);
                int postion = prefs.getInt("position", 0);
                Main_food_model model = dataSnapshot.getValue(Main_food_model.class);
                model.setMainFoodId(dataSnapshot.getKey());

                mDatalist.set(postion, model);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Main_food_model model = dataSnapshot.getValue(Main_food_model.class);
                model.setMainFoodId(dataSnapshot.getKey());
                mDatalist.remove(model);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void setMainFoodRecycler() {

        main_food_recycler.setHasFixedSize(true);
        main_food_recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mDatalist = new ArrayList<>();
        //final DatabaseReference refrence = FirebaseDatabase.getInstance().getReference("HomeCooker");

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("HomeCooker").child(_KEY).child("MainFood");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                    Main_food_model model = datas.getValue(Main_food_model.class);
                    model.setMainFoodId(datas.getKey());
                    //Main_food_model model = (Main_food_model) datas.child("name").getValue();
                    //String name = datas.child("name").getValue().toString();
                    //String name=datas.child("name").getValue().toString();
                    // String type=datas.child("type").getValue().toString();

                    mDatalist.add(model);

                    //get other items
                }

                mainAdapter = new MainAdapter(Home_cooker_DashBoard.this, mDatalist);
                //mainAdapter.notifyDataSetChanged();
                main_food_recycler.setAdapter(mainAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
//        refrence.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    final DatabaseReference refrence1 = FirebaseDatabase.getInstance().getReference("MainFood");
//
//                    for(DataSnapshot data : postSnapshot.getChildren()){
//                       String name = data.
//
//                    }
//
//                    Main_food_model model1 = postSnapshot.child("MainFood").getValue(Main_food_model.class);
//                    Main_food_model model = postSnapshot.getValue(Main_food_model.class);
//                    mDatalist.add(model);
//                }
//                mainAdapter = new MainAdapter(mDatalist);
//                main_food_recycler.setAdapter(mainAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(Home_cooker_DashBoard.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    private void getHomeCookerData() {
        SharedPreferences prefs = getSharedPreferences(UserHelperClass.shared, MODE_PRIVATE);
        _USERNAME = prefs.getString("username", null);
        _NAME = prefs.getString("name", null);
        _EMAIL = prefs.getString("email", null);
        _PHONENO = prefs.getString("phoneno", null);
        _PASSWORD = prefs.getString("password", null);
        _KEY = prefs.getString("homeCookerId", null);

    }

    public void openDialogAddMainFood() {
        DialogAddMainFoodAdapter adapter = new DialogAddMainFoodAdapter();
        adapter.show(getSupportFragmentManager(), "Main Food Dialog");

    }

//    public void openDialogCardView() {
//        DialogAddSubFoodAdapter adapter = new DialogAddSubFoodAdapter();
//        adapter.show(getSupportFragmentManager(), "Sub Food Dialog");
//
//    }


    @Override
    public void applySubFood(String subFoodName, String subFoodprice, String subFooDesc) {


        SharedPreferences prefs = getSharedPreferences("myMainFood", MODE_PRIVATE);
        mainFoodid = prefs.getString("mainfoodId", null);


        Sub_food_Model model = new Sub_food_Model(subFoodName, subFoodprice, subFooDesc);
//        reference.child(_KEY).push().setValue(model);
        reference.child(_KEY).child("MainFood").child(mainFoodid).child("SubFood").push().setValue(model);


        //setMainFoodRecycler();

        //setRecyclerView();

//        textViewUsername.setText(FoodName);
    }

    @Override
    public void applyText(String FoodName) {
        Main_food_model model = new Main_food_model(FoodName);
//        reference.child(_KEY).push().setValue(model);
        reference.child(_KEY).child("MainFood").push().setValue(model);
        //mainAdapter.notifyDataSetChanged();
        //setMainFoodRecycler();
        //setRecyclerView();

//        textViewUsername.setText(FoodName);
    }


    @Override
    public void updateMainFood(String FoodName) {
        final DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("HomeCooker");
        SharedPreferences prefs = getSharedPreferences("myMainFood", MODE_PRIVATE);
        mainFoodid = prefs.getString("mainfoodId", null);
        Map<String, Object> updatedValue = new HashMap<>();
        String link = _KEY + "/MainFood/" + mainFoodid + "/name";
        updatedValue.put(link, FoodName);
        ref1.updateChildren(updatedValue);
        //setMainFoodRecycler();
        //setRecyclerView();


//        reference.child(_KEY).child("MainFood").child(mainFoodid);
//        String b = "";
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

    @Override
    public void updateSubFood(String subFoodName, String subFoodprice, String subFooDesc) {
        SharedPreferences ref2 = getSharedPreferences("mySubFoodId", Context.MODE_PRIVATE);
        String subFoodId = ref2.getString("subFoodId", null);
        final DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("HomeCooker");
        SharedPreferences prefs = getSharedPreferences("myMainFood", MODE_PRIVATE);
        mainFoodid = prefs.getString("mainfoodId", null);

        Map<String, Object> updatedValue = new HashMap<>();
        String link = _KEY + "/MainFood/" + mainFoodid + "/SubFood/" + subFoodId + "/";
        Sub_food_Model model = new Sub_food_Model(subFoodName, subFoodprice, subFooDesc);
        updatedValue.put(link, model);
        ref1.updateChildren(updatedValue);
        Toast.makeText(this, "Value Updated", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_profile:
                startActivity(new Intent(Home_cooker_DashBoard.this, HomeCookerProfileTab.class));
                break;
            case R.id.nav_logout:
                startActivity(new Intent(Home_cooker_DashBoard.this, LoginActivity.class));
                SharedPreferences pref = getSharedPreferences(UserHelperClass.shared,MODE_PRIVATE);
                pref.edit().clear().commit();
                finish();
                break;
            default:
                return true;
        }

        return true;
    }
}
