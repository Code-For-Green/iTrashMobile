package com.github.codeforgreen.itrash.tasks;

import com.github.codeforgreen.itrash.api.request.MakePost;

import org.json.JSONObject;

import java.net.HttpURLConnection;

public class LoginTask extends MakePost {

    public LoginTask(JSONObject json) {
        super("", json);
    }

    @Override
    public void onResponse(HttpURLConnection connection) {
        // I tutaj co ma zrobic gdy dostanie odpowiedz
    }
}
