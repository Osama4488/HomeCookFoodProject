package com.example.myfirebasejavaproject.ModelsNew;

import androidx.annotation.Nullable;

public class practiceModel {

    String title,description,uid;




    public practiceModel(String title, String description) {
        this.title = title;
        this.description = description;

    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof  practiceModel){
            practiceModel  model = (practiceModel) obj ;
            return this.uid.equals(model.getUid());
        }
        else
            return  false;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public practiceModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
