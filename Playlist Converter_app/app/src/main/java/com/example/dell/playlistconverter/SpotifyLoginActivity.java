package com.example.dell.playlistconverter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

public class SpotifyLoginActivity extends Activity implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback {

    private static final String CLIENT_ID = "6fa3a252768441b9bf9b86d75830b016";
    private static final String REDIRECT_URI = "newtest://callback";

    final int REQUEST_CODE = 1337;

    static Player mPlayer;
    static String ACCESS_TOKEN;
    static String clientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify_login);

        // Initialize variablies related to authentication using CLIENT_ID and TOKEN
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        // Setting the scope for the data to read in
        builder.setScopes(new String[]{"user-read-private", "user-read-email"});
        // Show dialog
        builder.setShowDialog(true);
        // Build the request with the set scope above
        AuthenticationRequest request = builder.build();
        // Show dialog
        builder.setShowDialog(true);

        // Opens Spotify login page
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
        // set the clientID with the logged in user
        clientID = request.getClientId();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);

                ACCESS_TOKEN = response.getAccessToken();
                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        mPlayer = spotifyPlayer;
                        mPlayer.addConnectionStateCallback(SpotifyLoginActivity.this);
                        mPlayer.addNotificationCallback(SpotifyLoginActivity.this);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
        }
    }

    // method that gets called when the mPlayer gets destroyed
    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    // method that gets called when a playback event is received
    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d("MainActivity", "Playback event received: " + playerEvent.name());
        switch (playerEvent) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    // method that gets called when playback error is received
    @Override
    public void onPlaybackError(Error error) {
        Log.d("MainActivity", "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    // method that gets called when the user successfully logs in
    @Override
    public void onLoggedIn() {
        Log.d("YES", "User logged in");
        Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_SHORT).show();

        // play a track to user has logged in (for testing purpose)
        mPlayer.playUri(null, "spotify:track:2TpxZ7JUBn3uw46aR7qd6V", 0, 0);

        // when the user logs in, direct to SpotifySetfup activity
        Intent intent = new Intent(SpotifyLoginActivity.this, SpotifySetupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

        finish();
    }

    // method that gets called when the user logs out
    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
        Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();

        // go back to mainpage
        finish();
    }

    // method that gets called when the login fails
    @Override
    public void onLoginFailed(Error error) {

    }

    // method that gets called when the login  fails
    public void onLoginFailed(int i) {
        Log.d("MainActivity", "Login failed");
    }

    // method that gets called when the temporary error has occured
    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");
    }

    // method that gets called when connection message is received
    @Override
    public void onConnectionMessage(String message) {
        Log.d("MainActivity", "Received connection message: " + message);
    }
}