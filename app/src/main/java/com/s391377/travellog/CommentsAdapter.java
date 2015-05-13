package com.s391377.travellog;


import android.content.Context;
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
        // Lookup view for data population
        // TextView tvComment = (TextView) convertView.findViewById(R.id.tvComment);
        TextView tvLatitude = (TextView) convertView.findViewById(R.id.tvLatitude);
        TextView tvLongitude = (TextView) convertView.findViewById(R.id.tvLongitude);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        // Populate the data into the template view using the data object
        // tvComment.setText(comment.getComment());
        tvLatitude.setText(comment.getLatitude());
        tvLongitude.setText(comment.getLongitude());
        tvDate.setText(comment.getDate());
        tvTime.setText(comment.getTime());
        // Return the completed view to render on screen
        return convertView;
    }
}