package com.example.feichai.myapplication.SQLiteDataBase;

import java.sql.Timestamp;

public class Energy_Water {
    String user_id,energy_id;
    int water_count;
    Timestamp produce_time;
    int is_collect;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEnergy_id() {
        return energy_id;
    }

    public void setEnergy_id(String energy_id) {
        this.energy_id = energy_id;
    }

    public int getWater_count() {
        return water_count;
    }

    public void setWater_count(int water_count) {
        this.water_count = water_count;
    }

    public Timestamp getProduce_time() {
        return produce_time;
    }

    public void setProduce_time(Timestamp produce_time) {
        this.produce_time = produce_time;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }
}
