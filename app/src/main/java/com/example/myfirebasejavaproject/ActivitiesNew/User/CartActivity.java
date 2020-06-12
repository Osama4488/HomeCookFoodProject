package com.example.myfirebasejavaproject.ActivitiesNew.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    static int TotalQuantity=0;
    static String previousValue ="0";
    static int puraniValue =0;
    static int quantityKeyInteger = 0;
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
    HashSet<String> hashet;
    TextView showQuanity,showBill,homeCookerName;
    HashMap<String,Cart_Model> mList;
    RelativeLayout btnPlaceOrder;
    List<Integer> keys;
    List<Integer> quantityKeys;
    //HashMap<String,Integer> quantityKeysNew;
    static int totalBill;
    Appointment_Model apointmentModel;
    List<String> cartIdList;
    ImageView cartBackIconn;
    HashMap<String,String> quantityList;
    HashMap<String,String> quantityKeysNew;
    HashMap<Integer,String> priceList;
    ProgressBar progressbar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        progressbar = findViewById(R.id.progressBar);
        progressbar.setVisibility(View.GONE);
        btn = findViewById(R.id.elegantButton);
        billLayout = findViewById(R.id.billLayout);
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        showQuanity = findViewById(R.id.showQuanity);
        homeCookerName = findViewById(R.id.homecookername);
        showBill = findViewById(R.id.showBill);
        mList = new HashMap<>();
        keys = new ArrayList<>();
        quantityKeys = new ArrayList<>();
        quantityKeysNew = new HashMap<>();
        cartIdList = new ArrayList<>();
        quantityList = new HashMap<>();
        priceList = new HashMap<>();
        btnPlaceOrder = findViewById(R.id.billLayout);
        cartBackIconn = findViewById(R.id.cartBackIcon);
        postion_list = new ArrayList<>();
        hashet = new HashSet<>();
        setUpProgressBar();
        cartBackIconn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //startActivi-ty(new Intent(CartActivity.this,Menu_Profile.class));
            }
        });

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                for ( String key : mList.keySet() ) {
                    keys.add(Integer.valueOf(key));

                }
                int j =0;
                 size = keys.size();
                 String placedOrderId;
                refrence = UserHelperClass.path.child(_KEY).child("Placed-Order").push();
                //refrence.child(_KEY).child("Placed-Order").push();
                    for(int jj = 0 ; jj < mList.size();jj++){
                        Cart_Model model = new Cart_Model();
                        model = mList.get(Integer.toString(keys.get(j)));

                    }

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
                                apointmentModel.setTotalBill(Integer.toString(totalBill));
                               // apointmentModel.setTotalBill(Integer.toString(TotalBill));
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
//                else if(mList.size() == size){
                else{
                    //refrence = UserHelperClass.path.child(_KEY).child("Placed-Order").child();
                    refrence.child("date").setValue(apointmentModel.getDate());
                    refrence.child("time").setValue(apointmentModel.getTime());
                    refrence.child("TotalBill").setValue(apointmentModel.getTotalBill());
                    refrence.child("address").setValue(apointmentModel.getAddress());

                    for(int i =0;i < cartIdList.size();i++){
                         UserHelperClass.path.child(_KEY).child("Cart").child(cartIdList.get(i)).setValue(null);
                    }

                    totalBill = 0;
                    Toast.makeText(CartActivity.this, "Your Order has been Placed", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
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

            progressDialog.show();
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
              String homeCookerName =  intent.getStringExtra("homeCOkkername");
            PlaceOrder(ItemName,qty,price,subFooDID,positon,moneyToRemove,quantityToRemove,cartid,originalPrice,qty1,address,homeCookerName);
            Toast.makeText(CartActivity.this,positon +" "+qty + " "+price ,Toast.LENGTH_SHORT).show();
        }
    };


    private void PlaceOrder(String subFoodName,String qty,String price,String SubFoodId,String position,String moneyToRemove,String quantityToRemove,String cartID,String originalPrice,String originalQuantity,String address,String homeCookerName) {

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
        model.setSubFoodPrice(originalPrice);
        model.setAddress(address);
        model.setHomeCookerName(homeCookerName);

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
            progressDialog.dismiss();
        } else {

            quantityList.put(position, qty);
            priceList.put(Integer.valueOf(position), price);
            postion_list.add(Integer.valueOf(position));
            mList.put(position, model);
            //quantityKeysNew.put(position,position);
            String c = "";
            quantityKeys = new ArrayList<>();
            TotalQuantity = 0;
            TotalBill = 0;
//
//            for(int j =0; j < mList.size();j++){
//
//                    if(keys.equals(keys.get(j))){
//                        continue;
//                    }
//                    else {
//                        quantityKeysNew.put(position,mList.keySet().toString());
//                    }
//            }

            for ( String key : mList.keySet() ) {

                boolean k = keys.contains(key);
                boolean bb = hashet.contains(key);

                if(hashet.contains(key)){
                    continue;
                }
                else {
                    hashet.add(key);
                    keys.add(Integer.valueOf(key));
                }

               // quantityKeysNew.put(position,key);

            }

            for(int i =0; i< mList.size();i++){
                Cart_Model showViewModel = new Cart_Model();
                //model = mList.get(Integer.toString(i));
                showViewModel = mList.get(Integer.toString(keys.get(i)));
                TotalQuantity +=  Integer.valueOf(showViewModel.getQuantity());
                TotalBill += Integer.valueOf(showViewModel.getSubFoodPrice());
            }
            showQuanity.setText(Integer.toString(TotalQuantity));
            showBill.setText(Integer.toString(TotalBill));
            progressDialog.dismiss();
//            for(int j=0;j < quantityList.size();j++){
//
//                for(String key : quantityList.keySet()){
//                    quantityKeys.add(Integer.valueOf(key));
//                    break;
//                }
//                int index = quantityKeys.get(Integer.valueOf(j));
//                String b = quantityList.get(Integer.toString(index));
//               // String b =  quantityKeysNew.get(position);
//                if(b == null){
//                    continue;
//                }
//                //else if(b.equals(position )){
//                    String newValue = quantityList.get(position);
//                    if(TotalQuantity == Integer.valueOf(originalQuantity) && checkQantity == false){
//
//                    }
////                    else if(newValue.equals(qty) && TotalQuantity > Integer.valueOf(originalQuantity)){
////                        //TotalQuantity -= Integer.valueOf(qty);
////                        TotalQuantity -= Integer.valueOf("1");
////                        if(TotalQuantity == 1){
////                            checkQantity = false;
////                        }
////                    }
//                    else if(qty != originalQuantity && TotalQuantity > Integer.valueOf(originalQuantity)){
//                        //TotalQuantity -= Integer.valueOf(qty);
//                        TotalQuantity -= Integer.valueOf("1");
//                        if(TotalQuantity == 1){
//                            checkQantity = false;
//                        }
//                    }
//
//                    else{
//
//                        //TotalQuantity += Integer.valueOf(qty);
//
//                        puraniValue += Integer.valueOf(newValue);
//                        TotalQuantity += Integer.valueOf(newValue);
//
//                        if(Integer.valueOf(newValue) == 4){
//                            //TotalQuantity = 3;
//                            Toast.makeText(this, "You can only add 3 items per Food", Toast.LENGTH_SHORT).show();
//                        }
//                        checkQantity = true;
//
//                    }
//                //}
//
//            }


            //  showBill.setText(Integer.toString(TotalBill));
            //String c = "";






//            boolean check12 = false;
////            TotalBill = 0;
//            TotalQuantity = 0;
//            for ( String key : quantityList.keySet() ) {
//
//
//
//
//
//
//
//                String cd="";
//                for(int i = 0;i < quantityKeys.size();i++){
//                    if(quantityKeys.get(i).equals(key)){
//                        break;
//
//                    }
//                    else {
//                        continue;
//                    }
//                }
//

//                int j =  quantityKeys.get(Integer.valueOf(key));
//                if(key.equals(quantityKeys.get(Integer.valueOf(key)))){
//
//                }
//                else {
//                    break;
//                }
//                try{
//                  boolean n =   key.equals(quantityKeys.get(Integer.valueOf(position)));
//                }
//                catch(Exception e){
//
//
//                }


            }

