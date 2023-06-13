package com.example.chick.models;

import java.io.Serializable;
import java.util.Objects;

public class OrderFoodset implements Serializable {
    private Long id;

    private Order order;

    private Foodset foodset;

    private double coefficient;

    public OrderFoodset() {
    }

    public OrderFoodset(long id, Order order, Foodset foodset, double coefficient) {
        this.id = id;
        this.order = order;
        this.foodset = foodset;
        this.coefficient = coefficient;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Foodset getFoodset() {
        return foodset;
    }

    public void setFoodset(Foodset foodset) {
        this.foodset = foodset;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderFoodset that = (OrderFoodset) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
