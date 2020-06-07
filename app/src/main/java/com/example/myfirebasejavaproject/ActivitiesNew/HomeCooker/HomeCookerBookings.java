package com.example.myfirebasejavaproject.ActivitiesNew.HomeCooker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.AdaptersNew.HomeCooker.BookingsAdapter;
import com.example.myfirebasejavaproject.ModelsNew.Appointment_Model;
import com.example.myfirebasejavaproject.ModelsNew.UserHelperClass;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeCookerBookings extends AppCompatActivity {

    RecyclerView appointmentRecycler;
    List<Appointment_Model> mDatalist;
    ExpandableLayout expandable__layout;
    DatabaseReference refrence;
    String _KEY;
    ImageView backButton;

     static List<String> usersAppointmentIds;
     static int placedOrdersSize =0;
     static int check=0;
     BookingsAdapter mAdapter;
     static String cookerNames="";
     static String staticQuantity="";
     static String  staticPrice="";
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cooker_appointments);
        backButton = findViewById(R.id.cartBackIcon);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        expandable__layout = findViewById(R.id.expandable_layout);
//        expandable__layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                expandable__layout.collapse();
//            }
//        });
         editor = getSharedPreferences("placedOrderIds",MODE_PRIVATE).edit();

        appointmentRecycler = findViewById(R.id.OrdersRecyclerView);
//        Date currentTime = Calendar.getInstance().getTime();
//        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss aa");
//        LocalDateTime now = LocalDateTime.now();
//
//       // DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
//        Date dateobj = new Date();
        DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
        String Time = dateFormat.format(new Date());


        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        usersAppointmentIds = new ArrayList<>();


        setRecyclerView();

        //getPlacedOrders();


       // Toast.makeText(this, Time, Toast.LENGTH_SHORT).show();
