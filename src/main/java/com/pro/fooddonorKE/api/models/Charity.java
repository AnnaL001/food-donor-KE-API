package com.pro.fooddonorKE.api.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Charity {
    private int id;
    private String image;
    private String name;
    private String description;
    private List<Image> descriptionImages;
    private String type;
    private String location;
    private List<FoodDonation> foodDonations;
    private CharityContact contacts;
    private String website;

    public Charity() {
    }

    public Charity(String name, String type, String location, String description, String website) {
        this.id = 0;
        this.image = "";
        this.name = name;
        this.description = description;
        this.descriptionImages = new ArrayList<>();
        this.type = type;
        this.location = location;
        this.foodDonations = new ArrayList<>();
        this.contacts = new CharityContact();
        this.website = website;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Image> getDescriptionImages() {
        return descriptionImages;
    }

    public void setDescriptionImages(List<Image> descriptionImages) {
        this.descriptionImages = descriptionImages;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<FoodDonation> getFoodDonationTypes() {
        return foodDonations;
    }

    public void setFoodDonationTypes(List<FoodDonation> foodDonations) {
        this.foodDonations = foodDonations;
    }

    public CharityContact getContacts() {
        return contacts;
    }

    public void setContacts(CharityContact contacts) {
        this.contacts = contacts;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Charity charity = (Charity) o;
        return Objects.equals(name, charity.name) && Objects.equals(type, charity.type) && Objects.equals(location, charity.location) && Objects.equals(description, charity.description) && Objects.equals(website, charity.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, location, description, website);
    }
}
