package com.example.myfirebasejavaproject.AdaptersNew.HomeCooker;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirebasejavaproject.R;
import com.example.myfirebasejavaproject.ModelsNew.Appointment_Model;
import com.example.myfirebasejavaproject.ModelsNew.Cart_Model;

import java.util.HashMap;
import java.util.List;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.MyViewHolder>{

    Context mContext;
    List<Appointment_Model> mDataList;
    HashMap<Integer,Cart_Model> billList;
    Appointment_Model model1;
    SharedPreferences.Editor edit;
    static String elegantButtonNumber ;
    int check = 0;

    public BookingsAdapter(Context context, List<Appointment_Model> mDatalist) {
        this.mDataList = mDatalist;
        this.mContext = context;

    }

    @NonNull
    @Override
    public BookingsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.booking_card_design,parent,false);
        BookingsAdapter.MyViewHolder appointmentViewHolder = new BookingsAdapter.MyViewHolder(view);
        return appointmentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BookingsAdapter.MyViewHolder holder, final int position) {
        final Appointment_Model model = mDataList.get(position);



        holder.subFoodName.setText(model.getSubFoodName());
        holder.time.setText(model.getTime());
        holder.date.setText(model.getDate());

        holder.quantity.setText(model.getQuantity());
        holder.SubFoodPrice.setText(model.getSubFoodPrice());
        holder.title.setText("Items");
        holder.totalbill.setText(model.getTotalBill());
        holder.address.setText(model.getAddress());
        boolean isExpanded = mDataList.get(position).isExpanded();
        holder.wholeCartItem.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {


        TextView subFoodName,SubFoodPrice,title,quantity,date,time,address,totalbill;
        RelativeLayout wholeCartItem;
        private ImageView arrow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            totalbill = itemView.findViewById(R.id.totalbill);
            subFoodName = itemView.findViewById(R.id.appointmentName);
            SubFoodPrice = itemView.findViewById(R.id.price);
            title = itemView.findViewById(R.id.appointmentTitle);
            quantity = itemView.findViewById(R.id.quantityy);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            address = itemView.findViewById(R.id.BookingsAddress);
            wholeCartItem = itemView.findViewById(R.id.expandableLayout);
            arrow = itemView.findViewById(R.id.arrowRotate);
            arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Appointment_Model model = mDataList.get(getAdapterPosition());
                    model.setExpanded(!model.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                    expand();
                   // collapse();
//                    if(check == 1){
//
//                        check = 0;
//                    }
//                    else {
//                        expand();
//                        check = 1;
//                    }


            }
            });
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Appointment_Model model = mDataList.get(getAdapterPosition());
//                    model.setExpanded(!model.isExpanded());
//                    notifyItemChanged(getAdapterPosition());
                    //collapse();
                }
            });


        }



        public void expand() {
            animateExpand();
        }


        public void collapse() {
            animateCollapse();
        }
        private void animateExpand() {
            RotateAnimation rotate =
                    new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            rotate.setFillAfter(true);
            arrow.startAnimation(rotate);
        }

        private void animateCollapse() {
            RotateAnimation rotate =
                    new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            rotate.setFillAfter(true);
            arrow.startAnimation(rotate);
        }

    }

}

