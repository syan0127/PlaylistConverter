package com.example.dell.playlistconverter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import kaaes.spotify.webapi.android.models.UserPrivate;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SpotifyConvertActivity extends AppCompatActivity {

    // initialize fields
    TextView playlistChoose;
    private SpotifyService mSpotifyService;
    String userId = "";
    Spinner playlistSpinner;
    List<String> playlistNames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify_convert);

        playlistChoose = (TextView) findViewById(R.id.choose);
        playlistSpinner = findViewById(R.id.spinner_playlist);

        // list that stores the name of the playlists
        playlistNames = new ArrayList<String>();

        // add "Select Playlist" to the first item on spinner
        playlistNames.add("Select Playlist");

        getPlayList();

        // Spinner setup
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, playlistNames);
        playlistSpinner.setAdapter(dataAdapter);
        playlistSpinner.setAdapter(dataAdapter);
    }


    // get the user's playlists
    public void getPlayList() {

        // example code provided by the Spotify
        SpotifyApi api = new SpotifyApi();
        api.setAccessToken(SpotifyLoginActivity.ACCESS_TOKEN);
        SpotifyService spotify = api.getService();

        // gets user id of the currently logged in user
        spotify.getMe(new Callback<UserPrivate>() {
            @Override
            public void success(UserPrivate userPrivate, retrofit.client.Response response) {
                userId = userPrivate.id;
            }

            //handling failures
            @Override
            public void failure(RetrofitError error) {
                Log.d("user id failure: ", error.toString());
            }
        });

        // initialize variables needed to call the getMyPlaylists method
        Map<String, Object> options = new HashMap<>();
        final List<PlaylistSimple> userItems = new ArrayList<>();

        // stores users's playlists in the playlistnames list
        spotify.getMyPlaylists(options, new SpotifyCallback<Pager<PlaylistSimple>>() {
            @Override
            public void success(Pager<PlaylistSimple> playlistPager, Response response) {
                List<PlaylistSimple> items = playlistPager.items;

                // add PlaylistSimple objects (holds data of user's playlist) to items list
                for (PlaylistSimple item : items) {
                    if (item.owner.id.equals(userId) || item.collaborative) {
                        userItems.add(item);
                    }
                }

                // add the names of playlists to the playlistnames list
                for (PlaylistSimple ps : userItems) {
                    playlistNames.add(ps.name);
                }
            }

            // handling failure
            @Override
            public void failure(SpotifyError error) {
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
            case R.id.item_mainpage:
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