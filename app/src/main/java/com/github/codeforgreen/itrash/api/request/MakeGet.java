package com.github.codeforgreen.itrash.api.request;

import android.os.AsyncTask;
import android.util.Log;

import com.github.codeforgreen.itrash.util.Constants;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class MakeGet extends AsyncTask<String, Void, Void> {

    private final String url;

    public MakeGet(String path, String domain) {
        this.url = domain + path;
    }

    public MakeGet(String path) {
        this(path, Constants.getDOMAIN());
    }

    public abstract void onJson(JsonElement element, String... strings);

    public abstract void onError(HttpURLConnection connection);

    public abstract void onError(Throwable t);

    @Override
    protected Void doInBackground(String... strings) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(this.url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(false);
            conn.setDoInput(true);

            Log.i("METHOD", conn.getRequestMethod());
            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG" , conn.getResponseMessage());

            if (conn.getResponseCode() == 200) {
                JsonElement json = JsonParser.parseReader(new InputStreamReader(conn.getInputStream()));
                Log.i("JSON" , json.toString());
                this.onJson(json, strings);
            } else {
                this.onError(conn);
            }
            conn.disconnect();
        } catch (Throwable t) {
            t.printStackTrace();
            this.onError(t);
        }
        if (conn != null) {
            conn.disconnect();
        }
        return null;
    }
}
