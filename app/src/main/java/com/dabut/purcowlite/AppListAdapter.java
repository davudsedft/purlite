package com.dabut.purcowlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AppListAdapter extends ArrayAdapter<AppInfo> {
    private Context context;
    private int layoutResourceId;
    private List<AppInfo> data;

    public AppListAdapter(Context context, int layoutResourceId, List<AppInfo> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        AppInfoHolder holder;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new AppInfoHolder();
            holder.imgIcon = row.findViewById(R.id.imgIcon);
            holder.txtText = row.findViewById(R.id.check);
            holder.textView = row.findViewById(R.id.textView7);
            holder.mg22222 = row.findViewById(R.id.mg22222);

            row.setTag(holder);
        } else {
            holder = (AppInfoHolder) row.getTag();
        }

        AppInfo appInfo = data.get(position);
        holder.txtText.setText(appInfo.fam);
        holder.imgIcon.setImageDrawable(appInfo.icon);
        if (appInfo.check){
            holder.mg22222.setBackgroundResource(R.drawable.checkin);

        }else{
            holder.mg22222.setBackgroundResource(R.drawable.checkoff);

        }
        holder.textView.setText(appInfo.name);


        return row;
    }

    static class AppInfoHolder {
        ImageView imgIcon;
        TextView txtText;
        TextView textView;
        ImageView mg22222;



    }
}
