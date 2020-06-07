package com.example.myfirebasejavaproject.ActivitiesNew.HomeCooker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.AdaptersNew.HomeCooker.timePickerAdapter.TimePickerAdapter;
import com.example.myfirebasejavaproject.ModelsNew.HomeCookerTimingModel;
import com.example.myfirebasejavaproject.ModelsNew.UserHelperClass;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SetTimingsCookerProfile extends AppCompatActivity {

//    implements TimePickerDialog.OnTimeSetListener

    private Button monStartTime, monEndTime;
    private String setDay = "Days";
    private CheckBox moncheckbox;
    List<HomeCookerTimingModel> model;
    RecyclerView recyclerView;
    TimePickerAdapter mAdapter;
    List<String> dayNames;
    Button saveTime;
    DatabaseReference refrence;
    String _KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_timings_cooker_profile);
        recyclerView = findViewById(R.id.homeCookerTimeRecycler);
        dayNames = new ArrayList<>();
        model = new ArrayList<>();
        saveTime = findViewById(R.id.saveTime);
        setRecyclerView();
        saveTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("cookerTimings", Context.MODE_PRIVATE);
                String jsonData = prefs.getString("0", null);
                if (jsonData != null) {


                    Gson gson = new Gson();
                    //String jsonData = prefs.getString("0",null);
                    //HomeCookerTimingModel model =   gson.fromJson(jsonData, HomeCookerTimingModel.class);

                    Type type = new TypeToken<List<HomeCookerTimingModel>>() {
                    }.getType();
                    List<HomeCookerTimingModel> students = gson.fromJson(jsonData, type);
                   boolean check = checkValidationsForTimings(students);

                    getKey();
                    refrence = FirebaseDatabase.getInstance().getReference("HomeCooker");
                    refrence.child(_KEY).child("Timings").setValue(students);

                    Toast.makeText(SetTimingsCookerProfile.this, "Data Exists", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SetTimingsCookerProfile.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        Set<String> set = myScores.getStringSet("key", null);

//Set the values


//        moncheckbox = findViewById(R.id.Moncheckbox);
//        monStartTime =   findViewById(R.id.monStart);
//        monEndTime = findViewById(R.id.monEnd);
//        moncheckbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(moncheckbox.isChecked()){
//                    monStartTime.setEnabled(false);
//                    monEndTime.setEnabled(false);
//                }
//                else {
//                    monStartTime.setEnabled(true);
//                    monEndTime.setEnabled(true);
//                }
//
//            }
//        });
//        monStartTime =   findViewById(R.id.monStart);
//        monEndTime = findViewById(R.id.monEnd);
//        monStartTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogFragment timePicker = new TimePickerFragment();
//                timePicker.show(getSupportFragmentManager(),"Time Picker");
//                setDayToken("StartTime","Monday");
//            }
//        });
//        monEndTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogFragment timePicker = new TimePickerFragment();
//                timePicker.show(getSupportFragmentManager(),"Time Picker");
//                setDayToken("EndTime","Monday");
//            }
//        });

    }

    private boolean checkValidationsForTimings(List<HomeCookerTimingModel> list){

        for(int i =0; i< list.size();i++){



        }
        return false;
    }

    private String getKey() {
        SharedPreferences prefs = getSharedPreferences(UserHelperClass.shared, Context.MODE_PRIVATE);
        _KEY = prefs.getString("homeCookerId", null);
        return _KEY;
    }


    private void setRecyclerView() {
        dayNames = new ArrayList<>();
        dayNames.add("Monday");
        dayNames.add("Tuesday");
        dayNames.add("Wednesday");
        dayNames.add("Thursday");
        dayNames.add("Friday");
        dayNames.add("Saturday");
        dayNames.add("Sunday");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TimePickerAdapter(dayNames, model, SetTimingsCookerProfile.this);
        recyclerView.setAdapter(mAdapter);

    }

    private void setDayToken(String time, String day) {
        SharedPreferences.Editor editor = getSharedPreferences(setDay, MODE_PRIVATE).edit();
        editor.putString("day", day);
        editor.putString("time", time);
        editor.commit();
    }


//    @Override
//    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
////        String AM_PM ;
////        if(hourOfDay < 12) {
////            AM_PM = "AM";
////        } else {
////            AM_PM = "PM";
////        }
//
//      //   getDayToken();
////        SharedPreferences sharedPreferences = getSharedPreferences(setDay,MODE_PRIVATE);
////        sharedPreferences.getString("day", null);
////        sharedPreferences.getString("time", null);
//
//        String status = "AM";
//
//        if(hourOfDay > 11)
//        {
//            // If the hour is greater than or equal to 12
//            // Then the current AM PM status is PM
//            status = "PM";
//        }
//
//        // Initialize a new variable to hold 12 hour format hour value
//        int hour_of_12_hour_format;
//
//        if(hourOfDay > 11){
//
//            // If the hour is greater than or equal to 12
//            // Then we subtract 12 from the hour to make it 12 hour format time
//            hour_of_12_hour_format = hourOfDay - 12;
//        }
//        else {
//            hour_of_12_hour_format = hourOfDay;
//        }
//
//
//
//        // Get the calling activity TextView reference
//        TextView tv = findViewById(R.id.showMonStartTime);
//        // Display the 12 hour format time in app interface
//        tv.setText(hour_of_12_hour_format + " : " + minute + " : " + status);
//
//        //Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
//    }
}
