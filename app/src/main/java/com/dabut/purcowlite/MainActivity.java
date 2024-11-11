package com.dabut.purcowlite;

import static com.dabut.lib.v2ray.utils.V2rayConstants.SERVICE_CONNECTION_STATE_BROADCAST_EXTRA;
import static com.dabut.lib.v2ray.utils.V2rayConstants.V2RAY_SERVICE_STATICS_BROADCAST_INTENT;

import static java.lang.Thread.*;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.net.VpnService;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dabut.lib.v2ray.V2rayController;
import com.dabut.lib.v2ray.core.V2rayCoreExecutor;
import com.dabut.lib.v2ray.utils.Utilities;
import com.dabut.lib.v2ray.utils.V2rayConstants;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> vmessLinks, vmessLinks2, vmessLinks3, configarray, sslinks,vmesscount;
    ExecutorService executor;
    ArrayList<Bestserver> pingrun;
    String lowping,khata;
    private Toast backToast;
    private long backPressedTime;
    static Activity fa;
    private Thread myt,http,httpp;
    static NotificationManager notifManager;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private Thread proxyThread;
    public static final String Pova = "pova";
    SharedPreferences  pova,linq,flagsave;
    private ProgressDialog progressDialog;
    Button vpnon;
    static boolean lang = true;
    private long downloadID;
    int   loname;
    ProgressDialog updial,updial2;
    AlertDialog.Builder dialogBuilder3,dialogBuilder4,alertDialogBuilder2;
    private DrawerLayout drawerLayout ;
    NavigationView navigationView;

    String utext3,utext2,utext;
    private TextView  connected_server_delay,contextt,version;
    private EditText v2ray_config;
    private SharedPreferences sharedPreferences;
    private BroadcastReceiver v2rayBroadCastReceiver;
    String url3 = "aHR0cHM6Ly9wdXJuZXQuaXIvdnBuL3B1cmNvdi9QdXJDb3cuYXBr";

    @SuppressLint({"SetTextI18n", "UnspecifiedRegisterReceiverFlag", "WrongConstant", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         flagsave = getSharedPreferences("flagsave", MODE_PRIVATE);


        SharedPreferences preferences2 = getSharedPreferences("language", MODE_PRIVATE);
    fa = this;
        // اگر فایل preferences وجود دارد، مقدار متغیر isLightTheme را از آن بخوانید
        if (preferences2.contains("lang")) {
            lang = preferences2.getBoolean("lang", true);


        }

        // اگر تم فعلی روشن است، تم را روشن کنید و برعکس
        if (lang) {
            setApplicationLocale();

             khata = "خطا";



        } else {
            setApplicationLocale2();
             khata = "Error";

        }




        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar);

///////////////////////// فهپث
        Set<String> selectedPackages= new HashSet<>();

        Context context = this;
        SharedPreferences  prefs  = context.getSharedPreferences("com.dabut.purnetvray", Context.MODE_MULTI_PROCESS);

        selectedPackages.add("com.farazpardazan.enbank");
        selectedPackages.add("com.myirancell");
        selectedPackages.add("ir.divar");

        selectedPackages.add("ir.mci.ecareapp");
        selectedPackages.add("com.farsitel.bazaar");
        selectedPackages.add("ir.mservices.market");
        selectedPackages.add("com.sibche.aspardproject.app");
        selectedPackages.add("cab.snapp.passenger");
        selectedPackages.add("com.zoodfood.android");


        SharedPreferences prefs5 = getPreferences(MODE_MULTI_PROCESS);
        boolean isFirstRun = prefs5.getBoolean("firstRun", true);

        if (isFirstRun) {
            // Your one-time code here



            SharedPreferences.Editor editor7 = prefs.edit();
            editor7.putStringSet("selectedPackages", selectedPackages);
            editor7.apply();


            // Mark that the code has run
            SharedPreferences.Editor editor = prefs5.edit();
            editor.putBoolean("firstRun", false);
            editor.apply();
        }
        version = findViewById(R.id.versionnn);
        drawerLayout = (DrawerLayout) findViewById(R.id.dl);
        navigationView = (NavigationView) findViewById(R.id.na);









        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected (MenuItem item){
                item.setChecked(false);
                drawerLayout.closeDrawer(Gravity.RIGHT);

                int itemId = item.getItemId();
                if (itemId == R.id.tel) {//json


                    Intent browserIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/purcowbot"));
                    startActivity(browserIntent2);



                    return true;
                } else if (itemId == R.id.gith) {
                    Intent browserIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/davudsedft/purlite/releases"));
                    startActivity(browserIntent2);

                    return true;
                }
                else if (itemId == R.id.pay) {
                    Intent browserIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://davudsedft.github.io/Pay/"));
                    startActivity(browserIntent2);

                    return true;

                }

                else if (itemId == R.id.splite) {//                        String gg = "https://purnet.ir/pardakht";

                    Intent intent = new Intent(MainActivity.this , Pacapp.class);
                    startActivity(intent);
                    finish();


                    return true;
                } else if (itemId == R.id.updaate) {
                    update();

                    return true;

                } else if (itemId == R.id.lang) {
                    alertDialogBuilder2 = new AlertDialog.Builder(
                            MainActivity.this, R.style.MyDialogStyle);
                    alertDialogBuilder2
                            .setCancelable(true)
                            .setPositiveButton(getString(R.string.zaban), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                    SharedPreferences.Editor editor6 = preferences2.edit();

                                    // اگر تم فعلی روشن است، تم را تیره کنید و برعکس
                                    if (lang) {
                                        setApplicationLocale();

                                        lang = false;
                                    } else {
                                        setApplicationLocale2();
                                        lang = true;
                                    }

                                    // مقدار جدید متغیر isLightTheme را در فایل preferences ذخیره کنید
                                    editor6.putBoolean("lang", lang);
                                    editor6.apply();

                                    // فعالیت را دوباره راه‌اندازی کنید تا تغییرات اعمال شوند
                                   recreate();





                                }
                            }).setNegativeButton(getString(R.string.enseraf), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close


                                    dialog.cancel();

                                }
                            });



                    AlertDialog alertDialog6 = alertDialogBuilder2.create();

                    // show it
                    alertDialog6.show();


                    return true;
                }


                    return false;
            }

        });
















