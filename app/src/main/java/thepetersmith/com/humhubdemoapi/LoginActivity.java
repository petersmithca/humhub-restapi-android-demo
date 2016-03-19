package thepetersmith.com.humhubdemoapi;

import android.support.v4.app.Fragment;


public class LoginActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        // TODO Auto-generated method stub
        return new LoginFragment();
    }
}