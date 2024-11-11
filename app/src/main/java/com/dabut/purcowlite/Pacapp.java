package com.dabut.purcowlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dabut.lib.v2ray.V2rayController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pacapp extends AppCompatActivity {
    ArrayAdapter  adapter;
    boolean bbb = true;
    Drawable appIcon;
    EditText  ed;
    ProgressDialog updial;
    Set<String> selectedPackages2;
    List<Drawable> iconList;
    Set<String> retrievedPackages;
    ArrayList<String> selectedPackageList;
    ArrayList<AppInfo> applist;
    ArrayList<AppInfo> applist2;
    ArrayList<AppInfo> applist3;

    List<ApplicationInfo> installedApps;
    Set<String> selectedPackages;
    Set<String> selectedPackages5;
    boolean u = true;
    PackageManager packageManager;

    List<String> packageNames;
    List<String> packageNames2;

    boolean hhh = true;
    static SharedPreferences prefs;
    List<String> filteredPackages;

    ListView listView;

    private List<String> selectedApps = new ArrayList<>();
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_pacapp);
        updial= new ProgressDialog(this);



        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar);




        updial.setMessage(getString(R.string.daryaft));
        updial.show();

        Context context = this;
        prefs  = context.getSharedPreferences("com.dabut.purnetvray", Context.MODE_MULTI_PROCESS);

     //   V2rayController.StopV2ray(getApplicationContext());

        packageManager = getPackageManager();
        installedApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        packageNames= new ArrayList<>();
        packageNames2= new ArrayList<>();
        applist = new ArrayList<AppInfo>();
        applist2 = new ArrayList<AppInfo>();
        applist3 = new ArrayList<AppInfo>();

        prefs = getSharedPreferences("com.dabut.purnetvray", Context.MODE_MULTI_PROCESS);
        iconList   = new ArrayList<>();
        retrievedPackages = prefs.getStringSet("selectedPackages", null);
        selectedPackages2   = new HashSet<>();
        selectedPackageList = new ArrayList<>(retrievedPackages);
        selectedPackages5   = new HashSet<>();

        listView = findViewById(R.id.listview);

        new Thread(new Runnable() {
            @Override
            public void run() {



                try {
                    if (selectedPackageList.size() >0){


                        for (ApplicationInfo appInfo2 : installedApps) {

                            String m = appInfo2.sourceDir;
                            if (m.startsWith("/data/app")&& packageManager.getLaunchIntentForPackage(appInfo2.packageName) != null) {
                                if (selectedPackageList.contains(appInfo2.packageName)) {
                                    try {

                                        appIcon = getPackageManager().getApplicationIcon(appInfo2.packageName);

                                        //  packageNames.add(appInfo2.packageName);
                                        applist2.add(new AppInfo(appIcon,appInfo2.loadLabel(packageManager).toString(), appInfo2.packageName,  true));
                                    } catch (PackageManager.NameNotFoundException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        }



                    }



                    for (ApplicationInfo appInfo : installedApps) {
                        String m = appInfo.sourceDir;
                        if (selectedPackageList.contains(appInfo.packageName)) {
                            continue;

                        }

                        packageNames2.add(appInfo.loadLabel(packageManager).toString());

                        packageNames.add(appInfo.packageName);
                    }

                }catch (Exception e){

                }





                for (ApplicationInfo appInfo2 : installedApps) {

                    String m = appInfo2.sourceDir;
                    if (m.startsWith("/data/app")&& packageManager.getLaunchIntentForPackage(appInfo2.packageName) != null){
                        try {

                            appIcon = getPackageManager().getApplicationIcon(appInfo2.packageName);
                            packageNames.add(appInfo2.packageName);

                            if (selectedPackageList.contains(appInfo2.packageName)){
                                continue;
                            }

                            applist.add(new AppInfo(appIcon,appInfo2.loadLabel(packageManager).toString(), appInfo2.packageName,  false));



                        } catch (PackageManager.NameNotFoundException e) {
                            throw new RuntimeException(e);
                        }

                    }



                }


                applist.addAll(0,applist2);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        listView = findViewById(R.id.listview);
                        adapter = new AppListAdapter(Pacapp.this, R.layout.listview_item_row, applist);
                        listView.setAdapter(adapter);
                        updial.dismiss();

                    }
                });

            }
        }).start();




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AppInfo appInfo;
                if (u){
                    appInfo = applist.get(i); // دریافت شیء موجود در لیست

                }else {
                    appInfo = applist3.get(i); // دریافت شیء موجود در لیست

                }


                if ((listView.isItemChecked(i))) {



                    //   String selectedText = listView.getItemAtPosition(i).toString();

                    boolean newSelectedValue = !appInfo.check; // تغییر مقدار isSelected
                    appInfo.check = newSelectedValue;
                    listView.setItemChecked(i, newSelectedValue);

                    if (appInfo.check){
                        String    selectedText=    appInfo.name.toString();
                        selectedPackages2.add(selectedText);
                        Log.v("jjjjj",selectedText);
                        listView.invalidateViews();

                    }else {
                        String    selectedText=    appInfo.name.toString();

                        selectedPackages2.remove(selectedText);
                        selectedPackages5.remove(selectedText);
                        listView.invalidateViews();

                    }
                }

            }
        });


        Check();

        EditText editText = findViewById(R.id.search2);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // هیچ کاری لازم نیست انجام دهید
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // تغییر داده‌های لیست بر اساس متن جستجو
                for (int i = 0; i < listView.getCount(); i++) {
                    AppInfo appInfo = applist.get(i);

                    if (appInfo.check) {
                        String g = appInfo.name;
                        selectedPackages5.add(g);
                    }
                }

                filterData(s.toString());
                //  bbb = false;
                //  adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // هیچ کاری لازم نیست انجام دهید
            }
        });




    }

    private void filterData(String query) {


        if (applist != null) {
            applist3.clear();
            Log.d("mytag", "list is clear" + applist3.size());
        }

        for (int i = 0; i < applist.size(); i++) {
            u=false;

            if (applist.get(i).fam.toLowerCase().trim()
                    .contains(query.toLowerCase().trim()) ||applist.get(i).name.toLowerCase().trim()
                    .contains(query.toLowerCase().trim())) {
                AppInfo appInfo = applist.get(i);

                applist3.add(applist.get(i));

                HashSet hs = new HashSet();
                hs.addAll(applist3);
                applist3.clear();
                applist3.addAll(hs);




                adapter = new AppListAdapter(this, R.layout.listview_item_row, applist3);
                listView.setAdapter(adapter);

            }
        }


        if (query.equals("")) {
            u=true;
            //  this.recreate();

            adapter = new AppListAdapter(this, R.layout.listview_item_row, applist);
            listView.setAdapter(adapter);
            for (int i=0;i<listView.getCount();i++) {

            }


        }


    }



    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

        // super.onBackPressed();

        // super.onBackPressed();
        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(
                this, R.style.MyDialogStyle);

        // set title

        // set dialog message
        alertDialogBuilder
                .setIcon(R.drawable.ic_launcher)
                .setMessage(getString(R.string.whyapp))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {






                        selectedPackages   = new HashSet<>();
                        for (int ii = 0; ii < listView.getCount(); ii++) {



                            AppInfo appInfo2 = applist.get(ii); // دریافت شیء موجود در لیست


                            if (appInfo2.check) {
                                String selectedText = appInfo2.name;

                                if (selectedPackages.contains(selectedPackages2) && selectedPackages.contains(selectedPackages5)){
                                    continue;
                                }
                                selectedPackages.add(selectedText);
                            }
                        }
                        selectedPackages2.addAll(selectedPackages);
                        selectedPackages2.addAll(selectedPackages5);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putStringSet("selectedPackages", selectedPackages2);
                        editor.apply();

                        Toast.makeText(Pacapp.this, getString(R.string.ping), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Pacapp.this , MainActivity.class);
                        startActivity(intent);
                        finish();



                    }
                }).setNegativeButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity

                        dialog.cancel();


                    }
                }).setNeutralButton(getString(R.string.enseraf), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Pacapp.this, getString(R.string.nashod), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Pacapp.this , MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    public  void Check()
    {
        prefs = getSharedPreferences("com.dabut.purnetvray", Context.MODE_PRIVATE);

        retrievedPackages = prefs.getStringSet("selectedPackages", null);

//        adapter = new ArrayAdapter<>(this, R.layout.color, packageNames);
//        listView.setAdapter(adapter);
        try {
            selectedPackageList = new ArrayList<>(retrievedPackages);
            if (selectedPackageList.size()>0){
                for (int i = 0; i < listView.getCount(); i++) {
                    String itemText = listView.getItemAtPosition(i).toString();
                    if (selectedPackageList.contains(itemText)) {
                        //  listView.setSelection(i); // انتخاب دومین آیتم
                        listView.setItemChecked(i, true);

                    }
                    // listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


                }

            }
        }catch (Exception e){

        }



    }



}