/////////////////////خدثفهپث
        StringRequest request = new StringRequest("https://raw.githubusercontent.com/davudsedft/purlite/refs/heads/main/link/version.json", new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJsonData2(string);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), getString(R.string.khata), Toast.LENGTH_SHORT).show();
                updial.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
        rQueue.add(request);



        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() != Activity.RESULT_OK) {
                Toast.makeText(this, "Permission not granted.", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = VpnService.prepare(getApplicationContext());
        if (intent != null) {
            // we have not permission so taking it :)
            activityResultLauncher.launch(intent);
        }


        pova = getSharedPreferences("pova", Context.MODE_PRIVATE);

        if (pova.contains(Pova)) {


            pova.getString(Pova, null);

        }
        url3 = decodeBase64(url3);
        updial= new ProgressDialog(this,R.style.MyDialogStyle);


        if (savedInstanceState == null) {
            V2rayController.init(this, R.drawable.ic_launcher, "V2ray Android");


        }

        contextt = findViewById(R.id.core_version);
        ImageView imageView1 = findViewById(R.id.flgimg);
        imageView1.setBackgroundResource(flagsave.getInt("flagsave", R.drawable.ic_launcher));

        connected_server_delay = findViewById(R.id.connected_server_delay);
        vmessLinks = new ArrayList<String>();
        pingrun = new ArrayList<>();

        vpnon = findViewById(R.id.btnToggle);

        // initialize shared preferences for save or reload default config
        // reload previous config to edit text
        vpnon.setOnClickListener(view -> {
            if (V2rayController.getConnectionState() == V2rayConstants.CONNECTION_STATES.DISCONNECTED) {

//String t = "vless://635ffc16-45cc-4716-bdef-2486398240a3@iranshopnetwork.purtuf.ir:2096?path=%2F&security=tls&encryption=none&alpn=http/1.1&host=iranshopnetwork.purtuf.ir&fp=chrome&type=ws&sni=iranshopnetwork.purtuf.ir#nahidv4-vipnahid";


                if (contextt.getText().equals(getString(R.string.notconnect)) || contextt.getText().toString().contains(getString(R.string.retry)) || contextt.getText().toString().contains(khata) ){


                    connecting();
                   // V2rayController.startV2ray(MainActivity.this, "Server", t, null);

                    cont();
                }else {
                    Toast.makeText(this, getString(R.string.wait), Toast.LENGTH_SHORT).show();
                }





            } else {
                V2rayController.stopV2ray(this);
                contextt.setTextColor(Color.RED);
                pingrun.clear();

            }
        });

        if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }

        }

        // Check the connection delay of connected config.
        connected_server_delay.setOnClickListener(view -> {
            if (V2rayController.getConnectionState() == V2rayConstants.CONNECTION_STATES.CONNECTED) {

                connected_server_delay.setText(getString(R.string.testing));

                V2rayController.getConnectedV2rayServerDelay(this, delayResult -> runOnUiThread(() -> connected_server_delay.setText(getString(R.string.ping) + delayResult + "ms")));



            }else {
                Toast.makeText(this, getString(R.string.ebteda), Toast.LENGTH_SHORT).show();
            }


            // Don`t forget to do ui jobs in ui thread!
           ;
        });
        // Another way to check the connection delay of a config without connecting to it.


        // Check connection state when activity launch
        switch (V2rayController.getConnectionState()) {
            case CONNECTED:
                contextt.setText(getString(R.string.connected));
                setConnected();

                break;
            case DISCONNECTED:
                contextt.setText(getString(R.string.notconnect));
                contextt.setTextColor(Color.RED);


                setDisconnected();

                break;
            case CONNECTING:
                break;
            default:
                break;
        }
        //I tested several different ways to send information from the connection process side
        // to other places (such as interfaces, AIDL and singleton ,...) apparently the best way
        // to send information is broadcast.
        // So v2ray library will be broadcast information with action V2RAY_CONNECTION_INFO.
        v2rayBroadCastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                runOnUiThread(() -> {
                    switch ((V2rayConstants.CONNECTION_STATES) Objects.requireNonNull(intent.getExtras().getSerializable(SERVICE_CONNECTION_STATE_BROADCAST_EXTRA))) {
                        case CONNECTED:

        setConnected();
                            contextt.setText(getString(R.string.connected));

                            contextt.setTextColor(Color.GREEN);





                            break;
                        case DISCONNECTED:
                            contextt.setText(getString(R.string.notconnect));
                            contextt.setTextColor(Color.RED);



                            setDisconnected();
                            break;
                        case CONNECTING:
                            break;
                        default:
                            break;
                    }
                });
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(v2rayBroadCastReceiver, new IntentFilter(V2RAY_SERVICE_STATICS_BROADCAST_INTENT), RECEIVER_EXPORTED);
        } else {
            registerReceiver(v2rayBroadCastReceiver, new IntentFilter(V2RAY_SERVICE_STATICS_BROADCAST_INTENT));
        }
    }
    private void setDisconnected() {
        vpnon.setBackground(getResources().getDrawable(R.drawable.circle_button));
        vpnon.setText("OFF");
    }

    private void setConnected() {
        vpnon.setBackground(getResources().getDrawable(R.drawable.circle_button_on));
        vpnon.setText("ON");
    }
    private void connecting() {
        vpnon.setBackground(getResources().getDrawable(R.drawable.circle_button_connecting));
        vpnon.setText("....");
    }
    public static String getDefaultConfig() {
        return "";
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (locationAccepted && cameraAccepted) {

                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (v2rayBroadCastReceiver != null){
            unregisterReceiver(v2rayBroadCastReceiver);
        }
        if (proxyThread != null && proxyThread.isAlive()) {
            proxyThread.interrupt();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void fff() {
        int numServers = vmessLinks.size();
        CountDownLatch latch = new CountDownLatch(numServers);

        for (int j = 0; j < numServers; j++) {
            executor = Executors.newFixedThreadPool(1); // افزایش تعداد نخ‌ها برای بهبود سرعت

            int finalJ = j;
            executor.submit(() -> {
                try {
                    if (finalJ % 2 == 0) {
                        runOnUiThread(() -> {
                            contextt.setTextColor(Color.YELLOW);
                            contextt.setText(getString(R.string.testserver));
                        });
                    } else {
                        runOnUiThread(() -> {
                            contextt.setTextColor(Color.YELLOW);
                            contextt.setText(getString(R.string.selectserver));
                        });
                    }

                    Thread.sleep(1000); // کاهش زمان خواب برای بهبود سرعت

                    String selectedText = vmessLinks.get(finalJ);
                    if (selectedText.startsWith("ss://")) {
                        selectedText = sss(selectedText);
                    }

                    long server_delay = V2rayCoreExecutor.getConfigDelay(Utilities.normalizeV2rayFullConfig(selectedText));
                    if (server_delay > 0) {
                        pingrun.add(new Bestserver(selectedText, longToIntJavaWithMath(server_delay)));

                        // مرتب‌سازی لیست بر اساس پینگ (age)
                        Collections.sort(pingrun, new Comparator<Bestserver>() {
                            @Override
                            public int compare(Bestserver b1, Bestserver b2) {
                                return Integer.compare(b1.getPing(), b2.getPing());
                            }
                        });

                        // پس از مرتب‌سازی، کمترین پینگ در ایندکس صفر قرار دارد
                        Bestserver bestPingServer = pingrun.get(0);
                        lowping = bestPingServer.getName();
                           loname  = bestPingServer.getPing();
                        Log.v("foooooooooooooooooooooooooooo", lowping);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        executor.submit(() -> {
            try {
                if (!latch.await(20, TimeUnit.SECONDS)) {
                    // Timeout reached
                    runOnUiThread(() -> {
                        contextt.setTextColor(Color.RED);
                        contextt.setText(getString(R.string.khatad));
                    });
                    return;
                }

                runOnUiThread(() -> {
                    if (!pingrun.isEmpty()) {
                       // startMyThread();

                        pingrun.set(0,new Bestserver(lowping,loname));
                        String yy = pingrun.get(0).getName();
                        contextt.setTextColor(Color.GREEN);
                        contextt.setText(getString(R.string.moj));
                    //    Log.v("tyyyyyyyyyyyyyyyy",yy);
                        V2rayController.startV2ray(MainActivity.this, "Test Server", yy, null);
                        myloc();


                        pova = getSharedPreferences("pova", Context.MODE_PRIVATE);
                        SharedPreferences.Editor iran2edit = pova.edit();
                        iran2edit.putString(Pova, lowping);
                        iran2edit.apply();
                    } else {
                        contextt.setTextColor(Color.RED);
                        contextt.setText(getString(R.string.khatta));
                    }
                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        executor.shutdown(); // بستن ExecutorService پس از پایان کار
    }




    public void update() {


        alertDialogBuilder2 = new AlertDialog.Builder(
                MainActivity.this, R.style.MyDialogStyle);

        // set title
        alertDialogBuilder2.setTitle(getString(R.string.update));

        // set dialog message
        alertDialogBuilder2
                .setMessage(getString(R.string.doyoudo))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.check), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //json
                        updial.setMessage(getString(R.string.wait));
                        updial.show();


                        StringRequest request = new StringRequest("https://raw.githubusercontent.com/davudsedft/purlite/refs/heads/main/link/version.json", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String string) {
                                parseJsonData(string);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(getApplicationContext(), getString(R.string.khata), Toast.LENGTH_SHORT).show();
                                updial.dismiss();
                            }
                        });

                        RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
                        rQueue.add(request);



                        //endjson


                    }
                }).setNegativeButton(getString(R.string.enseraf), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close


                        dialog.cancel();

                    }
                });


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder2.create();

        // show it
        alertDialog.show();
        TextView messageView = (TextView)alertDialog.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);



    }


    public   void parseJsonData(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray fruitsArray = object.getJSONArray("purvpn");
            //   JSONStringer gh = new JSONStringer(jsonString);
            //  ArrayList al = new ArrayList();
            List<String> list = new ArrayList<String>();
            List<String> list2 = new ArrayList<String>();
            List<String> list3 = new ArrayList<String>();

            list.add(fruitsArray.getString(0));
            list2.add(fruitsArray.getString(1));



            String joined = TextUtils.join("",list);
            String joined3 = TextUtils.join("",list2);


            float gf=Float.parseFloat(joined3)/10;

            int number = Integer.parseInt(joined);
            float num = number+gf;

            PackageInfo pinfo = null;
            try {
                pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            }
            catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            assert pinfo != null;
            String versionName = pinfo.versionName;
            // String ff = BuildConfig.VERSION_NAME;
            float verCode=Float.parseFloat(versionName);
            if (num> verCode){


                loopthead();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        updial.dismiss();


                        // set title

                        alertDialogBuilder2 = new AlertDialog.Builder(
                                MainActivity.this, R.style.MyDialogStyle);


                        // set dialog message
                        alertDialogBuilder2
                                .setTitle(getString(R.string.dastras))
                                .setCancelable(true)
                                .setPositiveButton(getString(R.string.Dwnload), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {



                                        if (utext2 != null){


                                            progressDialog = new ProgressDialog(MainActivity.this, R.style.MyDialogStyle);
                                            progressDialog.setMessage(getString(R.string.sabr));
                                            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                            progressDialog.setCancelable(false);
                                            progressDialog.setMax(100);
                                            progressDialog.show();

                                            startDownload(utext2);


                                            registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));








                                        }else {
                                            Toast.makeText(MainActivity.this, getString(R.string.khatta), Toast.LENGTH_SHORT).show();
                                        }




                                    }
                                }).setNegativeButton(getString(R.string.badan), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, close


                                        dialog.cancel();

                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder2.create();

                        // show it
                        alertDialog.show();

                    }
                }, 2000);
















            }else {
                updial.dismiss();

                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogStyle);

                builder.setMessage(getString(R.string.tabrik))
                        .setCancelable(true);
                builder.setTitle(getString(R.string.beruz));

                AlertDialog alert = builder.create();

                // alert.setIcon(R.drawable.bej);
                alert.show();
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    public   void parseJsonData2(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray fruitsArray = object.getJSONArray("purvpn");
            //   JSONStringer gh = new JSONStringer(jsonString);
            //  ArrayList al = new ArrayList();
            List<String> list = new ArrayList<String>();
            List<String> list2 = new ArrayList<String>();
            List<String> list3 = new ArrayList<String>();

            list.add(fruitsArray.getString(0));
            list2.add(fruitsArray.getString(1));



            String joined = TextUtils.join("",list);
            String joined3 = TextUtils.join("",list2);


            float gf=Float.parseFloat(joined3)/10;

            int number = Integer.parseInt(joined);
            float num = number+gf;

            PackageInfo pinfo = null;
            try {
                pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            }
            catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            assert pinfo != null;
            String versionName = pinfo.versionName;
            // String ff = BuildConfig.VERSION_NAME;
            float verCode=Float.parseFloat(versionName);
            if (num> verCode){

                version.setText(getString(R.string.beruzzz)+"V"+verCode);

                version.setTextColor(Color.RED);




            }else {
                version.setText(getString(R.string.isberuz)+"V"+verCode);
                version.setTextColor(Color.GREEN);


            }




        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public  void loopthead(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    loop();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
    public void loop() throws IOException {
        String fileUrl = "https://raw.githubusercontent.com/davudsedft/purlite/refs/heads/main/link/link.txt";

        if (isLinkValid("https://github.com")) {
            System.out.println("لینک معتبر است.");
            URL textUrl = new URL(fileUrl);

            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection)textUrl.openConnection();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            connection.setRequestMethod("GET");

            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            reader.close();
            utext2 = stringBuilder.toString();


        } else {
            System.out.println("لینک معتبر نیست یا وجود ندارد.");
         runOnUiThread(()->{
             Toast.makeText(getBaseContext(), "خطا رخ داد", Toast.LENGTH_SHORT).show();
         });

        }







        //  URL textUrl = null;


    }
    public void cont() {

        http = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            contextt.setTextColor(Color.YELLOW);
                            contextt.setText(getString(R.string.connecting));
                        }
                    });
                    String url2 = "https://raw.githubusercontent.com/davudsedft/newpurnet/refs/heads/main/porcowlite.txt";

