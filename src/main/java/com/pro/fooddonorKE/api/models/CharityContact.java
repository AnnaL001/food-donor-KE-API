package com.pro.fooddonorKE.api.models;

import java.util.Objects;

public class CharityContact {
    private int id;
    private String phone;
    private String email;
    private String facebook;
    private String twitter;
    private String instagram;
    private int charity_id;

    public CharityContact() {
    }

    public CharityContact(String phone, String email, String facebook, String twitter, String instagram, int charity_id) {
        this.id = 0;
        this.phone = phone;
        this.email = email;
        this.facebook = facebook;
        this.twitter = twitter;
        this.instagram = instagram;
        this.charity_id = charity_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
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
        CharityContact contact = (CharityContact) o;
        return charity_id == contact.charity_id && Objects.equals(phone, contact.phone) && Objects.equals(email, contact.email) && Objects.equals(facebook, contact.facebook) && Objects.equals(twitter, contact.twitter) && Objects.equals(instagram, contact.instagram);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone, email, facebook, twitter, instagram, charity_id);
    }
}
