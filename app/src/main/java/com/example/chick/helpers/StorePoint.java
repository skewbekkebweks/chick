package com.example.chick.helpers;

import com.example.chick.models.Store;
import com.yandex.mapkit.geometry.Point;

public class StorePoint extends Point {
    private Store store;

    public StorePoint(double latitude, double longitude, Store store) {
        super(latitude, longitude);
        this.store = store;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
