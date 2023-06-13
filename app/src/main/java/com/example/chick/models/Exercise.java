package com.example.chick.models;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
public class Exercise implements Serializable {
    private long id;
    private String name;
    private String video;

    private Set<Category> categories;

    public Exercise() {
    }

    public Exercise(long id, String name, String video, Set<Category> categories) {
        this.id = id;
        this.name = name;
        this.video = video;
        this.categories = categories;
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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return id == exercise.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
