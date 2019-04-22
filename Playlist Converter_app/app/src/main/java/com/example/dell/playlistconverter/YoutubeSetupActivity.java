package com.example.dell.playlistconverter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class YoutubeSetupActivity extends AppCompatActivity {

    // button for loggin out
    Button youtube_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_setup);

        youtube_logout = findViewById(R.id.button_youtube_logout);

        youtube_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // creating the option button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }

    // assign activities for each menu in the option button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.item_setup:
                setup();
                return true;
            case R.id.item_logout:
                logout();
                return true;
            case R.id.item_mainpage :
                mainpage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // direct to PlatformSetup activity
    public void setup() {
        Intent intent = new Intent(this, PlatformSetupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    // direct to LoginPageActivity activity
    public void logout() {
        Intent intent = new Intent(this, LoginPageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    // direct to HomePageActivity activity
    public void mainpage() {
        Intent intent = new Intent(this, HomePageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}