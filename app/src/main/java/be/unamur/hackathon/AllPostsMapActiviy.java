package be.unamur.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllPostsMapActiviy extends FragmentActivity implements OnMapReadyCallback {

    private final String TAG = this.getClass().getName();

    private GoogleMap mMap;
    private List<Post> posts;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_posts_map_activiy);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        posts = new ArrayList<>();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(APIConfig.all_posts_url, null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {

                    String status = response.getString("status");

                    if (status.equals("ko")) {
                        Toast.makeText(AllPostsMapActiviy.this, "Erreur pendant la réception des données", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    JSONArray allPosts = response.getJSONArray("posts");

                    for (int i = 0 ; i < allPosts.length() ; i++) {

                        Post post = new Post();
                        JSONObject object = allPosts.getJSONObject(i);

                        post.setId(object.getInt("id"));
                        post.setActive(object.getBoolean("active"));
                        post.setPrice((float) object.getDouble("price"));
                        post.setLatitude(object.getDouble("latitude"));
                        post.setLongitude(object.getDouble("longitude"));
                        post.setAddress(object.getString("address"));
                        post.setHasPlugType1(object.getBoolean("type1"));
                        post.setHasPlugType2(object.getBoolean("type2"));
                        post.setHasPlugType3(object.getBoolean("type3"));
                        post.setHasPlugType4(object.getBoolean("type4"));

                        posts.add(post);
                    }

                    for (Post p : posts) {

                        mMap.addMarker(p.createMarkerOptions());


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

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                for (Post p : posts) {

                    if (p.getLatitude() == marker.getPosition().latitude && p.getLongitude() == marker.getPosition().longitude) {

                        Intent intent = new Intent(AllPostsMapActiviy.this, DetailsPostActivity.class);
                        intent.putExtra("post", p);
                        startActivity(intent);
                    }

                }

                return false;
            }
        });

        // Camera is set above Mons by default
        LatLng mons = new LatLng(50.4541, 3.9523);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mons));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );

    }
}
