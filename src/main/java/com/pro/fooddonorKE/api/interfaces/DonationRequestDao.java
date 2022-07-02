package com.pro.fooddonorKE.api.interfaces;

import com.pro.fooddonorKE.api.models.DonationRequest;

import java.util.List;

public interface DonationRequestDao {
  // CREATE
  void add(DonationRequest request);

  // READ
  List<DonationRequest> getDonationRequests(int charityId);
  List<DonationRequest> getDonationRequests(String location);
  List<DonationRequest> getDonationRequests();

  // UPDATE
  void update(DonationRequest request);

  // DELETE
  void delete(int id);
  void deleteAll();
}
