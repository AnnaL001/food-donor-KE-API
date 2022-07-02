package com.pro.fooddonorKE.api.interfaces;

import com.pro.fooddonorKE.api.models.CharityContact;

import java.util.List;

public interface CharityContactDao {
  // CREATE
  void add(CharityContact contact);

  // READ
  CharityContact get(int charityId);
  List<CharityContact> getAll();

  // UPDATE
  void update(CharityContact contact);

  // DELETE
  void delete(int id);
  void deleteAll();
}
