package com.pro.fooddonorKE.api.interfaces;

import java.util.List;

public interface FoodDonorKeDao<T> {
    // CREATE
    void add(T data);

    // READ
    List<T> getAll();
    T get(int id);

    // UPDATE
    void update(T data);

    // DELETE
    void delete(int id);
    void deleteAll();
}
