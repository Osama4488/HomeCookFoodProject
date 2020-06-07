package com.example.myfirebasejavaproject.AdaptersNew.HomeCooker.dialogsAdapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myfirebasejavaproject.R;

public class DialogUpdateSubFoodAdapter extends AppCompatDialogFragment {

    private EditText MainFoodName;
    private DialogUpdateSubFoodAdapter.DialogListener listener;
    Context mContext;
    private EditText subFoodName,subFoodPrice,subFoodDesc;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        SharedPreferences prefs = getActivity().getSharedPreferences("updateSubFoodName", Context.MODE_PRIVATE);
        final String updateSubFoodNAme = prefs.getString("subfoodName",null);
        final String updateSubFoodPrice = prefs.getString("subfoodPrice",null);
        final String updateSubFoodDesc = prefs.getString("subfoodDesc",null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_sub_food_dialog,null);
        builder.setView(view)
                .setTitle("Update Sub Food")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton
                        ("Update", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String foodName = subFoodName.getText().toString();
                                String foodPrice = subFoodPrice.getText().toString();
                                String foodDEsc = subFoodDesc.getText().toString();

                                if(foodName.equals(updateSubFoodNAme) && foodPrice.equals(updateSubFoodPrice) &&  foodDEsc.equals(updateSubFoodDesc)){
                                    Toast.makeText(getActivity().getApplicationContext(), "Old name found", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    String subfoodName = subFoodName.getText().toString();
                                    String subfoodprice = subFoodPrice.getText().toString();
                                    String subfoodDesc = subFoodDesc.getText().toString();
                                    listener.updateSubFood(foodName,foodPrice,foodDEsc);
                                }

                            }
                        });
        subFoodName = view.findViewById(R.id.subFoodName);

        subFoodPrice = view.findViewById(R.id.subFoodprice);

        subFoodDesc = view.findViewById(R.id.subFoodDesc);
        subFoodName.setText(updateSubFoodNAme);
        subFoodDesc.setText(updateSubFoodDesc);

        subFoodPrice.setText(updateSubFoodPrice);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogUpdateSubFoodAdapter.DialogListener) context;
        }
        catch(ClassCastException e){
            throw new ClassCastException(context.toString() + "Must implement Dialog");
        }
    }

    public interface  DialogListener{
        void updateSubFood(String subFoodName,String subFoodprice,String subFooDesc);
    }

}
