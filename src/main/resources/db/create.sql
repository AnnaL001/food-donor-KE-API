CREATE DATABASE fooddonorke;
\c fooddonorke;

CREATE TABLE charities(
    id serial PRIMARY KEY,
    name VARCHAR,
    type VARCHAR,
    location VARCHAR,
    description VARCHAR,
    website VARCHAR
);

CREATE TABLE donation_requests(
    id serial PRIMARY KEY,
    message VARCHAR,
    charity_id INT
);

 CREATE TABLE food_donations(
     id serial PRIMARY KEY,
     name VARCHAR,
     charity_id INT
 );

CREATE TABLE charity_contacts(
    id serial PRIMARY KEY,
    phone VARCHAR,
    email VARCHAR,
    facebook VARCHAR,
    twitter VARCHAR,
    instagram VARCHAR,
    charity_id INT
);

CREATE TABLE images(
    id serial PRIMARY KEY,
    url VARCHAR,
    type VARCHAR,
    charity_id INT
);

CREATE DATABASE fooddonorke_test WITH TEMPLATE fooddonorke;