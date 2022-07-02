package com.pro.fooddonorKE.api.dao;

import com.pro.fooddonorKE.api.interfaces.CharityContactDao;
import com.pro.fooddonorKE.api.models.CharityContact;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oCharityContactDao implements CharityContactDao {
  private final Sql2o sql2o;

  public Sql2oCharityContactDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  @Override
  public void add(CharityContact contact) {
    String query = "INSERT INTO charity_contacts(phone, email, facebook, twitter, instagram, charity_id) VALUES(:phone, :email, :facebook, :twitter, :instagram, :charity_id)";
    try(Connection connection = sql2o.open()){
      int id = (int) connection.createQuery(query, true)
              .bind(contact)
              .executeUpdate()
              .getKey();
      contact.setId(id);
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }

  @Override
  public CharityContact get(int charityId) {
    String query = "SELECT * FROM charity_contacts WHERE charity_id = :charityId";
    CharityContact contact;

    try(Connection connection = sql2o.open()){
      contact = connection.createQuery(query)
              .addParameter("charityId", charityId)
              .executeAndFetchFirst(CharityContact.class);
    } catch (Sql2oException exception){
      contact = new CharityContact();
      exception.printStackTrace();
    }

    return contact;
  }

  @Override
  public List<CharityContact> getAll() {
    String query = "SELECT * FROM charity_contacts";
    List<CharityContact> contacts;

    try(Connection connection = sql2o.open()){
      contacts = connection.createQuery(query).executeAndFetch(CharityContact.class);
    } catch (Sql2oException exception){
      contacts = new ArrayList<>();
      exception.printStackTrace();
    }

    return contacts;
  }

  @Override
  public void update(CharityContact contact) {
    String query = "UPDATE charity_contacts SET(phone, email, facebook, twitter, instagram) = (:phone, :email, :facebook, :twitter, :instagram) WHERE id = :id";
    try(Connection connection = sql2o.open()){
      connection.createQuery(query)
              .bind(contact)
              .addParameter("id", contact.getId())
              .executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }

  @Override
  public void delete(int id) {
    String delete = "DELETE FROM charity_contacts WHERE id = :id";
    try(Connection connection = sql2o.open()){
      connection.createQuery(delete)
              .addParameter("id", id)
              .executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }

  @Override
  public void deleteAll() {
    try(Connection connection = sql2o.open()){
      connection.createQuery("DELETE FROM charity_contacts")
              .executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }
}
