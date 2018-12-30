package com.example.macintosh.sharedprefobject;

import android.content.ClipData;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    List<Ingredients> ingredientsList;
    boolean isPinned = false;
    Ingredients onion;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textview);
        onion = new Ingredients("Onion",1,"TBSP");
//        Ingredients honey = new Ingredients("honey",2,"TSP");
//        Ingredients oliveOil = new Ingredients("olive oil",1,"TBSP");

//        IngredientsList.addIngredients(onion);
//        IngredientsList.addIngredients(honey);
//        IngredientsList.addIngredients(oliveOil);


        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        checkPreference();

    }

    @Override
    protected void onResume() {
        super.onResume();
        preferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

                checkPreference();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.pin_to_widget_id:
                //call method or store in sharedpreferences;return true;
                String title = checkIfPinnned();
                item.setTitle(title);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String checkIfPinnned(){
        String title;
        if(isPinned){
            Toast.makeText(this, getString(R.string.unpin), Toast.LENGTH_SHORT).show();
            isPinned = false;
            title = getString(R.string.pin);
            //unstore sharedpref

            if(preferences.contains("json_key")){
                editor.remove("json_key");
                editor.commit();
            }

        }else {

            Toast.makeText(this, getString(R.string.pin), Toast.LENGTH_SHORT).show();
            isPinned = true;
            title = getString(R.string.unpin);

            editor.putString("json_key",getJsonString(onion));
            editor.commit();
        }

        return title;
    }

    private void checkPreference(){

        if(preferences.contains("json_key")){
            String ingredStringFromSharedPref = preferences.getString("json_key","");
            Ingredients ingredientObjFromJsonString = getIngredientFromJson(ingredStringFromSharedPref);
            textView.setText(ingredientObjFromJsonString.getName()+"\n"+ ingredientObjFromJsonString.getUnit() + "\n" + ingredientObjFromJsonString.getQty());
        }else
            textView.setText("no object");


    }

    private String getJsonString(Ingredients ingredient){
        Gson gson = new Gson();
        return gson.toJson(ingredient);
    }

    private Ingredients getIngredientFromJson(String json){
        Gson gson = new Gson();
        Ingredients ingredient = gson.fromJson(json,Ingredients.class);
        return ingredient;
    }


}
