package thepetersmith.com.humhubdemoapi;

/**
 * Created by smith on 2016-03-11.
 */
import org.json.JSONObject;

public interface LoginFinishedListener {
    void onLoginFinished(JSONObject account);
}
