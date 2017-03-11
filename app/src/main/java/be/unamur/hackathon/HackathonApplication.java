package be.unamur.hackathon;

import android.app.Application;
import android.content.Intent;

/**
 * Created by simon on 11-03-17.
 */

public class HackathonApplication extends Application {

    private User currentConnectedUser;

    public HackathonApplication() {



    }

    public void connectUser(User user) {
        currentConnectedUser = user;
    }

    public void disconnectUser(MainActivity mainActivity) {

        currentConnectedUser = null;
        Intent intent = new Intent(mainActivity, LoginActivity.class);
        startActivity(intent);

    }
}
