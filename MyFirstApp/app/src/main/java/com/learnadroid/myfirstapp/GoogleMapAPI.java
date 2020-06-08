
package com.learnadroid.myfirstapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.learnadroid.myfirstapp.actor.Hotel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GoogleMapAPI extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private SearchView searchView;

    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;

    ConnectionClass connectionClass;
    ArrayList<Hotel> hotels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        connectionClass = new ConnectionClass();
        hotels = new ArrayList<Hotel>();

        searchView = (SearchView) findViewById(R.id.search_place);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = searchView.getQuery().toString();

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


    private void SearchLocation(ArrayList<Hotel> hotelList) {
        Toast.makeText(getApplicationContext(), "SearchLocation", Toast.LENGTH_LONG).show();

        for (int i = 0; i < hotelList.size(); i++) {
            String location = hotelList.get(i).getLocation();
            if (location != null && !location.equals("")) {
                Geocoder geocoder = new Geocoder(GoogleMapAPI.this);
                List<Address> addressList = null;
                try {
                    addressList = geocoder.getFromLocationName(location, 1);
                    if (addressList != null) {
                        Address address = addressList.get(0);

                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        Marker marker;
                        marker = mMap.addMarker(new MarkerOptions().position(latLng).title(hotelList.get(i).getName()));
                        marker.setTag(hotelList.get(i).getId());

                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick (Marker marker){
                int id = (int) marker.getTag();
                Toast.makeText(getApplicationContext(), "" + id, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(GoogleMapAPI.this, MainActivity.class);

                intent.putExtra("hotelId", id);
                startActivity(intent);
            }

        });

    }

    private void fetchLastLocation(final GoogleMap googleMap) {
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

                            mMap.setMyLocationEnabled(true);

                            SearchHotel searchHotel = new SearchHotel();
                            searchHotel.execute();

                        }
                    }
                });

    }

    private void RequestPermision() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(GoogleMapAPI.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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

    private ArrayList<Hotel> FakeDataListHotel() {
        ArrayList<Hotel> hotelList = new ArrayList<Hotel>();
        for (int i = 0; i < 3; i++) {
            hotelList.add(new Hotel(i + 1, "Hotel +" + i, "Hi", 5, null));
        }

        hotelList.get(0).setLocation("Bến xe Mỹ Đình");
        hotelList.get(1).setLocation("Đại học Quốc Gia Hà Nội");
        hotelList.get(2).setLocation("Đại học sư phạm");

        return hotelList;
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    // SearchListHotel
    private class SearchHotel extends AsyncTask<String, String, String> {


        String z = "";
        boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN();
                if (con == null) {
                    z = "Please check your internet connection";
                } else {
                    String query = " select * from hotel";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    while (rs.next()){
                        hotels.add(new Hotel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(5), null));
                    }

                    isSuccess = true;
                    z = "Login successfull - Mãi bên nhau bạn nhé!!";

                }
            } catch (Exception ex) {
                isSuccess = false;
                z = "Exceptions" + ex;
            }

            return z;
        }
        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getBaseContext(), "" + z, Toast.LENGTH_LONG).show();
            //fix loi
            SearchLocation(hotels);


        }
    }


}
