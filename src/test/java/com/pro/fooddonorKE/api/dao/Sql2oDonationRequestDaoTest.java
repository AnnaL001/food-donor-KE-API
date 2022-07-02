package com.pro.fooddonorKE.api.dao;

import com.pro.fooddonorKE.api.dao.parameter_resolver.DonationRequestParameterResolver;
import com.pro.fooddonorKE.api.interfaces.DonationRequestDao;
import com.pro.fooddonorKE.api.models.DonationRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DonationRequestParameterResolver.class)
class Sql2oDonationRequestDaoTest {
  private static Sql2oDonationRequestDao donationRequestDao;
  private static Connection connection;

  @BeforeAll
  public static void beforeAll() {
    Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/fooddonorke_test", "anna", "pol1234");
    donationRequestDao = new Sql2oDonationRequestDao(sql2o);
    connection = sql2o.open();
  }

  @Test
  @DisplayName("Test that a donation request can be added")
  public void add_addsDonationRequest_true(DonationRequest request) {
    donationRequestDao.add(request);
    assertTrue(donationRequestDao.getDonationRequests().contains(request));
  }

  @Test
  @DisplayName("Test that a donation request's id is set upon insert")
  public void add_setsDonationRequestId(DonationRequest request) {
    donationRequestDao.add(request);
    assertNotEquals(0, request.getId());
  }

  @Test
  @DisplayName("Test that a charity's donation requests can be retrieved")
  public void getDonationRequests_retrievesRequestsByCharity_true(DonationRequest request) {
    donationRequestDao.add(request);
    assertEquals(List.of(request), donationRequestDao.getDonationRequests(request.getCharity_id()));
  }

  @Test
  @DisplayName("Test that a donation requests can be retrieved based on location")
  public void getDonationRequests_retrievesRequestsByLocation_true(DonationRequest request) {
    donationRequestDao.add(request);
    assertEquals(List.of(request), donationRequestDao.getDonationRequests(request.getLocation()));
  }

  @Test
  @DisplayName("Test that all donation requests can be retrieved")
  public void getDonationRequests_retrievesAllRequests_true(DonationRequest request) {
    donationRequestDao.add(request);
    assertEquals(List.of(request), donationRequestDao.getDonationRequests());
  }



  @AfterEach
  public void tearDown() {
    donationRequestDao.deleteAll();
  }

  @AfterAll
  public static void afterAll() {
    connection.close();
  }
}