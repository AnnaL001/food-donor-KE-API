package com.pro.fooddonorKE.api.models;

import java.util.Objects;

public class FoodDonation {
    private int id;
    private String name;
    private int charity_id;

    public FoodDonation() {
    }

    public FoodDonation(String name, int charity_id) {
        this.id = 0;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodDonation that = (FoodDonation) o;
        return charity_id == that.charity_id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, charity_id);
    }
}
