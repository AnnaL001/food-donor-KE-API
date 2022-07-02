package com.pro.fooddonorKE.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pro.fooddonorKE.api.dao.Sql2oCharityDao;
import com.pro.fooddonorKE.api.dao.Sql2oImageDao;
import com.pro.fooddonorKE.api.enums.Response;
import com.pro.fooddonorKE.api.exception.ApiException;
import com.pro.fooddonorKE.api.models.Charity;
import com.pro.fooddonorKE.api.models.Image;
import com.pro.fooddonorKE.api.response.ApiResponse;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/fooddonorke", "anna", "pol1234");
        Sql2oCharityDao charityDao = new Sql2oCharityDao(sql2o);
        Sql2oImageDao imageDao = new Sql2oImageDao(sql2o);
        Gson gson = new Gson();

        // CREATE CHARITY
        post("/charities", "application/json", (request, response) -> {
            Charity charity = gson.fromJson(request.body(), Charity.class);
            if (charity == null){
                throw new ApiException("No input provided", Response.BAD_REQUEST);
            } else if (charityDao.getAll().contains(charity)){
                throw new ApiException("Duplicates not allowed", Response.CONFLICT);
            } else {
                charityDao.add(charity);
                response.status(Response.CREATED.getStatusCode());
                return gson.toJson(new ApiResponse(Response.CREATED.getStatusCode(), "SUCCESS"));
            }
        });

        // CREATE IMAGES
        post("/charities/:id/images/primary", "application/json", (request, response) -> {
            Image image = gson.fromJson(request.body(), Image.class);
            Charity charity = charityDao.get(parseInt(request.params("id")));

            if (charity == null){
                throw new ApiException(String.format("No charity with id %s exists", request.params("id")), Response.NOT_FOUND);
            } else if(image == null){
                throw new ApiException("No input provided", Response.BAD_REQUEST);
            } else if (imageDao.getImages().contains(image)){
                throw new ApiException("Duplicates not allowed", Response.CONFLICT);
            } else {
                // Add image details before insert
                image.setType(Image.PRIMARY_TYPE);
                image.setCharity_id(charity.getId());

                imageDao.add(image);
                response.status(Response.CREATED.getStatusCode());
                return gson.toJson(new ApiResponse(Response.CREATED.getStatusCode(), "SUCCESS"));
            }
        });

        // CREATE DESCRIPTION IMAGES
        post("charities/:id/images/secondary", "application/json", (request, response) -> {
            List<Image> images = gson.fromJson(request.body(), new TypeToken<List<Image>>() {}.getType());
            Charity charity = charityDao.get(parseInt(request.params("id")));

            if (charity == null){
                throw new ApiException(String.format("No charity with id %s exists", request.params("id")), Response.NOT_FOUND);
            } else if (images == null){
                throw new ApiException("No input provided", Response.BAD_REQUEST);
            } else  {
                // Add additional details before insert
                List<Image> transformedImages = new ArrayList<>();

                for (Image image: images) {
                    image.setType(Image.SECONDARY_TYPE);
                    image.setCharity_id(parseInt(request.params("id")));
                    transformedImages.add(image);
                }

                imageDao.add(transformedImages);
                response.status(Response.CREATED.getStatusCode());
                return gson.toJson(new ApiResponse(Response.CREATED.getStatusCode(), "SUCCESS"));
            }
        });

        // CREATE FOOD DONATION TYPES

        // CREATE CHARITY CONTACT

        //FILTERS
        exception(ApiException.class, (exception, request, response) -> {
            ApiResponse apiResponse = new ApiResponse(exception.getStatus().getStatusCode(), exception.getMessage());
            response.type("application/json");
            response.status(exception.getStatus().getStatusCode());
            response.body(gson.toJson(apiResponse));
        });

        after((request, response) -> response.type("application/json"));
    }
}