//        setRecyclerView();



    }

    private void getPlacedOrders(){
        int soii = UserHelperClass.idds.size();
        String c = "";
        String d = UserHelperClass.idds.get(0);
        Toast.makeText(HomeCookerBookings.this, Integer.toString(soii), Toast.LENGTH_SHORT).show();
        appointmentRecycler.setHasFixedSize(true);
        appointmentRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mDatalist = new ArrayList<>();
        mAdapter = new BookingsAdapter(this, mDatalist);
        appointmentRecycler.setAdapter(mAdapter);

       // appointment_model = new Appointment_Model();
        for(int j =0;j< UserHelperClass.idds.size();j++){
            setData(UserHelperClass.idds.get(j));
        }

        //for(int i =0;i<listOfIds.size();i++){

        //for(int i=0; i < soii;i++){

//
           // }
//            refrence = UserHelperClass.path.child(UserHelperClass.idds.get(i)).child("Placed-Order");
//            refrence.addChildEventListener(new ChildEventListener() {
//                @Override
//                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                    placedOrdersSize =  (int) dataSnapshot.getChildrenCount();
//                    Appointment_Model model1 = dataSnapshot.getValue(Appointment_Model.class);
//                    appointment_model.setTotalBill(model1.getTotalBill());
//                    appointment_model.setDate(model1.getDate());
//                    appointment_model.setTime(model1.getTime());
//                    for(DataSnapshot postsnapshot : dataSnapshot.getChildren()){
//                        //int size1 =  (int) postsnapshot.getChildrenCount();
//                        Appointment_Model model2 = postsnapshot.getValue(Appointment_Model.class);
//                        appointment_model.setSubFoodId(model2.getSubFoodId());
//                        appointment_model.setQuantity(model2.getQuantity());
//                        appointment_model.setSubFoodName(model2.getSubFoodName());
//                        appointment_model.setSubFoodPrice(model2.getSubFoodPrice());
//                        String c = "";
//                        mDatalist.add(appointment_model);
//                        mAdapter.notifyDataSetChanged();
//                        break;
//
////                        for(DataSnapshot presnapshot : postsnapshot.getChildren()){
////
////                            //Appointment_Model model3 = postsnapshot.getValue(Appointment_Model.class);
//////                            Appointment_Model model2 = presnapshot.getValue(Appointment_Model.class);
////
////                        }
//                    }
//                }
//
//                @Override
//                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                }
//
//                @Override
//                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                }
//
//                @Override
//                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });

        }

        private void setData(String j){

                refrence = UserHelperClass.path.child(j).child("Placed-Order");
                refrence.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        Appointment_Model appointment_model = new Appointment_Model();
                        placedOrdersSize =  (int) dataSnapshot.getChildrenCount();
                        Appointment_Model model1 = dataSnapshot.getValue(Appointment_Model.class);
                        appointment_model.setTotalBill(model1.getTotalBill());
                        appointment_model.setDate(model1.getDate());
                        appointment_model.setTime(model1.getTime());
                        appointment_model.setAddress(model1.getAddress());
                        for(DataSnapshot postsnapshot : dataSnapshot.getChildren()){
                            //int size1 =  (int) postsnapshot.getChildrenCount();
                            String key = postsnapshot.getKey();
                            if(postsnapshot.getKey().equals("TotalBill") || postsnapshot.getKey().equals("address")
                                    || postsnapshot.getKey().equals("date")|| postsnapshot.getKey().equals("time"))
                            {
                                break;
                            }
                            Appointment_Model model2 = postsnapshot.getValue(Appointment_Model.class);
                            appointment_model.setSubFoodId(model2.getSubFoodId());
                             appointment_model.setQuantity(model2.getQuantity());
                            appointment_model.setSubFoodName(model2.getSubFoodName());
                            appointment_model.setSubFoodPrice(model2.getSubFoodPrice());
                            mDatalist.add(appointment_model);


                            String c = "";

//                            if(mDatalist.size() == 0){
//                                cookerNames += model2.getSubFoodName();
//                                staticPrice += model2.getSubFoodPrice();
//                                staticQuantity += model2.getQuantity();
//
//                                // staticQuantity += model2.getQuantity();
//                                appointment_model.setSubFoodName(cookerNames);
//                                appointment_model.setSubFoodPrice(staticPrice);
//                                appointment_model.setQuantity(staticQuantity);
//                                //appointment_model.setQuantity(staticQuantity);
//                                mDatalist.add(appointment_model);
//                            }
//                            else if (mDatalist.size() != 0)  {
//                                staticPrice += ", "+ model2.getSubFoodPrice();
//                                cookerNames += ", " + model2.getSubFoodName();
//                                staticQuantity += ", "+ model2.getQuantity();
//                                appointment_model.setSubFoodName(cookerNames);
//                                appointment_model.setSubFoodPrice(staticPrice);
//                                appointment_model.setQuantity(staticQuantity);
//                                mDatalist.set(0,appointment_model);
//
//                            }


                            //break;
//                        for(DataSnapshot presnapshot : postsnapshot.getChildren()){
//
//                            //Appointment_Model model3 = postsnapshot.getValue(Appointment_Model.class);
////                            Appointment_Model model2 = presnapshot.getValue(Appointment_Model.class);
//
//                        }
                        }
                        mAdapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



//            refrence = UserHelperClass.path.child(j).child("Placed-Order");
//            refrence.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    placedOrdersSize = (int) dataSnapshot.getChildrenCount();
//                    Appointment_Model model1 = dataSnapshot.getValue(Appointment_Model.class);
//                    appointment_model.setTotalBill(model1.getTotalBill());
//                    appointment_model.setDate(model1.getDate());
//                    appointment_model.setTime(model1.getTime());
//                    for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {
//                        //int size1 =  (int) postsnapshot.getChildrenCount();
//                        Appointment_Model model2 = postsnapshot.getValue(Appointment_Model.class);
//                        appointment_model.setSubFoodId(model2.getSubFoodId());
//                        appointment_model.setQuantity(model2.getQuantity());
//                        appointment_model.setSubFoodName(model2.getSubFoodName());
//                        appointment_model.setSubFoodPrice(model2.getSubFoodPrice());
//                        String c = "";
//                        mDatalist.add(appointment_model);
//                        mAdapter.notifyDataSetChanged();
//                        break;
//
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });

        }

        //}



    private void setRecyclerView() {

        refrence = UserHelperClass.path;

        SharedPreferences prefs = getSharedPreferences(UserHelperClass.shared, MODE_PRIVATE);
        _KEY = prefs.getString("homeCookerId", null);
           refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    UserHelperClass model = postSnapshot.getValue(UserHelperClass.class);
                    // FeaturedHelperClass model1 = postSnapshot.getValue(FeaturedHelperClass.class);
                    if (model.getType().equals("Homecooker")) {

                    } else {
//                        model.setCookerId(postSnapshot.getKey());
//                        mDatalist.add(model);

                        String userId = postSnapshot.getKey();
                        //getPlacedOrders(userId);
//                       usersAppointmentIds.add(userId);
                       UserHelperClass.idds.add(userId);
                        //UserHelperClass.idds.size();
                        //getPlacedOrders(userId);
                        //usersAppointmentIds = null;


                        //final Appointment_Model appointment_model = new Appointment_Model();
//                       DatabaseReference  refrence1 = UserHelperClass.path.child(userId).child("Placed-Order");
//                        refrence1.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                for(DataSnapshot postsnapshot : dataSnapshot.getChildren()){
//                                    appointment_model = postsnapshot.getValue(Appointment_Model.class);
//                                    String c ="";
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });


                    }
                }
                refrence.removeEventListener(this);
                getPlacedOrders();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
}
