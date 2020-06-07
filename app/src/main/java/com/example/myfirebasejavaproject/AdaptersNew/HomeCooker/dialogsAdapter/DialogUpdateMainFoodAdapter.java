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

public class DialogUpdateMainFoodAdapter extends AppCompatDialogFragment {

    private EditText MainFoodName;
    private DialogUpdateMainFoodAdapter.DialogListener listener;
    Context mContext;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        SharedPreferences prefs = getActivity().getSharedPreferences("updateMainFoodName", Context.MODE_PRIVATE);
        final String updateMainFoodNAme = prefs.getString("mainfoodName",null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.update_add_main_food_dialog,null);
        builder.setView(view)
                .setTitle("Update Main Food")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton
                        ("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String foodName = MainFoodName.getText().toString();
                        if(foodName.equals(updateMainFoodNAme)){
                            Toast.makeText(getActivity().getApplicationContext(), "Old name found", Toast.LENGTH_LONG).show();
                        }
                        else {
                            listener.updateMainFood(foodName);
                        }

                    }
                });
        MainFoodName = view.findViewById(R.id.mainFoodName);
        MainFoodName.setText(updateMainFoodNAme);
        return builder.create();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogUpdateMainFoodAdapter.DialogListener) context;
        }
        catch(ClassCastException e){
            throw new ClassCastException(context.toString() + "Must implement Dialog");
        }
    }

    public interface  DialogListener{
        void updateMainFood(String FoodName);
    }




}
