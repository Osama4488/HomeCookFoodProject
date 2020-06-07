package com.example.myfirebasejavaproject.ActivitiesNew.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.AdaptersNew.User.cartAdapter;
import com.example.myfirebasejavaproject.ModelsNew.Appointment_Model;
import com.example.myfirebasejavaproject.ModelsNew.Cart_Model;
import com.example.myfirebasejavaproject.ModelsNew.UserHelperClass;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    static int TotalQuantity=0;
    static boolean checkQantity = false;
    static int TotalBill;
    int size;
    ElegantNumberButton btn;
    RelativeLayout billLayout;
    RecyclerView cartRecyclerView;
    List<Cart_Model> mDatalist;
    List<Integer> postion_list;
    private cartAdapter mAdapter;
    DatabaseReference refrence;
    SharedPreferences prefs;
    String _KEY;
    TextView showQuanity,showBill;
    HashMap<String,Cart_Model> mList;
    RelativeLayout btnPlaceOrder;
    List<Integer> keys;
    List<Integer> quantityKeys;
    static int totalBill;
    Appointment_Model apointmentModel;
    List<String> cartIdList;
    ImageView cartBackIconn;
    HashMap<Integer,String> quantityList;
    HashMap<Integer,String> priceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        btn = findViewById(R.id.elegantButton);
        billLayout = findViewById(R.id.billLayout);
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        showQuanity = findViewById(R.id.showQuanity);
        showBill = findViewById(R.id.showBill);
        mList = new HashMap<>();
        keys = new ArrayList<>();
        quantityKeys = new ArrayList<>();
        cartIdList = new ArrayList<>();
        quantityList = new HashMap<>();
        priceList = new HashMap<>();
        btnPlaceOrder = findViewById(R.id.billLayout);
        cartBackIconn = findViewById(R.id.cartBackIcon);
        postion_list = new ArrayList<>();
        cartBackIconn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //startActivity(new Intent(CartActivity.this,Menu_Profile.class));
            }
        });

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for ( String key : mList.keySet() ) {
                    keys.add(Integer.valueOf(key));

                }
                int j =0;
                 size = keys.size();
                 String placedOrderId;
                refrence = UserHelperClass.path.child(_KEY).child("Placed-Order").push();
                //refrence.child(_KEY).child("Placed-Order").push();
                    for(int i=0; i <mList.size();i++){


                            Cart_Model model = new Cart_Model();
                            //model = mList.get(Integer.toString(i));
                            model = mList.get(Integer.toString(keys.get(i)));

                            if(model != null){
                                 apointmentModel = new Appointment_Model();

                                totalBill +=  Integer.valueOf(model.getSubFoodPrice());
                               // apointmentModel.setSubFoodPrice(model.getSubFoodPrice());
                                //apointmentModel.setSubFoodName(model.getSubFoodName());
                                //apointmentModel.setQuantity(model.getQuantity());
                                DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
                                String Time = dateFormat.format(new Date());
                                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                apointmentModel.setDate(currentDate);
                                apointmentModel.setTime(Time);
                                SharedPreferences editor = getSharedPreferences("clickedProfile",Context.MODE_PRIVATE);
                                String cookerId = editor.getString("uid","");
                                apointmentModel.setCookerId(cookerId);
                                apointmentModel.setTotalBill(Integer.toString(TotalBill));
                                SharedPreferences prefs = getSharedPreferences(UserHelperClass.shared, MODE_PRIVATE);
                                String usersAddress = prefs.getString("address", null);
                                apointmentModel.setAddress(usersAddress);
                                //refrence.child(_KEY).child("Placed-Order").push().child(model.getSubFoodId()).setValue(model);
                                String subfoodid = model.getSubFoodId();
                                refrence.child(model.getSubFoodId()).setValue(model);
//                                refrence.child("Placed-Order").child(subfoodid).setValue(model);
                                //refrence.child("Placed-Order").push().setValue(model);
                                Toast.makeText(CartActivity.this, "Order Placed Sucessfully", Toast.LENGTH_SHORT).show();

                            }
                            else {

                                continue;
                            }


                    }
                if(mList.size() == 0){
                    Toast.makeText(CartActivity.this, "Select something to Order", Toast.LENGTH_SHORT).show();
                }
                else if(mList.size() == size){
                    //refrence = UserHelperClass.path.child(_KEY).child("Placed-Order").child();
                    refrence.child("date").setValue(apointmentModel.getDate());
                    refrence.child("time").setValue(apointmentModel.getTime());
                    refrence.child("TotalBill").setValue(apointmentModel.getTotalBill());
                    refrence.child("address").setValue(apointmentModel.getAddress());

                    for(int i =0;i < cartIdList.size();i++){
                         UserHelperClass.path.child(_KEY).child("Cart").child(cartIdList.get(i)).setValue(null);
                    }


                    Toast.makeText(CartActivity.this, "Your Order has been Placed", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(CartActivity.this, UserDashboard.class));
                }
            }
        });
        setCartRecyclerView();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

