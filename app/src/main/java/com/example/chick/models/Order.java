package com.example.chick.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class Order implements Serializable {
    private Long id;
    private Date date;
    private Store store;
    private Set<OrderFoodset> orderFoodsets;

    public Order() {
    }

    public Order(Long id, Date date, Store store, Set<OrderFoodset> orderFoodsets) {
        this.id = id;
        this.date = date;
        this.store = store;
        this.orderFoodsets = orderFoodsets;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Set<OrderFoodset> getOrderFoodsets() {
        return orderFoodsets;
    }

    public void setOrderFoodsets(Set<OrderFoodset> orderFoodsets) {
        this.orderFoodsets = orderFoodsets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(date, order.date) && Objects.equals(store, order.store) && Objects.equals(orderFoodsets, order.orderFoodsets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, store, orderFoodsets);
    }
}
