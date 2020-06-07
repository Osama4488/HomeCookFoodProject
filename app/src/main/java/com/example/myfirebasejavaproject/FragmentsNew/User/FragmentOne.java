package com.example.myfirebasejavaproject.FragmentsNew.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.AdaptersNew.User.menu_item_adapter;
import com.example.myfirebasejavaproject.ModelsNew.Main_food_model;
import com.example.myfirebasejavaproject.ModelsNew.Sub_food_Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentOne extends Fragment {


    RecyclerView menuItemsrecycler;
    menu_item_adapter mAdapter;
    RecyclerView.Adapter adapter;
    List<Sub_food_Model> mDatalist;
    DatabaseReference refrence;
    String _KEY;
    String mainFoodid;
    Map<String,List<Sub_food_Model>> list;
    HashMap<String,List<Sub_food_Model>> list1;
    public FragmentOne() {
        //Log.i("Fragment Check", "Fragment One Created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hello, container, false);
        menuItemsrecycler = view.findViewById(R.id.menu_items_recyclerView);
        setRecycerView();
        return view;

    }

    private void setRecycerView(){

        SharedPreferences editor = getActivity().getSharedPreferences("subFoodItemClick",Context.MODE_PRIVATE);
        String clikedItem = editor.getString("subFoodName","");
        menuItemsrecycler.setHasFixedSize(true);
        menuItemsrecycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        getKey();
        mDatalist = new ArrayList<>();
       // list = new Map<String, List<Sub_food_Model>>();
        list1 = new HashMap<>();
        mAdapter = new menu_item_adapter(mDatalist,getActivity().getApplicationContext());
        menuItemsrecycler.setAdapter(mAdapter);
        refrence = FirebaseDatabase.getInstance().getReference("HomeCooker").child(_KEY).child("MainFood");
        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Main_food_model item = postSnapshot.getValue(Main_food_model.class);
                    String name = item.getName();
                   // mainFoodid = postSnapshot.getKey();
                    SharedPreferences editor = getActivity().getSharedPreferences("subFoodItemClick", Context.MODE_PRIVATE);
                    String check = editor.getString("subFoodName", "");
                    if (check.equals(name)) {

                        for (DataSnapshot presnapshot : postSnapshot.getChildren()) {
                            for (DataSnapshot last : presnapshot.getChildren()) {
                                Sub_food_Model model = last.getValue(Sub_food_Model.class);
                                model.setSubFoodId(last.getKey());
                                mDatalist.add(model);

                            }
                            list1.put(name,mDatalist);
                            mAdapter.notifyDataSetChanged();
                           // mDatalist.clear();
                            String c = "";
                        }
                        break;
                    }

                }
                mAdapter = new menu_item_adapter(mDatalist,getActivity().getApplicationContext());
                menuItemsrecycler.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();









                    // FeaturedHelperClass model1 = postSnapshot.getValue(FeaturedHelperClass.class);
                    //if(model.getType().equals("User")){

                    //}
                    //else {
//                        model.setCookerId(postSnapshot.getKey());
//                        mDatalist.add(model);
                    //}




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
               // Toast.makeText(mContext, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
//        ArrayList<Sub_food_Model> list = new ArrayList<>();
//        list.add(new Sub_food_Model("asd","100","asda"));
//        list.add(new Sub_food_Model("asdads","100","asda"));
//        list.add(new Sub_food_Model("fgfgfg","100","asda"));
//        adapter = new menu_item_adapter(list,getActivity().getApplicationContext());
//        menuItemsrecycler.setAdapter(adapter);
    }

    private void getKey(){
        SharedPreferences prefs = getActivity().getSharedPreferences("clickedProfile",Context.MODE_PRIVATE);
        _KEY = prefs.getString("uid","");
    }





}