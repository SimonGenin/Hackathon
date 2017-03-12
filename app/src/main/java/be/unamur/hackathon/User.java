package be.unamur.hackathon;

import android.content.SharedPreferences;

/**
 * Created by simon on 11-03-17.
 */

public class User {

    private String name;
    private String firstname;
    private String pseudo;
    private boolean owner; // is the user owner of a post ?
    private String email;
    private String address;
    private int id;

    public void save(SharedPreferences preferences) {
        preferences.edit().putString("name", name).apply();
        preferences.edit().putString("pseudo", pseudo).apply();
        preferences.edit().putString("firstname", firstname).apply();
        preferences.edit().putString("email", email).apply();
        preferences.edit().putString("address", address).apply();
        preferences.edit().putBoolean("owner", owner).apply();
        preferences.edit().putInt("id", id).apply();
    }

    public void load(SharedPreferences preferences) {
        this.name = preferences.getString("name", "unknown");
        this.firstname = preferences.getString("firstname", "unknown");
        this.pseudo = preferences.getString("pseudo", "unknown");
        this.owner = preferences.getBoolean("owner", false);
        this.email = preferences.getString("email", "unknown");
        this.address = preferences.getString("address", "unknown");
        this.id = preferences.getInt("id", -1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }


}
