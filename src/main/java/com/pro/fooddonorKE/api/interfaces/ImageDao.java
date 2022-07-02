package com.pro.fooddonorKE.api.interfaces;

import com.pro.fooddonorKE.api.models.Image;

import java.util.List;

public interface ImageDao {
  // CREATE
  void add(Image image);
  void add(List<Image> images);

  // READ
  List<Image> getDescriptionImages(int charityId);
  Image getImage(int charityId);
  List<Image> getImages();

  // UPDATE
  void update(Image image);

  // DELETE
  void delete(int id);
  void deleteAll();
}
