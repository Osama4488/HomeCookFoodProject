package com.example.myfirebasejavaproject.adapters.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.models.FeaturedHelperClass;

import java.util.ArrayList;

public class MostViewedAdapter extends RecyclerView.Adapter<MostViewedAdapter.MVViewHolder> {

    ArrayList<FeaturedHelperClass> mostviewLocations;

    public MostViewedAdapter(ArrayList<FeaturedHelperClass> mostviewLocations) {
        this.mostviewLocations = mostviewLocations;
    }

    @NonNull
    @Override
    public MVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.most_viewed_card_design,parent,false);
        MVViewHolder mvViewHolder = new MVViewHolder(view);
        return mvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MVViewHolder holder, int position) {
        FeaturedHelperClass helperClass = mostviewLocations.get(position);
        holder.mvImage.setImageResource(helperClass.getImage());
        holder.mvTitle.setText(helperClass.getTitle());
        holder.mvDescription.setText(helperClass.getDescription());
    }

    @Override
    public int getItemCount() {
        return mostviewLocations.size();
    }


    public static class MVViewHolder extends  RecyclerView.ViewHolder{

        ImageView mvImage;
        TextView mvTitle,mvDescription;


        public MVViewHolder(@NonNull View itemView) {
            super(itemView);

            mvImage = itemView.findViewById(R.id.mv_image);
            mvTitle = itemView.findViewById(R.id.mv_title);
            mvDescription = itemView.findViewById(R.id.mv_desc);

        }

    }
}
