package com.github.codeforgreen.itrash.api.request;

import android.os.AsyncTask;
import android.util.Log;

import com.github.codeforgreen.itrash.util.Constants;

import org.json.JSONObject;

import java.io.BufferedReader;
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

    public abstract void onJson(JSONObject json);

    public abstract void onError(HttpURLConnection connection);

    public abstract void onError(Throwable t);

    @Override
    protected Void doInBackground(String... strings) {
        try {
            URL url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(false);
            conn.setDoInput(true);

            Log.i("METHOD", conn.getRequestMethod());
            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG" , conn.getResponseMessage());

            if (conn.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();

                Log.i("JSON" , sb.toString());
                this.onJson(new JSONObject(sb.toString()));
            } else {
                this.onError(conn);
            }
            conn.disconnect();
        } catch (Throwable t) {
            t.printStackTrace();
            this.onError(t);
        }
        return null;
    }
}
