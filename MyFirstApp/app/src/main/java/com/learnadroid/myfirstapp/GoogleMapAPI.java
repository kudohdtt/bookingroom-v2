package com.learnadroid.myfirstapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import com.google.android.gms.tasks.OnSuccessListener;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

public class GoogleMapAPI extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private SearchView searchView;

    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;



    String place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        searchView = (SearchView) findViewById(R.id.search_place);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = searchView.getQuery().toString();

                if (location != null && !location.equals("")){
                    Geocoder geocoder = new Geocoder(GoogleMapAPI.this);
                    List<Address> addressList = null;
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                        if (addressList != null){
                            Address address = addressList.get(0);
                            Toast.makeText(getApplicationContext(), address.toString(), Toast.LENGTH_LONG).show();
                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                        }else {
                            Toast toast = Toast.makeText(getApplicationContext(), "This address could not be found", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }catch (Exception e)
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "This address could not be found", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        e.printStackTrace();
                    }

                }

                return false;
            }


            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        //searchView.setQuery("Nơ 3", true);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    private void SearchLocation(String location) {
        if (location != null && !location.equals("")) {
            Geocoder geocoder = new Geocoder(GoogleMapAPI.this);
            List<Address> addressList = null;
            try {
                addressList = geocoder.getFromLocationName(location, 1);
                if (addressList != null) {
                    Address address = addressList.get(0);
                    Toast.makeText(getApplicationContext(), address.toString(), Toast.LENGTH_LONG).show();
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "This address could not be found", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            } catch (Exception e) {
                Toast toast = Toast.makeText(getApplicationContext(), "This address could not be found", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                e.printStackTrace();
            }
        }
    }

    private  void  fetchLastLocation(final GoogleMap googleMap) {
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            currentLocation = location;
                            Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + " " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();

                            mMap = googleMap;
                            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                            MarkerOptions mk;
                            mk = new MarkerOptions().position(latLng).title("Your location");
                            mMap.addMarker(mk);
                            //dmhhung
                            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener(){
                                @Override
                                public void onInfoWindowClick(Marker marker){
                                    if(marker.getTitle().equals("Your location")){
                                        Intent info = new Intent(getApplicationContext(), cacloaiphong.class);
                                        startActivity(info);
                                    }
                                }
                            });
                            mMap.setMyLocationEnabled(true);
                        }
                    }
                });

    }

    private void  RequestPermision()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(getApplicationContext(), "shouldShowRequestPermissionRationale", Toast.LENGTH_LONG).show();
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(GoogleMapAPI.this,
                                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        }else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION} , 1);
            Toast.makeText(getApplicationContext(), "requestPermissions", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            RequestPermision();

            return;
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation(googleMap);

    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
