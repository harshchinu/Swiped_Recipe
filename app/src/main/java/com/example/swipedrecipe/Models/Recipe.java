package com.example.swipedrecipe.Models;

import java.io.Serializable;

public class Recipe implements Serializable {
    /*"id":0,
      "name":"Uthappizza",
      "image":"https://i.imgur.com/tDnjTXf.jpg",
      "category":"mains",
      "label":"Hot",
      "price":"4.99",
      "description":"A unique combination of Indian Uthappam (pancake) and Italian pizza"*/

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }

    public String getLabel() {
        return label;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int id;
    public String name;
    public String image;
    public String category;
    public String label;
    public String price;
    public String description;
}
