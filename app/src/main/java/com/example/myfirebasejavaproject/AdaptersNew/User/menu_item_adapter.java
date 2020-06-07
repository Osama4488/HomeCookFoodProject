package com.example.myfirebasejavaproject.AdaptersNew.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.ModelsNew.Sub_food_Model;
import com.example.myfirebasejavaproject.ModelsNew.UserHelperClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class menu_item_adapter extends RecyclerView.Adapter<menu_item_adapter.menuViewHolder> {

     public boolean checkForNoItemInCart = false;
    List<Sub_food_Model> menuItems;
    String uid;
    Context mContext;
    int counter =0;
    DatabaseReference reference;
    String _KEY;
    static int i=0;

    public menu_item_adapter(List<Sub_food_Model> menuItems, Context context) {
        this.menuItems = menuItems;
        mContext = context;
    }

    public menu_item_adapter() {
    }

    @NonNull
    @Override
    public menu_item_adapter.menuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cooker_menu_items_layout, parent, false);
        menu_item_adapter.menuViewHolder menuViewHolder = new  menu_item_adapter.menuViewHolder(view);
        parent.removeAllViews();
        return menuViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final menu_item_adapter.menuViewHolder holder, int position) {

//        if(position > 0){
//            position -=1;
//        }
        SharedPreferences editor = mContext.getSharedPreferences(UserHelperClass.shared, MODE_PRIVATE);
         _KEY = editor.getString("homeCookerId","");

//         SharedPreferences editor = mContext.getSharedPreferences("clickedProfile",Context.MODE_PRIVATE);
//               _KEY =  editor.getString("uid","");
            int postion = holder.getLayoutPosition();
            int postion1 = holder.getAdapterPosition();
        final Sub_food_Model model = menuItems.get(holder.getAdapterPosition());
        final String name = model.getSubFoodName();
       // final String mainFoodid = model.getMainFoodId();
//        FeaturedHelperClass model = featuredLocations.get(position);
        //uid = featuredHelperClass.getCookerId();
         holder.name.setText(name);
        holder.price.setText(model.getSubFoodPrice());
        holder.description.setText(model.getSubFoodDesc());

        holder.bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = _KEY;
                //reference = UserHelperClass.path.child(_KEY);


                final Sub_food_Model Items = new Sub_food_Model();
                Items.setSubFoodId(model.getSubFoodId());
                Items.setSubFoodName(model.getSubFoodName());
                Items.setSubFoodPrice(model.getSubFoodPrice());

//                reference.child("Cart").orderByChild("SubFoodName").equalTo("U1EL5623")("value",snapshot => {
//                if (snapshot.exists()){
//      const userData = snapshot.val();
//                    console.log("exists!", userData);
//                }
//});


                reference = UserHelperClass.path.child(_KEY).child("Cart");

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                             Long g =  dataSnapshot.getChildrenCount();
                             int count = g.intValue();
                             i+=1;
                                Sub_food_Model modelCheck = postSnapshot.getValue(Sub_food_Model.class);
                                if(name.equals(modelCheck.getSubFoodName())){
                                    Toast.makeText(mContext, "Item Already Added in Cart", Toast.LENGTH_SHORT).show();
                                    i =0;
                                    checkForNoItemInCart = true;
                                    break;
                                }
                                else if(count == i){
                                        reference = UserHelperClass.path;
                                        reference.child(_KEY).child("Cart").push().setValue(Items);
                                    checkForNoItemInCart = true;
                                    Toast.makeText(mContext, "Item Added To Cart", Toast.LENGTH_LONG).show();
                                    i =0;
                                    break;
                                }

                        }
                        if(checkForNoItemInCart == false ){
                            reference = UserHelperClass.path;
                            reference.child(_KEY).child("Cart").push().setValue(Items);
                            Toast.makeText(mContext, "Item Added To Cart", Toast.LENGTH_LONG).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





//                holder.itemCounter.setVisibility(View.VISIBLE);
//                 counter+=1;
//                 holder.itemCounter.setText(Integer.toString(counter));
            }
        });
       // holder.priceGroup.removeAllViews();
        //menuItems.clear();



    }


    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public static class menuViewHolder extends RecyclerView.ViewHolder {


        Button btn;
        TextView name,price,description;
        CardView subFoodItemClick;
        TextView itemCounter;
        Button bookButton;

        public menuViewHolder(@NonNull View itemView) {
            super(itemView);

            price = itemView.findViewById(R.id.sub_food_price);
            name = itemView.findViewById(R.id.sub_food_name);
            description = itemView.findViewById(R.id.sub_food_description);
            subFoodItemClick = itemView.findViewById(R.id.subFoodItemCardView);
            bookButton = itemView.findViewById(R.id.bookButton);

            //itemCounter = itemView.findViewById(R.id.itemCounter);

        }
    }
}
