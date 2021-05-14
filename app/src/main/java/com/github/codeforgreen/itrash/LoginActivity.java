package com.github.codeforgreen.itrash;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.codeforgreen.itrash.tasks.LoginTask;

public class LoginActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        TextView register = findViewById(R.id.login_register);
        register.setMovementMethod(LinkMovementMethod.getInstance());
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
