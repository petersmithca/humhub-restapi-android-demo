package thepetersmith.com.services;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

import thepetersmith.com.humhubdemoapi.R;


public class FeedStore {
    private static final Long FIVE_MINUTE_CACHE = 300000L;
    private static final Integer TIMEOUT = 3000;
    private static final String SERVICES_API_URL = "http://10.0.2.2/api/";
    private static final String API_KEY = "B5BVwiLmV6A61k1LykyrIW9H";
    private final Context mContext;
    private static FeedStore sFeedStore;
    private static String networkError;
    private static String apiError;
    private static String generalError;
    private final Object mLock = new Object();

    private FeedStore(Context appContext) {
        mContext = appContext;
    }

    public static FeedStore get(Context c) {
        if (sFeedStore == null) {
            sFeedStore = new FeedStore(c.getApplicationContext());
        }

        generalError = c.getResources().getString(R.string.error_general);
        apiError = c.getResources().getString(R.string.error_api);
        networkError = c.getResources().getString(R.string.error_no_internet);
        return sFeedStore;
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private static String getResponseText(InputStream inStream) {
        // very nice trick from
        // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
        return new Scanner(inStream).useDelimiter("\\A").next();
    }

    public JSONObject login(String username, String password) throws Exception {
        if (!isOnline()) {
            throw new ApiException(networkError);
        }
        JSONObject obj = null;
        String result = null;
        URL url = new URL(SERVICES_API_URL + "user/login/" + username + "/" + password + "?access_token=" + API_KEY);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            urlConnection.setRequestMethod("GET");
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                throw new AuthenticationException("Unauthorized");
            } else if (statusCode != HttpURLConnection.HTTP_OK) {
                throw new AuthenticationException("" + statusCode);
            }
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            obj = new  JSONObject(getResponseText(in));
        }catch (Exception e) {
            throw e;
        } finally {
            urlConnection.disconnect();
        }
        return obj;
    }

    public Users users() throws Exception {
        if (!isOnline()) {
            throw new ApiException(networkError);
        }
        JSONArray items = new JSONArray();
        String result = null;
        Users users = new Users();
        URL url = new URL(SERVICES_API_URL + "user?eager=true&access_token=" + API_KEY);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {

            urlConnection.setRequestMethod("GET");
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                throw new AuthenticationException("Unauthorized");
            } else if (statusCode != HttpURLConnection.HTTP_OK) {
                throw new AuthenticationException("" + statusCode);
            }
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            items = new  JSONArray(getResponseText(in));
            for (int i = 0; i < items.length(); i++) {
                JSONObject child = items.getJSONObject(i);
                JSONObject profile = child.getJSONObject("profile");
                User item = new User();
                item.setId(child.getInt("id"));
                item.setUsername(child.getString("username"));
                item.setAvatar(child.getString("guid"));
                item.setfName(profile.getString("firstname"));
                item.setlName(profile.getString("lastname"));
                users.getItems().add(item);
            }
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            Log.d("g", e.getMessage());
            throw new Exception(generalError);
        }
        return users;
    }

    public Posts posts() throws Exception {
        if (!isOnline()) {
            throw new ApiException(networkError);
        }
        JSONArray items = new JSONArray();
        String result = null;
        Posts posts = new Posts();
        URL url = new URL(SERVICES_API_URL + "post?eager=true&access_token=" + API_KEY);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {

            urlConnection.setRequestMethod("GET");
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                throw new AuthenticationException("Unauthorized");
            } else if (statusCode != HttpURLConnection.HTTP_OK) {
                throw new AuthenticationException("" + statusCode);
            }
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            items = new  JSONArray(getResponseText(in));
            for (int i = 0; i < items.length(); i++) {
                JSONObject child = items.getJSONObject(i);
                JSONObject user = child.getJSONObject("user");
                JSONArray comments = child.getJSONArray("comments");
                Post item = new Post();
                item.setUserName(user.getString("username"));
                item.setAvatar(user.getString("guid"));
                item.setMessage(child.getString("message"));
                item.setCommentCount(comments.length());
                posts.getItems().add(item);
            }
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            Log.d("g", e.getMessage());
            throw new Exception(generalError);
        }
        return posts;
    }

}