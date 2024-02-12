package com.example.stretching;
public class DataItem {
    private int imageResId;
    private String description;
    public DataItem(int imageResId, String description) {
        this.imageResId = imageResId;
        this.description = description;
    }
    public int getImageResId() {
        return imageResId;
    }
    public String getDescription() {
        return description;
    }
}