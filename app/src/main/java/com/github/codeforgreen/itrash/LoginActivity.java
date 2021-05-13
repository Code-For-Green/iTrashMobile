package com.github.codeforgreen.itrash;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.codeforgreen.itrash.tasks.LoginTask;
import com.github.codeforgreen.itrash.util.Hash;

import org.json.JSONException;

import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        TextView register = findViewById(R.id.login_register);
        register.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void login_button(View view) throws JSONException, NoSuchAlgorithmException {
        EditText email = findViewById(R.id.login_email);
        EditText password = findViewById(R.id.login_password);

        new LoginTask(email.getText().toString(), Hash.hash(password.getText().toString())).execute(null, null);

        //Wersja robocza

        if (email.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void register_button(View view){
        Uri uri = Uri.parse("https://www.google.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
