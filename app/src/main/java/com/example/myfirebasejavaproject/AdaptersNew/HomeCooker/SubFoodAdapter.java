package com.example.myfirebasejavaproject.AdaptersNew.HomeCooker;

import android.content.Context;
import android.content.DialogInterface;
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

import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.AdaptersNew.HomeCooker.dialogsAdapter.DialogUpdateSubFoodAdapter;
import com.example.myfirebasejavaproject.ModelsNew.Sub_food_Model;
import com.example.myfirebasejavaproject.ModelsNew.UserHelperClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SubFoodAdapter extends RecyclerView.Adapter<SubFoodAdapter.MyViewHolder> {
    private Context mContext;
    private List<Sub_food_Model> mDatalist;
    public static final String SHARED_PREF_NAME = "myMainFood";
    String mainFoodid,subFoodid;
    String _KEY,MainFoodId;
    String getItemClickPosition = "clicked";
    String uid;

    public SubFoodAdapter(Context mContext, List<Sub_food_Model> mDatalist) {
        this.mDatalist = mDatalist;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public SubFoodAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_food_card_design,parent,false);
        SubFoodAdapter.MyViewHolder featuredViewHolder = new SubFoodAdapter.MyViewHolder(view);
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
    public void onBindViewHolder(@NonNull SubFoodAdapter.MyViewHolder holder, final int position) {

        SharedPreferences prefs = mContext.getSharedPreferences(UserHelperClass.shared,Context.MODE_PRIVATE);
        _KEY = prefs.getString("homeCookerId", null);

        final Sub_food_Model helperClass = mDatalist.get(position);
        // final Sub_food_Model subfoodmodel = mDatalist.get(position);
        holder.textView.setText(helperClass.getSubFoodName());

        // holder.delete.setTag(position);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getMainFoodId();
                uid = helperClass.getSubFoodId();
                AlertDialog dialog = new AlertDialog.Builder(mContext)
                        .setTitle("")
                        .setMessage("You sure you want to delete this?")

                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase.getInstance().getReference("HomeCooker").child(_KEY).child("MainFood").child(MainFoodId)
                                        .child("SubFood").child(uid).setValue(null);
                                Toast.makeText(mContext, "Item removed from database..", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancel",null)
                        .show();
//
//
            }
        });
        holder.cardview.setTag(position);
//        holder.cardview.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                int postion =    (Integer) v.getTag();
//                SharedPreferences.Editor editor1 = mContext.getSharedPreferences(getItemClickPosition,Context.MODE_PRIVATE).edit();
//                editor1.putInt("position",position);
//                editor1.commit();
//                mainFoodid= helperClass.getMainFoodId();
//                savePrefs(mainFoodid);
//                String name = helperClass.getName();
//                AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                DialogUpdateMainFoodAdapter adapter = new DialogUpdateMainFoodAdapter();
//                adapter.show(activity.getSupportFragmentManager(), "Update Main Food Dialog");
//                SharedPreferences.Editor editor = mContext.getSharedPreferences("updateMainFoodName", Context.MODE_PRIVATE).edit();
//                editor.putString("mainfoodName", name);
//                editor.apply();
//                return true;
//            }
//        });

//        holder.cardview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                int postion =    (Integer) v.getTag();
//                SharedPreferences.Editor editor1 = mContext.getSharedPreferences(getItemClickPosition,Context.MODE_PRIVATE).edit();
//                editor1.putInt("position",position);
//                editor1.commit();
//                subFoodid= helperClass.getSubFoodId();
//                savePrefs(subFoodid);
//                String name = helperClass.getSubFoodName();
//                AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                DialogUpdateSubFoodAdapter adapter = new DialogUpdateSubFoodAdapter();
//                adapter.show(activity.getSupportFragmentManager(), "Update Sub Food");
//            }
//        });

//        holder.cardview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//                SharedPreferences prefs = mContext.getSharedPreferences(UserHelperClass.shared,Context.MODE_PRIVATE);
//                _KEY = prefs.getString("homeCookerId", "");
//                subFoodid= helperClass.getSubFoodId();
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


        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                SharedPreferences prefs = mContext.getSharedPreferences(UserHelperClass.shared,Context.MODE_PRIVATE);
                _KEY = prefs.getString("homeCookerId", null);
                subFoodid= helperClass.getSubFoodId();
                getMainFoodId();
                SharedPreferences.Editor editor1 = mContext.getSharedPreferences(getItemClickPosition,Context.MODE_PRIVATE).edit();
                editor1.putInt("position",position);
                editor1.commit();
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("HomeCooker");
                reference.child(_KEY).child("MainFood").child(MainFoodId).child("SubFood").child(subFoodid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            //for(DataSnapshot data : dataSnapshot.getChildren()){


                                //subFoodid = data.getKey();
                                Sub_food_Model model = dataSnapshot.getValue(Sub_food_Model.class);
                                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                                DialogUpdateSubFoodAdapter adapter = new DialogUpdateSubFoodAdapter();
                                adapter.show(activity.getSupportFragmentManager(), "Sub Food Dialog");
                                SharedPreferences.Editor editor = mContext.getSharedPreferences("updateSubFoodName", Context.MODE_PRIVATE).edit();
                                editor.putString("subfoodName", model.getSubFoodName());
                                editor.putString("subfoodPrice", model.getSubFoodPrice());
                                editor.putString("subfoodDesc", model.getSubFoodDesc());
                                editor.apply();
                                saveSubFoodId(subFoodid);
                                savePrefs(mainFoodid);


                                //String foodname = model.getSubFoodName();

                               // break;
                            //}

//
//                            String subFoodDesc = dataSnapshot.child("subFoodDesc").getValue().toString();
                        }
                        else {
//                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                            DialogAddSubFoodAdapter adapter = new DialogAddSubFoodAdapter();
//                            adapter.show(activity.getSupportFragmentManager(), "Sub Food Dialog");
//
//                            savePrefs(mainFoodid);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });




        }

    private void saveSubFoodId(String id) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences("mySubFoodId", Context.MODE_PRIVATE).edit();
        editor.putString("subFoodId", id);
        editor.apply();
    }
    private void savePrefs(String id) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences("mainFoodID", Context.MODE_PRIVATE).edit();
        editor.putString("mainFoodId", id);
        editor.apply();
    }

    private void getMainFoodId(){
        SharedPreferences editor = mContext.getSharedPreferences("mainFood",Context.MODE_PRIVATE);
        MainFoodId = editor.getString("MainFoodId","");
        editor.getString("MainFoodName","");
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
