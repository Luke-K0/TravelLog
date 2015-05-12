package com.s391377.travellog;

import java.util.List;
import java.util.Random;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewAnimator;


public class MainActivity extends ListActivity {
    private CommentsDataSource datasource;
    public String _message = "placeholder";
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
        setListAdapter(adapter);

        ListView MainActivityLV = (ListView) findViewById(android.R.id.list);

        DispayContent(viewAnimator);

        MainActivityLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {

                //Toast temp_toast = Toast.makeText(getApplicationContext(), "Entry removed", Toast.LENGTH_SHORT);
                //temp_toast.show();



                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(arg0.getContext());
                dlgAlert.setMessage("Czy chcesz usunąć element?");
                dlgAlert.setTitle("Potwierdź usunięcie");
                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast komunikat = Toast.makeText(getApplicationContext(), "usunięto", Toast.LENGTH_SHORT);
                                komunikat.show();

                                //notyfikacja:
                                //displayNotification();


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
                dlgAlert.setNegativeButton("Anuluj", null);
                dlgAlert.create().show();

                return true;
            }
        });

        MainActivityLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast temp_toast = Toast.makeText(getApplicationContext(), "costam", Toast.LENGTH_SHORT);
                temp_toast.show();

                Visited(null);


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



    public void addNew(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment comment = null;
        String[] comments = new String[] { "Cool", _message, "Hate it" };
        int nextInt = new Random().nextInt(3);
        // save the new comment to the database
        datasource.open();
        comment = datasource.createComment(comments[nextInt] , "test1", "test2", "test3", "test4");
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
        if (getListAdapter().getCount() > 0) {
            comment = (Comment) getListAdapter().getItem(0);
            datasource.open();
            datasource.deleteComment(comment);
            datasource.close();
            adapter.remove(comment);
        }
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

    public void Visited(View view) {
        Intent intent = new Intent(this, VisitedLocation.class);
        startActivity(intent);
        //startActivityForResult(intent, ACTIVITY_CREATE);
    }

    public void AddLocation(View view) {
        Intent intent = new Intent(this, CurrentLocation.class);
        //startActivity(intent);
        startActivityForResult(intent, ACTIVITY_CREATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_CREATE) {
            if(resultCode == RESULT_OK) {
                _message = "testme";
                String message = data.getStringExtra(CurrentLocation.EXTRA_MESSAGE);
                //mTextView.setText(myStr);
                //Log.e(">>>>", "message " + message);
                _message = message;
                addNew(null);

            }
        }

    }
}
