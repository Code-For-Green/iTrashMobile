package com.github.codeforgreen.itrash;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.codeforgreen.itrash.api.Preferences;
import com.github.codeforgreen.itrash.api.calendar.CollectionNotification;
import com.github.codeforgreen.itrash.api.calendar.TrashType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class LoadingScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        this.createNotificationChannel();
        ImageView imageView = findViewById(R.id.gif);

        String token = Preferences.getToken(this);
        long expiration = Preferences.getExpiration(this);

        // Stwórz powiadomienie 18 maja o 15:00 wg strefy czasowej w telefonie, napisze ono że 18 maja jest odbiór śmieci
        // Jeśli to zostanie wywołane po 18 maja po 15:00 to powiadomienie zostanie wyświetlone tak szybko jak to możliwe
        CollectionNotification.notifyOn(this, TrashType.MYCIE_BIO, LocalDateTime.of(2021, 5, 18, 15, 0));

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (!token.isEmpty() && expiration != 0 && System.currentTimeMillis() < expiration) {
                startActivity(new Intent(LoadingScreenActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(LoadingScreenActivity.this, LoginActivity.class));
            }
            LoadingScreenActivity.this.finish();
        }, 3500);

        Glide.with(this)
                .load(R.raw.loadnig)
                .into(imageView);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.app_name), getString(R.string.notification_harmonogram_name), importance);
            channel.setDescription(getString(R.string.notification_harmonogram_description));
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void setAlarm() {
        int id = Preferences.incrementLastNotificationId(this);

        Intent intent = new Intent(this, CollectionReceiver.class);
        intent.putExtra("type", TrashType.PAPIER.toString());
        intent.putExtra("date", LocalDate.now());
        intent.putExtra("id", id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        LocalDateTime dateTime = LocalDateTime.of(LocalDate.of(2021, 5, 17), LocalTime.of(15, 35));
        long time = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        System.out.println(dateTime);
        System.out.println(time);

        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }
}