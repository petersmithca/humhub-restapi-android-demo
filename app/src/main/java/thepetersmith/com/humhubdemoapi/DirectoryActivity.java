package thepetersmith.com.humhubdemoapi;

import android.support.v4.app.Fragment;

/**
 * Created by smith on 2016-03-13.
 */
public class DirectoryActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        // TODO Auto-generated method stub
        return new DirectoryFragment();
    }
}
