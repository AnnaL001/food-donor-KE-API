package com.pro.fooddonorKE.api.models;

import java.util.Objects;

public class Image {
  private int id;
  private String url;
  private String type;
  private int charity_id;

  public Image() {
  }

  public Image(String url, String type, int charity_id) {
    this.id = 0;
    this.url = url;
    this.type = type;
    this.charity_id = charity_id;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
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
    Image image = (Image) o;
    return charity_id == image.charity_id && Objects.equals(url, image.url) && Objects.equals(type, image.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(url, type, charity_id);
  }
}
