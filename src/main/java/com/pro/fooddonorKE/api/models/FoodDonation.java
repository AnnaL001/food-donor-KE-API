package com.pro.fooddonorKE.api.models;

public class FoodDonation {
    private int id;
    private String name;
    private int charity_id;

    public FoodDonation(int id, String name, int charity_id) {
        this.id = id;
        this.name = name;
        this.charity_id = charity_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCharity_id() {
        return charity_id;
    }

    public void setCharity_id(int charity_id) {
        this.charity_id = charity_id;
    }
}
