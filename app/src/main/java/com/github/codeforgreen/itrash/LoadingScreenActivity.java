package com.github.codeforgreen.itrash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.codeforgreen.itrash.api.calendar.CollectionNotification;
import com.github.codeforgreen.itrash.api.calendar.TrashType;

import java.time.LocalDate;

public class LoadingScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        this.createNotificationChannel();
        ImageView imageView = findViewById(R.id.gif);

        SharedPreferences preferences = getSharedPreferences("iTrash", MODE_PRIVATE);
        String token = preferences.getString("Token", "");
        long expiration = preferences.getLong("Expiration", 0);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, new CollectionNotification(TrashType.PAPIER, LocalDate.now()).createNotification(this, LoadingScreenActivity.class));

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
}