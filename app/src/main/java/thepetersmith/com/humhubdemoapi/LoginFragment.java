package thepetersmith.com.humhubdemoapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.URLEncoder;

import thepetersmith.com.services.FeedStore;
import thepetersmith.com.humhubdemoapi.MainMenuActivity;

/**
 * Created by smith on 2016-03-11.
 */
public class LoginFragment extends Fragment implements LoginFinishedListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            ActionBar bar = ((ActionBarActivity) getActivity())
                    .getSupportActionBar();
            bar.hide();
        }
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());

        View v = inflater.inflate(R.layout.login_layout, parent, false);
        Button btnLogin = (Button) v.findViewById(R.id.login_login);
        CheckBox chkRemember = (CheckBox) v.findViewById(R.id.login_remember);
        EditText txtUsername = (EditText) v.findViewById(R.id.login_username);
        EditText txtPassword = (EditText) v.findViewById(R.id.login_password);
        if (!prefs.getString("username", "").equals("")) {
            chkRemember.setChecked(true);
        }
        txtUsername.setText(prefs.getString("username", ""));
        txtPassword.setText(prefs.getString("password", ""));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    final EditText txtUsername = (EditText) getActivity()
                            .findViewById(R.id.login_username);
                    final EditText txtPassword = (EditText) getActivity()
                            .findViewById(R.id.login_password);
                    final CheckBox chkRemember = (CheckBox) getActivity()
                            .findViewById(R.id.login_remember);
                    SharedPreferences prefs = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    if (chkRemember.isChecked()) {
                        SharedPreferences.Editor ed = prefs.edit();
                        ed.putString("username", txtUsername.getText()
                                .toString());
                        ed.putString("password", txtPassword.getText()
                                .toString());
                        ed.commit();
                    } else {
                        SharedPreferences.Editor ed = prefs.edit();
                        ed.putString("username", "");
                        ed.putString("password", "");
                        ed.commit();
                    }

                    new Login(LoginFragment.this, getActivity()
                            .getApplicationContext(), txtUsername.getText()
                            .toString(), txtPassword.getText().toString())
                            .execute();

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        return v;

    }

    @Override
    public void onLoginFinished(JSONObject result) {
        try {
            if (result != null) {
                SharedPreferences prefs = PreferenceManager
                        .getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor ed = prefs.edit();
                ed.putInt("userId", result.getInt("id"));
                ed.commit();
                Intent i = new Intent(getActivity(), MainMenuActivity.class);
                startActivity(i);
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    class Login extends AsyncTask<JSONObject, Void, JSONObject> {
        LoginFinishedListener mFinishedListener;
        Exception exceptionToBeThrown;
        String mUsername;
        String mPassword;
        Context mContext;

        Login(LoginFinishedListener finishedListener, Context context,
              String username, String password) {
            mFinishedListener = finishedListener;
            mContext = context;
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected JSONObject doInBackground(JSONObject... params) {
            JSONObject account = new JSONObject();
            try {

                FeedStore store = FeedStore.get(mContext);
                account = store.login(mUsername, mPassword);
            } catch (Exception e) {
                exceptionToBeThrown = e;
            }
            return account;

        }

        @Override
        protected void onPostExecute(JSONObject result) {
            if (exceptionToBeThrown instanceof Exception) {
                Toast.makeText(mContext, exceptionToBeThrown.getMessage(),
                        Toast.LENGTH_SHORT).show();
                result = null;
            }
            mFinishedListener.onLoginFinished(result);
        }

    }
}