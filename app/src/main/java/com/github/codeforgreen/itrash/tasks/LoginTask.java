package com.github.codeforgreen.itrash.tasks;

import com.github.codeforgreen.itrash.api.request.MakePost;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public class LoginTask extends MakePost {

    public LoginTask(String email, String password) throws JSONException {
        super("https://bot.indbuildcraft.pl/itrash", new JSONObject()
                .put("email", email)
                .put("password", password));
    }

    @Override
    public void onResponse(HttpURLConnection connection) {
        // I tutaj co ma zrobic gdy dostanie odpowiedz
    }
}
