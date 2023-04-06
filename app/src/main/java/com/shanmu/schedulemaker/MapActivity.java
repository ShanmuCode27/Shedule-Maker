package com.shanmu.schedulemaker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.shanmu.schedulemaker.databinding.ActivityMapBinding;

import java.util.List;
import java.util.Locale;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_CODE = 101;
    DbHelper dbHelper;

    private GoogleMap gMap;
    private ActivityMapBinding binding;
    private Location gpsLocation;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (isPermissionGranted()) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            try {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                latitude = gpsLocation.getLatitude();
                longitude = gpsLocation.getLongitude();

                Log.d("lat and long baby ", " lat " + latitude + " long " + longitude);
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                Address userAddress = addresses.get(0);

                dbHelper = new DbHelper(this);

                String maxAdd = userAddress.getAddressLine(0);

                Boolean insertResult = dbHelper.insertUserLocation(
                        1,
                        latitude,
                        longitude,
                        userAddress.getCountryName(),
                        userAddress.getAddressLine(0)
                );
                if (insertResult) {
                    Log.d("dbLocationInsert", "inserted successfully");
                } else {
                    Log.d("dbLocationInsert", "insertion failed");
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            requestLocationPermission();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == getPackageManager().PERMISSION_GRANTED) {
            gMap.setMyLocationEnabled(true);

        }
    }


    public boolean isPermissionGranted() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else {
            return false;
        }
    }

    public void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_CODE
        );
    }
}