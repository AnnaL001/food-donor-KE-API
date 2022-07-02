package com.pro.fooddonorKE.api.models;

import java.sql.Timestamp;
import java.util.Objects;

public class DonationRequest {
    private int id;
    private String message;
    private int charity_id;
    private Timestamp created_at;
    private Timestamp updated_at;

    private String location;

    public DonationRequest() {
    }

    public DonationRequest(String message, String location, int charity_id) {
        this.id = 0;
        this.message = message;
        this.location = location;
        this.charity_id = charity_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCharity_id() {
        return charity_id;
    }

    public void setCharity_id(int charity_id) {
        this.charity_id = charity_id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DonationRequest that = (DonationRequest) o;
        return charity_id == that.charity_id && Objects.equals(message, that.message) && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, location, charity_id);
    }
}
