package com.github.codeforgreen.itrash.api.calendar;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;

import com.github.codeforgreen.itrash.R;

import java.time.LocalDate;

public class CollectionNotification {

    private final TrashType type;
    private final LocalDate date;

    public CollectionNotification(TrashType type, LocalDate date) {
        this.type = type;
        this.date = date;
    }

    public Notification createNotification(Context context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        return new NotificationCompat.Builder(context, context.getString(R.string.app_name))
                .setSmallIcon(R.drawable.app_logo) // Ustawia ikonkę
                .setColor(Color.rgb(0, 0, 255)) // Ustawia kolor ikonki
                .setSubText("Przypomnienie") // Ustawia tekst obok ikonki
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
}
