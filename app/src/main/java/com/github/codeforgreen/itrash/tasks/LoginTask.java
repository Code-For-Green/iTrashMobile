package com.github.codeforgreen.itrash.tasks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.github.codeforgreen.itrash.MainActivity;
import com.github.codeforgreen.itrash.api.request.MakePost;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginTask extends MakePost {

    public LoginTask(AppCompatActivity activity, String email, String password) throws JSONException {
        super(activity, "login", new JSONObject()
                .put("Login", email)
                .put("Password", password));
    }

    @Override
    public void onJson(JSONObject json) {
        try {
            SharedPreferences.Editor editor = this.activity.getPreferences(Context.MODE_PRIVATE).edit();
            editor.putString("Token", json.getString("Token"));
            editor.putLong("Expiration", json.getLong("Expiration") * 1000);
            editor.apply();

            Intent intent = new Intent(this.activity, MainActivity.class);
            this.activity.startActivity(intent);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
