package com.example.myfirebasejavaproject.ModelsNew;

import androidx.annotation.Nullable;

public class Sub_food_Model {

    String subFoodName,subFoodPrice,subFoodDesc,subFoodId,mainFoodId;

    public Sub_food_Model(String subFoodName, String subFoodPrice, String subFoodDesc) {
        this.subFoodName = subFoodName;
        this.subFoodPrice = subFoodPrice;
        this.subFoodDesc = subFoodDesc;
    }

    public Sub_food_Model() {
    }



    public String getSubFoodId() {
        return subFoodId;
    }

    public void setSubFoodId(String subFoodId) {
        this.subFoodId = subFoodId;
    }

    public String getSubFoodName() {
        return subFoodName;
    }

    public void setSubFoodName(String subFoodName) {
        this.subFoodName = subFoodName;
    }

    public String getSubFoodPrice() {
        return subFoodPrice;
    }

    public void setSubFoodPrice(String subFoodPrice) {
        this.subFoodPrice = subFoodPrice;
    }

    public String getSubFoodDesc() {
        return subFoodDesc;
    }

    public void setSubFoodDesc(String subFoodDesc) {
        this.subFoodDesc = subFoodDesc;
    }
    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Sub_food_Model){
            Sub_food_Model model  = (Sub_food_Model) obj;
            return this.subFoodId.equals(model.getSubFoodId());

        }
        else {
            return false;
        }
    }
}
