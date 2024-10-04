package com.dabut.purcowlite;

import static android.content.Context.RECEIVER_EXPORTED;
import static androidx.core.content.ContextCompat.registerReceiver;
import static com.dabut.lib.v2ray.utils.V2rayConstants.CONNECTION_STATES.CONNECTED;
import static com.dabut.lib.v2ray.utils.V2rayConstants.CONNECTION_STATES.CONNECTING;
import static com.dabut.lib.v2ray.utils.V2rayConstants.CONNECTION_STATES.DISCONNECTED;
import static com.dabut.lib.v2ray.utils.V2rayConstants.SERVICE_CONNECTION_STATE_BROADCAST_EXTRA;
import static com.dabut.lib.v2ray.utils.V2rayConstants.V2RAY_SERVICE_COMMAND_EXTRA;
import static com.dabut.lib.v2ray.utils.V2rayConstants.V2RAY_SERVICE_COMMAND_INTENT;
import static com.dabut.lib.v2ray.utils.V2rayConstants.V2RAY_SERVICE_STATICS_BROADCAST_INTENT;
import static com.dabut.purcowlite.MainActivity.Pova;
import static com.dabut.purcowlite.R.drawable.circle;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.PowerManager;
import android.service.quicksettings.Tile;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.dabut.lib.v2ray.V2rayController;
import com.dabut.lib.v2ray.services.V2rayVPNService;
import com.dabut.lib.v2ray.utils.V2rayConstants;

import java.util.Objects;
public class MyWidgetProvider extends AppWidgetProvider  {
    private static final String SYNCE_CLICKED = "autumaticWidgetSyncButtonClick";
    private BroadcastReceiver v2rayBroadCastReceiver;
private static  boolean newcon = false;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        System.out.println("gppppppppppppppppppppppppppppppp");

//        final int count = appWidgetIds.length;
//        String status;
//
//        for (int i = 0; i < count; i++) {
//            int widgetId = appWidgetIds[i];
//            // ایجاد PendingIntent برای دکمه
//            Intent intent = new Intent(context, MyWidgetProvider.class);
//            intent.setAction("CLICKED");
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, widgetId, intent, PendingIntent.FLAG_IMMUTABLE);
//
//            // تنظیم کردن عملکرد دکمه با کلیک
//            views.setOnClickPendingIntent(R.id.myButton, pendingIntent);
//
//            // به‌روزرسانی ویجت
//            appWidgetManager.updateAppWidget(widgetId, views);
//        }
//


