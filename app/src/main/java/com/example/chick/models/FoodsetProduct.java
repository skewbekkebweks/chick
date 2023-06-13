package com.example.chick.models;

import java.io.Serializable;
import java.util.Objects;

public class FoodsetProduct implements Serializable {
    private long id;

    private Foodset foodset;

    private Product product;

    private int count;
    private int weight;

    public FoodsetProduct() {
    }

    public FoodsetProduct(long id, Foodset foodset, Product product, int count, int weight) {
        this.id = id;
        this.foodset = foodset;
        this.product = product;
        this.count = count;
        this.weight = weight;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Foodset getFoodset() {
        return foodset;
    }

    public void setFoodset(Foodset foodset) {
        this.foodset = foodset;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodsetProduct that = (FoodsetProduct) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
