package com.example.myfirebasejavaproject.ModelsNew;

public class praticeImageModel {

    private String mName,mImageUrl;

    public praticeImageModel() {

    }

    public praticeImageModel(String mName, String mImageUrl) {

        if(mName.trim().equals("")){
            mName = "No name";
        }
        this.mName = mName;
        this.mImageUrl = mImageUrl;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
