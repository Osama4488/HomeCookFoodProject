package com.example.myfirebasejavaproject.AdaptersNew.HomeCooker.dialogsAdapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myfirebasejavaproject.R;

public class DialogAddSubFoodAdapter extends AppCompatDialogFragment {

    private EditText subFoodName,subFoodPrice,subFoodDesc;
    private  DialogAddSubFoodAdapter.DialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_sub_food_dialog,null);
        builder.setView(view)
                .setTitle("Add Sub Food")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String subfoodName = subFoodName.getText().toString();
                        String subfoodprice = subFoodPrice.getText().toString();
                        String subfoodDesc = subFoodDesc.getText().toString();
                        listener.applySubFood(subfoodName,subfoodprice,subfoodDesc);
                    }
                });
        subFoodName = view.findViewById(R.id.subFoodName);
        subFoodPrice = view.findViewById(R.id.subFoodprice);
        subFoodDesc = view.findViewById(R.id.subFoodDesc);
        return builder.create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogAddSubFoodAdapter.DialogListener) context;
        }
        catch(ClassCastException e){
            throw new ClassCastException(context.toString() + "Must implement Dialog");
        }
    }

    public interface  DialogListener{
        void applySubFood(String subFoodName,String subFoodprice,String subFooDesc);
    }
}
