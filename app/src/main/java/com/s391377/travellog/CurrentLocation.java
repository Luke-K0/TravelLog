package com.s391377.travellog;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.util.Date;

public class CurrentLocation extends FragmentActivity {
    public String SLatitude, SLongitude, SDate, STime;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

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

        // Enable MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);

        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get Current Location
        Location myLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

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

        String currentDateString = SDate = DateFormat.getDateInstance().format(new Date());
        String currentTimeString = STime = DateFormat.getTimeInstance().format(new Date());

        String LatLetter, LongLetter;

        LatLetter = latitude > 0 ? "N" : "S";
        LongLetter = longitude > 0 ? "E" : "W";

        SLatitude = Double.toString(latitude);
        SLongitude = Double.toString(longitude);

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

        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("message", message);
        intent.putExtra("long", SLongitude);
        intent.putExtra("lat", SLatitude);
        intent.putExtra("date", SDate);
        intent.putExtra("time", STime);

        setResult(CurrentLocation.RESULT_OK, intent);

        finish();
    }


}