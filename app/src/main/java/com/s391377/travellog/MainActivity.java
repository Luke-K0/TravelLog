package com.s391377.travellog;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;


public class MainActivity extends ListActivity {
    private CommentsDataSource datasource;
    public static Comment addedComment;
    private static final int ACTIVITY_CREATE=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource = new CommentsDataSource(this);
        datasource.open();


        final ViewAnimator viewAnimator = (ViewAnimator) findViewById(R.id.viewAnimator1);

        List<Comment> values = datasource.getAllComments();
        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
                android.R.layout.simple_expandable_list_item_1, values);
        //setListAdapter(adapter);




        // Construct the data source
        ArrayList<Comment> arrayOfComments = new ArrayList<Comment>(values);
        // Create the adapter to convert the array to views
        CommentsAdapter commentsAdapter = new CommentsAdapter(this, arrayOfComments);
        // Attach the adapter to a ListView
        //ListView listView = (ListView) findViewById(R.id.lvItems);
        setListAdapter(commentsAdapter);


        //values.get(1).getDate();

        final ListView MainActivityLV = (ListView) findViewById(android.R.id.list);

        DispayContent(viewAnimator);

        MainActivityLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {

                //Toast temp_toast = Toast.makeText(getApplicationContext(), "Entry removed", Toast.LENGTH_SHORT);
                //temp_toast.show();



                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(arg0.getContext());
                dlgAlert.setMessage("Do you want to delete selected location?");
                dlgAlert.setTitle("Delete entry");
                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast komunikat = Toast.makeText(getApplicationContext(), "Entry deleted", Toast.LENGTH_SHORT);
                                komunikat.show();

                                ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
                                Comment comment = null;
                                if (getListAdapter().getCount() > 0) {
                                    comment = (Comment) getListAdapter().getItem(pos);
                                    datasource.open();
                                    datasource.deleteComment(comment);
                                    datasource.close();
                                    adapter.remove(comment);
                                }

                                adapter.notifyDataSetChanged();

                                DispayContent(viewAnimator);

                            }
                        });
                dlgAlert.setCancelable(true);
                dlgAlert.setNegativeButton("Cancel", null);
                dlgAlert.create().show();

                return true;
            }
        });

        MainActivityLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast temp_toast = Toast.makeText(getApplicationContext(), "You were here", Toast.LENGTH_SHORT);
                temp_toast.show();
                Comment comment = (Comment) MainActivityLV.getItemAtPosition(position);


                Intent intent = new Intent();
                Bundle bundle = new Bundle();

                bundle.putString("COMMENT", comment.getComment());
                bundle.putString("LATITUDE", comment.getLatitude());
                bundle.putString("LONGITUDE", comment.getLongitude());
                bundle.putString("DATE", comment.getDate());
                bundle.putString("TIME", comment.getTime());


                Visited(null, bundle);


            }
        });

    }

    public void DispayContent(ViewAnimator viewAnimator) {
        if (getListAdapter().getCount() > 0)
        {
            viewAnimator.setDisplayedChild(0);
        }
        else
        {
            viewAnimator.setDisplayedChild(1);
        }
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml



    public void addNew(View view, Comment comment) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        //Comment comment = null;
        //String[] comments = new String[] { "Cool", _message, "Hate it" };
        //int nextInt = new Random().nextInt(3);
        // save the new comment to the database
        datasource.open();
        comment = datasource.createComment(
                comment.getComment(),
                comment.getLatitude(),
                comment.getLongitude(),
                comment.getDate(),
                comment.getTime());
        datasource.close();
        adapter.add(comment);
        adapter.notifyDataSetChanged();


        final ViewAnimator viewAnimator = (ViewAnimator) findViewById(R.id.viewAnimator1);
        DispayContent(viewAnimator);

    }

    public void removeOld(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment comment = null;
        int i = getListAdapter().getCount();
        datasource.open();
        if (i > 0) {
            for (int j = 0; j < i; j++){
                comment = (Comment) getListAdapter().getItem(0);
                datasource.deleteComment(comment);
                adapter.remove(comment);
            }

        }
        datasource.close();
        adapter.notifyDataSetChanged();

        final ViewAnimator viewAnimator = (ViewAnimator) findViewById(R.id.viewAnimator1);
        DispayContent(viewAnimator);

    }

    @Override
    protected void onResume() {
        // datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        // datasource.close();
        super.onPause();
    }

    public void Visited(View view, Bundle bundle) {
        Intent intent = new Intent(this, VisitedLocation.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void AddLocation(View view) {
        Intent intent = new Intent(this, CurrentLocation.class);
        startActivityForResult(intent, ACTIVITY_CREATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_CREATE) {
            if(resultCode == RESULT_OK) {

                Comment comment = addedComment;
                addNew(null, comment);

            }
        }

    }

}
