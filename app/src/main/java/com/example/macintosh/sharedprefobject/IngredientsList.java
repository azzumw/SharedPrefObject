package com.example.macintosh.sharedprefobject;

import java.util.ArrayList;
import java.util.List;

public final class IngredientsList {

    static List<Ingredients> ingredientsList = new ArrayList<>();


    public static void addIngredients(Ingredients ingredients){

        ingredientsList.add(ingredients);
    }

    public static List<Ingredients> getIngredientsList()
    {
        if(ingredientsList!=null)
            return ingredientsList;

        return null;
    }

    public static Ingredients getIngredientAtIndex(int pos){
        return ingredientsList.get(pos);
    }
}
