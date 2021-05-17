package com.github.codeforgreen.itrash.api;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    public static String getToken(Context context) {
        return getPreferences(context).getString("Token", "");
    }

    public static long getExpiration(Context context) {
        return getPreferences(context).getLong("Expiration", 0);
    }

    public static int getLastNotificationId(Context context) {
        return getPreferences(context).getInt("LastNotificationId", 0);
    }

    public static int incrementLastNotificationId(Context context) {
        int id = getLastNotificationId(context) + 1;
        getEditor(context)
                .putInt("LastNotificationId", id)
                .apply();
        return id;
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences("iTrash", Context.MODE_PRIVATE);
    }
}
