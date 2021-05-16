package com.github.codeforgreen.itrash.api.calendar;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Region {

    private final String name;
    private final JsonObject options;

    public Region(String name, JsonObject options) {
        this.name = name;
        this.options = options;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getUlice() {
        List<String> ulice = new ArrayList<>();
        JsonArray array = this.options.getAsJsonArray("ulice");
        for (JsonElement element : array) {
            ulice.add(element.getAsString());
        }
        return ulice;
    }

    public List<String> getMiasta() {
        return this.getUlice();
    }

    public Harmonogram getHarmonogram() {
        return new Harmonogram(this.options.get("harmonogram").getAsJsonObject());
    }
}