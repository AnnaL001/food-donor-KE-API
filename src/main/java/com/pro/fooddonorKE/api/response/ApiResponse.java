package com.pro.fooddonorKE.api.response;

import com.google.gson.JsonElement;

public class ApiResponse {
  private int status;
  private String message;
  private JsonElement data;

  public static final String SUCCESS_MESSAGE = "SUCCESS";


  public ApiResponse(int status, String message) {
    this.status = status;
    this.message = message;
  }

  public ApiResponse(int status, String message, JsonElement data) {
    this.status = status;
    this.message = message;
    this.data = data;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public JsonElement getData() {
    return data;
  }

  public void setData(JsonElement data) {
    this.data = data;
  }
}
