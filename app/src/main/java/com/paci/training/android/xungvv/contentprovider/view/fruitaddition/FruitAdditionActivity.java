package com.paci.training.android.xungvv.contentprovider.view.fruitaddition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.paci.training.android.xungvv.contentprovider.R;

public class FruitAdditionActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.example.android.fruitlist.REPLY";
    private EditText mEditFruitNameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_addition);
        mEditFruitNameView = findViewById(R.id.edit_fruit_name);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditFruitNameView.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String fruitName = mEditFruitNameView.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY, fruitName);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}