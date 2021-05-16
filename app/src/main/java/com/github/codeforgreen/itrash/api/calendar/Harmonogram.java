package com.github.codeforgreen.itrash.api.calendar;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Harmonogram {

    private final Map<TrashType, JsonObject> options;

    public Harmonogram(JsonObject options) {
        Map<TrashType, JsonObject> mapped = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : options.entrySet()) {
            mapped.put(TrashType.of(entry.getKey()), entry.getValue().getAsJsonObject());
        }
        this.options = mapped;
    }

    public Map<TrashType, Set<LocalDate>> getData() {
        Map<TrashType, Set<LocalDate>> map = new HashMap<>();
        for (Map.Entry<TrashType, JsonObject> entry : this.options.entrySet()) {
            map.put(entry.getKey(), parseJsonObject(entry.getValue()));
        }
        return map;
    }

    public Map<TrashType, Set<LocalDate>> getData(Month month) {
        Map<TrashType, Set<LocalDate>> map = new HashMap<>();
        for (Map.Entry<TrashType, JsonObject> entry : this.options.entrySet()) {
            map.put(entry.getKey(), parseJsonObject(entry.getValue(), month));
        }
        return map;
    }

    public Set<LocalDate> getData(TrashType type) {
        JsonObject data = this.getRawData(type);
        return parseJsonObject(data);
    }

    public Set<LocalDate> getData(TrashType type, Month month) {
        JsonObject data = this.getRawData(type);
        return parseJsonObject(data, month);
    }

    private JsonObject getRawData(TrashType type) {
        return this.options.get(type);
    }

    private Set<LocalDate> parseJsonObject(JsonObject data) {
        List<Set<LocalDate>> dates = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : data.entrySet()) {
            Month month = Month.of(Integer.parseInt(entry.getKey()));
            JsonArray array = entry.getValue().getAsJsonArray();
            dates.add(parseLocalDates(month, array));
        }
        return dates.stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }

    private Set<LocalDate> parseJsonObject(JsonObject data, Month month) {
        String monthNumber = Integer.toString(month.getValue());
        if (data.has(monthNumber)) {
            JsonArray array = data.getAsJsonArray(monthNumber);
            return parseLocalDates(month, array);
        }
        return new HashSet<>();
    }

    private Set<LocalDate> parseLocalDates(Month month, JsonArray array) {
        Set<LocalDate> dates = new HashSet<>();
        for (JsonElement element : array) {
            int day = element.getAsInt();
            dates.add(LocalDate.of(LocalDate.now().getYear(), month, day));
        }
        return dates;
    }
}