//

                    if (isLinkValid(url2)) {
                        System.out.println("لینک معتبر است.");

                        System.out.println("لینک معتبر است.");

                        URL textUrl2 = null;
                        try {
                            textUrl2 = new URL(url2);

                            URLConnection connection2 = null;
                            connection2 = textUrl2.openConnection();
                            connection2.setConnectTimeout(5000);
                            connection2.setReadTimeout(5000);
                            InputStream inputStream2 = connection2.getInputStream();
                            BufferedReader reader2 = new BufferedReader(new InputStreamReader(inputStream2));
                            StringBuilder stringBuilder2 = new StringBuilder();
                            String line2;
                            while ((line2 = reader2.readLine()) != null) {
                                stringBuilder2.append(line2).append("\n");
                            }
                            reader2.close();

                            utext3 = stringBuilder2.toString();



                        } catch (Exception ignored) {
                        }

                        // String[] linkstring = utext.split("https://");


                        if (utext3 != null){



                            linq = getSharedPreferences("linq", Context.MODE_PRIVATE);
                            SharedPreferences.Editor iran2edit = linq.edit();

                            iran2edit.putString("linq", utext3);
                            iran2edit.apply();





















                            String link2 = linq.getString("linq", "");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    contextt.setTextColor(Color.YELLOW);
                                    contextt.setText(getString(R.string.makhzan));
                                }
                            });
                            // String url = link2;

                            try {
                                URL textUrl = new URL(link2);
                                URLConnection connection = null;
                                connection = textUrl.openConnection();
                                connection.setReadTimeout(5000);
                                connection.setReadTimeout(5000);
                                InputStream inputStream = connection.getInputStream();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                                StringBuilder stringBuilder = new StringBuilder();
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    stringBuilder.append(line).append("\n");
                                }
                                reader.close();
                                utext = stringBuilder.toString();

                            } catch (Exception e) {
                            }

                            if (utext != null){

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        contextt.setText(getString(R.string.barresi));

                                    }
                                });


                                if (isBase64(utext)) {
                                    // ابتدا تبدیل به رشته معمولی
                                    utext = decodeBase64(utext);

                                    // ادامه کد...
                                }



                                // utext = utext.replace("vmess://" , " ");
                                Pattern pattern = Pattern.compile("(ss|vmess|trojan|vless)://[^\n]+");
                                Matcher matcher = pattern.matcher(utext);
                                int u=0;
                                while (matcher.find() && u<200) {
                                    u++;
                                    vmessLinks.add(matcher.group());

                                }
                           //    startMyThread();
                                fff();



                            }else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        contextt.setText(getString(R.string.khatad));
                                        contextt.setTextColor(Color.rgb(153, 153, 0));
                                    }
                                });
                            }
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                contextt.setText(getString(R.string.khatad));
                                contextt.setTextColor(Color.rgb(153, 153, 0));
                            }
                        });
                    }

