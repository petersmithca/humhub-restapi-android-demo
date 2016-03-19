package thepetersmith.com.humhubdemoapi;

import android.support.v4.app.Fragment;

/**
 * Created by smith on 2016-03-19.
 */
public class PostsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        // TODO Auto-generated method stub
        return new PostsFragment();
    }
}
