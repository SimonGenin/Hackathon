package be.unamur.hackathon;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;

public class AllPostsMapActiviy extends FragmentActivity implements OnMapReadyCallback {

    private final String TAG = this.getClass().getName();

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_posts_map_activiy);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        JsonArrayRequest jsonRequest = new JsonArrayRequest(APIConfig.all_posts_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "Successfully reached and pull from server : " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AllPostsMapActiviy.this, "Une erreur de connexion est survenue.", Toast.LENGTH_LONG).show();
            }
        });

        // Start the connection
        RequestManager.getInstance(this).addToRequestQueue(jsonRequest);
        RequestManager.getInstance(this).getRequestQueue().start();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // TODO work the data

        // Mons
        LatLng mons = new LatLng(50.4541, 3.9523);
        mMap.addMarker(new MarkerOptions().position(mons).title("Mons RPZ"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mons));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );

    }
}
