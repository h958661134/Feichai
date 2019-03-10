package com.example.feichai.myapplication.bannerLib;

/**enum  枚举类型*/

public enum IndicatorLocation {
    Left(1),
    Center(0),
    Right(2);

    private int value;

    IndicatorLocation(int idx) {
        this.value = idx;
    }

    public int getValue() {
        return value;
    }
}

