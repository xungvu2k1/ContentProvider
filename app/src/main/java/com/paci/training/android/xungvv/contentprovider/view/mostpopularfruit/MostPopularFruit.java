package com.paci.training.android.xungvv.contentprovider.view.mostpopularfruit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.paci.training.android.xungvv.contentprovider.R;
import com.paci.training.android.xungvv.contentprovider.localdata.fruitmodel.Fruit;
import com.paci.training.android.xungvv.contentprovider.view.fruitaddition.FruitAdditionActivity;
import com.paci.training.android.xungvv.contentprovider.viewmodel.FruitViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MostPopularFruit extends AppCompatActivity {
    private TextView textView;
    private FruitViewModel mFruitViewModel;
    public static final int Fruit_Addition_ACTIVITY_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.most_popular_fruit_activity);

        textView = findViewById(R.id.textView);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    //insert
                    Uri uri = Uri.parse("content://com.example.myapp.provider/checked_item_table");
                    ContentValues values = new ContentValues();
                    values.put("fruitId", 16);
                    values.put("fruit_valid", 1);
                    Uri newUri = getContentResolver().insert(uri, values);

                    //update


                    //delete
//                    int rowsDeleted = getContentResolver().delete(uri, null, null);

                    //query
//                    Uri uri = Uri.parse("content://com.example.myapp.provider/checked_item_table");
                    String[] projection = {"fruitId", "fruit_valid"};
                    String selection = null;
                    String[] selectionArgs = null;
                    String sortOrder = null;

                    Cursor cursor = getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
                    if (cursor != null) {
                        try {
                            // Xử lý dữ liệu từ cursor
                            if (cursor.moveToFirst()) {
                                do {
                                @SuppressLint("Range") int fruitId = cursor.getInt(cursor.getColumnIndex("fruitId"));
                                @SuppressLint("Range") int fruitValid = cursor.getInt(cursor.getColumnIndex("fruit_valid"));
                                Log.e(" ", "fruitid: "+fruitId+", fruitValid: "+fruitValid);
                                    // Xử lý dữ liệu tại đây
                                } while (cursor.moveToNext());
                            }
                        } finally {
                            cursor.close();
                        }
                    }
                });
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final FruitListAdapter adapter = new FruitListAdapter(new FruitListAdapter.FruitDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*
        Use ViewModelProvider to associate your ViewModel with your Activity
        When your Activity first starts, the ViewModelProviders will create the ViewModel.
        When the activity is destroyed, for example through a configuration change, the ViewModel persists.
        When the activity is re-created, the ViewModelProviders return the existing ViewModel
         */
        mFruitViewModel = new ViewModelProvider(this).get(FruitViewModel.class);
        mFruitViewModel.getAllFruits().observe(this, fruits ->{
            // Update the cached copy of the words in the adapter.
            Log.e("acdd", "after observe");

            adapter.submitList(fruits);
        });

        //add fruit to database
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener( view -> {
            Intent intent = new Intent(MostPopularFruit.this, FruitAdditionActivity.class);
            startActivityForResult(intent, Fruit_Addition_ACTIVITY_REQUEST_CODE);
        });
    }

    //If this activity returns with RESULT_OK, insert the returned fruits into the
    // database by calling the insert() method of the FruitViewModel:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Fruit_Addition_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String fruitName = data.getStringExtra(FruitAdditionActivity.EXTRA_REPLY);
            Fruit fruit = new Fruit(5, fruitName, "this");
            mFruitViewModel.insert(fruit);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "empty not saved",
                    Toast.LENGTH_LONG).show();
        }
    }
}


//        ContentValues values = new ContentValues();
//        values.put("column_name", "data_value");
//        Uri uri = getContentResolver().insert(Uri.parse("content://com.example.myapp.provider/table_name"), values);
