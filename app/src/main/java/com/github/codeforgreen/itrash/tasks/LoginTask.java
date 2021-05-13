package com.github.codeforgreen.itrash.tasks;

import com.github.codeforgreen.itrash.api.request.MakePost;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginTask extends MakePost {

    public LoginTask(String email, String password) throws JSONException {
        super("login", new JSONObject()
                .put("Login", email)
                .put("Password", password));
    }
}
