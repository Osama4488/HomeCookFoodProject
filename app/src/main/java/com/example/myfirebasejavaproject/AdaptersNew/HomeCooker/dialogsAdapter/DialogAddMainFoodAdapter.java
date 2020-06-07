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

public class DialogAddMainFoodAdapter extends AppCompatDialogFragment  {

    private EditText MainFoodName;
    private DialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_main_food_dialog,null);
        builder.setView(view)
                .setTitle("Add Main Food")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String foodName = MainFoodName.getText().toString();
                        listener.applyText(foodName);
                    }
                });
        MainFoodName = view.findViewById(R.id.mainFoodName);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        }
        catch(ClassCastException e){
            throw new ClassCastException(context.toString() + "Must implement Dialog");
        }
    }

    public interface  DialogListener{
        void applyText(String FoodName);
    }
}