//            for(int i =0;i < mList.size(); i++){
//
//                 String m = Integer.toString(quantityKeys.get(i));
//                Cart_Model mainModel = mList.get(m);
//                TotalQuantity += Integer.valueOf(mainModel.getQuantity());
//            }
//
//            showQuanity.setText(Integer.toString(TotalQuantity));
//
//
//


//            for(int j=0;j < quantityList.size();j++){
//                // String b =  quantityList.get(position);
////                if(quantityList.get(position).equals(qty)){
//
//                String b =  Integer.toString(quantityKeys.get(j));
//                if(b.equals(position)){
//                    String puranivalue = quantityList.get(Integer.valueOf(position));
//                    if(TotalQuantity == Integer.valueOf(originalQuantity) && checkQantity == false){
//
//                    }
//                    else if(puranivalue.equals(qty) && TotalQuantity > Integer.valueOf(originalQuantity)){
//                        TotalQuantity -= Integer.valueOf(qty);
//                        if(TotalQuantity == 1){
//                            checkQantity = false;
//                        }
//                    }
//                    else{
//
//                        TotalQuantity += Integer.valueOf(qty);
//                        if(TotalQuantity == 4){
//                            TotalQuantity = 3;
//                        }
//                        checkQantity = true;
//                    }
//                }
//
//            }


            //  showBill.setText(Integer.toString(TotalBill));
            //String c = "";



//            for ( int key : quantityList.keySet() ) {
//                quantityKeys.add(Integer.valueOf(key));
//                break;
//
//            }
//
//            for(int j=0;j < mList.size();j++) {
//                if(quantityList.get(Integer.valueOf(position)).equals(qty)) {
//                    TotalQuantity += Integer.valueOf(qty);
//
//                }
//                else if(Integer.valueOf(qty) <  Integer.valueOf(quantityList.get(position))){
//                    TotalQuantity -= Integer.valueOf(qty);
//                }
//                else {
//                    TotalQuantity += Integer.valueOf(qty);
//                }
//
//
////               Cart_Model mainModel = mList.get(position);
////               TotalQuantity +=  Integer.valueOf(mainModel.getQuantity());
////               String cc = "";
//
//
//            }



                // String b =  quantityList.get(position);
//                if(quantityList.get(position).equals(qty)){



//                if(TotalQuantity == 0){
//                TotalQuantity += Integer.valueOf(qty);
//            }
//            else if(TotalQuantity > Integer.valueOf(qty)){
//
//            }









       // }
        //showQuanity.setText(Integer.toString(TotalQuantity));




    }
    private void setUpProgressBar() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading please wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void setCartRecyclerView(){
        progressDialog.show();
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
        progressDialog.dismiss();
    }

//
//    class Downloader extends AsyncTask<Void,Integer,Integer>{
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressbar.setMax(100);
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//            progressbar.setProgress(values[0]);
//        }
//
//        @Override
//        protected Integer doInBackground(Void... voids) {
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Integer integer) {
//            super.onPostExecute(integer);
//        }
//    }
}

