package com.example.dell.playlistconverter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class HomePageActivity extends AppCompatActivity {

    // VIews
    Button button_convert;
    Button button_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Initialize views
        button_convert = findViewById(R.id.button_convert);
        button_share = findViewById(R.id.button_share);

        // onClickListener
        button_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                platfromSelect();
            }
        });
        button_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Share function not implemented yet (TODO)
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

    // Button button_convert(onClickListener) -  go to platformSelect page
    public void platfromSelect() {
        Intent intent = new Intent(this, PlatformSelectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
