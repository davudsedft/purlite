package com.dabut.purcowlite;




import static com.dabut.purcowlite.MainActivity.Pova;
import static com.dabut.purcowlite.MainActivity.notifManager;
import static com.dabut.lib.v2ray.utils.V2rayConstants.SERVICE_CONNECTION_STATE_BROADCAST_EXTRA;
import static com.dabut.lib.v2ray.utils.V2rayConstants.V2RAY_SERVICE_STATICS_BROADCAST_INTENT;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

import androidx.annotation.RequiresApi;


import java.util.Objects;

import com.dabut.lib.v2ray.V2rayController;
import com.dabut.lib.v2ray.utils.V2rayConstants;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MyQSTileService extends TileService {

    String nameconf = "متصل";

    SharedPreferences Pref,Config;
    // Called when the user adds your tile.
    String offerChannelId = "DEV7DEV_AXL_CH_ID";


    @Override
    public void onTileAdded() {
        super.onTileAdded();
    }

    // Called when your app can update your tile.
    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    public void onStartListening() {
        super.onStartListening();

        Tile tile = getQsTile();
        Config = getSharedPreferences("config", Context.MODE_PRIVATE);


        switch (V2rayController.getConnectionState()) {
            case CONNECTED:
                tile.setState(Tile.STATE_ACTIVE);
                tile.setLabel(nameconf);
                tile.setContentDescription(tile.getLabel());
                tile.updateTile();
                break;
            case DISCONNECTED:


                tile.setState(Tile.STATE_INACTIVE);
                tile.setLabel("purlite");
                tile.setContentDescription(tile.getLabel());
                tile.updateTile();
                break;


        }

        BroadcastReceiver v2rayBroadCastReceiver = new BroadcastReceiver() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onReceive(Context context, Intent intent) {

                switch ((V2rayConstants.CONNECTION_STATES) Objects.requireNonNull(intent.getExtras().getSerializable(SERVICE_CONNECTION_STATE_BROADCAST_EXTRA))) {
                    case CONNECTED:

                        tile.setState(Tile.STATE_ACTIVE);
                        tile.setLabel(nameconf);
                        tile.setContentDescription(tile.getLabel());
                        tile.updateTile();


                        break;
                    case DISCONNECTED:
                        tile.setState(Tile.STATE_INACTIVE);
                        tile.setLabel("purlite");
                        tile.setContentDescription(tile.getLabel());
                        tile.updateTile();
                        break;
                    case CONNECTING:
                        tile.setState(Tile.STATE_ACTIVE);
                        tile.setLabel(nameconf);
                        tile.setContentDescription(tile.getLabel());
                        tile.updateTile();

                        break;
                    default:
                        break;
                }
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(v2rayBroadCastReceiver, new IntentFilter(V2RAY_SERVICE_STATICS_BROADCAST_INTENT), RECEIVER_EXPORTED);
        } else {
            registerReceiver(v2rayBroadCastReceiver, new IntentFilter(V2RAY_SERVICE_STATICS_BROADCAST_INTENT));
        }




    }

    // Called when your app can no longer update your tile.
    @Override
    public void onStopListening() {
        super.onStopListening();






    }

    // Called when the user taps on your tile in an active or inactive state.
    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    public void onClick() {
        super.onClick();
        Pref = getSharedPreferences("pova", Context.MODE_PRIVATE);
        String text = Pref.getString(Pova, "");





        Tile tile = getQsTile();
        if (tile.getLabel() == "purlite") {


            tile.setLabel(nameconf);
            tile.setState(Tile.STATE_ACTIVE);



            V2rayController.StartV2ray(this, "Default", text, null);
            tile.setContentDescription(tile.getLabel());
            tile.updateTile();



        } else {
            V2rayController.StopV2ray(this);
            tile.setLabel("purlite");
            tile.setState(Tile.STATE_INACTIVE);
            tile.setContentDescription(tile.getLabel());
            tile.updateTile();

        }

        BroadcastReceiver v2rayBroadCastReceiver = new BroadcastReceiver() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onReceive(Context context, Intent intent) {

                switch ((V2rayConstants.CONNECTION_STATES) Objects.requireNonNull(intent.getExtras().getSerializable(SERVICE_CONNECTION_STATE_BROADCAST_EXTRA))) {
                    case CONNECTED:

                        tile.setState(Tile.STATE_ACTIVE);
                        tile.setLabel(nameconf);
                        tile.setContentDescription(tile.getLabel());
                        tile.updateTile();


                        break;
                    case DISCONNECTED:
                        tile.setState(Tile.STATE_INACTIVE);
                        tile.setLabel("purlite");
                        tile.setContentDescription(tile.getLabel());
                        tile.updateTile();
                        break;
                    case CONNECTING:
                        tile.setState(Tile.STATE_ACTIVE);
                        tile.setLabel(nameconf);
                        tile.setContentDescription(tile.getLabel());
                        tile.updateTile();

                        break;
                    default:
                        break;
                }
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(v2rayBroadCastReceiver, new IntentFilter(V2RAY_SERVICE_STATICS_BROADCAST_INTENT), RECEIVER_EXPORTED);
        } else {
            registerReceiver(v2rayBroadCastReceiver, new IntentFilter(V2RAY_SERVICE_STATICS_BROADCAST_INTENT));
        }




    }

    @SuppressLint("UnspecifiedImmutableFlag")
    public void simpleNotification() {
        notifManager.cancel(8);


    }

    // Called when the user removes your tile.
    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
    }


}
