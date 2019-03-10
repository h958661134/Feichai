package com.example.feichai.myapplication.SQLiteDataBase;

public class Energy_Tree {
    String user_name;
    int energy_count,tree_level,energy_need,energy_percent;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getEnergy_count() {
        return energy_count;
    }

    public void setEnergy_count(int energy_count) {
        this.energy_count = energy_count;
    }

    public int getTree_level() {
        return tree_level;
    }

    public void setTree_level(int tree_level) {
        this.tree_level = tree_level;
    }

    public int getEnergy_need() {
        return energy_need;
    }

    public void setEnergy_need(int energy_need) {
        this.energy_need = energy_need;
    }

    public int getEnergy_percent() {
        return energy_percent;
    }

    public void setEnergy_percent(int energy_percent) {
        this.energy_percent = energy_percent;
    }

    public void add_Energy_Water(int count){
        this.energy_count +=count;
    }

}
