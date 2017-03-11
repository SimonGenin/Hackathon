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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AllPostsMapActiviy extends FragmentActivity implements OnMapReadyCallback {

    private final String TAG = this.getClass().getName();

    private GoogleMap mMap;
    private List<Post> posts;

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

                try {

                    for (int i = 0 ; i < response.length() ; i++) {

                        Post post = new Post();
                        JSONObject object = response.getJSONObject(i);

                        post.setId(object.getInt("id"));
                        post.setActive(object.getBoolean("active"));
                        post.setOwner(object.getBoolean("owner"));
                        post.setPrice((float) object.getDouble("price"));
                        post.setLatitude(object.getInt("latitude"));
                        post.setLongitude(object.getInt("longitude"));
                        post.setAddress(object.getString("address"));
                        post.setHasPlugType1(object.getBoolean("type1"));
                        post.setHasPlugType2(object.getBoolean("type2"));
                        post.setHasPlugType3(object.getBoolean("type3"));
                        post.setHasPlugType4(object.getBoolean("type4"));

                        posts.add(post);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


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
        for (Post p : posts) {

            mMap.addMarker(p.createMarkerOptions());

        }

        // Camera is set above Mons by default
        LatLng mons = new LatLng(50.4541, 3.9523);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mons));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );

    }
}
