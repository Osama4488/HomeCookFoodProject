package com.example.myfirebasejavaproject.ModelsNew;

import androidx.annotation.Nullable;

public class Cart_Model {

    String subFoodName,subFoodPrice,Quantity,cartId,SubFoodId,address;


    public Cart_Model() {
    }


    public Cart_Model(String subFoodName, String subFoodPrice, String quantity) {
        this.subFoodName = subFoodName;
        this.subFoodPrice = subFoodPrice;
        Quantity = quantity;

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubFoodId() {
        return SubFoodId;
    }

    public void setSubFoodId(String subFoodId) {
        SubFoodId = subFoodId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
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

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Cart_Model){
            Cart_Model model  = (Cart_Model) obj;
            return this.cartId.equals(model.getCartId());
        }
        else {
            return false;
        }
    }
}
