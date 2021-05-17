package com.github.codeforgreen.itrash.tasks;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import android.widget.Toast;

import com.github.codeforgreen.itrash.MainActivity;
import com.github.codeforgreen.itrash.api.Preferences;
import com.github.codeforgreen.itrash.api.request.MakePost;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.HttpURLConnection;

public class LoginTask extends MakePost {

    @SuppressLint("StaticFieldLeak")
    public final Activity activity;

    public LoginTask(Activity activity, String email, String password) {
        super("login", prepare("Login", email, "Password", password));
        this.activity = activity;
    }

    @Override
    public void onJson(JsonElement element, String... strings) {
        try {
            JsonObject json = element.getAsJsonObject();
            Preferences.getEditor(this.activity)
                    .putString("Token", json.get("Token").getAsString())
                    .putLong("Expiration", json.get("Expiration").getAsLong() * 1000)
                    .apply();

            Intent intent = new Intent(this.activity, MainActivity.class);
            this.activity.startActivity(intent);
            this.activity.finish();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void onError(HttpURLConnection connection) {
        this.activity.runOnUiThread(() -> {
            try {
                Toast.makeText(this.activity.getApplicationContext(), connection.getResponseMessage(), Toast.LENGTH_LONG).show();
            } catch (Throwable t) {
                this.onError(t);
            }
        });
    }

    @Override
    public void onError(Throwable t) {
        this.activity.runOnUiThread(() -> Toast.makeText(this.activity.getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show());
    }
}
