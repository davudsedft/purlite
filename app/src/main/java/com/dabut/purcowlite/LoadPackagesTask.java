package com.dabut.purcowlite;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LoadPackagesTask extends AsyncTask<Void, Void, List<AppInfo>> {
    private Context context;
    private List<String> packageNames;
    ArrayAdapter  adapter;
    boolean bbb = true;
    Drawable appIcon;
    EditText ed;
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

    List<String> packageNames2;

    SearchView searchView;
    boolean hhh = true;
    static SharedPreferences prefs;
    List<String> filteredPackages;

    ListView listView;
    androidx.constraintlayout.widget.ConstraintLayout layout;
    boolean isLightTheme = true;

    public LoadPackagesTask(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
        this.packageManager = context.getPackageManager();
        this.packageNames = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(context, "Loading packages...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected List<AppInfo> doInBackground(Void... voids) {

        try {
            if (selectedPackageList.size() >0){


                for (ApplicationInfo appInfo2 : installedApps) {

                    String m = appInfo2.sourceDir;
                    if (m.startsWith("/data/app")&& packageManager.getLaunchIntentForPackage(appInfo2.packageName) != null) {
                        if (selectedPackageList.contains(appInfo2.packageName)) {
                            try {

                                appIcon = context.getPackageManager().getApplicationIcon(appInfo2.packageName);

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


        return applist2;
    }

    @Override
    protected void onPostExecute(List<AppInfo> packageNames) {
        super.onPostExecute(packageNames);
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, packageNames);
        adapter = new AppListAdapter(context, R.layout.listview_item_row, applist);

        listView.setAdapter(adapter);
        Toast.makeText(context, "Packages loaded successfully!", Toast.LENGTH_SHORT).show();
    }

    public void filterData(String query) {
        adapter.getFilter().filter(query);
    }
}
