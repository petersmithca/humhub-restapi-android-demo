package thepetersmith.com.humhubdemoapi;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import thepetersmith.com.services.FeedStore;
import thepetersmith.com.services.Users;

/**
 * Created by smith on 2016-03-13.
 */
public class DirectoryFragment extends ListFragment implements
        UsersFinishedListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new User(DirectoryFragment.this, getActivity()
                .getApplicationContext()).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.base_container, container, false);
    }

    private class User extends
            AsyncTask<Users, Void, Users> {
        UsersFinishedListener mFinishedListener;
        Context mContext;

        User(UsersFinishedListener finishedListener, Context context) {
            mFinishedListener = finishedListener;
            mContext = context;
        }

        @Override
        protected Users doInBackground(Users... params) {
            Users users = new Users();
            try {
                FeedStore store = FeedStore.get(mContext);
                users = store.users();
            } catch (Exception e) {
                users.setMessage(e.getMessage());
            }
            return users;

        }

        @Override
        protected void onPostExecute(Users result) {
            if (result.getMessage().equals("")) {
                mFinishedListener.onUsersFinished(result);
            } else {
                Log.d("Login", result.getMessage());
                Toast.makeText(getActivity(), result.getMessage(),
                        Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }

    }

    @Override
    public void onUsersFinished(Users users) {
        UsersAdapter adapter = new UsersAdapter(getActivity(),
                R.id.adapter_item, users.getItems());
        View header = getActivity().getLayoutInflater().inflate(
                R.layout.list_header, null);
        ListView lv = getListView();
        lv.addHeaderView(header, null, false);
        this.setListAdapter(adapter);
    }

}
