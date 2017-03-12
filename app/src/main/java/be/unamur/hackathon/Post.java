package be.unamur.hackathon;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;

/**
 * Created by simon on 11-03-17.
 */

public class Post implements Serializable {

    private int id;

    private double latitude;
    private double longitude;
    private float price;
    private boolean isActive;
    private boolean hasPlugType1;
    private boolean hasPlugType2;
    private boolean hasPlugType3;
    private boolean hasPlugType4;
    private String address;


    public MarkerOptions createMarkerOptions() {

        MarkerOptions options = new MarkerOptions();
        LatLng place = new LatLng(latitude, longitude);
        options.position(place);
        options.title(address);
        return options;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isHasPlugType1() {
        return hasPlugType1;
    }

    public void setHasPlugType1(boolean hasPlugType1) {
        this.hasPlugType1 = hasPlugType1;
    }

    public boolean isHasPlugType2() {
        return hasPlugType2;
    }

    public void setHasPlugType2(boolean hasPlugType2) {
        this.hasPlugType2 = hasPlugType2;
    }

    public boolean isHasPlugType3() {
        return hasPlugType3;
    }

    public void setHasPlugType3(boolean hasPlugType3) {
        this.hasPlugType3 = hasPlugType3;
    }

    public boolean isHasPlugType4() {
        return hasPlugType4;
    }

    public void setHasPlugType4(boolean hasPlugType4) {
        this.hasPlugType4 = hasPlugType4;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
