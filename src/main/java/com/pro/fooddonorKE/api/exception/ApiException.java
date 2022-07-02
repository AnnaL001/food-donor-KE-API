package com.pro.fooddonorKE.api.exception;

import com.pro.fooddonorKE.api.enums.Response;

public class ApiException extends RuntimeException {
  private Response status;

  public ApiException(String message, Response status) {
    super(message);
    this.status = status;
  }

  public Response getStatus() {
    return status;
  }

  public void setStatus(Response status) {
    this.status = status;
  }
}
