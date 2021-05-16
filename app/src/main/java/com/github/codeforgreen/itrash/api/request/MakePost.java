package com.github.codeforgreen.itrash.api.request;

import android.os.AsyncTask;
import android.util.Log;

import com.github.codeforgreen.itrash.util.Constants;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class MakePost extends AsyncTask<String, Void, Void> {

    private final String url;
    private final JsonObject json;

    public MakePost(String path, JsonObject json, String domain) {
        this.url = domain + path;
        this.json = json;
    }

    public MakePost(String path, JsonObject json) {
        this(path, json, Constants.getDOMAIN());
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

            if (conn.getResponseCode() == 200) {
                JsonElement json = JsonParser.parseReader(new InputStreamReader(conn.getInputStream()));
                Log.i("JSON" , json.toString());
                this.onJson(json, strings);
            } else {
                this.onError(conn);
            }
        } catch (Throwable t) {
            t.printStackTrace();
            this.onError(t);
        }
        if (conn != null) {
            conn.disconnect();
        }
        return null;
    }

    protected static JsonObject prepare(Object... objects) {
        JsonObject object = new JsonObject();
        for (int i = 0; i < objects.length; i = i + 2) {
            Object obj = objects[i + 1];
            if (obj instanceof JsonElement) {
                object.add(objects[i].toString(), ((JsonElement) obj));
            } else if (obj instanceof Number) {
                object.addProperty(objects[i].toString(), (Number) obj);
            } else if (obj instanceof String) {
                object.addProperty(objects[i].toString(), (String) obj);
            } else if (obj instanceof Boolean) {
                object.addProperty(objects[i].toString(), (Boolean) obj);
            } else if (obj instanceof Character) {
                object.addProperty(objects[i].toString(), (Character) obj);
            }
        }
        return object;
    }
}