//        for (int i = 0; i < vmessLinks.size(); i++) {
//            vmessLinks.set(i, vmessLinks.get(i).replace("vmess://", ""));
//        }

                        Log.v("kkkkkkkkk",vmessLinks.get(10));


                        // vmessLinks.add(substrings.replace("vmess://" , " "));
//new Thread(new Runnable() {
//    @Override
//    public void run() {
//        try {
//            Thread.sleep(200);
//
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}).start();




                    } else {
                        System.out.println("لینک معتبر نیست یا وجود ندارد.");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                contextt.setTextColor(Color.RED);
                                contextt.setText(getString(R.string.khata));
                            }
                        });
                    }



                    //  Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        http.start();




    }
    private static boolean isBase64(String input) {
        try {
            Base64.decode(input, Base64.DEFAULT);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private  String decodeBase64(String coded) {
        byte[] valueDecoded = new byte[0];
        try {
            valueDecoded = Base64.decode(coded.getBytes("UTF-8"), Base64.DEFAULT);
        } catch (UnsupportedEncodingException ignored) {
        }
        return new String(valueDecoded);
    }

    private void startMyThread2() {
        myt = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("10 seccccc");
                    try {
                        sleep(10000); // تاخیر ۱۰ ثانیه
                        // اجرای عملیات مورد نظر
                        if (pingrun.size()>0){
                            if (executor.isTerminated()){

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        contextt.setTextColor(Color.GREEN);
                                        contextt.setText(getString(R.string.moj));
                                        try {
                                            sleep(2000);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }

                                    }


                                });
                                myt.interrupt();
                            }



                            //   this.interrupt();
                        }else {
                            if (executor.isTerminated()){

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        contextt.setTextColor(Color.RED);
                                        contextt.setText(getString(R.string.khatta));

                                    }
                                });
                                myt.interrupt();
                            }



                            // this.interrupt();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return; // بازگشت از ترد
                    }
                }
            }
        });
        myt.start();
    }

    private void lasstrun() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                V2rayController.startV2ray(MainActivity.this, "Test Server", pova.getString(Pova,""), null);

            }
        });
    }

    public int longToIntJavaWithMath(long number) {
        return Math.toIntExact(number);
    }
    public static boolean isLinkValid(String url) {
        try {
            URL linkUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) linkUrl.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            return (responseCode == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            return false;
        }
    }


    private  String sss(String link) {
       String sss = null;

        try {
            String[] su = link.split("@");
            String[] m = su[1].split("#");
            String[] serverport = m[0].split(":");


            String s = su[0];
            s = s.replace("ss://", "");
            s = decodeBase64(s);
            String[] ramzpass = s.split(":");

            String server = serverport[0];
            String port = serverport[1];
            String auth = ramzpass[0];
            String pass = ramzpass[1];
            String name = m[1];


            sss = "{\n" +
                    "  \"dns\": {\n" +
                    "    \"hosts\": {\n" +
                    "      \"domain:googleapis.cn\": \"googleapis.com\"\n" +
                    "    },\n" +
                    "    \"servers\": [\n" +
                    "      \"1.1.1.1\"\n" +
                    "    ]\n" +
                    "  },\n" +
                    "  \"inbounds\": [\n" +
                    "    {\n" +
                    "      \"listen\": \"127.0.0.1\",\n" +
                    "      \"port\": 10808,\n" +
                    "      \"protocol\": \"socks\",\n" +
                    "      \"settings\": {\n" +
                    "        \"auth\": \"noauth\",\n" +
                    "        \"udp\": true,\n" +
                    "        \"userLevel\": 8\n" +
                    "      },\n" +
                    "      \"sniffing\": {\n" +
                    "        \"destOverride\": [\n" +
                    "          \"http\",\n" +
                    "          \"tls\"\n" +
                    "        ],\n" +
                    "        \"enabled\": true\n" +
                    "      },\n" +
                    "      \"tag\": \"socks\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"listen\": \"127.0.0.1\",\n" +
                    "      \"port\": 10809,\n" +
                    "      \"protocol\": \"http\",\n" +
                    "      \"settings\": {\n" +
                    "        \"userLevel\": 8\n" +
                    "      },\n" +
                    "      \"tag\": \"http\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"log\": {\n" +
                    "    \"loglevel\": \"warning\"\n" +
                    "  },\n" +
                    "  \"outbounds\": [\n" +
                    "    {\n" +
                    "      \"mux\": {\n" +
                    "        \"concurrency\": -1,\n" +
                    "        \"enabled\": false,\n" +
                    "        \"xudpConcurrency\": 8,\n" +
                    "        \"xudpProxyUDP443\": \"\"\n" +
                    "      },\n" +
                    "      \"protocol\": \"shadowsocks\",\n" +
                    "      \"settings\": {\n" +
                    "        \"servers\": [\n" +
                    "          {\n" +
                    "            \"address\": \"" + server + "\",\n" +
                    "            \"level\": 8,\n" +
                    "            \"method\": \"" + auth + "\",\n" +
                    "            \"ota\": false,\n" +
                    "            \"password\": \"" + pass + "\",\n" +
                    "            \"port\": " + port + "\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      \"streamSettings\": {\n" +
                    "        \"network\": \"tcp\",\n" +
                    "        \"security\": \"\"\n" +
                    "      },\n" +
                    "      \"tag\": \"proxy\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"protocol\": \"freedom\",\n" +
                    "      \"settings\": {},\n" +
                    "      \"tag\": \"direct\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"protocol\": \"blackhole\",\n" +
                    "      \"settings\": {\n" +
                    "        \"response\": {\n" +
                    "          \"type\": \"http\"\n" +
                    "        }\n" +
                    "      },\n" +
                    "      \"tag\": \"block\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"routing\": {\n" +
                    "    \"domainStrategy\": \"IPIfNonMatch\",\n" +
                    "    \"rules\": [\n" +
                    "      {\n" +
                    "        \"ip\": [\n" +
                    "          \"1.1.1.1\"\n" +
                    "        ],\n" +
                    "        \"outboundTag\": \"proxy\",\n" +
                    "        \"port\": \"53\",\n" +
                    "        \"type\": \"field\"\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "}";

        }catch (Exception e){
            sss = "{\n" +
                    "  \"dns\": {\n" +
                    "    \"hosts\": {\n" +
                    "      \"domain:googleapis.cn\": \"googleapis.com\"\n" +
                    "    },\n" +
                    "    \"servers\": [\n" +
                    "      \"1.1.1.1\"\n" +
                    "    ]\n" +
                    "  },\n" +
                    "  \"inbounds\": [\n" +
                    "    {\n" +
                    "      \"listen\": \"127.0.0.1\",\n" +
                    "      \"port\": 10808,\n" +
                    "      \"protocol\": \"socks\",\n" +
                    "      \"settings\": {\n" +
                    "        \"auth\": \"noauth\",\n" +
                    "        \"udp\": true,\n" +
                    "        \"userLevel\": 8\n" +
                    "      },\n" +
                    "      \"sniffing\": {\n" +
                    "        \"destOverride\": [\n" +
                    "          \"http\",\n" +
                    "          \"tls\"\n" +
                    "        ],\n" +
                    "        \"enabled\": true\n" +
                    "      },\n" +
                    "      \"tag\": \"socks\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"listen\": \"127.0.0.1\",\n" +
                    "      \"port\": 10809,\n" +
                    "      \"protocol\": \"http\",\n" +
                    "      \"settings\": {\n" +
                    "        \"userLevel\": 8\n" +
                    "      },\n" +
                    "      \"tag\": \"http\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"log\": {\n" +
                    "    \"loglevel\": \"warning\"\n" +
                    "  },\n" +
                    "  \"outbounds\": [\n" +
                    "    {\n" +
                    "      \"mux\": {\n" +
                    "        \"concurrency\": -1,\n" +
                    "        \"enabled\": false,\n" +
                    "        \"xudpConcurrency\": 8,\n" +
                    "        \"xudpProxyUDP443\": \"\"\n" +
                    "      },\n" +
                    "      \"protocol\": \"shadowsocks\",\n" +
                    "      \"settings\": {\n" +
                    "        \"servers\": [\n" +
                    "          {\n" +
                    "            \"address\": \"156.146.38.163\",\n" +
                    "            \"level\": 8,\n" +
                    "            \"method\": \"aes-128-cfb\",\n" +
                    "            \"ota\": false,\n" +
                    "            \"password\": \"shadowsocks\",\n" +
                    "            \"port\": 443\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      \"streamSettings\": {\n" +
                    "        \"network\": \"tcp\",\n" +
                    "        \"security\": \"\"\n" +
                    "      },\n" +
                    "      \"tag\": \"proxy\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"protocol\": \"freedom\",\n" +
                    "      \"settings\": {},\n" +
                    "      \"tag\": \"direct\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"protocol\": \"blackhole\",\n" +
                    "      \"settings\": {\n" +
                    "        \"response\": {\n" +
                    "          \"type\": \"http\"\n" +
                    "        }\n" +
                    "      },\n" +
                    "      \"tag\": \"block\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"remarks\": \"\uD83C\uDDFA\uD83C\uDDF8US-156.146.38.163-0262\",\n" +
                    "  \"routing\": {\n" +
                    "    \"domainStrategy\": \"IPIfNonMatch\",\n" +
                    "    \"rules\": [\n" +
                    "      {\n" +
                    "        \"ip\": [\n" +
                    "          \"1.1.1.1\"\n" +
                    "        ],\n" +
                    "        \"outboundTag\": \"proxy\",\n" +
                    "        \"port\": \"53\",\n" +
                    "        \"type\": \"field\"\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "}";

        }




        return sss;

    }



    public String getV2rayServerDelay(String config) {

        try {
            int  server_delay = longToIntJavaWithMath(V2rayCoreExecutor.getConfigDelay(Utilities.normalizeV2rayFullConfig(config)));



            if (server_delay == -1) {
                return " سرور مشکل دارد : " + "🤦‍";



            } else {
                if (server_delay >= 0 && server_delay < 600) {
                    return "پینگ جت: " + String.valueOf(server_delay);

                } else if (server_delay >= 600 && server_delay <= 800) {
                    return "پینگ عالی: " + String.valueOf(server_delay);


                } else {
                    if (server_delay > 800 && server_delay < 1000) {
                        return "پینگ خوب : " + String.valueOf(server_delay);


                    } else if (server_delay >= 1000 && server_delay < 1500) {
                        return "پینگ معمولی: " + String.valueOf(server_delay);


                    }

                    else if (server_delay==-2) {
                        return " رییید در اینترنت: " + String.valueOf(server_delay);


                    }

                    else {
                        return "پینگ ضعیف: " + String.valueOf(server_delay);

                    }

                }

            }
        }catch (Exception ignored){

        }



        return "خطا";
    }


    private  String vll(String url){

        String protocol = url.substring(0, url.indexOf("://"));
        String rest = url.substring(url.indexOf("://") + 3);
        String uuid = rest.substring(0, rest.indexOf('@'));
        rest = rest.substring(rest.indexOf('@') + 1);
        String server = rest.substring(0, rest.indexOf(':'));
        rest = rest.substring(rest.indexOf(':') + 1);
        String port2 = rest.substring(0, rest.indexOf('?'));
        int port = Integer.parseInt(port2);

        rest = rest.substring(rest.indexOf('?') + 1);
        String[] params = rest.split("&");
        String security = params[0].split("=")[1];
        String encryption = params[1].split("=")[1];
        String host = params[2].split("=")[1];
        String headerType = params[3].split("=")[1];
        String type = params[4].split("=")[1];
        String[]  k = type.split("#");
        type = k[0];
        String app = url.substring(url.indexOf('#') + 1);
        System.out.println("Protocol: " + protocol);
        System.out.println("UUID: " + uuid);
        System.out.println("Server: " + server);
        System.out.println("Port: " + port);
        System.out.println("Security: " + security);
        System.out.println("Encryption: " + encryption);
        System.out.println("Host: " + host);
        System.out.println("HeaderType: " + headerType);
        System.out.println("Type: " + type);
        System.out.println("App: " + app);

        String mm = "{\n" +
                "  \"dns\": {\n" +
                "    \"hosts\": {\n" +
                "      \"geosite:category-ads-all\": \"127.0.0.1\",\n" +
                "      \"domain:googleapis.cn\": \"googleapis.com\",\n" +
                "      \"dns.pub\": [\n" +
                "        \"1.12.12.12\",\n" +
                "        \"120.53.53.53\"\n" +
                "      ],\n" +
                "      \"dns.alidns.com\": [\n" +
                "        \"223.5.5.5\",\n" +
                "        \"223.6.6.6\",\n" +
                "        \"2400:3200::1\",\n" +
                "        \"2400:3200:baba::1\"\n" +
                "      ],\n" +
                "      \"one.one.one.one\": [\n" +
                "        \"1.1.1.1\",\n" +
                "        \"1.0.0.1\",\n" +
                "        \"2606:4700:4700::1111\",\n" +
                "        \"2606:4700:4700::1001\"\n" +
                "      ],\n" +
                "      \"dns.google\": [\n" +
                "        \"8.8.8.8\",\n" +
                "        \"8.8.4.4\",\n" +
                "        \"2001:4860:4860::8888\",\n" +
                "        \"2001:4860:4860::8844\"\n" +
                "      ]\n" +
                "    },\n" +
                "    \"servers\": [\n" +
                "      \"1.1.1.1\",\n" +
                "      {\n" +
                "        \"address\": \"1.1.1.1\",\n" +
                "        \"domains\": [\n" +
                "          \"domain:googleapis.cn\",\n" +
                "          \"domain:gstatic.com\"\n" +
                "        ],\n" +
                "        \"port\": 53\n" +
                "      },\n" +
                "      {\n" +
                "        \"address\": \"223.5.5.5\",\n" +
                "        \"domains\": [\n" +
                "          \"domain:dns.alidns.com\",\n" +
                "          \"domain:dns.pub\",\n" +
                "          \"domain:doh.pub\",\n" +
                "          \"domain:dot.pub\",\n" +
                "          \"domain:doh.360.cn\",\n" +
                "          \"domain:dot.360.cn\",\n" +
                "          \"geosite:cn\",\n" +
                "          \"geosite:geolocation-cn\"\n" +
                "        ],\n" +
                "        \"expectIPs\": [\n" +
                "          \"geoip:cn\"\n" +
                "        ],\n" +
                "        \"port\": 53\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"inbounds\": [\n" +
                "    {\n" +
                "      \"listen\": \"127.0.0.1\",\n" +
                "      \"port\": 10808,\n" +
                "      \"protocol\": \"socks\",\n" +
                "      \"settings\": {\n" +
                "        \"auth\": \"noauth\",\n" +
                "        \"udp\": true,\n" +
                "        \"userLevel\": 8\n" +
                "      },\n" +
                "      \"sniffing\": {\n" +
                "        \"destOverride\": [\n" +
                "          \"http\",\n" +
                "          \"tls\"\n" +
                "        ],\n" +
                "        \"enabled\": true,\n" +
                "        \"routeOnly\": false\n" +
                "      },\n" +
                "      \"tag\": \"socks\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"listen\": \"127.0.0.1\",\n" +
                "      \"port\": 10809,\n" +
                "      \"protocol\": \"http\",\n" +
                "      \"settings\": {\n" +
                "        \"userLevel\": 8\n" +
                "      },\n" +
                "      \"tag\": \"http\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"log\": {\n" +
                "    \"loglevel\": \"warning\"\n" +
                "  },\n" +
                "  \"outbounds\": [\n" +
                "    {\n" +
                "      \"mux\": {\n" +
                "        \"concurrency\": -1,\n" +
                "        \"enabled\": false,\n" +
                "        \"xudpConcurrency\": 8,\n" +
                "        \"xudpProxyUDP443\": \"\"\n" +
                "      },\n" +
                "      \"protocol\": \"vless\",\n" +
                "      \"settings\": {\n" +
                "        \"vnext\": [\n" +
                "          {\n" +
                "            \"address\": \""+server+"\",\n" +
                "            \"port\": "+port+",\n" +
                "            \"users\": [\n" +
                "              {\n" +
                "                \"encryption\": \"none\",\n" +
                "                \"flow\": \"\",\n" +
                "                \"id\": \""+uuid+"\",\n" +
                "                \"level\": 8,\n" +
                "                \"security\": \"auto\"\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"streamSettings\": {\n" +
                "        \"network\": \""+type+"\",\n" +
                "        \"security\": \"none\",\n" +
                "        \"tcpSettings\": {\n" +
                "          \"header\": {\n" +
                "            \"request\": {\n" +
                "              \"headers\": {\n" +
                "                \"Connection\": [\n" +
                "                  \"keep-alive\"\n" +
                "                ],\n" +
                "                \"Host\": [\n" +
                "                  \""+host+"\"\n" +
                "                ],\n" +
                "                \"Pragma\": \"no-cache\",\n" +
                "                \"Accept-Encoding\": [\n" +
                "                  \"gzip, deflate\"\n" +
                "                ],\n" +
                "                \"User-Agent\": [\n" +
                "                  \"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36\",\n" +
                "                  \"Mozilla/5.0 (iPhone; CPU iPhone OS 10_0_2 like Mac OS X) AppleWebKit/601.1 (KHTML, like Gecko) CriOS/53.0.2785.109 Mobile/14A456 Safari/601.1.46\"\n" +
                "                ]\n" +
                "              },\n" +
                "              \"method\": \"GET\",\n" +
                "              \"path\": [\n" +
                "                \"/\"\n" +
                "              ],\n" +
                "              \"version\": \"1.1\"\n" +
                "            },\n" +
                "            \"type\": \""+headerType+"\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"tag\": \"proxy\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"protocol\": \"freedom\",\n" +
                "      \"settings\": {\n" +
                "        \"domainStrategy\": \"UseIP\"\n" +
                "      },\n" +
                "      \"tag\": \"direct\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"protocol\": \"blackhole\",\n" +
                "      \"settings\": {\n" +
                "        \"response\": {\n" +
                "          \"type\": \"http\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"tag\": \"block\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"remarks\": \"app-wa7t0kga\",\n" +
                "  \"routing\": {\n" +
                "    \"domainStrategy\": \"AsIs\"," +
                "\"rules\": [\n" +
                "      {\n" +
                "        \"ip\": [\n" +
                "          \"1.1.1.1\"\n" +
                "        ],\n" +
                "        \"outboundTag\": \"proxy\",\n" +
                "        \"port\": \"53\",\n" +
                "        \"type\": \"field\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"ip\": [\n" +
                "          \"223.5.5.5\"\n" +
                "        ],\n" +
                "        \"outboundTag\": \"direct\",\n" +
                "        \"port\": \"53\",\n" +
                "        \"type\": \"field\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"domain\": [\n" +
                "          \"domain:googleapis.cn\",\n" +
                "          \"domain:gstatic.com\"\n" +
                "        ],\n" +
                "        \"outboundTag\": \"proxy\",\n" +
                "        \"type\": \"field\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"network\": \"udp\",\n" +
                "        \"outboundTag\": \"block\",\n" +
                "        \"port\": \"443\",\n" +
                "        \"type\": \"field\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"domain\": [\n" +
                "          \"geosite:category-ads-all\"\n" +
                "        ],\n" +
                "        \"outboundTag\": \"block\",\n" +
                "        \"type\": \"field\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"ip\": [\n" +
                "          \"geoip:private\"\n" +
                "        ],\n" +
                "        \"outboundTag\": \"direct\",\n" +
                "        \"type\": \"field\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"domain\": [\n" +
                "          \"geosite:private\"\n" +
                "        ],\n" +
                "        \"outboundTag\": \"direct\",\n" +
                "        \"type\": \"field\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"domain\": [\n" +
                "          \"domain:dns.alidns.com\",\n" +
                "          \"domain:dns.pub\",\n" +
                "          \"domain:doh.pub\",\n" +
                "          \"domain:dot.pub\",\n" +
                "          \"domain:doh.360.cn\",\n" +
                "          \"domain:dot.360.cn\",\n" +
                "          \"geosite:cn\",\n" +
                "          \"geosite:geolocation-cn\"\n" +
                "        ],\n" +
                "        \"outboundTag\": \"direct\",\n" +
                "        \"type\": \"field\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"ip\": [\n" +
                "          \"223.5.5.5/32\",\n" +
                "          \"223.6.6.6/32\",\n" +
                "          \"2400:3200::1/128\",\n" +
                "          \"2400:3200:baba::1/128\",\n" +
                "          \"119.29.29.29/32\",\n" +
                "          \"1.12.12.12/32\",\n" +
                "          \"120.53.53.53/32\",\n" +
                "          \"2402:4e00::/128\",\n" +
                "          \"2402:4e00:1::/128\",\n" +
                "          \"180.76.76.76/32\",\n" +
                "          \"2400:da00::6666/128\",\n" +
                "          \"114.114.114.114/32\",\n" +
                "          \"114.114.115.115/32\",\n" +
                "          \"180.184.1.1/32\",\n" +
                "          \"180.184.2.2/32\",\n" +
                "          \"101.226.4.6/32\",\n" +
                "          \"218.30.118.6/32\",\n" +
                "          \"123.125.81.6/32\",\n" +
                "          \"140.207.198.6/32\",\n" +
                "          \"geoip:cn\"\n" +
                "        ],\n" +
                "        \"outboundTag\": \"direct\",\n" +
                "        \"type\": \"field\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"outboundTag\": \"proxy\",\n" +
                "        \"port\": \"0-65535\",\n" +
                "        \"type\": \"field\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";


        return  mm;

    }

    public  String utf88(String ss){
        byte[] bytes=ss.getBytes();
        String s=new String(bytes, StandardCharsets.UTF_8);
        // String sf = new String(bytes, StandardCharsets.UTF_8);
        s = new String(s.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        return s;

    }
    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    public  void downloadAndInstallApk(Context context, String url) {

        File previousFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "purlite.apk");
        if (previousFile.exists()) {
            previousFile.delete(); // حذف فایل قبلی
        }
        progressDialog = new ProgressDialog(this,R.style.MyDialogStyle);
        progressDialog.setMessage(getString(R.string.stayhere));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("دانلود purlite");
        request.setDescription("purvpn...");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "purlite.apk");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        long downloadId = downloadManager.enqueue(request);

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (downloadId == id) {
                    Intent installIntent = new Intent(Intent.ACTION_VIEW);
                    installIntent.setDataAndType(downloadManager.getUriForDownloadedFile(downloadId), "application/vnd.android.package-archive");
                    installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    context.startActivity(installIntent);
                    progressDialog.dismiss();

                }
            }
        }, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true ;
    }

    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.as){
            if (drawerLayout.isDrawerOpen(Gravity.RIGHT)){
                drawerLayout.closeDrawer(Gravity.RIGHT);
            }else {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        }else {
            Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
    private void setApplicationLocale() {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(new Locale("fa"));
        } else {
            config.setLocale(new Locale("fa"));
        }
        resources.updateConfiguration(config, dm);
    }
    private void setApplicationLocale2() {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(new Locale("en"));
        } else {
            config.setLocale(new Locale("en"));
        }
        resources.updateConfiguration(config, dm);
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.RIGHT)){
            drawerLayout.closeDrawer(Gravity.RIGHT);
        }else {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                backToast.cancel();
                super.onBackPressed();
                return;
            } else {
                backToast = Toast.makeText(getBaseContext(), getString(R.string.exit), Toast.LENGTH_SHORT);
                backToast.show();
            }
            backPressedTime = System.currentTimeMillis();

        }
        getSharedPreferences("PREFERENCE" , MODE_PRIVATE).edit().putBoolean("firstrun",true).commit();

    }

    private  void myloc2(){
        Log.v("tyyyyyyyyyyyyyyyy",lowping);
        V2rayController.startV2ray(MainActivity.this, "Test Server", lowping, null);


        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (V2rayController.getConnectionState() == V2rayConstants.CONNECTION_STATES.CONNECTED){
            Toast.makeText(MainActivity.this, "jjjjjj", Toast.LENGTH_SHORT).show();

          myloc();

        }
    }

    public void myloc() {
        String ip = pingrun.get(0).getName();

        if (ip.startsWith("vless://")) {
            String[] vless = ip.split("@");
            String[] vless2 = vless[1].split("path=");
            String vl = vless2[0];
            String[] kk = vl.split(":");
            vl = kk[0];

            int count = 0;
            for (int i = 0; i < vl.length(); i++) {
                if (vl.charAt(i) == ':') {
                    count++;

                }

            }
            if (count == 0) {
                ip = vl;
            }



            String ipAddress = ip;
            GeoIpUtil.getCountryByIp(this, ipAddress, new GeoIpUtil.CountryCallback() {
                @Override
                public void onSuccess(String country) {


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            int f =    flag(country);
                            ImageView imageView1 = findViewById(R.id.flgimg);
                            imageView1.setBackgroundResource(f);
                            SharedPreferences.Editor e = flagsave.edit();
                            e.putInt("flagsave",f);
                            e.apply();

                        }
                    }, 2000);

                }

                @Override
                public void onError(String error) {
                }
            });





        }
        if ( ip.contains("dns")) {

            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(ip);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            JSONArray outboundsArray = null;
            try {
                outboundsArray = jsonObj.getJSONArray("outbounds");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            JSONObject shadowsocksObj = null;
            try {
                shadowsocksObj = outboundsArray.getJSONObject(0).getJSONObject("settings").getJSONArray("servers").getJSONObject(0);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            try {
                String vl = shadowsocksObj.getString("address");


                int count = 0;
                for (int i = 0; i < vl.length(); i++) {
                    if (vl.charAt(i) == ':') {
                        count++;

                    }

                }
                if (count == 0) {
                    ip = vl;
                }

                Log.v("ppppppppppppppppppppppppp",ip);

                String ipAddress = ip;
                GeoIpUtil.getCountryByIp(this, ipAddress, new GeoIpUtil.CountryCallback() {
                    @Override
                    public void onSuccess(String country) {


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                            int f =    flag(country);
                                ImageView imageView1 = findViewById(R.id.flgimg);
                                imageView1.setBackgroundResource(f);
                                SharedPreferences.Editor e = flagsave.edit();
                                e.putInt("flagsave",f);
                                e.apply();

                            }
                        }, 3000);

                    }

                    @Override
                    public void onError(String error) {
                    }
                });






            } catch (JSONException e) {
                throw new RuntimeException(e);
            }










        } if (ip.startsWith("vmess://")) {
            ip = ip.replace("vmess://", "");
            //  clipboardtext = clipboardtext.replace("#DF90#", "");

            ip = decodeBase64(ip);

            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject((ip));
                //  int ff = jsonObject.getInt("aid");


                String vl = jsonObject.getString("add");
                int count = 0;
                for (int i = 0; i < vl.length(); i++) {
                    if (vl.charAt(i) == ':') {
                        count++;

                    }

                }
                if (count == 0) {
                    ip = vl;
                }

                String ipAddress = ip;
                GeoIpUtil.getCountryByIp(this, ipAddress, new GeoIpUtil.CountryCallback() {
                    @Override
                    public void onSuccess(String country) {


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                int f =    flag(country);
                                ImageView imageView1 = findViewById(R.id.flgimg);
                                imageView1.setBackgroundResource(f);
                                SharedPreferences.Editor e = flagsave.edit();
                                e.putInt("flagsave",f);
                                e.apply();


                            }
                        }, 2000);

                    }

                    @Override
                    public void onError(String error) {
                    }
                });


            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }if (ip.startsWith("trojan")){
            String[] su = ip.split("@");


            String[] m = su[1].split("\\?");
            // String[] serverport = m[0].split(":");


            ip = su[0];
            ip = ip.replace("trojan://", "");
            // s = decodeBase64(s);
            Log.v("llllll", ip);


            String[] ipport = m[0].split(":");

            ip = ipport[0];


            int count = 0;
            for (int i = 0; i < ip.length(); i++) {
                if (ip.charAt(i) == ':') {
                    count++;

                }

            }
            if (count == 0) {

                String ipAddress = ip;
                GeoIpUtil.getCountryByIp(this, ipAddress, new GeoIpUtil.CountryCallback() {
                    @Override
                    public void onSuccess(String country) {


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                int f =    flag(country);
                                ImageView imageView1 = findViewById(R.id.flgimg);
                                imageView1.setBackgroundResource(f);
                                SharedPreferences.Editor e = flagsave.edit();
                                e.putInt("flagsave",f);
                                e.apply();


                            }
                        }, 1000);

                    }

                    @Override
                    public void onError(String error) {
                    }
                });


            }


        }


        }
    private int flag(String namecont) {
        int img = R.drawable.ic_launcher;;
        if (namecont.equals("JP")){
            img =  R.drawable.japan;

        } else if (namecont.equals("IN")) {
            img =  R.drawable.india;

        } else if (namecont.equals("IT")) {
            img =  R.drawable.italy;

        } else if (namecont.equals("LV")) {
            img =  R.drawable.leton;

        }else if (namecont.equals("AU")) {
            img =  R.drawable.ostra;

        } else if (namecont.equals("BE")) {
            img =  R.drawable.belgic;

        }else if (namecont.equals("BG")) {
            img =  R.drawable.bolghar;

        } else if (namecont.equals("BR")) {
            img =  R.drawable.brazil;

        }else if (namecont.equals("CA")) {
            img =  R.drawable.canada;

        } else if (namecont.equals("CH")) {
            img =  R.drawable.sovis;

        }else if (namecont.equals("EL")) {
            img =  R.drawable.youn;

        } else if (namecont.equals("DE")) {
            img =  R.drawable.german;

        }else if (namecont.equals("DK")) {
            img =  R.drawable.danm;

        } else if (namecont.equals("EE")) {
            img =  R.drawable.stove;

        }else if (namecont.equals("ES")) {
            img =  R.drawable.spania;

        } else if (namecont.equals("FI")) {
            img =  R.drawable.finlaand;

        }else if (namecont.equals("FR")) {
            img =  R.drawable.frans;

        } else if (namecont.equals("UK")) {
            img =  R.drawable.britania;

        }else if (namecont.equals("HU")) {
            img =  R.drawable.majar;

        } else if (namecont.equals("IE")) {
            img =  R.drawable.irland;

        }else if (namecont.equals("NL")) {
            img =  R.drawable.holand;

        } else if (namecont.equals("NO")) {
            img =  R.drawable.norvej;

        }else if (namecont.equals("PL")) {
            img =  R.drawable.lahestan;

        } else if (namecont.equals("RO")) {
            img =  R.drawable.roman;

        }else if (namecont.equals("RS")) {
            img =  R.drawable.serb;

        } else if (namecont.equals("SE")) {
            img =  R.drawable.sued;

        }else if (namecont.equals("SG")) {
            img =  R.drawable.sang;

        } else if (namecont.equals("SK")) {
            img =  R.drawable.slov;

        }else if (namecont.equals("UA")) {
            img =  R.drawable.ocra;

        } else if (namecont.equals("US")) {
            img =  R.drawable.usa;

        }
        else if (namecont.equals("KR")){
            img =  R.drawable.kore;

        } else if (namecont.equals("")) {
            img =  R.drawable.ic_launcher;

        }
        else if (namecont.equals("CN")) {
            img =  R.drawable.chin;
        }

        return  img;


    }

    private void flag2(String namecont) {
        int img;
        ImageView imageView1 = findViewById(R.id.flgimg);
        if (namecont.equals("JP")){
            imageView1.setBackgroundResource(R.drawable.japan);
        } else if (namecont.equals("IN")) {
            imageView1.setBackgroundResource(R.drawable.india);

        } else if (namecont.equals("IT")) {
            imageView1.setBackgroundResource(R.drawable.italy);

        } else if (namecont.equals("LV")) {
            imageView1.setBackgroundResource(R.drawable.leton);

        }else if (namecont.equals("AU")) {
            imageView1.setBackgroundResource(R.drawable.ostra);

        } else if (namecont.equals("BE")) {
            imageView1.setBackgroundResource(R.drawable.belgic);

        }else if (namecont.equals("BG")) {
            imageView1.setBackgroundResource(R.drawable.bolghar);

        } else if (namecont.equals("BR")) {
            imageView1.setBackgroundResource(R.drawable.brazil);

        }else if (namecont.equals("CA")) {
            imageView1.setBackgroundResource(R.drawable.canada);

        } else if (namecont.equals("CH")) {
            imageView1.setBackgroundResource(R.drawable.sovis);

        }else if (namecont.equals("EL")) {
            imageView1.setBackgroundResource(R.drawable.youn);

        } else if (namecont.equals("DE")) {
            imageView1.setBackgroundResource(R.drawable.german);

        }else if (namecont.equals("DK")) {
            imageView1.setBackgroundResource(R.drawable.danm);

        } else if (namecont.equals("EE")) {
            imageView1.setBackgroundResource(R.drawable.stove);

        }else if (namecont.equals("ES")) {
            imageView1.setBackgroundResource(R.drawable.spania);

        } else if (namecont.equals("FI")) {
            imageView1.setBackgroundResource(R.drawable.finlaand);

        }else if (namecont.equals("FR")) {
            imageView1.setBackgroundResource(R.drawable.frans);

        } else if (namecont.equals("UK")) {
            imageView1.setBackgroundResource(R.drawable.britania);

        }else if (namecont.equals("HU")) {
            imageView1.setBackgroundResource(R.drawable.majar);

        } else if (namecont.equals("IE")) {
            imageView1.setBackgroundResource(R.drawable.irland);

        }else if (namecont.equals("NL")) {
            imageView1.setBackgroundResource(R.drawable.holand);

        } else if (namecont.equals("NO")) {
            imageView1.setBackgroundResource(R.drawable.norvej);

        }else if (namecont.equals("PL")) {
            imageView1.setBackgroundResource(R.drawable.lahestan);

        } else if (namecont.equals("RO")) {
            imageView1.setBackgroundResource(R.drawable.roman);

        }else if (namecont.equals("RS")) {
            imageView1.setBackgroundResource(R.drawable.serb);

        } else if (namecont.equals("SE")) {
            imageView1.setBackgroundResource(R.drawable.sued);

        }else if (namecont.equals("SG")) {
            imageView1.setBackgroundResource(R.drawable.sang);

        } else if (namecont.equals("SK")) {
            imageView1.setBackgroundResource(R.drawable.slov);

        }else if (namecont.equals("UA")) {
            imageView1.setBackgroundResource(R.drawable.ocra);

        } else if (namecont.equals("US")) {
            imageView1.setBackgroundResource(R.drawable.usa);

        }
        else if (namecont.equals("KR")){
            imageView1.setBackgroundResource(R.drawable.kore);
        } else if (namecont.equals("")) {
            imageView1.setBackgroundResource(R.drawable.ic_launcher);

        }
        else if (namecont.equals("CN")) {
            imageView1.setBackgroundResource(R.drawable.chin);

        }

    }

    private void startDownload(String url) {

        File previousFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "purlitenew.apk");
        if (previousFile.exists()) {
            previousFile.delete(); // حذف فایل قبلی
        }

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("دانلود نسخه جدید");
        request.setDescription("purlite...");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "purlitenew.apk");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadID = manager.enqueue(request);

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean downloading = true;

                while (downloading) {
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(downloadID);
                    Cursor cursor = manager.query(query);
                    if (cursor.moveToFirst()) {
                        @SuppressLint("Range")
                        int bytesDownloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                        @SuppressLint("Range")
                        int bytesTotal = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                        if (bytesTotal > 0) {
                            final int progress = (int) ((bytesDownloaded * 100L) / bytesTotal);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.setProgress(progress);
                                }
                            });
                        }

                        @SuppressLint("Range")
                        int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        if (status == DownloadManager.STATUS_SUCCESSFUL || status == DownloadManager.STATUS_FAILED) {
                            downloading = false;
                        }
                    }
                    cursor.close();
                }
            }
        }).start();
    }
    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

            progressDialog.dismiss();
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (downloadID == id) {
                Intent installIntent = new Intent(Intent.ACTION_VIEW);
                installIntent.setDataAndType(downloadManager.getUriForDownloadedFile(downloadID), "application/vnd.android.package-archive");
                installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.startActivity(installIntent);
                progressDialog.dismiss();            }
        }
    };

}