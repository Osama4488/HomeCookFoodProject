package com.example.myfirebasejavaproject.AdaptersNew.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.ModelsNew.Cart_Model;
import com.example.myfirebasejavaproject.ModelsNew.UserHelperClass;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class cartAdapter  extends RecyclerView.Adapter<cartAdapter.MyViewHolder>{
    String paise;
    Context mContext;
    List<Cart_Model> mDataList;
    HashMap<Integer,Cart_Model> billList;
    HashMap<Integer,String> quantityList;
    Cart_Model model1;
    String _KEY;
     String elegantButtonNumber ;
    public cartAdapter(Context context, List<Cart_Model> mDatalist) {
        this.mDataList = mDatalist;
        this.mContext = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cart_item_layout,parent,false);
        cartAdapter.MyViewHolder cartViewHolder = new cartAdapter.MyViewHolder(view);
        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
       final Cart_Model model = mDataList.get(position);
        SharedPreferences prefs = mContext.getSharedPreferences(UserHelperClass.shared,Context.MODE_PRIVATE);
        _KEY = prefs.getString("homeCookerId", null);

        holder.subFoodName.setText(model.getSubFoodName());
        holder.SubFoodPrice.setText(model.getSubFoodPrice());
        holder.homeCookerName.setText(model.getHomeCookerName());
        billList = new HashMap<>();
        quantityList = new HashMap<>();

        holder.deletItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String cartId = model.getCartId();

                FirebaseDatabase.getInstance().getReference("HomeCooker").child(_KEY).child("Cart").child(cartId).setValue(null);
                Toast.makeText(mContext, "Cart Item Removed..", Toast.LENGTH_SHORT).show();

            }
        });


        holder.cartCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.elegantNumberButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {

        if(holder.check.isChecked()){


            String carTId = model.getCartId();
            elegantButtonNumber = holder.elegantNumberButton.getNumber();
            int quantity = Integer.valueOf(holder.elegantNumberButton.getNumber());
            int price =  Integer.valueOf(model.getSubFoodPrice()) * quantity;
            String a = Integer.toString(price);
            //  int price = Integer.valueOf(model.getSubFoodPrice());
            holder.SubFoodPrice.setText(Integer.toString(price));
            //price = Integer.valueOf(model.getSubFoodPrice());
            model1 = new Cart_Model();
            model1.setQuantity(Integer.toString(quantity));
            model1.setSubFoodPrice(Integer.toString(price));
            model1.setSubFoodName(model.getSubFoodName());

            billList.put(holder.getAdapterPosition(),model1);
            //quantityList.put(holder.getAdapterPosition(),holder.elegantNumberButton.getNumber());

            String ItemName = model.getSubFoodName();
//            String qty = Integer.toString(quantity);
            String qty = Integer.toString(1);
           // paise = Integer.toString(price);
            String subFoodId  = model.getSubFoodId();
            String postion = Integer.toString(position);
            Intent intent = new Intent("custom-message");
            intent.putExtra("quantity",elegantButtonNumber);
            intent.putExtra("item",ItemName);
//            intent.putExtra("price",paise);
            intent.putExtra("price",Integer.toString(price));
            intent.putExtra("SubFoodid",subFoodId);
            intent.putExtra("postionClicked",postion);
            intent.putExtra("CartId",carTId);
            intent.putExtra("originalPrice",model.getSubFoodPrice());
            intent.putExtra("postionClicked",postion);
            intent.putExtra("quantityByElegant",holder.elegantNumberButton.getNumber());
            intent.putExtra("originalPrice",model.getSubFoodPrice());
            intent.putExtra("Address",model.getAddress());
            intent.putExtra("homeCOkkername",model.getHomeCookerName());
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
        }



            }
        });
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.check.isChecked()){

                    String carTId = model.getCartId();
                    elegantButtonNumber = holder.elegantNumberButton.getNumber();
                    int quantity = Integer.valueOf(holder.elegantNumberButton.getNumber());
                    int price =  Integer.valueOf(model.getSubFoodPrice()) * quantity;
                    //  int price = Integer.valueOf(model.getSubFoodPrice());
                    holder.SubFoodPrice.setText(Integer.toString(price));
                    model1 = new Cart_Model();
                    model1.setQuantity(Integer.toString(quantity));
                    model1.setSubFoodPrice(Integer.toString(price));
                    model1.setSubFoodName(model.getSubFoodName());

                    billList.put(holder.getAdapterPosition(),model1);

                    String ItemName = model.getSubFoodName();
                    String qty = Integer.toString(quantity);
                     paise = Integer.toString(price);
                    String subFoodId  = model.getSubFoodId();
                    String postion = Integer.toString(position);
                    Intent intent = new Intent("custom-message");
                    intent.putExtra("quantity",elegantButtonNumber);
                    intent.putExtra("item",ItemName);
                    intent.putExtra("price",paise);
                    intent.putExtra("SubFoodid",subFoodId);
                    intent.putExtra("postionClicked",postion);
                    intent.putExtra("CartId",carTId);
                    intent.putExtra("originalPrice",model.getSubFoodPrice());
                    intent.putExtra("quantityByElegant",holder.elegantNumberButton.getNumber());
                    intent.putExtra("Address",model.getAddress());
                    intent.putExtra("homeCOkkername",model.getHomeCookerName());
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

                }
                else {
                    String carTId = model.getCartId();
                    int quantity = Integer.valueOf(holder.elegantNumberButton.getNumber());
                    int price =  Integer.valueOf(model.getSubFoodPrice()) * quantity;
                    Intent intent = new Intent("custom-message");
                    intent.putExtra("quantity","");
                    intent.putExtra("item","");
//                    intent.putExtra("price","");
                    intent.putExtra("price", Integer.toString(price));
                    intent.putExtra("quantityToRemove",holder.elegantNumberButton.getNumber());
//                    intent.putExtra("priceToRemove",model.getSubFoodPrice());
                    intent.putExtra("priceToRemove",model.getSubFoodPrice());
                    intent.putExtra("postionClicked",Integer.toString(position));
                    intent.putExtra("CartId",carTId);
                    intent.putExtra("Address","");
                    intent.putExtra("homeCOkkername",model.getHomeCookerName());
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);


                }


