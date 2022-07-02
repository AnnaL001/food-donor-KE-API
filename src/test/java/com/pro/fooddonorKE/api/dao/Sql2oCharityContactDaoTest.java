package com.pro.fooddonorKE.api.dao;

import com.pro.fooddonorKE.api.dao.parameter_resolver.CharityContactParameterResolver;
import com.pro.fooddonorKE.api.models.CharityContact;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Collections;

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
  @DisplayName("Test that a charity's contact details can be added")
  public void add_addsCharityContact_true(CharityContact contact) {
    contactDao.add(contact);
    assertNotNull(contactDao.get(contact.getCharity_id()));
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
    CharityContact foundContact = contactDao.get(contact.getCharity_id());
    assertEquals(contact, foundContact);
  }

  @Test
  @DisplayName("Test that all contacts can be retrieved")
  public void getAll_retrievesAllContacts(CharityContact contact) {
    contactDao.add(contact);
    CharityContact[] contacts = {contact};
    assertEquals(Collections.singletonList(contact), contactDao.getAll());
  }

  @Test
  @DisplayName("Test that a charity's contact details can be updated")
  public void update_updatesCharityContact_true(CharityContact charityContact) {
    contactDao.add(charityContact);

    // Update contact details
    charityContact.setPhone("+254718983799");
    charityContact.setEmail("info@ourheart.org");
    charityContact.setFacebook("https://facebook.com/ourheart");
    charityContact.setTwitter("https://twitter.com/ourheart");
    charityContact.setInstagram("https://instagram.com/ourheart");
    contactDao.update(charityContact);

    assertEquals(charityContact, contactDao.get(charityContact.getCharity_id()));
  }

  @Test
  @DisplayName("Test that a charity's data can be deleted")
  public void delete_deletesCharityContact_false(CharityContact contact) {
    contactDao.add(contact);
    contactDao.delete(contact.getId());
    assertFalse(contactDao.getAll().contains(contact));
  }

  @Test
  @DisplayName("Test that all charities data can be deleted")
  public void deleteAll_deletesAllCharitiesContacts_true(CharityContact contact) {
    contactDao.add(contact);
    contactDao.add(setUpContact());
    contactDao.deleteAll();
    assertEquals(0, contactDao.getAll().size());
  }

  public CharityContact setUpContact(){
    return new CharityContact("+254718983790", "info@ourheart1.org", "https://facebook.com/ourheart1", "https://twitter.com/ourheart1", "https://instagram.com/ourheart1", 2);
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