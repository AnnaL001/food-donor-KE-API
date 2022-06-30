package com.pro.fooddonorKE.api.models;

import java.sql.Timestamp;
import java.util.Date;

public class DonationRequest {
    private int id;
    private String message;
    private int charity_id;
    private Timestamp created_at;

    public DonationRequest(String message, int charity_id) {
        this.id = 0;
        this.message = message;
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
}
