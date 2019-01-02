package com.example.macintosh.sharedprefobject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListViewWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

//        Log.e("TAG",list.get(1).getName()+"");
        return new AppWidgetListView(getApplicationContext());
    }
}

class AppWidgetListView implements RemoteViewsService.RemoteViewsFactory{

    private List<Ingredients> ingredientsArrayList;
    private Context context;

    public AppWidgetListView( Context context) {
        ingredientsArrayList = new ArrayList<>();
        this.context = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String json = preferences.getString(context.getString(R.string.json_key),"no preference");
//        ingredientsArrayList = getIngredientFromJson(json);

        if(preferences.contains(context.getString(R.string.json_key))){
            ingredientsArrayList = getIngredientFromJson(json);

        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientsArrayList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_provider);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String json = preferences.getString(context.getString(R.string.json_key),"no preference");
        String name="";
        if(preferences.contains(context.getString(R.string.json_key))){
            ingredientsArrayList = getIngredientFromJson(json);
            name = ingredientsArrayList.get(position).getName();
        }

        views.setTextViewText(R.id.maintv, name);


//        Intent fillInIntent = new Intent();
//        fillInIntent.putExtra("ItemTitle",ingredientsArrayList.get(position).getName());
//        views.setOnClickFillInIntent(R.id.parentView, fillInIntent);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private List<Ingredients> getIngredientFromJson(String json){
        Gson gson = new Gson();
        Ingredients[] ingredient = gson.fromJson(json,Ingredients[].class);
        return Arrays.asList(ingredient);
    }
}


