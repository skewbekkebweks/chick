package com.example.chick.models;

import java.io.Serializable;
import java.util.Objects;

public class Store implements Serializable {
    private Long id;
    private String name;
    private String address;
    private Double longitude;
    private Double latitude;
    private Long yaId;

    public Store() {
    }

    public Store(Long id, String name, String address, Double longitude, Double latitude, Long yaId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.yaId = yaId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Long getYaId() {
        return yaId;
    }

    public void setYaId(Long yaId) {
        this.yaId = yaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return Objects.equals(id, store.id) && Objects.equals(name, store.name) && Objects.equals(address, store.address) && Objects.equals(longitude, store.longitude) && Objects.equals(latitude, store.latitude) && Objects.equals(yaId, store.yaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, longitude, latitude, yaId);
    }
}
