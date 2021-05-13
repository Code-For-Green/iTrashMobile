package com.github.codeforgreen.itrash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.github.codeforgreen.itrash.tasks.LoginTask;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button clickButton = (Button) findViewById(R.id.login_button);
        clickButton.setOnClickListener(v -> new LoginTask(new JSONObject()).execute(null, null));
    }
}
