package com.paci.training.android.xungvv.contentprovider.view.mostpopularfruit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.paci.training.android.xungvv.contentprovider.R;
import com.paci.training.android.xungvv.contentprovider.localdata.fruitmodel.Fruit;
import com.paci.training.android.xungvv.contentprovider.view.fruitaddition.FruitAdditionActivity;
import com.paci.training.android.xungvv.contentprovider.viewmodel.FruitViewModel;

public class MostPopularFruit extends AppCompatActivity {
    private FruitViewModel mFruitViewModel;
    public static final int Fruit_Addition_ACTIVITY_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.most_popular_fruit_activity);


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
