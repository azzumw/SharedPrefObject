package com.example.macintosh.sharedprefobject;

import java.util.ArrayList;
import java.util.List;

public final class IngredientsList {


    private static final ArrayList<Ingredients> ingredients = new ArrayList<Ingredients>();





    public static ArrayList<Ingredients> getIngredientsList()
    {
        if(ingredients.size()>0){
            ingredients.clear();
        }
        ingredients.add(new Ingredients("onion",2,"TBSP"));
        ingredients.add(new Ingredients("honey",1,"TSP"));
        ingredients.add(new Ingredients("bee pollen",3,"TBSP"));
        return ingredients;
    }

}
