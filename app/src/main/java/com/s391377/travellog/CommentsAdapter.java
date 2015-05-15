package com.s391377.travellog;


import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CommentsAdapter extends ArrayAdapter<Comment> {
    public CommentsAdapter(Context context, ArrayList<Comment> comments) {
        super(context, 0, comments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Comment comment = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
        }
        Double lat = Double.parseDouble(comment.getLatitude());
        Double lon = Double.parseDouble(comment.getLongitude());
        String LatLon[] = latlong(lat, lon);
        TextView tvLatitude = (TextView) convertView.findViewById(R.id.tvLatitude);
        TextView tvLongitude = (TextView) convertView.findViewById(R.id.tvLongitude);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        tvLatitude.setText(LatLon[0]);
        tvLongitude.setText(LatLon[1]);
        tvDate.setText(comment.getDate());
        tvTime.setText(comment.getTime());

        return convertView;
    }

    public String[] latlong(double lat, double lon) {

        String LatLetter, LongLetter;

        LatLetter = lat > 0 ? "N" : "S";
        LongLetter = lon > 0 ? "E" : "W";

        String Lat = Location.convert(lat, Location.FORMAT_SECONDS);
        String Long = Location.convert(lon, Location.FORMAT_SECONDS);

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

        String latitude = LatPart1 + "\u00B0" + LatPart2 + "\'" + LatPart3 + "\"" + LatLetter;
        String longitude = LongPart1 + "\u00B0" + LongPart2 + "\'" + LongPart3 + "\"" + LongLetter;

        String LatLon[] = new String[2];
        LatLon[0] = latitude;
        LatLon[1] = longitude;

        return LatLon;
    }
}