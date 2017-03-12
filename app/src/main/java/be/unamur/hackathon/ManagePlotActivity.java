package be.unamur.hackathon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ManagePlotActivity extends AppCompatActivity {

    private final static String TAG = ManagePlotActivity.class.getName();

    private Switch activateSwitch;
    private Button startButton;
    private TextView overKWTV;
    private EditText priceET;

    private HackathonApplication application;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_plot);

        application = (HackathonApplication) getApplication();
        currentUser = application.getCurrentConnectedUser();

        JsonObjectRequest getRequest = new JsonObjectRequest(APIConfig.my_post + "?id=" + currentUser.getId(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String status = response.getString("status");

                    if (status.equals("ko")) {
                        finish();
                        Toast.makeText(ManagePlotActivity.this, "Erreur lors de la réception", Toast.LENGTH_SHORT).show();
                    }

                    JSONObject data = response.getJSONObject("post");

                    boolean isActivate = data.getBoolean("active");
                    float price = (float) data.getDouble("price");

                    if (isActivate != activateSwitch.isActivated()) {
                        activateSwitch.toggle();
                    }

                    priceET.setText(String.valueOf(price));

                } catch (JSONException e) {
                    Toast.makeText(ManagePlotActivity.this, "Erreur lors de la réception", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ManagePlotActivity.this, "Erreur lors de la connexion", Toast.LENGTH_SHORT).show();
            }
        });

        RequestManager.getInstance(ManagePlotActivity.this).addToRequestQueue(getRequest);
        RequestManager.getInstance(ManagePlotActivity.this).getRequestQueue().start();


        activateSwitch = (Switch) findViewById(R.id.activate_switch);
        startButton = (Button) findViewById(R.id.start_button);
        overKWTV = (TextView) findViewById(R.id.over_kw_tv);
        priceET = (EditText) findViewById(R.id.kw_price_et);

        activateSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> params = new HashMap<String, String>();
                params.put("active", String.valueOf(activateSwitch.isActivated()));
                PostRequest request = new PostRequest(APIConfig.post_active, params, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Success");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error");
                    }
                });

                RequestManager.getInstance(ManagePlotActivity.this).addToRequestQueue(request);
                RequestManager.getInstance(ManagePlotActivity.this).getRequestQueue().start();
            }
        });

        overKWTV.setVisibility(View.INVISIBLE);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overKWTV.setVisibility(View.VISIBLE);
            }
        });

        priceET.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (!hasFocus) {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("price", String.valueOf(priceET.getText()));
                    PostRequest request = new PostRequest(APIConfig.post_price, params, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "Success");
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "Error");
                        }
                    });

                    RequestManager.getInstance(ManagePlotActivity.this).addToRequestQueue(request);
                    RequestManager.getInstance(ManagePlotActivity.this).getRequestQueue().start();

                }
            }
        });

    }
}
