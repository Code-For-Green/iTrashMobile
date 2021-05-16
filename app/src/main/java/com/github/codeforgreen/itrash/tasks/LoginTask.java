package com.github.codeforgreen.itrash.tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.github.codeforgreen.itrash.MainActivity;
import com.github.codeforgreen.itrash.api.request.MakePost;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.HttpURLConnection;

public class LoginTask extends MakePost {

    @SuppressLint("StaticFieldLeak")
    public final AppCompatActivity activity;

    public LoginTask(AppCompatActivity activity, String email, String password) {
        super("login", prepare("Login", email, "Password", password));
        this.activity = activity;
    }

    @Override
    public void onJson(JsonElement element, String... strings) {
        try {
            JsonObject json = element.getAsJsonObject();
            SharedPreferences.Editor editor = this.activity.getSharedPreferences("iTrash", Context.MODE_PRIVATE).edit();
            editor.putString("Token", json.get("Token").getAsString());
            editor.putLong("Expiration", json.get("Expiration").getAsLong() * 1000);
            editor.apply();

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