//
//        BroadcastReceiver v2rayBroadCastReceiver = new BroadcastReceiver() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onReceive(Context context, Intent intent) {
//
//                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//                RemoteViews remoteViews;
//                ComponentName componentName;
//
//                switch ((V2rayConstants.CONNECTION_STATES) Objects.requireNonNull(intent.getExtras().getSerializable(SERVICE_CONNECTION_STATE_BROADCAST_EXTRA))) {
//                    case CONNECTED:
//
//                        remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
//                        componentName = new ComponentName(context, MyWidgetProvider.class);
//                        remoteViews.setTextViewText(R.id.myButton, "on");
//                        remoteViews.setTextColor(R.id.myButton,Color.GREEN);
//                        //   remoteViews.setImageViewResource(R.id.myButton,R.drawable.kkk);
//
//                        appWidgetManager.updateAppWidget(componentName, remoteViews);
//
//
//
//
//                        break;
//                    case DISCONNECTED:
//                        remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
//                        componentName = new ComponentName(context, MyWidgetProvider.class);
//                        remoteViews.setTextViewText(R.id.myButton, "off");
//                        remoteViews.setTextColor(R.id.myButton,Color.RED);
//                        //  remoteViews.setImageViewResource(R.id.myButton,R.drawable.circle);
//
//                        appWidgetManager.updateAppWidget(componentName, remoteViews);
//                        break;
//                    case CONNECTING:
//
//
//
//
//                        break;
//                    default:
//                        break;
//                }
//            }
//        };
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            context.registerReceiver(v2rayBroadCastReceiver, new IntentFilter(V2RAY_SERVICE_STATICS_BROADCAST_INTENT), RECEIVER_EXPORTED);
//        } else {
//            context.registerReceiver(v2rayBroadCastReceiver, new IntentFilter(V2RAY_SERVICE_STATICS_BROADCAST_INTENT));
//        }
//
//
//

         v2rayBroadCastReceiver = new BroadcastReceiver() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onReceive(Context context, Intent intent) {

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                RemoteViews remoteViews;
                ComponentName componentName;
                switch ((V2rayConstants.CONNECTION_STATES) Objects.requireNonNull(intent.getExtras().getSerializable(SERVICE_CONNECTION_STATE_BROADCAST_EXTRA))) {
                    case CONNECTED:
                        System.out.println("mmmmmmmmmmmmm");

                        remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
                        componentName = new ComponentName(context, MyWidgetProvider.class);
                        remoteViews.setTextViewText(R.id.myButton, "on");
                        remoteViews.setTextColor(R.id.myButton,Color.GREEN);
                        //   remoteViews.setImageViewResource(R.id.myButton,R.drawable.kkk);
                        remoteViews.setInt(R.id.myButton, "setBackgroundResource", R.drawable.fon);

                        appWidgetManager.updateAppWidget(componentName, remoteViews);




                        break;
                    case DISCONNECTED:
                        remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
                        componentName = new ComponentName(context, MyWidgetProvider.class);
                        remoteViews.setTextViewText(R.id.myButton, "off");
                        remoteViews.setTextColor(R.id.myButton,Color.RED);
                        //  remoteViews.setImageViewResource(R.id.myButton,R.drawable.circle);
                        remoteViews.setInt(R.id.myButton, "setBackgroundResource", R.drawable.foff);
                        V2rayController.stopV2ray(context.getApplicationContext());

                        appWidgetManager.updateAppWidget(componentName, remoteViews);
                        break;

                    default:
                        break;
                }
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getApplicationContext().registerReceiver(v2rayBroadCastReceiver, new IntentFilter(V2RAY_SERVICE_STATICS_BROADCAST_INTENT), RECEIVER_EXPORTED);
        } else {
            context.getApplicationContext().registerReceiver(v2rayBroadCastReceiver, new IntentFilter(V2RAY_SERVICE_STATICS_BROADCAST_INTENT));
        }







        final int count = appWidgetIds.length;
        String status;

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];
            // ایجاد PendingIntent برای دکمه
            RemoteViews remoteViews;
            ComponentName componentName;
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            componentName = new ComponentName(context, MyWidgetProvider.class);
            remoteViews.setOnClickPendingIntent(R.id.myButton, getPendingSelf(context,  SYNCE_CLICKED, widgetId));
            appWidgetManager.updateAppWidget(componentName, remoteViews);


        }


    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    public void onReceive(Context context, @SuppressLint("UnsafeIntentLaunch") Intent intent) {
        super.onReceive(context, intent);
        System.out.println("byyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
        System.out.println(intent.getAction());

         v2rayBroadCastReceiver = new BroadcastReceiver() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onReceive(Context context, Intent intent) {

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                RemoteViews remoteViews;
                ComponentName componentName;

                switch ((V2rayConstants.CONNECTION_STATES) Objects.requireNonNull(intent.getExtras().getSerializable(SERVICE_CONNECTION_STATE_BROADCAST_EXTRA))) {
                    case CONNECTED:
                        System.out.println("xxxxxxxxxxxxxxx");
                        newcon = true;
                        remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
                        componentName = new ComponentName(context, MyWidgetProvider.class);
                        remoteViews.setTextViewText(R.id.myButton, "on");
                        remoteViews.setTextColor(R.id.myButton,Color.GREEN);
                        //   remoteViews.setImageViewResource(R.id.myButton,R.drawable.kkk);
                        remoteViews.setInt(R.id.myButton, "setBackgroundResource", R.drawable.fon);

                        appWidgetManager.updateAppWidget(componentName, remoteViews);




                        break;
                    case DISCONNECTED:
                        newcon = false;

                        remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
                        componentName = new ComponentName(context, MyWidgetProvider.class);
                        remoteViews.setTextViewText(R.id.myButton, "off");
                        remoteViews.setTextColor(R.id.myButton,Color.RED);
                        //  remoteViews.setImageViewResource(R.id.myButton,R.drawable.circle);
                        remoteViews.setInt(R.id.myButton, "setBackgroundResource", R.drawable.foff);

                        appWidgetManager.updateAppWidget(componentName, remoteViews);
                        break;

                    default:
                        break;
                }
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(v2rayBroadCastReceiver, new IntentFilter(V2RAY_SERVICE_STATICS_BROADCAST_INTENT), RECEIVER_EXPORTED);
        } else {
            context.registerReceiver(v2rayBroadCastReceiver, new IntentFilter(V2RAY_SERVICE_STATICS_BROADCAST_INTENT));
        }


        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews;
        ComponentName componentName;

        if (SYNCE_CLICKED.equals(intent.getAction())) {
             boolean m= false;
             System.out.println(newcon);
            if (newcon){
                V2rayController.stopV2ray(context);
                System.out.println("uuuuuuuuuuuuu");


            }else {


                switch (V2rayController.getConnectionState()) {
                    case CONNECTED:
                        V2rayController.stopV2ray(context.getApplicationContext());
                        System.out.println("fffffffffffffffffffffffffffff");

                        remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
                        componentName = new ComponentName(context, MyWidgetProvider.class);
                        remoteViews.setTextViewText(R.id.myButton, "off");
                        remoteViews.setTextColor(R.id.myButton,Color.RED);
                        //  remoteViews.setImageViewResource(R.id.myButton,R.drawable.circle);
                        remoteViews.setInt(R.id.myButton, "setBackgroundResource", R.drawable.foff);
                        appWidgetManager.updateAppWidget(componentName, remoteViews);

                        break;
                    case CONNECTING:
                        V2rayController.stopV2ray(context.getApplicationContext());
                        System.out.println("fffffffffffffffffffffffffffff");

                        break;
                    case DISCONNECTED:
                        System.out.println("bbbbbbbbbbb");




                        remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
                        componentName = new ComponentName(context, MyWidgetProvider.class);
                        remoteViews.setTextViewText(R.id.myButton, "on");
                        remoteViews.setTextColor(R.id.myButton,Color.GREEN);
                        //   remoteViews.setImageViewResource(R.id.myButton,R.drawable.kkk);
                        remoteViews.setInt(R.id.myButton, "setBackgroundResource", R.drawable.fon);


                        SharedPreferences Pref = context.getSharedPreferences("pova", Context.MODE_PRIVATE);
                        String text = Pref.getString(Pova, "");

                            V2rayController.StartV2ray(context, "Default", text, null);




                        appWidgetManager.updateAppWidget(componentName, remoteViews);


                        break;
                    default:
                        V2rayController.StopV2ray(context.getApplicationContext());
                        break;


                }



            }








        }




















    }


    protected PendingIntent getPendingSelf(Context context, String action, int wigdetid) {

        Intent intent = new Intent(context, getClass());
        intent.setAction(action);


        return PendingIntent.getBroadcast(context, wigdetid, intent, PendingIntent.FLAG_IMMUTABLE);

    }


    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        if (v2rayBroadCastReceiver != null){
            context.unregisterReceiver(v2rayBroadCastReceiver);
        }

    }
}