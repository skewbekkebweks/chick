package com.example.chick.models;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class User implements Serializable {
    private long id;
    private String name;
    private boolean sex;
    private int weight;
    private int height;
    private String email;
    private boolean isAdmin;

    private Set<Category> categories;

    private Set<Exercise> exercises;

    private Set<UserCourse> userCourses;

    private Set<Order> orders;

    public User() {
    }

    public User(long id, String name, boolean sex, int weight, int height, String email, boolean isAdmin, Set<Category> categories, Set<Exercise> exercises, Set<UserCourse> userCourses, Set<Order> orders) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.weight = weight;
        this.height = height;
        this.email = email;
        this.isAdmin = isAdmin;
        this.categories = categories;
        this.exercises = exercises;
        this.userCourses = userCourses;
        this.orders = orders;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }

    public Set<UserCourse> getUserCourses() {
        return userCourses;
    }

    public void setUserCourses(Set<UserCourse> userCourses) {
        this.userCourses = userCourses;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
