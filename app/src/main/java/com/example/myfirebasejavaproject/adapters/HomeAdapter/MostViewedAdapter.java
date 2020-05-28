package com.example.myfirebasejavaproject.adapters.HomeAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.models.FeaturedHelperClass;
import com.example.myfirebasejavaproject.models.UserHelperClass;
import com.example.myfirebasejavaproject.user.Menu_Profile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MostViewedAdapter extends RecyclerView.Adapter<MostViewedAdapter.MVViewHolder> {

    List<UserHelperClass> mostviewLocations;
    Context mContext;
    String uid;

    public MostViewedAdapter(List<UserHelperClass> mostviewLocations, Context context) {
        this.mostviewLocations = mostviewLocations;
        mContext = context;
    }

    @NonNull
    @Override
    public MVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.most_viewed_card_design, parent, false);
        MVViewHolder mvViewHolder = new MVViewHolder(view);
        return mvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MVViewHolder holder, int position) {
        final UserHelperClass featuredHelperClass = mostviewLocations.get(position);
//        FeaturedHelperClass model = featuredLocations.get(position);
        uid = featuredHelperClass.getCookerId();


        // holder.image.setImageResource(featuredHelperClass.getImage());
        holder.title.setText(featuredHelperClass.getName());
        Picasso.get().load(featuredHelperClass.getmImageUrl())
                .fit()
                .centerCrop()
                .into(holder.image);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid = featuredHelperClass.getCookerId();

                String name = featuredHelperClass.getName();
                SharedPreferences.Editor editor = mContext.getSharedPreferences("clickedProfile", Context.MODE_PRIVATE).edit();
                editor.putString("uid", uid);
                editor.putString("imageurl", featuredHelperClass.getmImageUrl());
                editor.commit();
                mContext.startActivity(new Intent(mContext, Menu_Profile.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mostviewLocations.size();
    }


    public static class MVViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title, desc;
        CardView cardView;

        public MVViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.mv_image);
            title = itemView.findViewById(R.id.mv_title);
            desc = itemView.findViewById(R.id.mv_desc);
            cardView = itemView.findViewById(R.id.card_viewMostViewd);
        }
    }
}
