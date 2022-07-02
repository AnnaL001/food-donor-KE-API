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

  @Test
  public void update_updatesCharityData_true(Charity charity) {
    charityDao.add(charity);

    // Update charity data
    charity.setName("Our Heart Rehabilitation Center");
    charity.setType("Human Services");
    charity.setLocation("Westlands, Nairobi");
    charity.setDescription("A place of rehabilitation for vulnerable children");
    charity.setWebsite("https://ourheartrehababilitationcenter.org");
    charityDao.update(charity);

    assertEquals(charity, charityDao.get(charity.getId()));
  }

  @Test
  @DisplayName("Test that a charity's data can be deleted")
  public void delete_deletesCharity_false(Charity charity) {
    charityDao.add(charity);
    charityDao.delete(charity.getId());
    assertFalse(charityDao.getAll().contains(charity));
  }

  @Test
  @DisplayName("Test that all charities data can be deleted")
  public void deleteAll_deletesAllCharities_true(Charity charity) {
    charityDao.add(charity);
    charityDao.add(setUpCharity());
    charityDao.deleteAll();
    assertEquals(0, charityDao.getAll().size());
  }

  public Charity setUpCharity(){
    return new Charity("Huruma Center", "Education Charity", "Huruma", "A school for the differently abled", "https://www.hurumacenter.edu");
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