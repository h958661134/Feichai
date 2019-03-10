package com.example.feichai.myapplication;

public class Integral {
    private String name;
    private int imageId;
    private String needIntegral;

    public String getNeedIntegral() {
        return needIntegral;
    }

    public Integral(String name, int imageId, String needIntegral)
    {
        this.name=name;
        this.imageId=imageId;
        this.needIntegral=needIntegral;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }


}
