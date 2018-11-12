package com.example.mengl03.chapter11_map_hospitals_restaurants_schools;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    private double latitude, longitude;
    private int ProRadius = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        gMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // insert new positioning for the starting point with the marker
    }

    public void searchClick(View v){
        EditText ETlocation = (EditText)findViewById(R.id.ETsearch);
        String location = ETlocation.getText().toString();  // a String object, called 'location', reads it's text value and converts it to a String
        List<Address> addList = null; // create a list object to receive all the possible matches of cities and initialize it to null

        if(location != null || !location.equals("")){   // if the location variable isn't empty OR the location variable IS NOT equal to a space, then....
            Geocoder geo = new Geocoder(this);  // create a Geocoder object called geo and pass it this
            try{
                addList = geo.getFromLocationName(location, 1);
            }
            catch(IOException e){
                e.printStackTrace();
            }

            Address address = addList.get(0);
            LatLng latLng = new LatLng((address.getLatitude()), address.getLongitude());
            gMap.addMarker(new MarkerOptions().position(latLng).title("Hello " + location));
            gMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

            latitude = address.getLatitude();
            longitude = address.getLongitude();

        }
    }

    public String getUrl(double latitude, double longitude, String nearPlaces){
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleURL.append("location=" +  latitude + "," + longitude);
        googleURL.append("&radius=" + ProRadius);
        googleURL.append("&type=" + nearPlaces);
        googleURL.append("&sensor=true");
        googleURL.append("&key=" + "AIzaSyAUlRslNmO2hiC6EodG7Evy9eL0Bh47mdM");

        Log.d("GoogleMapsActivity", "url = " + googleURL.toString());
        return googleURL.toString();
    }

    public void onClick(View v) throws IOException{
        String hospital = "hospital";
        String school = "school";
        String restaurant = "restaurant";
        String embassy = "embassy";

        Object transferData[] = new Object[2];
        GetPlaces getNearByPlaces = new GetPlaces();

        switch (v.getId()){
            case R.id.hospitals:
                gMap.clear();
                String url = getUrl(latitude, longitude, hospital);
                transferData[0] = gMap;
                transferData[1] = url;
                getNearByPlaces.execute(transferData);
                Toast.makeText(this, "Searching for nearby hospitals", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Showing for nearby hospitals", Toast.LENGTH_SHORT).show();
                break;

            case R.id.schools:
                gMap.clear();
                url = getUrl(latitude, longitude, school);
                transferData[0] = gMap;
                transferData[1] = url;
                getNearByPlaces.execute(transferData);
                Toast.makeText(this, "Searching for nearby schools", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Showing for nearby schools", Toast.LENGTH_SHORT).show();
                break;

            case R.id.restaurants:
                gMap.clear();
                url = getUrl(latitude, longitude, restaurant);
                transferData[0] = gMap;
                transferData[1] = url;
                getNearByPlaces.execute(transferData);
                Toast.makeText(this, "Searching for nearby restaurants", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Showing for nearby restaurants", Toast.LENGTH_SHORT).show();
                break;

            case R.id.embassies:
                gMap.clear();
                url = getUrl(latitude, longitude, embassy);
                transferData[0] = gMap;
                transferData[1] = url;
                getNearByPlaces.execute(transferData);
                Toast.makeText(this, "Searching for nearby embassies", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Showing for nearby embassies", Toast.LENGTH_SHORT).show();
                break;


        }
    }
}
