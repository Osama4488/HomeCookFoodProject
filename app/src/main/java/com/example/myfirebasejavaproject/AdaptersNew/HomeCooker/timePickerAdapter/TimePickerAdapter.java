package com.example.myfirebasejavaproject.AdaptersNew.HomeCooker.timePickerAdapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.ModelsNew.HomeCookerTimingModel;
import com.example.myfirebasejavaproject.ModelsNew.UserHelperClass;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TimePickerAdapter  extends RecyclerView.Adapter<TimePickerAdapter.Myviewholder> {


    private List<String> dayList;
    private Map<String, String> dayHashList;
    private List<HomeCookerTimingModel> CookerTimingsModelList;
    Context context;
    Map<Integer, String> a = new HashMap<>();
    HomeCookerTimingModel model;
    private List<HomeCookerTimingModel> mDatalist = new ArrayList<>();
    Set<HomeCookerTimingModel> set = new HashSet<HomeCookerTimingModel>();
    String _KEY;
    DatabaseReference refrence;

    public TimePickerAdapter(List<String> dayList, List<HomeCookerTimingModel> cookerTimingsModelList, Context context) {
        this.dayList = dayList;
        CookerTimingsModelList = cookerTimingsModelList;
        this.context = context;
        setUpDaysList();
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_cooker_timing_schedule, parent, false);
        return new TimePickerAdapter.Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {

        SharedPreferences settings = context.getSharedPreferences("cookerTimings", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
        final String dayName = dayList.get(position);
        holder.dayCB.setText(dayList.get(position));


        for (int i = 0; i < CookerTimingsModelList.size(); i++) {
            try {
                if (dayName.equalsIgnoreCase(dayHashList.get(CookerTimingsModelList.get(i).getHomeCookerTimingDay()))) {
                    holder.dayCB.setChecked(true);

                    SimpleDateFormat _24HFormat = new SimpleDateFormat("HH:mm");
                    SimpleDateFormat _12HFormat = new SimpleDateFormat("hh:mm a");

                    String[] splitingOpenDateTime = CookerTimingsModelList.get(i).getHomeCookerOpenTime().split("T");
                    String[] splitingCloseDateTime = CookerTimingsModelList.get(i).getHomeCookerCloseTime().split("T");

                    Date openTime = _24HFormat.parse(splitingOpenDateTime[1]);
                    String finalOpenTime = _12HFormat.format(openTime);

                    Date closeTime = _24HFormat.parse(splitingCloseDateTime[1]);
                    String finalCloseTime = _12HFormat.format(closeTime);

                    holder.openingTimeBN.setText(finalOpenTime);
                    holder.closingTimeBN.setText(finalCloseTime);
                }
            } catch (Exception e) {
                //Log.e(TAG, "onBindViewHolder: " + e);
            }
        }


        holder.dayCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.dayCB.isChecked()) {
                    holder.openingTimeBN.setEnabled(false);
                    holder.closingTimeBN.setEnabled(false);
                }
                else {
                    holder.openingTimeBN.setEnabled(true);
                    holder.closingTimeBN.setEnabled(true);
                }
            }
        });

        holder.openingTimeBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int calHour = c.get(Calendar.HOUR_OF_DAY);
                int calMinute = c.get(Calendar.MINUTE);
                model = new HomeCookerTimingModel();
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                int po = position;
                                Calendar datetime = Calendar.getInstance();
                                datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                datetime.set(Calendar.MINUTE, minute);

                                int hour = hourOfDay % 12;

                                String _12HourFormatTime = String.format("%02d:%02d %s", hour == 0 ? 12 : hour, minute, hourOfDay < 12 ? "am" : "pm");
                                getList(_12HourFormatTime, position, "OpenTime",dayName);
                                holder.openingTimeBN.setText(_12HourFormatTime);
                            }
                        }, calHour, calMinute, false);
                timePickerDialog.show();
            }
        });


        holder.closingTimeBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int calHour = c.get(Calendar.HOUR_OF_DAY);
                int calMinute = c.get(Calendar.MINUTE);
                model = new HomeCookerTimingModel();
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                Calendar datetime = Calendar.getInstance();
                                datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                datetime.set(Calendar.MINUTE, minute);

                                int hour = hourOfDay % 12;
                                String _12HourFormatTime = String.format("%02d:%02d %s", hour == 0 ? 12 : hour, minute, hourOfDay < 12 ? "am" : "pm");
                                //String b = holder.closingTimeBN.getText().toString();
                                getList(_12HourFormatTime, position, "CloseTime",dayName);
                                holder.closingTimeBN.setText(_12HourFormatTime);
                            }
                        }, calHour, calMinute, false);
                timePickerDialog.show();
            }
        });

    }


    private String getKey() {
        SharedPreferences prefs = context.getSharedPreferences(UserHelperClass.shared, Context.MODE_PRIVATE);
        _KEY = prefs.getString("homeCookerId", null);
        return _KEY;
    }

    private void getList(String time, int position, String tag,String day1) {
        a.put(position, time);
//             String opentime = mDatalist.get(position).getHomeCookerOpenTime();
//             String closetime = mDatalist.get(position).getHomeCookerCloseTime();
//
//
//        if(mDatalist.get(position).getHomeCookerOpenTime() != null){
//            model.setHomeCookerCloseTime(time);
//            mDatalist.set(position,model);
//        }
//        else {
//            model.setHomeCookerOpenTime(time);
//            mDatalist.set(position,model);
//        }
        setUpDaysList();
        String po = Integer.toString(position);
        String day = dayHashList.get(po);
        if (tag == "OpenTime") {

//                    if(position >= mDatalist.size()){
//
//                    }
            //boolean a =mDatalist.get(position).getHomeCookerCloseTime().isEmpty();
//            if(mDatalist.contains(day)){
//
//            }
//            else {
//                model.setHomeCookerTimingDay(day);
//                model.setHomeCookerOpenTime(time);
//                mDatalist.add(model);
//            }

            try {
                String k = mDatalist.get(position).getHomeCookerCloseTime();
                model.setHomeCookerOpenTime(time);
                model.setHomeCookerCloseTime(k);
                model.setHomeCookerTimingDay(day);
                mDatalist.set(position, model);
            } catch (Exception e) {
                model.setHomeCookerTimingDay(day);
                model.setHomeCookerOpenTime(time);
                mDatalist.add(model);
                //mDatalist.add(position,model);
            }

//                    catch(Exception e) {

//                        model.setHomeCookerTimingDay(day);
//                        model.setHomeCookerOpenTime(time);
//                        mDatalist.add(model);
//                            mDatalist.get(position).setHomeCookerOpenTime(time);


        }


        //}
        else {
            try {
                String k = mDatalist.get(position).getHomeCookerOpenTime();
                model.setHomeCookerCloseTime(time);
                model.setHomeCookerOpenTime(k);
                model.setHomeCookerTimingDay(day);
                mDatalist.set(position, model);
            } catch (Exception e) {
                model.setHomeCookerTimingDay(day);
                model.setHomeCookerCloseTime(time);
                 mDatalist.add(model);
              //  mDatalist.add(position,model);
            }

////                    model.setHomeCookerCloseTime(time);
//                     model.setHomeCookerTimingDay(day);
//                    mDatalist.get(position).setHomeCookerCloseTime(time);

        }


//            List<Cars> cars= new ArrayList<Cars>();
//            cars.add(a);
//            cars.add(b);
//            cars.add(c);
//            cars.add(d);
//
//            gson = new Gson();
//            String jsonCars = gson.toJson(cars);
//            Log.d("TAG","jsonCars = " + jsonCars);


            //ArrayList<HomeCookerTimingModel> fileList = new ArrayList<HomeCookerTimingModel>();
            Gson gson = new Gson();
            String jsonData = gson.toJson(mDatalist);
            SharedPreferences.Editor editor = context.getSharedPreferences("cookerTimings",Context.MODE_PRIVATE).edit();
           editor.putString("0", jsonData);
           editor.commit();
//
//                    getKey();
//                    for(int i =0; i< mDatalist.size();i++){
//                    HomeCookerTimingModel model = new HomeCookerTimingModel(mDatalist.get(i).getHomeCookerOpenTime(),mDatalist.get(i).getHomeCookerCloseTime(),mDatalist.get(i).getHomeCookerTimingDay());
//                        intent.putExtra("0",mDatalist.get(0));

//                        refrence = FirebaseDatabase.getInstance().getReference("HomeCooker");
//                        refrence.child(_KEY).child("Timings").setValue(model);



//
    }



    // mDatalist.size();


    @Override
    public int getItemCount() {
        return dayList.size();
    }

    private void setUpDaysList() {
        dayHashList = new HashMap<>();
        dayHashList.put("0", "MONDAY");
        dayHashList.put("1", "TUESDAY");
        dayHashList.put("2", "WEDNESDAY");
        dayHashList.put("3", "THURSDAY");
        dayHashList.put("4", "FRIDAY");
        dayHashList.put("5", "SATURDAY");
        dayHashList.put("6", "SUNDAY");
    }

//

    public class Myviewholder extends RecyclerView.ViewHolder {

        private Button openingTimeBN, closingTimeBN;
        private CheckBox dayCB;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            openingTimeBN = itemView.findViewById(R.id.item_openingTimeBN);
            closingTimeBN = itemView.findViewById(R.id.item_closingTimeBN);
            dayCB = itemView.findViewById(R.id.parlour_schedule_dayCB);

        }
    }
}
