package com.example.myfirebasejavaproject.Networks;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public  class Utils {
    public static Task<Void> removeUser(String id){
        Task<Void> task = FirebaseDatabase.getInstance().getReference("practice").child(id)
                .setValue(null);
        return task;
    }
}
