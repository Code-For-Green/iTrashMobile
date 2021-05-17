package com.github.codeforgreen.itrash;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

import com.github.codeforgreen.itrash.api.calendar.CollectionNotification;
import com.github.codeforgreen.itrash.api.calendar.TrashType;

import java.time.LocalDateTime;

public class CollectionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("id", (int) System.currentTimeMillis());
        CollectionNotification notification = this.createObject(intent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(id, notification.create(context, LoadingScreenActivity.class));
    }

    private CollectionNotification createObject(Intent intent) {
        TrashType type = TrashType.valueOf(intent.getStringExtra("type"));
        LocalDateTime dateTime = ((LocalDateTime) intent.getSerializableExtra("date"));
        return new CollectionNotification(type, dateTime.toLocalDate());
    }
}
