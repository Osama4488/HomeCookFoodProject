package com.example.myfirebasejavaproject.ModelsNew;

public class FeaturedHelperClass {

    int image,cookerId;
    String title,description;

    public FeaturedHelperClass(int image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public int getCookerId() {
        return cookerId;
    }

    public void setCookerId(int cookerId) {
        this.cookerId = cookerId;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
