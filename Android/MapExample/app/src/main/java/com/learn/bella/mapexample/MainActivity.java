package com.learn.bella.mapexample;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private GoogleMap mMap;

    private static final int PERM_ACCESS_COARSE_LOCATION = 13;
    private static final int PERM_ACCESS_FINE_LOCATION = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //GET API KEY: https://developers.google.com/maps/documentation/android-api/signup

        ((SupportMapFragment) getSupportFragmentManager().
                findFragmentById(R.id.map)).getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                if (mMap != null) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    mMap.setTrafficEnabled(false);
                    drawPolygon();
                    drawPolyLine();
                    checkPermissions();

                    mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                        @Override
                        public boolean onMyLocationButtonClick() {

                            LocationManager locationManager = (LocationManager)
                                    getSystemService(Context.LOCATION_SERVICE);
                            Criteria criteria = new Criteria();

                            @SuppressLint("MissingPermission")
                            Location location = locationManager.getLastKnownLocation(locationManager
                                    .getBestProvider(criteria, false));

                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 12.0f));
                            return true;
                        }
                    });
                }
            }
        });


        //Open map button

        Button openMap = findViewById(R.id.open_map_app);
        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 47.758345, 12.643630);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                getBaseContext().startActivity(intent);
            }
        });
    }

    private void drawPolygon(){
        if(mMap != null) {
            PolygonOptions polyRect = new PolygonOptions().add(
                    new LatLng(47.716065, 12.549697),
                    new LatLng(47.795741, 12.569175),
                    new LatLng(47.796355, 12.747507),
                    new LatLng(47.713876, 12.706693));
            Polygon polygon = mMap.addPolygon(polyRect);
            polygon.setFillColor(Color.GREEN);
            polygon.setStrokeColor(Color.GRAY);
            polygon.setStrokeWidth(5);
        }
    }

    private void drawPolyLine(){
        if(mMap != null) {
            PolylineOptions polyLineOpts = new PolylineOptions().add(
                    new LatLng(47.828317, 12.651450),
                    new LatLng(47.870164, 12.639100),
                    new LatLng(47.890557, 12.537375));
            Polyline polyline = mMap.addPolyline(polyLineOpts);
            polyline.setColor(Color.RED);
        }
    }

    private void checkPermissions(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERM_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else{
            mMap.setMyLocationEnabled(true);
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERM_ACCESS_COARSE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

        }else{
            mMap.setMyLocationEnabled(true);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERM_ACCESS_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if(mMap != null){
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
            }
            case PERM_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if(mMap != null){
                        }
                } else {

                }
                break;
            }
        }
    }
}
