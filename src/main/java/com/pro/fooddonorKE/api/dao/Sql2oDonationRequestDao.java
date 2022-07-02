package com.pro.fooddonorKE.api.dao;

import com.pro.fooddonorKE.api.interfaces.DonationRequestDao;
import com.pro.fooddonorKE.api.models.DonationRequest;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oDonationRequestDao implements DonationRequestDao {
  private final Sql2o sql2o;

  public Sql2oDonationRequestDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  @Override
  public void add(DonationRequest request) {
    try(Connection connection = sql2o.open()){
      String query = "INSERT INTO donation_requests(message, location, charity_id, created_at, updated_at) VALUES (:message, :location, :charity_id, now(), now())";
      int id = (int) connection.createQuery(query, true)
              .bind(request)
              .executeUpdate()
              .getKey();
      request.setId(id);
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }

  @Override
  public List<DonationRequest> getDonationRequests(int charityId) {
    String query = "SELECT * FROM donation_requests WHERE charity_id = :charityId ORDER BY created_at DESC";
    List<DonationRequest> requests;

    try(Connection connection = sql2o.open()){
      requests = connection.createQuery(query)
              .addParameter("charityId", charityId)
              .executeAndFetch(DonationRequest.class);
    } catch (Sql2oException exception){
      requests = new ArrayList<>();
      exception.printStackTrace();
    }

    return requests;
  }

  @Override
  public List<DonationRequest> getDonationRequests(String location) {
    String query = "SELECT * FROM donation_requests WHERE location LIKE CONCAT('%',:location,'%') ORDER BY created_at DESC";
    List<DonationRequest> requests;

    try(Connection connection = sql2o.open()){
      requests = connection.createQuery(query)
              .addParameter("location", location)
              .executeAndFetch(DonationRequest.class);
    } catch (Sql2oException exception){
      requests = new ArrayList<>();
      exception.printStackTrace();
    }

    return requests;
  }

  @Override
  public List<DonationRequest> getDonationRequests() {
    String query = "SELECT * FROM donation_requests ORDER BY created_at DESC";
    List<DonationRequest> requests;

    try(Connection connection = sql2o.open()){
      requests = connection.createQuery(query).executeAndFetch(DonationRequest.class);
    } catch (Sql2oException exception){
      requests = new ArrayList<>();
      exception.printStackTrace();
    }

    return requests;
  }

  @Override
  public void update(DonationRequest request) {
    String query = "UPDATE donation_requests SET (message, location, updated_at) = (:message, :location, now()) WHERE id = :id";

    try(Connection connection = sql2o.open()){
      connection.createQuery(query)
              .bind(request)
              .addParameter("id", request.getId())
              .executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }

  @Override
  public void delete(int id) {
    String delete = "DELETE FROM donation_requests WHERE id = :id";

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
      connection.createQuery("DELETE FROM donation_requests")
              .executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }
}
