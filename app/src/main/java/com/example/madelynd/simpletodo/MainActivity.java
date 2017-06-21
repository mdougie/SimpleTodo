package com.example.madelynd.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //declare fields
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //reference ListView
        lvItems = (ListView) findViewById(R.id.lvItems);

        //initialize items
        readItems();

        //items = new ArrayList<>();

        //init adapter
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        //wire adapter on the view
        lvItems.setAdapter(itemsAdapter);

        //add items to list
        //items.add("Make up bed");
        //items.add("Brush teeth");
        //items.add("Gummy vitamins!");
        //items.add("Dance break :)");

        // setup Listener on creation
        setupListViewListener();
    }

    //removing an item function
    private void setupListViewListener() {
        // set the ListView's itemLongClickListener
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // remove item in the list at the index given by position
                items.remove(position);
                // notify the adapter that the dataset changed
                itemsAdapter.notifyDataSetChanged();
                // give user information that it was removed
                //Log.i("MainActivity", "Removed item " + position);

                //store updated list (data)
                writeItems();

                // return true to tell the framework that the long click was consumed
                return true;
            }
        });
    }

    //create the add function
    public void onAddItem(View v) {
        // reference EditText created with layout
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        // grab content as String
        String itemText = etNewItem.getText().toString();
        // add item to list through adapter
        itemsAdapter.add(itemText);

        //store updated list
        writeItems();

        // clear EditText
        etNewItem.setText("");
        // notification to user
        //Toast.makeText(getApplicationContext(), "Item added to list", Toast.LENGTH_SHORT).show();
    }

    // support persistence

    // returns the file in which the data is stored
    private File getDataFile() {
        return new File(getFilesDir(), "todo.txt");
    }

    // read the items from the file system
    private void readItems() {
        try {
            // create the array using the content in the file
            items = new ArrayList<String>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            // print the error to the console
            e.printStackTrace();
            // just load an empty list
            items = new ArrayList<>();
        }
    }

    // write the items to the filesystem
    private void writeItems() {
        try {
            // save the item list as a line-delimited text file
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            // print the error to the console
            e.printStackTrace();
        }
    }
}
