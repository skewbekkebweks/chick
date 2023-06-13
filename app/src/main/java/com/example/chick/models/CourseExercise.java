package com.example.chick.models;

import java.io.Serializable;
import java.util.Objects;

public class CourseExercise implements Serializable {
    private long id;

    private Course course;

    private Exercise exercise;

    private String description;
    private Integer days;
    private Integer hours;
    private Integer minutes;

    public CourseExercise() {
    }

    public CourseExercise(long id, Course course, Exercise exercise, String description, Integer days, Integer hours, Integer minutes) {
        this.id = id;
        this.course = course;
        this.exercise = exercise;
        this.description = description;
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseExercise that = (CourseExercise) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
