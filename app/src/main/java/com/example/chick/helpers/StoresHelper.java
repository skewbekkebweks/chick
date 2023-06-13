package com.example.chick.helpers;

import com.example.chick.models.Store;
import com.example.chick.server.StoreService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.Retrofit;

public class StoresHelper {
    public static JsonElement data;

    private static final Retrofit retrofit = RetroHelper.getYaServer();
    private static final StoreService storeService = retrofit.create(StoreService.class);
    private static final String APIKEY = "1bd56a06-f70a-4d8a-bdbb-918e9a0ea2f1";
//    private static final String TEXT = "Пятёрочка";
    private static final String TEXT = "Продукты";
    private static final String LANG = "ru_RU";
    private static final String TYPE = "biz";
    private static final Integer RESULTS = 50;
    private static final double SIZE = 0.1;

    public static ArrayList<Store> getStores() {
        JsonArray array = data.getAsJsonObject().getAsJsonArray("features");
        ArrayList<Store> stores = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject s = array.get(i).getAsJsonObject();
            double longitude, latitude;
            JsonArray coords = s.getAsJsonObject("geometry").getAsJsonArray("coordinates");
            longitude = coords.get(0).getAsDouble();
            latitude = coords.get(1).getAsDouble();
            JsonObject props = s.getAsJsonObject("properties");
            JsonObject metaData = props.getAsJsonObject("CompanyMetaData");
            long yaId;
            String name, address;
            yaId = metaData.get("id").getAsLong();
            name = metaData.get("name").getAsString();
            address = metaData.get("address").getAsString();
            Store store = new Store(null, name, address, longitude, latitude, yaId);
            stores.add(store);
        }
        return stores;
    }

    @SafeVarargs
    public static void loadStores(double longitude, double latitude, Callable<Void>... functions) {
        double long1, long2, lat1, lat2;
        long1 = longitude - SIZE;
        long2 = longitude + SIZE;
        lat1 = latitude - SIZE;
        lat2 = latitude + SIZE;
        String bbox = long1 + "," + lat1 + "~" + long2 + "," + lat2;
        Call<JsonElement> call = storeService.getStoresData(APIKEY, TEXT, LANG, TYPE, bbox, RESULTS);
        System.out.println(call.request().url().toString());
        call.enqueue((ChickCallback<JsonElement>) (call1, response) -> {
            data = response.body();
            CallHelper.callFunctions(functions);
        });
    }
}
