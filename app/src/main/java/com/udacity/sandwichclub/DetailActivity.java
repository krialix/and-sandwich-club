package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView tvNameContent;
    private TextView tvAlsoKnownAsContent;
    private TextView tvPlaceOfOriginContent;
    private TextView tvIngredientsContent;
    private TextView tvDescriptionContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        ImageView tvIngredients = findViewById(R.id.iv_detail_image);
        tvNameContent = findViewById(R.id.tv_detail_name_content);
        tvAlsoKnownAsContent = findViewById(R.id.tv_detail_also_known_content);
        tvPlaceOfOriginContent = findViewById(R.id.tv_detail_place_of_origin_content);
        tvIngredientsContent = findViewById(R.id.tv_detail_ingredients_content);
        tvDescriptionContent = findViewById(R.id.tv_detail_description_content);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(tvIngredients);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        tvNameContent.setText(sandwich.getMainName());
        tvAlsoKnownAsContent.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        tvPlaceOfOriginContent.setText(sandwich.getPlaceOfOrigin());
        tvIngredientsContent.setText(TextUtils.join(", ", sandwich.getIngredients()));
        tvDescriptionContent.setText(sandwich.getDescription());
    }
}
