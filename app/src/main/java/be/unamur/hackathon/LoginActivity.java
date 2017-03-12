package be.unamur.hackathon;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getName();

    private HackathonApplication application;

    // UI references.
    private EditText mPseudoView;
    private EditText mPasswordView;
    private View mProgressView;

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        application = (HackathonApplication) getApplication();

        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("is_saved", false)){
            User user = new User();
            user.load(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
            application.connectUser(user);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        title = (TextView) findViewById(R.id.app_name_tv);
        Typeface titleTypeFace = Typeface.createFromAsset(getAssets(), "fonts/OleoScript-Regular.ttf");
        title.setTypeface(titleTypeFace);

        // Set up the login form.
        mPseudoView = (EditText) findViewById(R.id.pseudo_tv);
        mPasswordView = (EditText) findViewById(R.id.password_tv);


        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mProgressView = findViewById(R.id.login_progress);

    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mPseudoView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mPseudoView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            preformConnection(email, password);
        }
    }

    private void preformConnection(String pseudo, String password) {

        Map parameters = new HashMap();
        parameters.put("username", pseudo);
        parameters.put("password", password);

        PostRequest postRequest = new PostRequest(APIConfig.login_url, parameters, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Server responses : " + response);

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String status = jsonResponse.getString("status");

                    if (status.equals("ok"))
                    {
                        User user = new User();
                        user.setAddress(jsonResponse.getString("address"));
                        user.setFirstname(jsonResponse.getString("first_name"));
                        user.setEmail(jsonResponse.getString("email"));
                        user.setName(jsonResponse.getString("last_name"));
                        user.setPseudo(jsonResponse.getString("username"));
                        user.setOwner(jsonResponse.getBoolean("owner"));
                        user.setId(jsonResponse.getInt("id"));

                        application.connectUser(user);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                    else
                    {
                        mPasswordView.setError(getString(R.string.error_incorrect_password));
                        mPasswordView.requestFocus();
                    }

                } catch (JSONException e) {
                    Toast.makeText(LoginActivity.this, "Une erreur est survenue lors de l'authentification.", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Une erreur de connexion est survenue", Toast.LENGTH_LONG).show();
            }
        });

        // We start the post request
        RequestManager.getInstance(LoginActivity.this).addToRequestQueue(postRequest);
        RequestManager.getInstance(LoginActivity.this).getRequestQueue().start();

    }


}

