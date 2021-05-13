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
import com.github.codeforgreen.itrash.util.Constants;
import com.github.codeforgreen.itrash.util.Hash;

import org.json.JSONObject;

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
            JSONObject json = new LoginTask(email.getText().toString(), Hash.hash(password.getText().toString())).execute(null, null).get();
            Constants.TOKEN = json.getString("Token");
            Constants.EXPIRATION = json.getInt("Expiration");
            System.out.println(Constants.TOKEN);
            System.out.println(Constants.EXPIRATION);

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void register_button(View view){
        Uri uri = Uri.parse("https://www.google.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
