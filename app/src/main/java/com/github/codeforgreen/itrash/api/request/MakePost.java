package com.github.codeforgreen.itrash.api.request;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class MakePost extends AsyncTask<String, Void, Void> {

    private final String url;
    private final JSONObject json;

    public MakePost(String url, JSONObject json) {
        this.url = url;
        this.json = json;
    }

    public abstract void onResponse(HttpURLConnection connection);

    @Override
    protected Void doInBackground(String... strings) {
        try {
            URL url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            Log.i("JSON", this.json.toString());
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(this.json.toString());

            os.flush();
            os.close();

            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG" , conn.getResponseMessage());

            this.onResponse(conn);

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