//                if(holder.check.isChecked()){
//
//                    billList.put(holder.getAdapterPosition(),model1);
//                    //billList.values();
//                      Gson gson = new Gson();
////                    String jsonData = gson.toJson(billList);
////                    SharedPreferences.Editor editor = mContext.getSharedPreferences("billList",Context.MODE_PRIVATE).edit();
////                    editor.putString("payment", jsonData);
////                    editor.putString("position",Integer.toString(holder.getAdapterPosition()));
////                    editor.commit();
//                    //edit = mContext.getSharedPreferences("billList",billList);
//                    //holder.wholeCartItem.setEnabled(false);
//                }
//                else {
//                    billList.remove(holder.getAdapterPosition());
//                    //holder.wholeCartItem.setEnabled(true);
//                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {


        TextView subFoodName,SubFoodPrice,homeCookerName;
        RelativeLayout wholeCartItem;
        CheckBox check;
        ElegantNumberButton elegantNumberButton;
        CardView cartCardview;
        ImageView deletItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            subFoodName = itemView.findViewById(R.id.subFoodname);
            SubFoodPrice = itemView.findViewById(R.id.subFoodPrice);
            wholeCartItem = itemView.findViewById(R.id.CartWholeItem);
            check = itemView.findViewById(R.id.checkBox);
            elegantNumberButton = itemView.findViewById(R.id.elegantButton);
            cartCardview = itemView.findViewById(R.id.cartCardView);
            deletItem = itemView.findViewById(R.id.deleteCartItem);
            homeCookerName = itemView.findViewById(R.id.homecookername);
        }
    }

}
