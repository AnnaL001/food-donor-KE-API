package com.pro.fooddonorKE.api.dao;

import com.pro.fooddonorKE.api.interfaces.ImageDao;
import com.pro.fooddonorKE.api.models.Image;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oImageDao implements ImageDao {
  private final Sql2o sql2o;

  public Sql2oImageDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  @Override
  public void add(Image image) {
    String query = "INSERT into images(url, type, charity_id) VALUES(:url, :type, :charity_id)";
    try(Connection connection = sql2o.open()){
      int id = (int) connection.createQuery(query)
              .bind(image)
              .executeUpdate()
              .getKey();
      image.setId(id);
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }

  @Override
  public void add(List<Image> images) {
    for (Image image: images) {
      if(!getImages().contains(image)){
        add(image);
      }
    }
  }

  @Override
  public List<Image> getDescriptionImages(int charityId) {
    String query = "SELECT * FROM images WHERE charity_id = :charityId AND type = 'secondary'";
    List<Image> descriptionImages;

    try(Connection connection = sql2o.open()){
      descriptionImages = connection.createQuery(query)
              .addParameter("charityId", charityId)
              .executeAndFetch(Image.class);
    } catch (Sql2oException exception){
      descriptionImages = new ArrayList<>();
      exception.printStackTrace();
    }

    return descriptionImages;
  }

  @Override
  public Image getImage(int charityId) {
    String query = "SELECT * FROM images WHERE id = :charityId AND type = 'primary'";
    Image image;

    try(Connection connection = sql2o.open()){
      image = connection.createQuery(query)
              .addParameter("charityId", charityId)
              .executeAndFetchFirst(Image.class);
    } catch (Sql2oException exception){
      image = new Image();
      exception.printStackTrace();
    }

    return image;
  }

  @Override
  public List<Image> getImages() {
    String query = "SELECT * FROM images";
    List<Image> images;

    try(Connection connection = sql2o.open()){
      images = connection.createQuery(query)
              .executeAndFetch(Image.class);
    } catch (Sql2oException exception){
      images = new ArrayList<>();
      exception.printStackTrace();
    }

    return images;
  }

  @Override
  public void update(Image image) {
    String query = "UPDATE images SET url = :url WHERE id = :id";

    try(Connection connection = sql2o.open()){
      connection.createQuery(query)
              .bind(image)
              .addParameter("id", image.getId())
              .executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }

  @Override
  public void delete(int id) {
    String query = "DELETE FROM images WHERE id = :id";

    try(Connection connection = sql2o.open()){
      connection.createQuery(query)
              .addParameter("id", id)
              .executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }

  @Override
  public void deleteAll() {
    try(Connection connection = sql2o.open()){
      connection.createQuery("DELETE FROM images").executeUpdate();
    } catch (Sql2oException exception){
      exception.printStackTrace();
    }
  }
}
