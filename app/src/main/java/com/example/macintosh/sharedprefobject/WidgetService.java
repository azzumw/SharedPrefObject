package com.example.macintosh.sharedprefobject;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

public class WidgetService extends IntentService{

    public static final String ACTION_UPDATE_LIST_VIEW = "com.example.macintosh.sharedprefobject.widgetservice.update_app_widget_list";
    
    public WidgetService( ) {
        super("WidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

             if(ACTION_UPDATE_LIST_VIEW.equals(action)){
                handleActionUpdateListView();
            }
        }
    }

    private void handleActionUpdateListView() {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, WidgetProvider.class));

        WidgetProvider.updateAllAppWidget(this, appWidgetManager,appWidgetIds);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listView);
    }


    public static void startActionUpdateAppWidgets(Context context) {
        Intent intent = new Intent(context, WidgetService.class);

        intent.setAction(ACTION_UPDATE_LIST_VIEW);

        context.startService(intent);
    }
}
