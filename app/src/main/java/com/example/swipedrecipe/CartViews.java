package com.example.swipedrecipe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.swipedrecipe.Adapters.arrayAdapter;
import com.example.swipedrecipe.Models.Recipe;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class CartViews extends AppCompatActivity {

    private Recipe recipes[];
    private arrayAdapter arrayAdapter;
    private int i;
    List<Recipe> rowItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_views);

        rowItems = new ArrayList<Recipe>();
        getRecipes();
        arrayAdapter = new arrayAdapter(this, R.layout.card_view_recipe, rowItems);
        ListView listView = (ListView) findViewById(R.id.cartItems);
        listView.setAdapter(arrayAdapter);
    }

    private void getRecipes() {
        System.out.println("Here");
        if (getIntent().getSerializableExtra("recipes")!=null) {
            rowItems.addAll((List<Recipe>) getIntent().getSerializableExtra("recipes"));
            System.out.println(rowItems.size());
        }
    }
}