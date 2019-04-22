package com.example.dell.playlistconverter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class PlatformSetupActivity extends AppCompatActivity {

    // Views
    Button button_spotify;
    Button button_youtube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platform_setup);

        // Initialize views
        button_spotify = findViewById(R.id.button_spotify);
        button_youtube = findViewById(R.id.button_youtube);

        // onClickListener
        button_spotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSetupPage_spotify();
            }
        });
        button_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSetupPage_youtube();
            }
        });

    }

    //// Below is the code for the menu option in the top-right side of the app ////
    // Create the option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }

    // Handle what each labels does in clicked in the option menu
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

    // If the setup menu is clicked go to the setup menu page
    public void setup() {
        Intent intent = new Intent(this, PlatformSetupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    // Logout of the whole app
    public void logout() {
        Intent intent = new Intent(this, LoginPageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    // go to mainpage
    public void mainpage() {
        Intent intent = new Intent(this, HomePageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    // Button onClickListener - Button button_spotify
    public void openSetupPage_spotify() {
        Intent intent = new Intent(this, SpotifyLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    // Button onClickListener - Button button_youtube
    public void openSetupPage_youtube() {
        Intent intent = new Intent(this, YoutubeLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }


}
