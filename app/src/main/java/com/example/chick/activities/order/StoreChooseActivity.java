package com.example.chick.activities.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chick.R;
import com.example.chick.databinding.ActivityStoreChooseBinding;
import com.example.chick.helpers.DataHelper;
import com.example.chick.helpers.StorePoint;
import com.example.chick.helpers.StoresHelper;
import com.example.chick.models.Foodset;
import com.example.chick.models.Order;
import com.example.chick.models.OrderFoodset;
import com.example.chick.models.Store;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.runtime.ui_view.ViewProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class StoreChooseActivity extends AppCompatActivity {
    ActivityStoreChooseBinding binding;

    private Foodset foodset;
    private static final int REQUEST_CODE_PERMISSION = 1;
    private FusedLocationProviderClient fusedLocationClient;

    private ArrayList<StorePoint> storePoints = new ArrayList<>();
    private ArrayList<MapObjectTapListener> mapObjectTapListeners = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStoreChooseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        foodset = (Foodset) intent.getSerializableExtra(getResources().getString(R.string.FOODSET));

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getPermission();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        binding.mapView.onStart();
    }

    @Override
    protected void onStop() {
        binding.mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                this.getPermission();
            } else {
                finish();
            }
        }
    }

    private void getPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_PERMISSION);
        } else {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    binding.mapView.getMap().move(new CameraPosition(new Point(location.getLatitude(), location.getLongitude()), 15.0f, 0.0f, 0.0f), new Animation(Animation.Type.SMOOTH, 0), null);
                    StoresHelper.loadStores(location.getLongitude(), location.getLatitude(), this::onLoadingStores);
                } else {

                    Toast.makeText(this, "Геолокация недоступна", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }

    private Void onLoadingStores() {
        ArrayList<Store> stores = StoresHelper.getStores();
        View placemark = new View(this);
        placemark.setBackground(getBaseContext().getDrawable(R.drawable.placemark));
        placemark.setMinimumWidth(100);
        placemark.setMinimumHeight(100);

        for (Store store : stores) {
            StorePoint p = new StorePoint(store.getLatitude(), store.getLongitude(), store);
            storePoints.add(p);
            MapObjectTapListener mapObjectTapListener = (mapObject, point) -> placemarkOnClick(mapObject, point, store);
            mapObjectTapListeners.add(mapObjectTapListener);
            binding.mapView.getMap()
                    .getMapObjects()
                    .addPlacemark(p, new ViewProvider(placemark))
                    .addTapListener(mapObjectTapListener);

        }
        return null;
    }

    public boolean placemarkOnClick(@NonNull MapObject mapObject, @NonNull Point point, Store store) {
         new AlertDialog.Builder(this)
                .setTitle("Вы уверены?")
                .setMessage("Оформить заказ в магазин " + store.getName() + ", находящийся по адресу " + store.getAddress())
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton(R.string.yes, (dialog, whichButton) -> {
                    Order order = new Order();
                    order.setStore(store);

                    OrderFoodset orderFoodset = new OrderFoodset();
                    orderFoodset.setFoodset(foodset);
                    orderFoodset.setCoefficient(1.0);
                    Set<OrderFoodset> orderFoodsets = new HashSet<>();
                    orderFoodsets.add(orderFoodset);
                    order.setOrderFoodsets(orderFoodsets);

                    DataHelper.saveOrder(order);

                    Toast.makeText(this, "Заказ успешно оформлен", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton(R.string.no, null).show();
        return false;
    }
}