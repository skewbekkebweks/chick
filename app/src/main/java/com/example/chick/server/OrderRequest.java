package com.example.chick.server;

import com.example.chick.models.Store;

import java.util.ArrayList;

public class OrderRequest {
    private Store store;
    private ArrayList<Long> foodsetsId;

    public OrderRequest() {
    }

    public OrderRequest(Store store, ArrayList<Long> foodsetsId) {
        this.store = store;
        this.foodsetsId = foodsetsId;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public ArrayList<Long> getFoodsetsId() {
        return foodsetsId;
    }

    public void setFoodsetsId(ArrayList<Long> foodsetsId) {
        this.foodsetsId = foodsetsId;
    }
}
