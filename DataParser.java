package com.example.mengl03.chapter11_map_hospitals_restaurants_schools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

    public class DataParser {
        private HashMap<String, String> getSInglePlace(JSONObject googlPlaceJASON) {
            HashMap<String, String> googlePlaceMap = new HashMap<>();
            String namesPlaces = "-NA-";
            String vicinity = "-NA-";
            String latitude = "";
            String longitude = "";
            String reference = "";

            try {
                if (!googlPlaceJASON.isNull("name")) {
                    namesPlaces = googlPlaceJASON.getString("name");
                }
                if (!googlPlaceJASON.isNull("vicinity")) {
                    vicinity = googlPlaceJASON.getString("vicinity");
                }
                latitude = googlPlaceJASON.getJSONObject("geometry").getJSONObject("location").getString("lat");
                longitude = googlPlaceJASON.getJSONObject("geometry").getJSONObject("location").getString("lng");
                reference = googlPlaceJASON.getString("reference");

                googlePlaceMap.put("place_name", namesPlaces);
                googlePlaceMap.put("vicinity", vicinity);
                googlePlaceMap.put("lat", latitude);
                googlePlaceMap.put("lng", longitude);
                googlePlaceMap.put("reference", reference);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return googlePlaceMap;

        }
            // getAllPaces builds the list
        private List<HashMap<String, String>> getAllPaces (JSONArray jsonArray) {
            int counter = jsonArray.length();
            List<HashMap<String, String>>  nearPlacesList = new ArrayList<>();
            HashMap<String, String> nearPlacesMap = null;

            for (int i = 0; i < counter; i++) {
                try {
                    nearPlacesMap = getSInglePlace((JSONObject) jsonArray.get(i));
                    nearPlacesList.add(nearPlacesMap);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            return nearPlacesList;
        }

        public List<HashMap<String,String>> parse (String JSONdata) {
            JSONArray jsonArray = null;
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(JSONdata);
                jsonArray = jsonObject.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return getAllPaces(jsonArray);

        }
    }
