package com.example.myfirebasejavaproject.ActivitiesNew.HomeCooker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.AdaptersNew.HomeCooker.MainAdapter;
import com.example.myfirebasejavaproject.AdaptersNew.HomeCooker.SubFoodAdapter;
import com.example.myfirebasejavaproject.AdaptersNew.HomeCooker.dialogsAdapter.DialogAddSubFoodAdapter;
import com.example.myfirebasejavaproject.AdaptersNew.HomeCooker.dialogsAdapter.DialogUpdateSubFoodAdapter;
import com.example.myfirebasejavaproject.ModelsNew.Sub_food_Model;
import com.example.myfirebasejavaproject.ModelsNew.UserHelperClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hom_cooker_sub_food_Activity extends AppCompatActivity implements DialogAddSubFoodAdapter.DialogListener, DialogUpdateSubFoodAdapter.DialogListener {

    String Mainfoodid,MainFoodName, _KEY;
    ImageView backIcon;
    DatabaseReference reference;
    private TextView txtTitle;
    private FloatingActionButton floatingActionButton;
    private Button button;
    private RecyclerView sub_food_recycler;
    private SubFoodAdapter mAdapter;
    List<Sub_food_Model> mDatalist;
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
        setContentView(R.layout.activity_hom_cooker_sub_food_);

        getData();
        backIcon = findViewById(R.id.backIcon);
        txtTitle = findViewById(R.id.toolbar_title);
        txtTitle.setText(MainFoodName);
        sub_food_recycler = findViewById(R.id.subFoodRecycler);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        floatingActionButton = findViewById(R.id.addSubFood);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAddMainFood();
            }
        });
        getHomeCookerData();


        reference = FirebaseDatabase.getInstance().getReference("HomeCooker");

        setRecyclerView();

    }
    private void getData(){
        SharedPreferences editor = getSharedPreferences("mainFood", Context.MODE_PRIVATE);
        Mainfoodid = editor.getString("MainFoodId","");
      MainFoodName = editor.getString("MainFoodName","");
    }

    private void setRecyclerView(){
        getData();
        mDatalist = new ArrayList<>();
        sub_food_recycler.setHasFixedSize(true);
        sub_food_recycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SubFoodAdapter(this, mDatalist);
        sub_food_recycler.setAdapter(mAdapter);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("HomeCooker").child(_KEY).child("MainFood").child(Mainfoodid).child("SubFood");
        usersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // for (DataSnapshot datas : dataSnapshot.getChildren()) {
//                    Main_food_model model = datas.getValue(Main_food_model.class);
//                    model.setMainFoodId(datas.getKey());
                //int count =   (int) dataSnapshot.getChildrenCount() ;
                Sub_food_Model model = dataSnapshot.getValue(Sub_food_Model.class);
                model.setSubFoodId(dataSnapshot.getKey());


                mDatalist.add(model);
                //mAdapter.notifyItemInserted(count);
                mAdapter.notifyDataSetChanged();

                //}

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                SharedPreferences prefs = getSharedPreferences(getItemClickPosition, MODE_PRIVATE);
                int postion = prefs.getInt("position", 0);
                Sub_food_Model model = dataSnapshot.getValue(Sub_food_Model.class);
                model.setSubFoodId(dataSnapshot.getKey());

                mDatalist.set(postion, model);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Sub_food_Model model = dataSnapshot.getValue(Sub_food_Model.class);
                model.setSubFoodId(dataSnapshot.getKey());
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

    public void openDialogAddMainFood() {
        DialogAddSubFoodAdapter adapter = new DialogAddSubFoodAdapter();
        adapter.show(getSupportFragmentManager(), "Sub Food Dialog");

    }
    private void getHomeCookerData() {
        SharedPreferences prefs = getSharedPreferences(UserHelperClass.shared, MODE_PRIVATE);
        _KEY = prefs.getString("homeCookerId", null);

    }

    @Override
    public void applySubFood(String subFoodName, String subFoodprice, String subFooDesc) {
        SharedPreferences editor = getSharedPreferences("mainFood",Context.MODE_PRIVATE);
        String mainFoodId = editor.getString("MainFoodId","");


        Sub_food_Model model = new Sub_food_Model(subFoodName, subFoodprice, subFooDesc);
//        reference.child(_KEY).push().setValue(model);
        reference.child(_KEY).child("MainFood").child(mainFoodId).child("SubFood").push().setValue(model);
        Toast.makeText(this, "Sub Food Added Sucessfully", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void updateSubFood(String subFoodName, String subFoodprice, String subFooDesc) {

        SharedPreferences ref2 = getSharedPreferences("mySubFoodId", Context.MODE_PRIVATE);
        String subFoodId = ref2.getString("subFoodId", null);
        final DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("HomeCooker");
        SharedPreferences prefs = getSharedPreferences("mainFood", MODE_PRIVATE);
        mainFoodid = prefs.getString("MainFoodId", null);

        Map<String, Object> updatedValue = new HashMap<>();
        String link = _KEY + "/MainFood/" + mainFoodid + "/SubFood/" + subFoodId + "/";
        Sub_food_Model model = new Sub_food_Model(subFoodName, subFoodprice, subFooDesc);
        updatedValue.put(link, model);
        ref1.updateChildren(updatedValue);
        Toast.makeText(this, "Value Updated", Toast.LENGTH_SHORT).show();
    }
}
