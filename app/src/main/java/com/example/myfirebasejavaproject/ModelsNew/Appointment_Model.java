package com.example.myfirebasejavaproject.ModelsNew;

import java.io.Serializable;

public class Appointment_Model implements Serializable {

    String subFoodName,subFoodPrice,Quantity,cartId,SubFoodId,address,date,time,CookerId,TotalBill,placedOrderId;
    private boolean expanded ;
    public Appointment_Model(String subFoodName, String subFoodPrice, String quantity, String address, String date, String time) {
        this.subFoodName = subFoodName;
        this.subFoodPrice = subFoodPrice;
        Quantity = quantity;
        this.address = address;
        this.date = date;
        this.time = time;
        expanded = false;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public Appointment_Model() {
    }

    public String getPlacedOrderId() {
        return placedOrderId;
    }

    public void setPlacedOrderId(String placedOrderId) {
        this.placedOrderId = placedOrderId;
    }

    public String getCookerId() {
        return CookerId;
    }

    public String getTotalBill() {
        return TotalBill;
    }

    public void setTotalBill(String totalBill) {
        TotalBill = totalBill;
    }

    public void setCookerId(String cookerId) {
        CookerId = cookerId;
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

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getSubFoodId() {
        return SubFoodId;
    }

    public void setSubFoodId(String subFoodId) {
        SubFoodId = subFoodId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
