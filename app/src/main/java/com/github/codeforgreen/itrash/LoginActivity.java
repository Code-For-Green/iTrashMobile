package com.github.codeforgreen.itrash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.codeforgreen.itrash.tasks.LoginTask;

import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        TextView register = findViewById(R.id.login_register);
        register.setMovementMethod(LinkMovementMethod.getInstance());

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        String token = preferences.getString("Token", "");
        long expiration = preferences.getLong("Expiration", 0);
        Log.i("Token", token);
        Log.i("CurrentDate", String.valueOf(System.currentTimeMillis()));
        Log.i("Expiration", String.valueOf(expiration));
        if (!token.isEmpty() && expiration != 0 && System.currentTimeMillis() < expiration) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void login_button(View view) {
        EditText email = findViewById(R.id.login_email);
        EditText password = findViewById(R.id.login_password);
        try {
            new LoginTask(this, email.getText().toString(), password.getText().toString()).execute(null, null);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void register_button(View view) {
        Uri uri = Uri.parse("https://www.google.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
