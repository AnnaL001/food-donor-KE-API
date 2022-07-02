package com.pro.fooddonorKE.api.dao;

import com.pro.fooddonorKE.api.dao.parameter_resolver.CharityContactParameterResolver;
import com.pro.fooddonorKE.api.models.Charity;
import com.pro.fooddonorKE.api.models.CharityContact;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(CharityContactParameterResolver.class)
class Sql2oCharityContactDaoTest {
  private static Sql2oCharityContactDao contactDao;
  private static Connection connection;
  
  @BeforeAll
  public static void beforeAll() {
    Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/fooddonorke_test", "anna", "pol1234");
    contactDao = new Sql2oCharityContactDao(sql2o);
    connection = sql2o.open();
  }

  @Test
  @DisplayName("Test that a charity's contact can be added")
  public void add_addsCharityContact_true(CharityContact contact) {
    contactDao.add(contact);
    assertNotNull(contactDao.getContact(contact.getCharity_id()));
  }

  @Test
  @DisplayName("Test that a charity's contact id is set upon insert")
  public void add_setsCharityContactId_true(CharityContact contact) {
    contactDao.add(contact);
    assertNotEquals(0, contact.getId());
  }

  @Test
  @DisplayName("Test that a charity's contact can be retrieved")
  public void getContact_retrievesCharityContact(CharityContact contact) {
    contactDao.add(contact);
    CharityContact foundContact = contactDao.getContact(contact.getCharity_id());
    assertEquals(contact, foundContact);
  }



  @AfterEach
  public void tearDown() {
    contactDao.deleteAll();
  }

  @AfterAll
  public static void afterAll() {
    connection.close();
  }
}