//        SharedPreferences prefs = getSharedPreferences("billList", Context.MODE_PRIVATE);
//        String jsonData = prefs.getString("payment","");
//        String postion = prefs.getString("position","");
//        if(jsonData != ""){
//            Gson gson = new Gson();
//            //String jsonData = prefs.getString("0",null);
//            //HomeCookerTimingModel model =   gson.fromJson(jsonData, HomeCookerTimingModel.class);
//
//            Type type = new TypeToken<HashMap<Integer,String>>() {
//            }.getType();
//            HashMap<Integer,String> students = gson.fromJson(jsonData, type);
//            showBill.setText(students.get(postion));
//        }




//        btn.setOnClickListener(new ElegantNumberButton.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String num = btn.getNumber();
//                billLayout.setVisibility(View.VISIBLE);
//                Toast.makeText(CartActivity.this, num, Toast.LENGTH_SHORT).show();
//            }
//        });


    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String ItemName = intent.getStringExtra("item");
            String qty = intent.getStringExtra("quantity");
            String price = intent.getStringExtra("price");
            String originalPrice = intent.getStringExtra("originalPrice");
            String subFooDID = intent.getStringExtra("SubFoodid");
            String positon = intent.getStringExtra("postionClicked");
            String moneyToRemove = intent.getStringExtra("priceToRemove");
            String quantityToRemove = intent.getStringExtra("quantityToRemove");
            cartIdList.add(intent.getStringExtra("CartId"));
            String cartid = intent.getStringExtra("CartId");
              String qty1 = intent.getStringExtra("quantityByElegant");
              String address = intent.getStringExtra("Address");
            PlaceOrder(ItemName,qty,price,subFooDID,positon,moneyToRemove,quantityToRemove,cartid,originalPrice,qty1,address);
            Toast.makeText(CartActivity.this,positon +" "+qty + " "+price ,Toast.LENGTH_SHORT).show();
        }
    };


    private void PlaceOrder(String subFoodName,String qty,String price,String SubFoodId,String position,String moneyToRemove,String quantityToRemove,String cartID,String originalPrice,String originalQuantity,String address) {

//        Cart_Model model = new Cart_Model();
//        model.setSubFoodId(SubFoodId);
//        model.setSubFoodName(subFoodName);
//        model.setQuantity(qty);
//        model.setSubFoodPrice(originalPrice);
//        model.setAddress(address);
        Cart_Model model = new Cart_Model();
        model.setSubFoodId(SubFoodId);
        model.setSubFoodName(subFoodName);
        model.setQuantity(qty);
        model.setSubFoodPrice(price);
        model.setAddress(address);

        if (subFoodName.equals("")) {
            mList.remove(position);
            postion_list.remove(position);
//            TotalQuantity -= LastAmount ;
//            TotalBill -=LastAmount;
            cartIdList.remove(cartID);
            TotalQuantity -= Integer.valueOf(quantityToRemove);
            //TotalBill -=Integer.valueOf(moneyToRemove);
            TotalBill -= Integer.valueOf(price);
            showQuanity.setText(Integer.toString(TotalQuantity));
            showBill.setText(Integer.toString(TotalBill));

            quantityList.remove(Integer.valueOf(position));
            priceList.remove(Integer.valueOf(position));
        } else {


            quantityList.put(Integer.valueOf(position), qty);
            priceList.put(Integer.valueOf(position), price);
            postion_list.add(Integer.valueOf(position));
            mList.put(position, model);
            //TotalQuantity = 0;
            TotalBill = 0;



            for ( int key : quantityList.keySet() ) {
                quantityKeys.add(Integer.valueOf(key));
                break;

            }

            for(int j=0;j < quantityList.size();j++){
                // String b =  quantityList.get(position);
//                if(quantityList.get(position).equals(qty)){

                String b =  Integer.toString(quantityKeys.get(j));
                if(b.equals(position)){
                     String puranivalue = quantityList.get(Integer.valueOf(position));
                     if(TotalQuantity == Integer.valueOf(originalQuantity) && checkQantity == false){

                     }
                    else if(puranivalue.equals(qty) && TotalQuantity > Integer.valueOf(originalQuantity)){
                         TotalQuantity -= Integer.valueOf(qty);
                         if(TotalQuantity == 1){
                             checkQantity = false;
                         }
                     }
                     else{

                         TotalQuantity += Integer.valueOf(qty);
                         if(TotalQuantity == 4){
                             TotalQuantity = 3;
                         }
                         checkQantity = true;
                     }
                }

            }

            showQuanity.setText(Integer.toString(TotalQuantity));
            //  showBill.setText(Integer.toString(TotalBill));
            String c = "";



            /////////////


//            for (int i = 0; i < postion_list.size(); i++) {
//
//
//                for (int j = 0; j < quantityList.size(); j++) {
//
//                    TotalQuantity += Integer.valueOf(quantityList.get(Integer.valueOf(position)));
//                    TotalBill += Integer.valueOf(priceList.get(Integer.valueOf(position)));
//
//                }
//                showQuanity.setText(Integer.toString(TotalQuantity));
//                showBill.setText(Integer.toString(TotalBill));
//                String c = "";
//
//
//            }



            //  Is algo ko likhne mai bht time laga or fazool tha easy logic laga ke kam aadhe ghnte mai hogya :D
            //  well not really :p




//
//            if(quantityList.size() == 1){
//                for(int i=Integer.valueOf(position); i<= quantityList.size(); i++){
//                    if(quantityList.get(i) == null){
//                        continue;
//                    }
//                    TotalQuantity += Integer.valueOf(quantityList.get(i));
//                    TotalBill += Integer.valueOf(priceList.get(i));
//                }
//
//                showQuanity.setText(Integer.toString(TotalQuantity));
//                showBill.setText(Integer.toString(TotalBill));
//                String c = "";
//
//
//            }
//
//           else if(quantityList.size() > 1) {
//               int j = 0;
//               for(int y = postion_list.get(j); y < postion_list.size();y++){
//
//                   for(int i=0; i< quantityList.size(); i++){
////
//                    TotalQuantity += Integer.valueOf(quantityList.get(i));
//                    TotalBill += Integer.valueOf(priceList.get(i));
//                }
//
//                showQuanity.setText(Integer.toString(TotalQuantity));
//                showBill.setText(Integer.toString(TotalBill));
//                String c = "";
//                j++;
//
//               }


//                for(int i=0; i< quantityList.size(); i++){
////
////                    TotalQuantity += Integer.valueOf(quantityList.get(i));
////                   // TotalBill += Integer.valueOf(priceList.get(i));
////                }


        }




        


        String c ="";

    }
    private void setCartRecyclerView(){
        prefs = getSharedPreferences(UserHelperClass.shared, MODE_PRIVATE);
        _KEY = prefs.getString("homeCookerId","");
        mDatalist = new ArrayList<>();
        cartRecyclerView.setHasFixedSize(true);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new cartAdapter(this, mDatalist);
        cartRecyclerView.setAdapter(mAdapter);
        refrence = UserHelperClass.path.child(_KEY).child("Cart");
        refrence.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Cart_Model model = dataSnapshot.getValue(Cart_Model.class);
                model.setCartId(dataSnapshot.getKey());


                mDatalist.add(model);
                //mAdapter.notifyItemInserted(count);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Cart_Model model = dataSnapshot.getValue(Cart_Model.class);
                model.setCartId(dataSnapshot.getKey());
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
}
