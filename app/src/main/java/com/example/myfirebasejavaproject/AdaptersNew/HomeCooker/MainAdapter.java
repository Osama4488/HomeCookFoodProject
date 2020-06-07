package com.example.myfirebasejavaproject.AdaptersNew.HomeCooker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirebasejavaproject.ActivitiesNew.HomeCooker.Hom_cooker_sub_food_Activity;
import com.example.myfirebasejavaproject.ModelsNew.Main_food_model;
import com.example.myfirebasejavaproject.ModelsNew.UserHelperClass;
import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.AdaptersNew.HomeCooker.dialogsAdapter.DialogUpdateMainFoodAdapter;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private Context mContext;
    private List<Main_food_model> mDatalist;
    public static final String SHARED_PREF_NAME = "myMainFood";
    String mainFoodid,subFoodid;
    String _KEY;
    String getItemClickPosition = "clicked";
    String uid;

    public MainAdapter(Context mContext, List<Main_food_model> mDatalist) {
        this.mDatalist = mDatalist;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_food_card_design,parent,false);
        MyViewHolder featuredViewHolder = new MyViewHolder(view);
        return featuredViewHolder;
    }



//    ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
//        @Override
//        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//            return false;
//        }
//
//        @Override
//        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//
//        }
//    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        SharedPreferences prefs = mContext.getSharedPreferences(UserHelperClass.shared,Context.MODE_PRIVATE);
        _KEY = prefs.getString("homeCookerId", null);

        final Main_food_model helperClass = mDatalist.get(position);
       // final Sub_food_Model subfoodmodel = mDatalist.get(position);
        holder.textView.setText(helperClass.getName());

           // holder.delete.setTag(position);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


             uid = helperClass.getMainFoodId();
             AlertDialog dialog = new AlertDialog.Builder(mContext)
                     .setTitle("")
                     .setMessage("You sure you want to delete this?")

                     .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             FirebaseDatabase.getInstance().getReference("HomeCooker").child(_KEY).child("MainFood").child(uid).setValue(null);
                             Toast.makeText(mContext, "Item removed from database..", Toast.LENGTH_SHORT).show();
                         }
                     }).setNegativeButton("Cancel",null)
                     .show();

//
//
            }
        });
        holder.cardview.setTag(position);
        holder.cardview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                int postion =    (Integer) v.getTag();
                SharedPreferences.Editor editor1 = mContext.getSharedPreferences(getItemClickPosition,Context.MODE_PRIVATE).edit();
                editor1.putInt("position",position);
                editor1.commit();
                mainFoodid= helperClass.getMainFoodId();
                savePrefs(mainFoodid);
                String name = helperClass.getName();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                DialogUpdateMainFoodAdapter adapter = new DialogUpdateMainFoodAdapter();
                adapter.show(activity.getSupportFragmentManager(), "Update Main Food Dialog");
                SharedPreferences.Editor editor = mContext.getSharedPreferences("updateMainFoodName", Context.MODE_PRIVATE).edit();
                editor.putString("mainfoodName", name);
                editor.apply();
                return true;
            }
        });

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid = helperClass.getMainFoodId();
                SharedPreferences.Editor editor = mContext.getSharedPreferences("mainFood",Context.MODE_PRIVATE).edit();
                editor.putString("MainFoodId",uid);
                editor.putString("MainFoodName",helperClass.getName());
                editor.commit();
                mContext.startActivity(new Intent(mContext, Hom_cooker_sub_food_Activity.class));
                //int postion =    (Integer) v.getTag();
//                SharedPreferences.Editor editor1 = mContext.getSharedPreferences(getItemClickPosition,Context.MODE_PRIVATE).edit();
//                editor1.putInt("position",position);
//                editor1.commit();
//                mainFoodid= helperClass.getMainFoodId();
//                savePrefs(mainFoodid);
//                String name = helperClass.getName();
//                AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                DialogAddSubFoodAdapter adapter = new DialogAddSubFoodAdapter();
//                adapter.show(activity.getSupportFragmentManager(), "Add Sub Food");
            }
        });

//        holder.cardview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//                SharedPreferences prefs = mContext.getSharedPreferences(UserHelperClass.shared,Context.MODE_PRIVATE);
//                _KEY = prefs.getString("homeCookerId", null);
//                mainFoodid= helperClass.getMainFoodId();
//                SharedPreferences.Editor editor1 = mContext.getSharedPreferences(getItemClickPosition,Context.MODE_PRIVATE).edit();
//                editor1.putInt("position",position);
//                editor1.commit();
//                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("HomeCooker");
//                reference.child(_KEY).child("MainFood").child(mainFoodid).child("SubFood").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists()){
//                            for(DataSnapshot data : dataSnapshot.getChildren()){
//
//
//                                subFoodid = data.getKey();
//                                Sub_food_Model model = data.getValue(Sub_food_Model.class);
//                                AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                                DialogUpdateSubFoodAdapter adapter = new DialogUpdateSubFoodAdapter();
//                                adapter.show(activity.getSupportFragmentManager(), "Sub Food Dialog");
//                                SharedPreferences.Editor editor = mContext.getSharedPreferences("updateSubFoodName", Context.MODE_PRIVATE).edit();
//                                editor.putString("subfoodName", model.getSubFoodName());
//                                editor.putString("subfoodPrice", model.getSubFoodPrice());
//                                editor.putString("subfoodDesc", model.getSubFoodDesc());
//                                editor.apply();
//                                saveSubFoodId(subFoodid);
//                                savePrefs(mainFoodid);
//
//                                //String foodname = model.getSubFoodName();
//
//
//                            }
//
////
////                            String subFoodDesc = dataSnapshot.child("subFoodDesc").getValue().toString();
//                        }
//                        else {
//                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                            DialogAddSubFoodAdapter adapter = new DialogAddSubFoodAdapter();
//                            adapter.show(activity.getSupportFragmentManager(), "Sub Food Dialog");
//
//                            savePrefs(mainFoodid);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//
//            }
//        });




    }

    private void saveSubFoodId(String id) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences("mySubFoodId", Context.MODE_PRIVATE).edit();
        editor.putString("subFoodId", id);
        editor.apply();
    }
    private void savePrefs(String id) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences("myMainFood", Context.MODE_PRIVATE).edit();
        editor.putString("mainfoodId", id);
        editor.apply();
    }

    @Override
    public int getItemCount() {
        return mDatalist.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        CardView cardview;
        ImageView delete;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            textView = itemView.findViewById(R.id.main_food_name);
            cardview = itemView.findViewById(R.id.card_viewMainFood);
            delete = itemView.findViewById(R.id.deleteMainFood);

        }
    }
}
