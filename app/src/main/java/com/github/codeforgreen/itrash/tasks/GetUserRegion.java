package com.github.codeforgreen.itrash.tasks;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.codeforgreen.itrash.R;
import com.github.codeforgreen.itrash.api.request.MakePost;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public class GetUserRegion extends MakePost {

    public GetUserRegion(AppCompatActivity activity, String token) throws JSONException {
        super(activity, "getUserRegion", new JSONObject()
                .put("Token", token));
    }

    @Override
    public void onJson(JSONObject json) {
        TextView calendarTopText = this.activity.findViewById(R.id.calendar_top_text);
        try {
            calendarTopText.setText(String.format(this.activity.getResources().getString(R.string.calendar_topText)+ " &s", json.getString("region")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(HttpURLConnection connection) {
        this.activity.runOnUiThread(() -> {

            // Tymczasowe, pamiętać wywalić
             TextView calendarTopText = this.activity.findViewById(R.id.calendar_top_text);
             calendarTopText.setText(String.format(this.activity.getResources().getString(R.string.calendar_topText) + " %s", "WRZEŚNIA REGION 1"));
            //

            try {
                Toast.makeText(this.activity.getApplicationContext(), connection.getResponseMessage(), Toast.LENGTH_LONG).show();
            } catch (Throwable t) {
                this.onError(t);
            }
        });
    }

    @Override
    public void onError(Throwable t) {
        this.activity.runOnUiThread(() -> Toast.makeText(this.activity.getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show());

    }
}
