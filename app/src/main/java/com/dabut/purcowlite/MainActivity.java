package com.dabut.purcowlite;

import static com.dabut.lib.v2ray.utils.V2rayConstants.SERVICE_CONNECTION_STATE_BROADCAST_EXTRA;
import static com.dabut.lib.v2ray.utils.V2rayConstants.V2RAY_SERVICE_STATICS_BROADCAST_INTENT;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.net.VpnService;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dabut.lib.v2ray.V2rayController;
import com.dabut.lib.v2ray.core.V2rayCoreExecutor;
import com.dabut.lib.v2ray.utils.Utilities;
import com.dabut.lib.v2ray.utils.V2rayConstants;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> vmessLinks, vmessLinks2, vmessLinks3, configarray, sslinks,vmesscount;
    ExecutorService executor;
    ArrayList<Bestserver> pingrun;
    String lowping;
    private Thread myt,http,httpp;
    static NotificationManager notifManager;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private Thread proxyThread;
    public static final String Pova = "pova";
    SharedPreferences  pova,linq;
    ImageView imgbtn;
    ProgressDialog updial,updial2;
    AlertDialog.Builder dialogBuilder3,dialogBuilder4,alertDialogBuilder2;

    String utext3,utext2,utext;
    private TextView  connected_server_delay,contextt,txtbtn;
    private EditText v2ray_config;
    private SharedPreferences sharedPreferences;
    private BroadcastReceiver v2rayBroadCastReceiver;
    String url3 = "aHR0cHM6Ly9wdXJuZXQuaXIvdnBuL3B1cmNvdi9QdXJDb3cuYXBr";

    @SuppressLint({"SetTextI18n", "UnspecifiedRegisterReceiverFlag", "WrongConstant"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar);




        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        CardView cardView = (CardView) findViewById(R.id.vpnBtn);
        txtbtn = (TextView) findViewById(R.id.txtbtn);
        imgbtn = (ImageView) findViewById(R.id.imgbtn);

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
            contextt = findViewById(R.id.core_version);

            connected_server_delay = findViewById(R.id.connected_server_delay);
            vmessLinks = new ArrayList<String>();
            pingrun = new ArrayList<>();

        }

        // initialize shared preferences for save or reload default config
        // reload previous config to edit text
        cardView.setOnClickListener(view -> {
            if (V2rayController.getConnectionState() == V2rayConstants.CONNECTION_STATES.DISCONNECTED) {




                if (contextt.getText().equals(" متصل نیست") || contextt.getText().toString().contains("مجدد") || contextt.getText().toString().contains("خطا") ){
                    imgbtn.setBackgroundResource(R.drawable.yellow);
                    txtbtn.setText("...");
                    txtbtn.setTextColor(Color.YELLOW);


                    cont();
                }else {
                    Toast.makeText(this, "صبر کنید", Toast.LENGTH_SHORT).show();
                }





            } else {
                V2rayController.stopV2ray(this);
                contextt.setTextColor(Color.RED);

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

                connected_server_delay.setText("در حال تست");

                V2rayController.getConnectedV2rayServerDelay(this, delayResult -> runOnUiThread(() -> connected_server_delay.setText("پینگ : " + delayResult + "ms")));



            }else {
                Toast.makeText(this, "ابتدا متصل شوید", Toast.LENGTH_SHORT).show();
            }


            // Don`t forget to do ui jobs in ui thread!
           ;
        });
        // Another way to check the connection delay of a config without connecting to it.


        // Check connection state when activity launch
        switch (V2rayController.getConnectionState()) {
            case CONNECTED:
                txtbtn.setText("ON");
                contextt.setText("متصل شد");

                txtbtn.setTextColor(Color.GREEN);
                imgbtn.setBackgroundResource(R.drawable.kkk);

                break;
            case DISCONNECTED:
                txtbtn.setText("OFF");
                contextt.setText(" متصل نیست");
                contextt.setTextColor(Color.RED);

                txtbtn.setTextColor(Color.rgb(219, 102, 200));

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
                            txtbtn.setTextColor(Color.GREEN);

                            txtbtn.setText("ON");
                            imgbtn.setBackgroundResource(R.drawable.kkk);

                            contextt.setText("متصل شد");

                            contextt.setTextColor(Color.GREEN);





                            break;
                        case DISCONNECTED:
                            txtbtn.setText("OFF");
                            contextt.setText(" متصل نیست");
                            contextt.setTextColor(Color.RED);

                            //  connection.setBackgroundColor("");
                            imgbtn.setBackgroundResource(R.drawable.circle);
                            txtbtn.setTextColor(Color.rgb(219, 102, 200));
                            //  simpleNotification();
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

                    MainActivity.UpdateApp updateApp = new MainActivity.UpdateApp();
                    updateApp.setContext(MainActivity.this);
                    updateApp.execute(url3);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.tel) {

            Intent browserIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/purcowbot"));
            startActivity(browserIntent2);




        } else if (id == R.id.gith) {
            Intent browserIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/davudsedft/purlite/releases"));
            startActivity(browserIntent2);
            return true;

        }else if (id == R.id.splite) {
           Intent intent = new Intent(this , Packapp.class);
           startActivity(intent);
           finish();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NotifyDataSetChanged")
    public  void fff(){


        for (int j = 0; j <= vmessLinks.size(); j++) {
            //  new RetrieveFeedTask2().execute("https://www.google.com");

            executor = Executors.newFixedThreadPool(1);


            if (j % 2 == 0 ) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        contextt.setTextColor(Color.YELLOW);

                        contextt.setText("در حال تست سرورها");
                        // one.start();
                    }
                });
            }

            if (j % 2 != 0 ) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        contextt.setTextColor(Color.YELLOW);

                        contextt.setText("در حال انتخاب سرور");

                    }
                });
            }


            int finalJ = j;

            executor.submit(() -> {
                System.out.println("ثطثزعفثق۲ قعددد ");
                try {

                    Thread.sleep(3000);


                    String   selectedText= vmessLinks.get(finalJ);

                    if (selectedText.startsWith("ss://")){
                        selectedText = sss(selectedText);

                    }

                    //   wwww = getV2rayServerDelay(selectedText);


                    System.out.println("سرور پوارو");

                    //  int delay = (int) V2rayCoreExecutor.getConfigDelay(Utilities.normalizeV2rayFullConfig(selectedText));

                    long server_delay = V2rayCoreExecutor.getConfigDelay(Utilities.normalizeV2rayFullConfig(selectedText));


                    if (server_delay>0) {
                        pingrun.add(new Bestserver(selectedText, longToIntJavaWithMath(server_delay)));

                        @SuppressLint({"NewApi", "LocalSuppress"})

                        Bestserver youngestPerson = Collections.min(pingrun, Comparator.comparing(Bestserver::getAge));
                        lowping = youngestPerson.getName();

                        pova = getSharedPreferences("pova", Context.MODE_PRIVATE);

                        SharedPreferences.Editor iran2edit = pova.edit();

                        iran2edit.putString(Pova, lowping);
                        iran2edit.apply();







                    }

                    //  executor.execute(new MyRunnable(finalJ));







                }catch (Exception e){

                }

            });





        }

        executor.shutdown();




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
                            contextt.setText("در حال اتصال");
                        }
                    });
                    String url2 = "https://raw.githubusercontent.com/davudsedft/newpurnet/main/porcowlite.txt";



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
                                    contextt.setText("متصل به مخازن");
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
                                        contextt.setText("در حال بررسی سرور ها");

                                    }
                                });

                                if (!utext.contains("vmess://") || !utext.contains("vless://") || !utext.contains("trojan://")) {
                                   // utext = decodeBase64(utext);

                                }
                                // utext = utext.replace("vmess://" , " ");
                                Pattern pattern = Pattern.compile("(ss|vmess|trojan|vless)://[^\n]+");
                                Matcher matcher = pattern.matcher(utext);
                                int u=0;
                                while (matcher.find() && u<200) {
                                    u++;
                                    vmessLinks.add(matcher.group());

                                }
                                startMyThread();
                                fff();



                            }else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        contextt.setText("خطا در دریافت");
                                        contextt.setTextColor(Color.rgb(153, 153, 0));
                                    }
                                });
                            }
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                contextt.setText("خطا در دریافت");
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
                                contextt.setText("خطا در اتصال");
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

    private  String decodeBase64(String coded) {
        byte[] valueDecoded = new byte[0];
        try {
            valueDecoded = Base64.decode(coded.getBytes("UTF-8"), Base64.DEFAULT);
        } catch (UnsupportedEncodingException ignored) {
        }
        return new String(valueDecoded);
    }

    private void startMyThread() {
        myt = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("10 seccccc");
                    try {
                        Thread.sleep(10000); // تاخیر ۱۰ ثانیه
                        // اجرای عملیات مورد نظر
                        if (pingrun.size()>0){
                            if (executor.isTerminated()){

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        contextt.setTextColor(Color.GREEN);
                                        contextt.setText("مجدد متصل شو");
                                        V2rayController.startV2ray(MainActivity.this, "Test Server", pova.getString(Pova,""), null);

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
                                        contextt.setText("مجدد متصل شو");

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


    @SuppressLint("StaticFieldLeak")
    public  class UpdateApp extends AsyncTask<String, Integer, String> {
        private Context mContext;
        private ProgressDialog mPDialog;

        void setContext(Activity context) {
            mContext = context;
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPDialog = new ProgressDialog(mContext ,R.style.MyDialogStyle);
                    mPDialog.setMessage("لطفا صبر کنید...");
                    mPDialog.setIndeterminate(true);
                    mPDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    mPDialog.setCancelable(false);
                    mPDialog.show();

                }
            });
        }

        @Override
        protected String doInBackground(String... arg0) {
            String fileUrl = arg0[0];

            try {



                String unicurl = arg0[0] + "?nocache="+System.currentTimeMillis();

                URL url = new URL(unicurl);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                HttpURLConnection c2 = (HttpURLConnection) url.openConnection();
                c2.setRequestMethod("GET");

                int lengthOfFile2 = c2.getContentLength();

                c2.disconnect();


                String PATH = Objects.requireNonNull(mContext.getExternalFilesDir(null)).getAbsolutePath();
                File file = new File(PATH);
                boolean isCreate = file.mkdirs();

                File outputFile = new File(file, "my_apk.apk");
                // اضافه کردن هدر Range به درخواست
                long downloadedBytes = 0;
                if (outputFile.exists()) {
                    if (outputFile.length() >= lengthOfFile2){
                        boolean delete = outputFile.delete();

                    }else {
                        downloadedBytes = outputFile.length();
                        c.setRequestProperty("Range", "bytes=" + downloadedBytes + "-");
                    }

                }
                c.connect();
                int lengthOfFile = c.getContentLength();
                if (outputFile.exists() && outputFile.length()==lengthOfFile2){

                    boolean delete = outputFile.delete();
                }

                FileOutputStream fos = new FileOutputStream(outputFile,true);

                InputStream is = c.getInputStream();

                byte[] readBuffer = new byte[1024 * 10];
                int len1;
                long total = downloadedBytes;
                while ((len1 = is.read(readBuffer)) != -1) {
                    total += len1;
                    fos.write(readBuffer, 0, len1);
                    publishProgress((int) ((total * 100) / lengthOfFile2));
                    System.out.println(lengthOfFile);

                }
                fos.close();
                is.close();
                if (mPDialog != null)
                    mPDialog.dismiss();
                installApk();
            } catch (Exception e) {
                Log.e("UpdateAPP", "Update error! " + e.getMessage());
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mPDialog != null)
                mPDialog.show();

        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (mPDialog != null) {
                mPDialog.setIndeterminate(false);
                mPDialog.setMax(100);
                mPDialog.setProgress(values[0]);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (mPDialog != null)
                mPDialog.dismiss();
            if (result != null)
                Toast.makeText(mContext, "Download error: " + result, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(mContext, "File Downloaded", Toast.LENGTH_SHORT).show();




        }


        @SuppressLint("ObsoleteSdkInt")
        private void installApk() {
            try {
                String PATH = Objects.requireNonNull(mContext.getExternalFilesDir(null)).getAbsolutePath();
                File file = new File(PATH + "/my_apk.apk");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                if (Build.VERSION.SDK_INT >= 24) {
                    Uri downloaded_apk = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", file);
                    intent.setDataAndType(downloaded_apk, "application/vnd.android.package-archive");
                    @SuppressLint("QueryPermissionsNeeded") List<ResolveInfo> resInfoList = mContext.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList) {
                        mContext.grantUriPermission(mContext.getApplicationContext().getPackageName() + ".provider", downloaded_apk, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);
                } else {
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }



    public  String utf88(String ss){
        byte[] bytes=ss.getBytes();
        String s=new String(bytes, StandardCharsets.UTF_8);
        // String sf = new String(bytes, StandardCharsets.UTF_8);
        s = new String(s.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        return s;

    }

}
