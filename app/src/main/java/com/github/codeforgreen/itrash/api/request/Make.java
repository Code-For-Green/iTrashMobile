package com.github.codeforgreen.itrash.api.request;

import android.os.AsyncTask;

import com.github.codeforgreen.itrash.util.Constants;
import com.google.gson.JsonElement;

import java.net.HttpURLConnection;

public abstract class Make extends AsyncTask<String, Void, Void> {

    protected final String url;

    public Make(String path, String domain) {
        this.url = domain + path;
    }

    public Make(String path) {
        this(path, Constants.getDOMAIN());
    }

    public abstract void onJson(JsonElement element, String... strings);

    public abstract void onError(HttpURLConnection connection);

    public abstract void onError(Throwable t);
}
