package be.unamur.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsPostActivity extends AppCompatActivity {

    ImageView plugOneIV;
    ImageView plugTwoIV;
    ImageView plugThreeIV;
    ImageView plugFourIV;

    TextView postTitleTV;
    TextView addressTV;
    TextView townTV;

    TextView availableKWTV;
    TextView priceTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_post);

        plugOneIV = (ImageView) findViewById(R.id.plug_1_iv);
        plugTwoIV = (ImageView) findViewById(R.id.plug_2_iv);
        plugThreeIV = (ImageView) findViewById(R.id.plug_3_iv);
        plugFourIV = (ImageView) findViewById(R.id.plug_4_iv);

        postTitleTV = (TextView) findViewById(R.id.post_title_tv);
        addressTV = (TextView) findViewById(R.id.address_tv);
        townTV = (TextView) findViewById(R.id.town_tv);

        availableKWTV = (TextView) findViewById(R.id.kw_available_tv);
        priceTV = (TextView) findViewById(R.id.price_available_tv);

        Intent intent = getIntent();
        Post post = (Post) intent.getSerializableExtra("post");

        postTitleTV.setText("Borne " + post.getId());
        addressTV.setText(post.getAddress());


    }
}
