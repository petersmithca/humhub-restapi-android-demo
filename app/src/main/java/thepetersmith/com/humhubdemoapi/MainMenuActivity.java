package thepetersmith.com.humhubdemoapi;

import android.support.v4.app.Fragment;
import thepetersmith.com.humhubdemoapi.MainMenuFragment;

public class MainMenuActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new MainMenuFragment();
    }
}