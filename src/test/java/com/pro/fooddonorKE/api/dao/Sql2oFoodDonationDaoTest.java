package com.pro.fooddonorKE.api.dao;

import com.pro.fooddonorKE.api.dao.parameter_resolver.FoodDonationParameterResolver;
import com.pro.fooddonorKE.api.models.FoodDonation;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(FoodDonationParameterResolver.class)
class Sql2oFoodDonationDaoTest {
  private static Sql2oFoodDonationDao foodDonationDao;
  private static Connection connection;

  @BeforeAll
  public static void beforeAll() {
    Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/fooddonorke_test", "anna", "pol1234");
    foodDonationDao = new Sql2oFoodDonationDao(sql2o);
    connection = sql2o.open();
  }

  @Test
  @DisplayName("Test that a food donation type can be added")
  public void add_addsFoodDonation_true(FoodDonation donation) {
    foodDonationDao.add(List.of(donation));
    assertTrue(foodDonationDao.getFoodDonations().contains(donation));
  }

  @Test
  @DisplayName("Test that a food donation type's id is set upon insert")
  public void add_setsFoodDonationId_true(FoodDonation donation) {
    foodDonationDao.add(List.of(donation));
    assertNotEquals(0, donation.getId());
  }

  @Test
  @DisplayName("Test that a charity's food donation types can be retrieved")
  public void getFoodDonations_retrieveFoodDonationsByCharity(FoodDonation donation) {
    foodDonationDao.add(List.of(donation));
    assertEquals(List.of(donation), foodDonationDao.getFoodDonations(donation.getCharity_id()));
  }

  @Test
  @DisplayName("Test that all charity food donation types can be retrieved")
  public void getFoodDonations_retrievesAllFoodDonations_true(FoodDonation donation) {
    foodDonationDao.add(List.of(donation));
    assertEquals(List.of(donation), foodDonationDao.getFoodDonations());
  }



  @AfterEach
  public void tearDown() {
    foodDonationDao.deleteAll();
  }

  @AfterAll
  public static void afterAll() {
    connection.close();
  }
}