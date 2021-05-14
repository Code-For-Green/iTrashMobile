package com.github.codeforgreen.itrash.api.request;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.github.codeforgreen.itrash.util.Constants;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class MakePost extends AsyncTask<String, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    public final AppCompatActivity activity;
    private final String url;
    private final JSONObject json;

    public MakePost(AppCompatActivity activity, String path, JSONObject json, String domain) {
        this.activity = activity;
        this.url = domain + path;
        this.json = json;
    }

    public MakePost(AppCompatActivity activity, String path, JSONObject json) {
        this(activity, path, json, Constants.getDOMAIN());
    }

    public abstract void onJson(JSONObject json);

    public abstract void onError(HttpURLConnection connection);

    public abstract void onError(Throwable t);

    @Override
    protected Void doInBackground(String... strings) {
        try {
            URL url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            Log.i("JSON", this.json.toString());
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(this.json.toString());

            os.flush();
            os.close();

            Log.i("METHOD", conn.getRequestMethod());
            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG" , conn.getResponseMessage());
            conn.disconnect();

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
        } catch (Throwable t) {
            t.printStackTrace();
            this.onError(t);
        }
        return null;
    }
}
