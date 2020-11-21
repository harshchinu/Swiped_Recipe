package com.example.swipedrecipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swipedrecipe.Adapters.arrayAdapter;
import com.example.swipedrecipe.Models.Recipe;
import com.example.swipedrecipe.NetworkCalls.APIClient;
import com.example.swipedrecipe.NetworkCalls.JsonApiCalls;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private arrayAdapter arrayAdapter ;
    Button sortAscending,SortDescending,cart;
    List<Recipe> rowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(getBaseContext());
        sortAscending=findViewById(R.id.ascendingsort);
        SortDescending=findViewById(R.id.descendingsort);
        cart=findViewById(R.id.cart);


        rowItems = new ArrayList<Recipe>();
        Paper.book().delete("recipes");
        getRecipes();
        arrayAdapter = new arrayAdapter(this, R.layout.card_view_recipe, rowItems );


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Paper.book().read("recipes") != null) {
                    List<Recipe> cartRecipes = Paper.book().read("recipes");
                    Intent intent = new Intent(MainActivity.this,CartViews.class);
                    intent.putExtra("recipes", (Serializable) cartRecipes);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "No item in carts", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);


        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Recipe obj = (Recipe) dataObject;
                rowItems.remove(obj);
                rowItems.add(obj);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Recipe obj = (Recipe) dataObject;
                rowItems.remove(obj);
                arrayAdapter.notifyDataSetChanged();
                if(Paper.book().read("recipes")==null){
                    List<Recipe> cartRecipes = new ArrayList<>();
                    cartRecipes.add(obj);
                    Paper.book().write("recipes",cartRecipes);
                }else {
                    List<Recipe> cartRecipes=  Paper.book().read("recipes");
                    cartRecipes.add(obj);
                    Paper.book().write("recipes",cartRecipes);
                }

            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });

        sortAscending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rowItems.size()>0){

                    Collections.sort(rowItems, new Comparator<Recipe>() {
                        @Override
                        public int compare(Recipe recipe, Recipe t1) {
                            return (int) (Double.parseDouble(recipe.getPrice())-Double.parseDouble(t1.getPrice()));
                        }
                    });
                    flingContainer.getTopCardListener().selectLeft();
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });

        SortDescending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rowItems.size()>0) {
                    Collections.sort(rowItems, new Comparator<Recipe>() {
                        @Override
                        public int compare(Recipe recipe, Recipe t1) {
                            return (int) (Double.parseDouble(t1.getPrice())-Double.parseDouble(recipe.getPrice()));
                        }
                    });
                    flingContainer.getTopCardListener().selectLeft();
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Recipe obj = (Recipe) dataObject;
                Toast.makeText(MainActivity.this, obj.getName(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getRecipes() {
        JsonApiCalls jsonApiCalls = APIClient.getClient().create(JsonApiCalls.class);
        Call<List<Recipe>> call = jsonApiCalls.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                rowItems.addAll(response.body());
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }
}