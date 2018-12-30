package com.example.macintosh.sharedprefobject;

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

import java.util.List;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences preferences;
    List<Ingredients> ingredientsList;
    Ingredients onion;
    String title;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textview);
        onion = new Ingredients("Onion",1,"TBSP");

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);


        if(savedInstanceState!=null){

            title = savedInstanceState.getString("widgetTitle");
        }else {
            title = getString(R.string.pin);

        }

        updateTextView(preferences.getString(getString(R.string.json_key),"no preference"));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("widgetTitle",title);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.pin_to_widget_id);
        if(preferences.contains(getString(R.string.json_key))){
            title = getString(R.string.unpin);

        }else{
            title = getString(R.string.pin);
        }
        item.setTitle(title);

        return super.onPrepareOptionsMenu(menu);
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
                title = checkIfPinnned();
                item.setTitle(title);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String checkIfPinnned(){
        /**
         * This title is based on SharedPreferences State
         * If there are any SharedPreference stored then the
         * title should be 'Unpin From Widget', else it shoudl be
         * 'Pin to Widget*/
        if(title.equals(getString(R.string.unpin))){
            Toast.makeText(this, getString(R.string.unpin), Toast.LENGTH_SHORT).show();
            title = getString(R.string.pin);

            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(getString(R.string.json_key));
            editor.apply();

        }else {

            Toast.makeText(this, getString(R.string.pin), Toast.LENGTH_SHORT).show();
            title = getString(R.string.unpin);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(getString(R.string.json_key),getJsonString(onion));
            editor.apply();
        }

        return title;
    }

    private void updateTextView(String s){

        textView.setText(s);
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

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String msg;
        if(sharedPreferences.contains(key)){
            String ingredStringFromSharedPref = preferences.getString(getString(R.string.json_key),"");
            Ingredients ingredientObjFromJsonString = getIngredientFromJson(ingredStringFromSharedPref);
            msg = ingredientObjFromJsonString.getName() + "\n" + ingredientObjFromJsonString.getUnit() + "\n" + ingredientObjFromJsonString.getQty();
        }else {
            msg = "No preference";
        }

        updateTextView(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        preferences.unregisterOnSharedPreferenceChangeListener(this);
    }
}
