package com.example.mengl03.chapter11_map_hospitals_restaurants_schools;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.io.DataOutput;

public class GetPlaces extends AsyncTask<Object, String, String> {
    private String googlePlaceData;
    private GoogleMap gMap;
    private String url;

    @Override
    protected String doInBackground(Object... objects){
        gMap =(GoogleMap) objects[0];   // array of GoogleMap object called 'gMap'
        url = (String)objects[1];   // array of strings called 'url'

        DownloadURL  downloadURL = new DownloadURL();   // an object of the DownloadURL class is constructed
        try{
            googlePlaceData = downloadURL.ReadURLs(url);    // the DownloadURL object called 'downloadURL' reads the 'url' String and assigns it to the 'googlePlaceData' String
        }
        catch(IOException e){
            e.printStackTrace();    // if things don't work out, handle the exception with the printStackTrace method
        }
        return googlePlaceData; // return the url String in googlePlaceData
    }

    protected void onPostExecute(String s){
        List<HashMap<String, String>> nearPlacesList = null;    // list object to receive locations, starts off empty (null)
        DataParser dataParser = new DataParser();   // object to format the data
        nearPlacesList = dataParser.parse(s);   // data formatted then added to the list

        DisplayPlaces(nearPlacesList);
    }

    private void DisplayPlaces(List<HashMap<String, String>> nearPlacesList){
        for(int i = 0; i < nearPlacesList.size(); i++){
            MarkerOptions markerOptions = new MarkerOptions();

            HashMap<String, String> nearPlaces = nearPlacesList.get(i);

            String nameOfPlace = nearPlaces.get("place_name");
            String vicinity = nearPlaces.get("vicinity");

            double lat = Double.parseDouble(nearPlaces.get("lat"));
            double lng = Double.parseDouble(nearPlaces.get("lng"));

            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(nameOfPlace + " : " + vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

            gMap.addMarker(markerOptions);
            gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            gMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
    }

}
