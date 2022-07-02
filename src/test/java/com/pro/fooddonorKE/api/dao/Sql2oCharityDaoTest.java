package com.pro.fooddonorKE.api.dao;

import com.pro.fooddonorKE.api.dao.parameter_resolver.CharityParameterResolver;
import com.pro.fooddonorKE.api.models.Charity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(CharityParameterResolver.class)
class Sql2oCharityDaoTest {
  private static Sql2oCharityDao charityDao;
  private static Connection connection;

  @BeforeAll
  public static void beforeAll() {
    Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/fooddonorke_test", "anna", "pol1234");
    charityDao = new Sql2oCharityDao(sql2o);
    connection = sql2o.open();
  }

  @Test
  @DisplayName("Test that a charity can be added")
  public void add_addsCharity_true(Charity charity) {
    charityDao.add(charity);
    assertTrue(charityDao.getAll().contains(charity));
  }

  @Test
  public void add_setsNewId_true(Charity charity) {
    int initialId = charity.getId();
    charityDao.add(charity);
    assertNotEquals(initialId, charity.getId());
  }

  @Test
  @DisplayName("Test that the list of charities can be retrieved")
  public void getAll_retrievesCharity_true(Charity charity) {
    charityDao.add(charity);
    Charity[] charities = {charity};
    assertEquals(Arrays.asList(charities), charityDao.getAll());
  }

  @Test
  @DisplayName("Test that a specific charity can be retrieved")
  public void get_retrievesSpecificCharity_true(Charity charity) {
    charityDao.add(charity);
    Charity foundCharity = charityDao.get(charity.getId());
    assertEquals(charity, foundCharity);
  }


  @AfterEach
  public void tearDown() {
    charityDao.deleteAll();
  }

  @AfterAll
  public static void afterAll() {
    connection.close();
  }
}