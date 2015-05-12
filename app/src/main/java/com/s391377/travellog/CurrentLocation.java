package com.s391377.travellog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.util.Date;

public class CurrentLocation extends FragmentActivity {
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    public final static String EXTRA_MESSAGE = "com.s391377.travellog.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);

        setUpMapIfNeeded();

        if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }

    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker").snippet("Snippet"));

        // Enable MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);

        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location
        Location myLocation = locationManager.getLastKnownLocation(provider);

        // set map type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Get latitude of the current location
        double latitude = myLocation.getLatitude();

        // Get longitude of the current location
        double longitude = myLocation.getLongitude();

        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Show the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!").snippet("Consider yourself located"));

        String currentDateString = DateFormat.getDateInstance().format(new Date());
        String currentTimeString = DateFormat.getTimeInstance().format(new Date());

        String LatLetter, LongLetter;

        if (latitude > 0)
        {
            LatLetter = "N";
        }
        else
        {
            LatLetter = "S";
        }

        if (longitude > 0)
        {
            LongLetter = "E";
        }
        else
        {
            LongLetter = "W";
        }


        String Lat = Location.convert(latitude, Location.FORMAT_SECONDS);
        String Long = Location.convert(longitude, Location.FORMAT_SECONDS);

        String[] LatParts = Lat.split(":");
        String LatPart1 = LatParts[0];
        String LatPart2 = LatParts[1];
        String LatPart3 = LatParts[2];
        String[] LatParts3 = LatPart3.split(",");
        LatPart3 = LatParts3[0];

        String[] LongParts = Long.split(":");
        String LongPart1 = LongParts[0];
        String LongPart2 = LongParts[1];
        String LongPart3 = LongParts[2];
        String[] LongParts3 = LongPart3.split(",");
        LongPart3 = LongParts3[0];

        TextView date = (TextView)findViewById(R.id.date);
        date.setText(currentDateString);
        TextView time = (TextView)findViewById(R.id.time);
        time.setText(currentTimeString);

        TextView coords = (TextView)findViewById(R.id.coordinates);
        coords.setText("Latitude: " + LatPart1 + "\u00B0" + LatPart2 + "\'" + LatPart3 + "\"" + LatLetter + "\n" + "Longitude: " + LongPart1 + "\u00B0" + LongPart2 + "\'" + LongPart3 + "\"" + LongLetter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    public void addvisited(View view) {
        String message = "location";
        Intent intent = new Intent();
        intent.putExtra(EXTRA_MESSAGE, message);
        setResult(CurrentLocation.RESULT_OK, intent);
        finish();
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    //private void setUpMap() {
    //    mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    //}



}