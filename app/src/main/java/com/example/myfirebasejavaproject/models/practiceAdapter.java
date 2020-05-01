package com.example.myfirebasejavaproject.models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirebasejavaproject.common.practiceDetailActivity;
import com.example.myfirebasejavaproject.Networks.Utils;
import com.example.myfirebasejavaproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class practiceAdapter  extends RecyclerView.Adapter<practiceAdapter.MyViewHolder> {


    public static final String USERV_KEY = "user_key";
    private Context content;
    private List<practiceModel> mDataList;

    public practiceAdapter(Context content, List<practiceModel> mDataList) {
        this.content = content;
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(content).inflate(R.layout.practice_card_design,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       final practiceModel model = mDataList.get(position);
        holder.title.setText(model.getTitle() + "|" + model.getDescription());

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = model.getUid();
                Intent intent =  new Intent(content, practiceDetailActivity.class);
                intent.putExtra(USERV_KEY,uid);
                content.startActivity(intent);
            }
        });

        holder.title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String uid = model.getUid();
                Task<Void> voidTask = Utils.removeUser(uid);
                voidTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(content, "User Removed from database...", Toast.LENGTH_SHORT).show();
                    }
                });


                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title,desc;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textviewpractice);
        }
    }
}
