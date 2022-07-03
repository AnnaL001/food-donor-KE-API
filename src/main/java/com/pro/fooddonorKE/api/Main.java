package com.pro.fooddonorKE.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pro.fooddonorKE.api.dao.*;
import com.pro.fooddonorKE.api.enums.Response;
import com.pro.fooddonorKE.api.exception.ApiException;
import com.pro.fooddonorKE.api.models.*;
import com.pro.fooddonorKE.api.response.ApiResponse;
import org.sql2o.Sql2o;

import java.util.*;

import static java.lang.Integer.parseInt;
import static spark.Spark.*;

public class Main {
    static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/fooddonorke", "anna", "pol1234");
    static Sql2oCharityDao charityDao = new Sql2oCharityDao(sql2o);
    static Sql2oImageDao imageDao = new Sql2oImageDao(sql2o);
    static Sql2oFoodDonationDao foodDonationDao = new Sql2oFoodDonationDao(sql2o);
    static Sql2oCharityContactDao charityContactDao = new Sql2oCharityContactDao(sql2o);

    public static void main(String[] args) {
        Sql2oDonationRequestDao donationRequestDao = new Sql2oDonationRequestDao(sql2o);
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
                return gson.toJson(new ApiResponse(Response.CREATED.getStatusCode(), ApiResponse.SUCCESS_MESSAGE));
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
                return gson.toJson(new ApiResponse(Response.CREATED.getStatusCode(), ApiResponse.SUCCESS_MESSAGE));
            }
        });

        // CREATE DESCRIPTION IMAGES
        post("/charities/:id/images/secondary", "application/json", (request, response) -> {
            List<Image> images = gson.fromJson(request.body(), new TypeToken<ArrayList<Image>>() {}.getType());
            Charity charity = charityDao.get(parseInt(request.params("id")));

            if (charity == null){
                throw new ApiException(String.format("No charity with id %s exists", request.params("id")), Response.NOT_FOUND);
            } else if (images == null){
                throw new ApiException("No input provided", Response.BAD_REQUEST);
            } else  {
                // Add additional details before insert
                for (int i = 0; i < images.size() ; i++) {
                    Image image = images.get(i);
                    image.setType(Image.SECONDARY_TYPE);
                    image.setCharity_id(parseInt(request.params("id")));
                    images.set(i, image);
                }

                imageDao.add(images);
                response.status(Response.CREATED.getStatusCode());
                return gson.toJson(new ApiResponse(Response.CREATED.getStatusCode(), ApiResponse.SUCCESS_MESSAGE));
            }
        });

        // CREATE FOOD DONATION TYPES
        post("/charities/:id/fooddonations", "application/json", (request, response) -> {
            List<FoodDonation> foodDonations =  gson.fromJson(request.body(), new TypeToken<ArrayList<FoodDonation>>() {}.getType());
            Charity charity = charityDao.get(parseInt(request.params("id")));

            if(charity == null){
                throw new ApiException(String.format("No charity with id %s exists", request.params("id")), Response.NOT_FOUND);
            } else if (foodDonations == null){
                throw new ApiException("No input provided", Response.BAD_REQUEST);
            } else {
                // Add additional details before insert
                for (int i = 0; i < foodDonations.size(); i++) {
                    FoodDonation foodDonation = foodDonations.get(i);
                    foodDonation.setCharity_id(charity.getId());
                    foodDonations.set(i, foodDonation);
                }

                foodDonationDao.add(foodDonations);
                response.status(Response.CREATED.getStatusCode());
                return gson.toJson(new ApiResponse(Response.CREATED.getStatusCode(), ApiResponse.SUCCESS_MESSAGE));
            }
        });

        // CREATE CHARITY CONTACT
        post("/charities/:id/contacts", "application/json", (request, response) -> {
            CharityContact contact = gson.fromJson(request.body(), CharityContact.class);
            Charity charity = charityDao.get(parseInt(request.params("id")));

            if (charity == null){
                throw new ApiException(String.format("No charity with id %s exists", request.params("id")), Response.NOT_FOUND);
            } else if (contact == null) {
                throw new ApiException("No input provided", Response.BAD_REQUEST);
            } else if (charityContactDao.getAll().contains(contact)){
                throw new ApiException("Duplicates not allowed", Response.CONFLICT);
            } else {
                // Add additional details before insert
                contact.setCharity_id(charity.getId());

                charityContactDao.add(contact);
                response.status(Response.CREATED.getStatusCode());
                return gson.toJson(new ApiResponse(Response.CREATED.getStatusCode(), ApiResponse.SUCCESS_MESSAGE));
            }
        });

        // CREATE DONATION REQUEST
        post("/charities/:id/requests", "application/json", (request, response) -> {
            DonationRequest donationRequest = gson.fromJson(request.body(), DonationRequest.class);
            Charity charity = charityDao.get(parseInt(request.params("id")));

            if(charity == null){
                throw new ApiException(String.format("No charity with id %s exists", request.params("id")), Response.NOT_FOUND);
            } else if (donationRequest == null) {
                throw new ApiException("No input provided", Response.BAD_REQUEST);
            } else if (donationRequestDao.getDonationRequests().contains(donationRequest)) {
                throw new ApiException("Duplicates not allowed", Response.CONFLICT);
            } else {
                // Add additional details before insert
                donationRequest.setCharity_id(charity.getId());
                donationRequestDao.add(donationRequest);
                response.status(Response.CREATED.getStatusCode());
                return gson.toJson(new ApiResponse(Response.CREATED.getStatusCode(), ApiResponse.SUCCESS_MESSAGE));
            }
        });

        // READ CHARITIES BY LOCATION
        get("/charities/locations", "application/json", (request, response) -> {
            List<Charity> charities = charityDao.getCharitiesByLocation(request.queryParams("location"));

            if (charities == null){
                throw new ApiException("Not a valid argument for 'location'", Response.BAD_REQUEST);
            } else if(charities.size() == 0){
                throw new ApiException(String.format("No charities in location '%s' listed", request.queryParams("location")), Response.NOT_FOUND);
            } else {
                ApiResponse apiResponse = new ApiResponse(Response.OK.getStatusCode(), ApiResponse.SUCCESS_MESSAGE, new Gson().toJsonTree(transformCharityList(charities)));
                return gson.toJson(apiResponse);
            }
        });

        // READ CHARITIES BY TYPE
        get("/charities/types", "application/json", (request, response) -> {
            List<Charity> charities = charityDao.getCharitiesByType(request.queryParams("type"));

            if (charities == null){
                throw new ApiException("Not a valid argument for 'type'", Response.BAD_REQUEST);
            } else if(charities.size() == 0){
                throw new ApiException(String.format("No charities of type '%s' listed", request.queryParams("type")), Response.NOT_FOUND);
            } else {
                ApiResponse apiResponse = new ApiResponse(Response.OK.getStatusCode(), ApiResponse.SUCCESS_MESSAGE, new Gson().toJsonTree(transformCharityList(charities)));
                return gson.toJson(apiResponse);
            }
        });

        // READ CHARITY
        get("/charities/:id", "application/json", (request, response) -> {
            Charity charity = charityDao.get(parseInt(request.params("id")));

            if (charity == null){
                throw new ApiException(String.format("No charity with id '%s' listed", request.params("id")), Response.NOT_FOUND);
            } else {
                ApiResponse apiResponse = new ApiResponse(Response.OK.getStatusCode(), ApiResponse.SUCCESS_MESSAGE, new Gson().toJsonTree(charity));
                return gson.toJson(apiResponse);
            }
        });

        // READ DONATION REQUESTS BY LOCATION
        get("/requests/locations", "application/json", (request, response) -> {
            List<DonationRequest> requests = donationRequestDao.getDonationRequests(request.queryParams("location"));

            if (requests == null){
                throw new ApiException("Not a valid argument for 'location'", Response.BAD_REQUEST);
            } else if (requests.size() == 0) {
                throw new ApiException(String.format("No requests of location '%s' listed", request.queryParams("location")), Response.NOT_FOUND);
            } else {
                ApiResponse apiResponse = new ApiResponse(Response.OK.getStatusCode(), ApiResponse.SUCCESS_MESSAGE, new Gson().toJsonTree(requests));
                return gson.toJson(apiResponse);
            }
        });

        // READ DONATION REQUESTS BY CHARITY
        get("charities/:id/requests", "application/json", (request, response) -> {
            List<DonationRequest> requests = donationRequestDao.getDonationRequests(parseInt(request.params("id")));

            if (requests == null){
                throw new ApiException(String.format("No charity with id '%s' listed", request.params("id")), Response.NOT_FOUND);
            } else if (requests.size() == 0) {
                throw new ApiException(String.format("No requests by charity '%s' listed", request.params("id")), Response.NOT_FOUND);
            } else {
                ApiResponse apiResponse = new ApiResponse(Response.OK.getStatusCode(), ApiResponse.SUCCESS_MESSAGE, new Gson().toJsonTree(requests));
                return gson.toJson(apiResponse);
            }
        });

        //FILTERS
        exception(ApiException.class, (exception, request, response) -> {
            ApiResponse apiResponse = new ApiResponse(exception.getStatus().getStatusCode(), exception.getMessage());
            response.type("application/json");
            response.status(exception.getStatus().getStatusCode());
            response.body(gson.toJson(apiResponse));
        });

        after((request, response) -> response.type("application/json"));
    }

    public static List<Charity> transformCharityList(List<Charity> charities){
        ArrayList<Charity> charityArrayList = (ArrayList<Charity>) charities;

        charityArrayList.replaceAll(Main::transformCharity);

        return charityArrayList;
    }

    public static Charity transformCharity(Charity charity){
        Image image = imageDao.getImage(charity.getId());
        List<Image> images = imageDao.getDescriptionImages(charity.getId());
        List<FoodDonation> foodDonations = foodDonationDao.getFoodDonations(charity.getId());
        CharityContact contact = charityContactDao.get(charity.getId());

        // Transform charity
        charity.setImage(image.getUrl());
        charity.setDescriptionImages(images);
        charity.setFoodDonationTypes(foodDonations);
        charity.setContacts(contact);


        return charity;
    }
}