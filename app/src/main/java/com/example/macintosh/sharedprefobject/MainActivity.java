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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ArrayList<Ingredients> ingredientsArrayList;
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



        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        checkPreference();

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
            editor.remove(getString(R.string.onion_name_key));editor.commit();
            editor.remove(getString(R.string.onion_unit_key));editor.commit();
            editor.remove(getString(R.string.onion_qty_key));
            editor.commit();


        }else {

            Toast.makeText(this, getString(R.string.pin), Toast.LENGTH_SHORT).show();
            isPinned = true;
            title = getString(R.string.unpin);

            //store sharedpref
            editor.putString(getString(R.string.onion_name_key),onion.getName());
            editor.commit();
            editor.putString(getString(R.string.onion_unit_key),onion.getUnit());
            editor.commit();
            editor.putInt(getString(R.string.onion_qty_key),onion.getQty());
            editor.commit();
        }

        return title;
    }

    private void checkPreference(){
        String name = preferences.getString("ONION_NAME","default");
        String unit = preferences.getString("ONION_UNIT","default unit");
        int quant = preferences.getInt("ONION_QTY",0);

        textView.setText(name+"\n"+unit + "\n" + quant);
    }
}
