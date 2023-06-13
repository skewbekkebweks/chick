package com.example.chick.models;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class Foodset implements Serializable {
    private long id;

    private Set<FoodsetProduct> foodsetProducts;

    public Foodset() {
    }

    public Foodset(long id, Set<FoodsetProduct> foodsetProducts) {
        this.id = id;
        this.foodsetProducts = foodsetProducts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<FoodsetProduct> getFoodsetProducts() {
        return foodsetProducts;
    }

    public void setFoodsetProducts(Set<FoodsetProduct> foodsetProducts) {
        this.foodsetProducts = foodsetProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Foodset foodset = (Foodset) o;
        return id == foodset.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
