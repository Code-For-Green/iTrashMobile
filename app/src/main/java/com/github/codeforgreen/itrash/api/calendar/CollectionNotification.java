package com.github.codeforgreen.itrash.api.calendar;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;

import com.github.codeforgreen.itrash.CollectionReceiver;
import com.github.codeforgreen.itrash.R;
import com.github.codeforgreen.itrash.api.Preferences;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class CollectionNotification {

    private final TrashType type;
    private final LocalDate date;

    public CollectionNotification(TrashType type, LocalDate date) {
        this.type = type;
        this.date = date;
    }

    public Notification create(Context context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        return new NotificationCompat.Builder(context, context.getString(R.string.app_name))
                .setSmallIcon(R.drawable.app_logo) // Ustawia ikonkę
                .setColor(Color.rgb(0, 0, 255)) // Ustawia kolor ikonki
                .setSubText(context.getString(R.string.reminder)) // Ustawia tekst obok ikonki
                .setContentTitle(this.type.niceName) // Ustawia tytuł powiadomienia
                .setContentText(this.date.toString())
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(this.date.toString() + "\nPamiętaj o wystawieniu pojemnika bądź worków przed godziną 6:30!")) // Ustawia tekst powiadomienia
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Ustawia priorytet
                .setCategory(NotificationCompat.CATEGORY_REMINDER) // Ustawia kategorię
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // Ustawia widoczność
                .setLights(Color.rgb(0, 0, 255), 50, 50) // Ustawia diodę powiadomień (nieprzetestowane)
                .setAutoCancel(true) // Ustawia usunięcie powiadomienia po jego kliknięciu
                .setContentIntent(pendingIntent) // Ustawia akcję po kliknięciu powiadomienia
                .build();
    }

    public static void notifyOn(Context context, TrashType type, LocalDateTime dateTime) {
        notifyOn(context, type, dateTime, dateTime.toLocalDate());
    }

    public static void notifyOn(Context context, TrashType type, LocalDateTime dateTime, LocalDate date) {
        int id = Preferences.incrementLastNotificationId(context);

        Intent intent = new Intent(context, CollectionReceiver.class);
        intent.putExtra("type", type.toString()); // TrashType is Serializable, but it always returns null
        intent.putExtra("date", date);
        intent.putExtra("id", id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long time = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }
}
