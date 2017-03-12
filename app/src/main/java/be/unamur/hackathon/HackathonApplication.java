package be.unamur.hackathon;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by simon on 11-03-17.
 */

public class HackathonApplication extends Application {

    private User currentConnectedUser;

    public void connectUser(User user) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        currentConnectedUser = user;
        user.save(preferences);
        preferences.edit().putBoolean("is_saved", true).apply();
    }

    public void disconnectUser(MainActivity mainActivity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        currentConnectedUser = null;
        preferences.edit().putBoolean("is_saved", false).apply();
        Intent intent = new Intent(mainActivity, LoginActivity.class);
        startActivity(intent);
    }

    public User getCurrentConnectedUser() {
        return currentConnectedUser;
    }
}
