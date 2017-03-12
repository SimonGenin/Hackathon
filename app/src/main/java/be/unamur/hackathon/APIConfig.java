package be.unamur.hackathon;

/**
 * Created by simon on 10-03-17.
 */
public class APIConfig {

    public static final String base_url = "https://simple-toolchain-1489223200903.mybluemix.net";
    // public static final String base_url = "127.0.0.1";
    public static final String api_url = base_url + "/api";

    public static final String login_url = api_url + "/login";
    public static final String all_posts_url = api_url + "/posts";

    public static final String my_post = api_url + "/getMyPost";
    public static final String post_price = api_url + "/setPostPrice";
    public static final String post_active = api_url + "/setPostActive";

}
