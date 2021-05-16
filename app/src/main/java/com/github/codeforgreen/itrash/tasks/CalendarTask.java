package com.github.codeforgreen.itrash.tasks;

import android.annotation.SuppressLint;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.codeforgreen.itrash.api.calendar.Region;
import com.github.codeforgreen.itrash.api.request.MakeGet;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.HttpURLConnection;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public abstract class CalendarTask extends MakeGet {

    @SuppressLint("StaticFieldLeak")
    private final AppCompatActivity activity;
    private final Type type;

    public CalendarTask(AppCompatActivity activity, Type type) {
        super(type.path + ".json", "https://bot.indbuildcraft.pl/");
        this.activity = activity;
        this.type = type;
    }

    @Override
    public void onJson(JsonElement element, String... strings) {
        try {
            JsonObject json = element.getAsJsonObject();
            HashMap<String, Region> regions = new HashMap<>();
            for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                regions.put(entry.getKey(), new Region(entry.getKey(), entry.getValue().getAsJsonObject()));
            }
            System.out.println(regions);
            System.out.println(regions.get(type.niceName + ". Region 6").getHarmonogram().getData(Month.APRIL));
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

    public enum Type {
        MIASTA("miasta", "Miasto"),
        WIOSKI("wioski", "Wioski"),
        ;

        private final String path, niceName;

        Type(String path, String niceName) {
            this.path = path;
            this.niceName = niceName;
        }
    }
}
