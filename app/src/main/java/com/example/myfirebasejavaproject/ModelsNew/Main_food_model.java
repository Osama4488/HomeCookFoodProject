package com.example.myfirebasejavaproject.ModelsNew;

import androidx.annotation.Nullable;

public class Main_food_model {
    String name,mainFoodId;


    public Main_food_model() {
    }

    public Main_food_model( String name) {

        this.name = name;
    }



    public String getMainFoodId() {
        return mainFoodId;
    }

    public void setMainFoodId(String mainFoodId) {
        this.mainFoodId = mainFoodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Main_food_model){
            Main_food_model model  = (Main_food_model) obj;
            return this.mainFoodId.equals(model.getMainFoodId());
        }
        else {
            return false;
        }
    }
}
