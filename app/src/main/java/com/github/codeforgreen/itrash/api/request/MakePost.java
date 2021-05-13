package com.github.codeforgreen.itrash.api.request;

import android.os.AsyncTask;
import android.util.Log;

import com.github.codeforgreen.itrash.util.Constants;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class MakePost extends AsyncTask<String, Void, JSONObject> {

    private final String url;
    private final JSONObject json;

    public MakePost(String path, JSONObject json, String domain) {
        this.url = domain + path;
        this.json = json;
    }

    public MakePost(String path, JSONObject json) {
        this(path, json, Constants.DOMAIN);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
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

            conn.disconnect();

            return new JSONObject(conn.getResponseMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return new JSONObject();
    }
}
