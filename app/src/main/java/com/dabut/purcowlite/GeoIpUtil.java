package com.dabut.purcowlite;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class GeoIpUtil {

    private static final String TAG = "GeoIpUtil";
    private static final String BASE_URL = "http://ip-api.com/json/";

    // تعریف اینترفیس برای callback
    public interface CountryCallback {
        void onSuccess(String country);
        void onError(String error);
    }

    // تابع برای دریافت کشور از طریق آی‌پی
    public static void getCountryByIp(Context context, String ip, final CountryCallback callback) {
        String url = BASE_URL + ip;

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // استخراج نام کشور از پاسخ JSON
                            String country = response.getString("countryCode");
                            callback.onSuccess(country);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onError(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error: " + error.toString());
                        callback.onError(error.toString());
                    }
                });

        queue.add(jsonObjectRequest);
    }
}
