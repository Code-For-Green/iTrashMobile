package com.github.codeforgreen.itrash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class LoadingScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        ImageView imageView = findViewById(R.id.gif);

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        String token = preferences.getString("Token", "");
        long expiration = preferences.getLong("Expiration", 0);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!token.isEmpty() && expiration != 0 && System.currentTimeMillis() < expiration) {
                    startActivity(new Intent(LoadingScreenActivity.this, MainActivity.class));
                }else {
                    startActivity(new Intent(LoadingScreenActivity.this, LoginActivity.class));
                }
                LoadingScreenActivity.this.finish();
            }
        }, 4000);

        Glide.with(this)
                .load(R.raw.loadnig)
                .into(imageView);
    }
}