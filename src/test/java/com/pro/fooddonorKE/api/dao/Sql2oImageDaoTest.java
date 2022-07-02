package com.pro.fooddonorKE.api.dao;

import com.pro.fooddonorKE.api.dao.parameter_resolver.ImageParameterResolver;
import com.pro.fooddonorKE.api.models.Image;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ImageParameterResolver.class)
class Sql2oImageDaoTest {
  private static Sql2oImageDao imageDao;
  private static Connection connection;

  @BeforeAll
  public static void beforeAll() {
    Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/fooddonorke_test", "anna", "pol1234");
    imageDao = new Sql2oImageDao(sql2o);
    connection = sql2o.open();
  }

  @Test
  @DisplayName("Test that a charity's image can be added")
  public void add_addsImage_true(Image image) {
    imageDao.add(image);
    assertTrue(imageDao.getImages().contains(image));
  }

  @Test
  @DisplayName("Test that an image's id is set upon insert")
  public void add_setsImageId(Image image) {
    imageDao.add(image);
    assertNotEquals(0, image.getId());
  }

  @Test
  @DisplayName("Test that description images can be added")
  public void add_addsDescriptionImages_true() {
    imageDao.add(setUpDescriptionImages());
    assertEquals(setUpDescriptionImages(), imageDao.getImages());
  }

  @Test
  @DisplayName("Test that a charity's description images can be retrieved")
  public void getDescriptionImages_retrievesDescriptionImages_true() {
    imageDao.add(setUpDescriptionImages());
    assertEquals(setUpDescriptionImages(), imageDao.getDescriptionImages(1));
  }

  @Test
  @DisplayName("Test that a charity's image can be retrieved")
  public void getImage_retrievesImage_true(Image image) {
    imageDao.add(image);
    assertEquals(image, imageDao.getImage(image.getId()));

  }

  @Test
  public void getImages_retrievesAllImages_true(Image image) {
    imageDao.add(image);
    imageDao.add(setUpDescriptionImages());
    assertEquals(3, imageDao.getImages().size());
  }


  public List<Image> setUpDescriptionImages() {
    return new ArrayList<>(List.of(
            new Image("https://www.image1.png", "secondary", 1),
            new Image("https://www.image2.png", "secondary", 1)));
  }

  @AfterEach
  public void tearDown() {
    imageDao.deleteAll();
  }

  @AfterAll
  public static void afterAll() {
    connection.close();
  }
}