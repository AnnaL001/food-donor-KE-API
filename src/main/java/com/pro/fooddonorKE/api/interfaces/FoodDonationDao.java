package com.pro.fooddonorKE.api.interfaces;

import com.pro.fooddonorKE.api.models.FoodDonation;

import java.util.List;

public interface FoodDonationDao {
  // CREATE
  void add(List<FoodDonation> donations);

  // READ
  List<FoodDonation> getFoodDonations(int charityId);
  List<FoodDonation> getFoodDonations();

  // UPDATE
  void update(FoodDonation donation);

  // DELETE
  void delete(int id);
  void deleteAll();
}
