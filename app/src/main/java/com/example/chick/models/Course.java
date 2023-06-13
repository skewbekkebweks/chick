package com.example.chick.models;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class Course implements Serializable {
    private long id;
    private String name;
    private String description;
    private int duration;

    private Set<Category> categories;

    private Set<CourseFoodset> courseFoodsets;

    private Set<CourseExercise> courseExercises;

    public Course() {
    }

    public Course(long id, String name, String description, int duration, Set<Category> categories, Set<CourseFoodset> courseFoodsets, Set<CourseExercise> courseExercises) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.categories = categories;
        this.courseFoodsets = courseFoodsets;
        this.courseExercises = courseExercises;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<CourseFoodset> getCourseFoodsets() {
        return courseFoodsets;
    }

    public void setCourseFoodsets(Set<CourseFoodset> courseFoodsets) {
        this.courseFoodsets = courseFoodsets;
    }

    public Set<CourseExercise> getCourseExercises() {
        return courseExercises;
    }

    public void setCourseExercises(Set<CourseExercise> courseExercises) {
        this.courseExercises = courseExercises;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
