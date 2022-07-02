package com.pro.fooddonorKE.api.dao;

import com.pro.fooddonorKE.api.interfaces.FoodDonorKeDao;
import com.pro.fooddonorKE.api.models.Charity;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oCharityDao implements FoodDonorKeDao<Charity> {
    private final Sql2o sql2o;

    public Sql2oCharityDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Charity data) {
        try(Connection connection = sql2o.open()){
            String query = "INSERT INTO charities(name, type, location, description, website) VALUES (:name, :type, :location, :description, :website)";
            int id = (int) connection.createQuery(query, true)
                    .bind(data)
                    .executeUpdate()
                    .getKey();
            data.setId(id);
        } catch (Sql2oException exception){
            exception.printStackTrace();
        }
    }

    @Override
    public List<Charity> getAll() {
        String query = "SELECT * FROM charities";
        List<Charity> charities;

        try(Connection connection = sql2o.open()){
            charities = connection.createQuery(query).executeAndFetch(Charity.class);
        } catch (Sql2oException exception){
            charities = new ArrayList<>();
            exception.printStackTrace();
        }

        return charities;
    }

    @Override
    public Charity get(int id) {
        String query = "SELECT * FROM charities WHERE id = :id";
        Charity charity;

        try(Connection connection = sql2o.open()){
            charity = connection.createQuery(query)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Charity.class);
        } catch (Sql2oException exception){
            charity = new Charity();
            exception.printStackTrace();
        }

        return charity;
    }

    @Override
    public void update(Charity data) {
        String query = "UPDATE charities SET(name, type, location, description, website) = (:name, :type, :location, :description, :website) WHERE id = :id";
        try(Connection connection = sql2o.open()){
            connection.createQuery(query)
                    .bind(data)
                    .addParameter("id", data.getId())
                    .executeUpdate();
        } catch (Sql2oException exception){
            exception.printStackTrace();
        }
    }

    public List<Charity> getCharitiesByLocation(String location){
        String query = "SELECT * FROM charities WHERE location = :location";
        List<Charity> charities;

        try(Connection connection = sql2o.open()){
            charities = connection.createQuery(query)
                    .addParameter("location", location)
                    .executeAndFetch(Charity.class);
        } catch (Sql2oException exception){
            charities = new ArrayList<>();
            exception.printStackTrace();
        }

        return charities;
    }

    public List<Charity> getCharitiesByType(String type){
        String query = "SELECT * FROM charities WHERE type = :type";
        List<Charity> charities;

        try(Connection connection = sql2o.open()){
            charities = connection.createQuery(query)
                    .addParameter("type", type)
                    .executeAndFetch(Charity.class);
        } catch (Sql2oException exception){
            charities = new ArrayList<>();
            exception.printStackTrace();
        }

        return charities;
    }

    @Override
    public void delete(int id) {
        String delete = "DELETE FROM charities WHERE id = :id";
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
            connection.createQuery("DELETE FROM charities")
                    .executeUpdate();
        } catch (Sql2oException exception){
            exception.printStackTrace();
        }
    }
}
