package com.pro.fooddonorKE.api.dao;

import com.pro.fooddonorKE.api.interfaces.FoodDonationDao;
import com.pro.fooddonorKE.api.models.FoodDonation;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oFoodDonationDao implements FoodDonationDao {
  private final Sql2o sql2o;

  public Sql2oFoodDonationDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  @Override
  public void add(List<FoodDonation> donations) {
    try(Connection connection = sql2o.open()){
      String query = "INSERT INTO food_donations(name, charity_id) VALUES (:name, :charity_id)";
      for (FoodDonation donation: donations) {
        int id = (int) connection.createQuery(query, true)
                .bind(donation)
                .executeUpdate()
                .getKey();
        donation.setId(id);
      }
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }

  @Override
  public List<FoodDonation> getFoodDonations(int charityId) {
    String query = "SELECT * FROM food_donations WHERE charity_id = :charityId";
    List<FoodDonation> foodDonations;

    try(Connection connection = sql2o.open()){
      foodDonations = connection.createQuery(query)
              .addParameter("charityId", charityId)
              .executeAndFetch(FoodDonation.class);
    } catch (Sql2oException exception){
      foodDonations = new ArrayList<>();
      exception.printStackTrace();
    }

    return foodDonations;
  }

  @Override
  public List<FoodDonation> getFoodDonations() {
    String query = "SELECT * FROM food_donations";
    List<FoodDonation> foodDonations;

    try(Connection connection = sql2o.open()){
      foodDonations = connection.createQuery(query)
              .executeAndFetch(FoodDonation.class);
    } catch (Sql2oException exception){
      foodDonations = new ArrayList<>();
      exception.printStackTrace();
    }

    return foodDonations;
  }

  @Override
  public void update(FoodDonation donation) {
    String query = "UPDATE food_donations SET name = :name WHERE id = :id";

    try(Connection connection = sql2o.open()){
      connection.createQuery(query)
              .bind(donation)
              .addParameter("id", donation.getId())
              .executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }

  @Override
  public void delete(int id) {
    String delete = "DELETE FROM food_donations WHERE id = :id";

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
      connection.createQuery("DELETE FROM food_donations")
              .executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }
}
